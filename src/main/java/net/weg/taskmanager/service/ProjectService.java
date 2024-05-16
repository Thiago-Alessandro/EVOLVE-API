package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.*;
import net.weg.taskmanager.model.dto.converter.Converter;
import net.weg.taskmanager.model.dto.converter.get.GetProjectConverter;
import net.weg.taskmanager.model.dto.post.PostProjectDTO;
import net.weg.taskmanager.model.entity.*;
import net.weg.taskmanager.model.dto.get.GetProjectDTO;
import net.weg.taskmanager.model.property.Property;
import net.weg.taskmanager.repository.*;
import net.weg.taskmanager.security.model.entity.Role;
import net.weg.taskmanager.security.service.RoleService;
import net.weg.taskmanager.utils.ColorUtils;
import net.weg.taskmanager.utils.FileUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.management.InvalidAttributeValueException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.Collection;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    private final TaskService taskService;
    private final StatusService statusService;
    private final PropertyService propertyService;
    private final RoleService roleService;
    private final ProjectChatService projectChatService;
    private final UserProjectService userProjectService;
    private final HistoricService historicService;
    private final TeamNotificationService teamNotificationService;
    private final ChartService chartService;
    private final StatusRepository statusRepository;


    private final TeamRepository teamRepository;
    private final DashboardRepository dashboardRepository;
    //    private final ModelMapper modelMapper;
    private final CommentRepository commentRepository;

    private final UserProjectRepository userProjectRepository;

    private final Converter<GetProjectDTO, Project> converter = new GetProjectConverter();

    public GetProjectDTO findById(Long id) {
        Project project = findProjectById(id);
        project.setCharts(chartService.getChartsValues(project));
        return converter.convertOne(project);
    }

    
    public GetProjectDTO updateStatusList(Long projectId,Long userActionId, Status status){
        Project project = projectRepository.findById(projectId).get();
        if(project.getStatusList()!=null){
            for(Status statusFor : project.getStatusList()){
                if(Objects.equals(status.getId(), statusFor.getId())){
                    BeanUtils.copyProperties(status, statusFor);
                    return converter.convertOne(treatAndSave(project));
                }
            }
            project.getStatusList().add(status);
        } else {
            project.setStatusList(new ArrayList<>());
            project.getStatusList().add(status);
        }
        return converter.convertOne(treatAndSave(project));
    }

    public Collection<GetProjectDTO> findByUserId(Long userId) {
        Collection<UserProject> userProjects = userProjectService.findAllByUserId(userId);
        Collection<Project> projects = new HashSet<>();
        userProjects.forEach(userProject -> projects.add(findProjectById(userProject.getProjectId())));
        return converter.convertAll(projects);
    }

    public Collection<GetProjectDTO> findByTeamId(Long teamId) {
        Optional<Collection<Project>> optionalProjects = projectRepository.findAllByTeam_Id(teamId);
        if (optionalProjects.isEmpty()) throw new NoSuchElementException();
        return converter.convertAll(optionalProjects.get());
    }

//    public Collection<GetProjectDTO> findAll() {
//        Collection<Project> projects =  projectRepository.findAll();
//        return converter.convertAll(projects);
//    }

    public GetProjectDTO create(PostProjectDTO projectDTO) {
        Project project = new Project(projectDTO);
        System.out.println(projectDTO);
        project.setStatusList(statusRepository.saveAll(project.getStatusList()));
        project.setMembers(new ArrayList<>());
        Project projectSaved = projectRepository.save(project);

        setCreator(projectDTO.getCreator(), projectSaved);
        createProjectChat(projectSaved);
        setDefaultRole(projectSaved);

//        Collection<Status> listaNova = new HashSet<>();
//        if(projectDTO.getStatusList()!=null){
//            for (Status st: projectDTO.getStatusList()){
//                listaNova.add(new Status(st.getName(), st.getBackgroundColor(), st.getTextColor(), st.getEnabled()));
//            }
//        }
//        projectSaved.setStatusList(listaNova);

        return converter.convertOne(treatAndSave(projectSaved));
    }

    private void createProjectChat(Project project){
        ProjectChat chat = new ProjectChat();
//        chat.setProject(project);
        chat.setProject(project);
        ProjectChat createdChat = projectChatService.create(chat);
        project.setChat(createdChat);
    }

    private void setCreator(User user, Project project){
        Role role = roleService.getRoleByName("PROJECT_CREATOR");
        UserProject userProject = new UserProject(user.getId(), project.getId(), role);
        userProject.setManager(true);
        userProjectService.create(userProject);
    }


