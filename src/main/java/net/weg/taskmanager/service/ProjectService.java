package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.*;
import net.weg.taskmanager.model.dto.converter.Converter;
import net.weg.taskmanager.model.dto.converter.get.GetProjectConverter;
import net.weg.taskmanager.model.dto.get.GetTaskDTO;
import net.weg.taskmanager.model.entity.*;
import net.weg.taskmanager.model.dto.get.GetProjectDTO;
import net.weg.taskmanager.model.dto.post.PostProjectDTO;
import net.weg.taskmanager.model.property.Property;
import net.weg.taskmanager.model.record.PriorityRecord;
import net.weg.taskmanager.repository.*;
import net.weg.taskmanager.security.model.entity.Role;
import net.weg.taskmanager.security.service.RoleService;
import net.weg.taskmanager.service.processor.ProjectProcessor;
import net.weg.taskmanager.utils.ColorUtils;
import net.weg.taskmanager.utils.FileUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.management.InvalidAttributeValueException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.Collection;

@Service
@AllArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    private final TaskService taskService;
    private final StatusService statusService;
    private final PropertyService propertyService;
    private final RoleService roleService;
    private ProjectChatService projectChatService;
    private final UserService userService;
    private final UserProjectService userProjectService;


    private final UserProjectRepository userProjectRepository;

    private final Converter<GetProjectDTO, Project> converter = new GetProjectConverter();
    
//    public GetProjectDTO updateStatusList(Long id, Status status){
//        Project project = projectRepository.findById(id).get();
//        if(project.getStatusList()!=null){
//            for(Status statusFor : project.getStatusList()){
//                if(Objects.equals(status.getId(), statusFor.getId())){
//                    BeanUtils.copyProperties(status, statusFor);
//                    return converter.convertOne(treatAndSave(project));
//                }
//            }
//            project.getStatusList().add(status);
//        } else {
//            project.setStatusList(new ArrayList<>());
//            project.getStatusList().add(status);
//        }
//        return converter.convertOne(treatAndSave(project));
//    }




    public GetProjectDTO findById(Long id) {
        Project project = findProjectById(id);
        return converter.convertOne(project);
    }


    public Collection<GetProjectDTO> findAll() {
        Collection<Project> projects =  projectRepository.findAll();
        return converter.convertAll(projects);
    }

    public GetProjectDTO create(PostProjectDTO projectDTO) {
        Project project = new Project(projectDTO);
        Project projectSaved = projectRepository.save(project);

        setCreator(projectDTO.getCreator(), projectSaved);
        createProjectChat(projectSaved);
        setDefaultRole(projectSaved);

//        //Referencia o projeto nas suas propriedades
//        propertiesSetProject(project);

        return converter.convertOne(treatAndSave(projectSaved));
    }

    private void setCreator(User user, Project project){
        Role role = roleService.getRoleByName("PROJECT_CREATOR");
        UserProject userProject = new UserProject(user.getId(), project.getId(), role);
        userProject.setManager(true);
        userProjectService.create(userProject);
    }

//    public GetProjectDTO update(PutProjectDTO projectDTO){
//
//        Project project = projectRepository.findById(projectDTO.getId()).get();
//        modelMapper.map(projectDTO, project);
//
//        updateProjectChat(project);
//
//        return converter.convertOne(treatAndSave(project));
//    }

    private void createProjectChat(Project project){
        ProjectChat chat = new ProjectChat();
        chat.setProject(project);
        ProjectChat createdChat = projectChatService.create(chat);
        project.setChat(createdChat);
    }

//    public GetProjectDTO update(PutProjectDTO projectDTO) {
//        Project project = findProjectById(projectDTO.getId());
//        modelMapper.map(projectDTO, project);
//        syncProjectInfos(project);
//        return transformToGetProjectDTO(treatAndSave(project));
//    }

    public void delete(Long id) {
        Project project = findProjectById(id);
        taskService.deleteAll(project.getTasks());
        statusService.deleteAll(project.getStatusList());
        projectRepository.deleteById(id);
    }

    private final UserRepository userRepository;

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

    public GetProjectDTO patchStatusList(Long projectId, Collection<Status> statusList) throws InvalidAttributeValueException {
        if(statusList == null) throw new InvalidAttributeValueException("StatusList on project cannot be null");
        Project project = findProjectById(projectId);
        project.setStatusList(statusList);
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



//    private void setCreatorRole(Project project) {
//        User creator = userProjectService.findProjectCreator(project.getId());
//        Long creatorId = creator.getId();
//        Long projectId = project.getId();
//        Role role = roleService.getRoleByName("PROJECT_CREATOR");
//        UserProject userProject = new UserProject(creatorId, projectId, role);
//        userProjectService.create(userProject);
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
        boolean result = !userProjectService.existsById(new UserProjectId(userProject.getUserId(), userProject.getProjectId()));
        System.out.println(userProjectRepository.findByUserIdAndProjectId(userProject.getUserId(), userProject.getProjectId()));
        System.out.println(result);
        return result;
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

//    private GetProjectDTO transformToGetProjectDTO(Project project) {
//        GetProjectDTO getProjectDTO = new GetProjectDTO();
//        Collection<GetTaskDTO> getTaskDTOS = new HashSet<>();
//
//        BeanUtils.copyProperties(project, getProjectDTO);
//
//        if (project.getTasks() != null) {
//            project.getTasks().forEach((task -> {
//                GetTaskDTO getTaskDTO = new GetTaskDTO();
//                PriorityRecord priorityRecord = new PriorityRecord(task.getPriority().name(), task.getPriority().backgroundColor);
//                BeanUtils.copyProperties(task, getTaskDTO);
//                getTaskDTO.setPriority(priorityRecord);
//                getTaskDTOS.add(getTaskDTO);
//            }));
//        }
//
//        getProjectDTO.setTasks(getTaskDTOS);
//        return getProjectDTO;
//    }

    //endregion

}