//    public GetProjectDTO update(PutProjectDTO projectDTO, Long actionUserId){
//
//        Project project = findProjectById(projectDTO.getId());
//        modelMapper.map(projectDTO, project);
//
//        updateProjectChat(project);
//        teamNotificationService.updateProjectInfoNotification(projectDTO.getId(), actionUserId);
//
//        return converter.convertOne(treatAndSave(project));
//    }

    @Transactional
    public void delete(Long id) {
        Project project = findProjectById(id);
        taskService.deleteAll(project.getTasks());
        statusService.deleteAll(project.getStatusList());
        dashboardRepository.deleteDashboardsByProject_Id(project.getId());
        projectRepository.deleteById(id);
    }



//    public GetProjectDTO update(PutProjectDTO projectDTO) {
//        Project project = findProjectById(projectDTO.getId());
//        modelMapper.map(projectDTO, project);
//        syncProjectInfos(project);
//        return transformToGetProjectDTO(treatAndSave(project));
//    }


    public Project findProjectById(Long projectId){
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if(optionalProject.isEmpty()) throw new NoSuchElementException();
        return optionalProject.get();
    }

    public GetProjectDTO patchName(Long projectId, String name) throws InvalidAttributeValueException {
        if(name == null) throw new InvalidAttributeValueException("Name on project cannot be null");
        Project project = findProjectById(projectId);
        project.setName(name);
             return converter.convertOne(treatAndSave(project));
    }

    public GetProjectDTO patchDescription(Long projectId, String description) throws InvalidAttributeValueException {
        if(description == null) throw new InvalidAttributeValueException("Description on project cannot be null");
        Project project = findProjectById(projectId);
        project.setDescription(description);
             return converter.convertOne(treatAndSave(project));
    }

    public GetProjectDTO patchImage(Long projectId, MultipartFile image) throws InvalidAttributeValueException {
        if(image == null) throw new InvalidAttributeValueException("Image on project cannot be null");
        Project project = findProjectById(projectId);
        project.setImage(FileUtils.buildFileFromMultipartFile(image));
             return converter.convertOne(treatAndSave(project));
    }

    public GetProjectDTO patchImageRemove(Long projectId){
        Project project = findProjectById(projectId);
        project.setImage(null);
             return converter.convertOne(treatAndSave(project));
    }

    public GetProjectDTO patchImageColor(Long projectId, String imageColor) throws InvalidAttributeValueException {
        if(imageColor == null || !ColorUtils.isHexColorValid(imageColor)) throw new InvalidAttributeValueException("Not valid format for imageColor on Project. Expecting a hexCode");
        Project project = findProjectById(projectId);
        project.setImageColor((imageColor));
             return converter.convertOne(treatAndSave(project));
    }

    public GetProjectDTO patchFinalDate(Long projectId, LocalDateTime finalDate) throws InvalidAttributeValueException {
        Project project = findProjectById(projectId);
        if(finalDate == null || finalDate.isBefore(project.getCreationDate())) throw new InvalidAttributeValueException("Image on project cannot be null");
        project.setFinalDate(finalDate);
        return converter.convertOne(treatAndSave(project));
    }

    public GetProjectDTO patchProperties(Long projectId, Collection<Property> properties) throws InvalidAttributeValueException {
        //falta converter oq é ecebido do front (acho que recebe uma record)
        if(properties == null) throw new InvalidAttributeValueException("Properties on project cannot be null");
        Project project = findProjectById(projectId);
        project.setProperties(properties);
        propertyService.createProjectPropertiesIfNotExists(project);
             return converter.convertOne(treatAndSave(project));
    }

    public GetProjectDTO patchStatusList(Long projectId,Long actionUserId, Collection<Status> statusList) throws InvalidAttributeValueException {
        if(statusList == null) throw new InvalidAttributeValueException("StatusList on project cannot be null");
        Project project = findProjectById(projectId);
        project.getStatusList().removeAll(project.getStatusList());
        statusRepository.saveAll(statusList);
        project.getStatusList().addAll(statusList);

//        project.setStatusList(statusList);
        return converter.convertOne(treatAndSave(project));
    }

    public GetProjectDTO patchStatusListRemove(Long projectId, Long statusId) {
        Project project = findProjectById(projectId);
        Status status = statusService.findStatusById(statusId);
        if(!project.getStatusList().remove(status)) throw new NoSuchElementException("The project specified does not contains any status with id: " + statusId + " in statusList");
        return converter.convertOne(treatAndSave(project));
    }

    public GetProjectDTO patchMembers(Long projectId, Collection<UserProject> members) throws InvalidAttributeValueException {
        if(members == null) throw new InvalidAttributeValueException("Members on project cannot be null");
        Project project = findProjectById(projectId);
        Collection<UserProject> filteredMembers = syncUserProjectTable(project, members);
        project.setMembers(filteredMembers);
        updateProjectChat(project);
        return converter.convertOne(treatAndSave(project));
    }

    public GetProjectDTO patchTasks(Long projectId,Collection<Task> tasks) throws InvalidAttributeValueException {
        if(tasks == null) throw new InvalidAttributeValueException("Tasks on project cannot be null");
        Project project = findProjectById(projectId);
        project.setTasks(tasks);
        return converter.convertOne(treatAndSave(project));
    }

    public GetProjectDTO patchTasksRemove(Long projectId, Long taskId) {
        Project project = findProjectById(projectId);
        Task task = taskService.findTaskById(taskId);
        if(!project.getTasks().remove(task)) throw new NoSuchElementException("The especified project does not have task of id: " + taskId + " in tasks");
        return converter.convertOne(treatAndSave(project));
    }

    public GetProjectDTO patchDefaultRole(Long projectId, Role defaultRole) throws InvalidAttributeValueException {
        if (defaultRole == null) throw new InvalidAttributeValueException("DefaultRole on project cannot be null");
        Project project = findProjectById(projectId);
        project.setDefaultRole(defaultRole);
             return converter.convertOne(treatAndSave(project));
    }

//    public Collection<GetProjectDTO> getProjectsByUserId(Long id){
//        User user = userRepository.findById(id).get();
//        Collection<Project> projects = projectRepository.findProjectsByMembersContaining(user);
//
//        projects.forEach(projectProcessor::resolveProject);
//        return converter.convertAll(projects);
//    }




    private void setDefaultRole(Project project){
        Role role = roleService.getRoleByName("PROJECT_COLABORATOR");
        project.setDefaultRole(role);
    }

//    public Collection<GetProjectDTO> getProjectsByTeam(Long teamId, Long userId){
//
//        Team team = teamRepository.findById(teamId).get();
//
//        if(team.getParticipants().contains(userRepository.findById(userId).get())){
//            Collection<Project> projects = projectRepository.findAllByTeam_Id(teamId);
//            return converter.convertAll(projects);
//        }
//
//        throw new RuntimeException("Usuario não encontrado no time");
//    }


    private Collection<UserProject> syncUserProjectTable(Project project, Collection<UserProject> userProjects) {

        userProjects.stream()
                .filter(this::userProjectTableNotExists)
                .forEach(this::createDefaultUserProject);

        deleteUserProjectIfUserIsNotAssociate(project);
        return userProjectService.findAllWithProjectId(project.getId());
    }


    private void createDefaultUserProject(UserProject userProject) {
        Role defaultRole = userProject.getProject().getDefaultRole();
        userProject.setRole(defaultRole);
        userProjectService.create(userProject);
    }

    private boolean userProjectTableNotExists(UserProject userProject) {
            return !userProjectService.existsById(new UserProjectId(userProject.getUserId(), userProject.getProjectId()));
    }

    private void deleteUserProjectIfUserIsNotAssociate(Project project) {
        Collection<UserProject> userProjects = userProjectService.findAllWithProjectId(project.getId());
        userProjects.stream()
                .filter(userProject -> !project.getMembers().contains(userProject.getUser()))
                .forEach(userProjectService::delete);
    }

    private void updateProjectChat(Project project) {
        HashSet<User> members = new HashSet<>();
        project.getMembers().forEach(userProject -> members.add(userProject.getUser()));
        project.setChat(projectChatService.patchUsers(project.getChat().getId(), members));
    }

//    private boolean updateMemberProfileAcess(Long projectId, Long memberId, String profileAcessName) {
//        Project project = findProjectById(projectId);
//        User member = userService.findUserById(memberId);
//        UserProject userProject = userProjectRepository.findByUserIdAndProjectId(member.getId(), project.getId());
//        Role role = roleService.getRoleByName(profileAcessName);
//        boolean existsOnProject = projectRepository.existsByIdAndRolesContaining(project.getId(), role);
//        if (existsOnProject) {
//            userProject.setRole(role);
//            userProjectRepository.save(userProject);
//            return true;
//        }
//        return false;
//    }


    //region deprecated methods


    private Project treatAndSave(Project project) {
        project.updateLastTimeEdited();
        return projectRepository.save(project);
    }

//    public GetProjectDTO deleteUserFromProject(Long idProject, Long actionUserId, Collection<UserProject> userProjectsToRemove) {
//        Project project = projectRepository.findById(idProject).get();
//
//
//        Set<UserProjectId> userIdsToRemove = userProjectsToRemove.stream()
//                .filter(userProject -> !userProject.isManager())
//                .map(userProject -> new UserProjectId(userProject.getUserId(), userProject.getProjectId()))
//                .collect(Collectors.toSet());
//
//        Set<UserProject> updatedMembers = project.getMembers().stream()
//                .filter(userProject -> !userIdsToRemove.contains(new UserProjectId(userProject.getUserId(), userProject.getProjectId())))
//                .collect(Collectors.toSet());
//
//        project.setMembers(updatedMembers);
//        teamNotificationService.deleteUserFromProjectNotification(idProject,actionUserId,userIdsToRemove);
//        return converter.convertOne(treatAndSave(project));
//    }
//
    private final UserRepository userRepository;
//
//    public GetProjectDTO addUserToProject(Long projectId, Long actionUserId, Long userAddedId) {
//        Project project = projectRepository.findById(projectId).get();
//        User userToAdd = userRepository.findById(userAddedId).get();
//        project.getMembers().add(userToAdd);
//        teamNotificationService.addUserToProject(projectId,actionUserId,userAddedId);
//        return converter.convertOne(treatAndSave(project));
//    }




    public Comment patchNewComment(Long projectId, Comment newComment, Long userId) {
        Project project = projectRepository.findById(projectId).get();
        newComment.setProject(project);
        Comment commentSaved = commentRepository.save(newComment);
        project.getComments().add(commentSaved);
//        project = historicService.patchNewCommentHistoric(projectId, userId).getProject();
        teamNotificationService.patchNewCommentProjectNotification(projectId,userId);
        projectRepository.save(project);
        return commentSaved;
    }

    public Collection<Comment> deleteComment(Long commentId, Long projectId, Long userId) {

        Project project = projectRepository.findById(projectId).get();
        Comment comment = commentRepository.findById(commentId).get();

        project.getComments().remove(comment);

        projectRepository.save(project);
        commentRepository.deleteById(commentId);
//        project = historicService.deleteCommentHistoric(projectId, userId);

        return project.getComments();
    }

    public Collection<Comment> getAllCommentsOfTask(Long projectId) {
        return commentRepository.findAllByProject_Id(projectId);
    }

    public GetProjectDTO deleteStatus(Long projectId, Status status) {
        Project project = projectRepository.findById(projectId).get();
        try {
            project.getStatusList().remove(status);
            projectRepository.save(project);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return converter.convertOne(treatAndSave(project));
    }
}
