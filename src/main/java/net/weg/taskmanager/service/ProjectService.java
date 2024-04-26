package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.*;
import net.weg.taskmanager.model.dto.get.GetProjectDTO;
import net.weg.taskmanager.model.dto.get.GetTaskDTO;
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

@Service
@AllArgsConstructor
//@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    private final TaskService taskService;
    private final StatusService statusService;
    private final PropertyService propertyService;
    private final RoleService roleService;
    private final UserService userService;

//    private final ModelMapper modelMapper;


    private final UserProjectService userProjectService;
    private final UserProjectRepository userProjectRepository;


    public GetProjectDTO findById(Long id) {
        Project project = findProjectById(id);
        ProjectProcessor.getInstance().resolveProject(project);
        return transformToGetProjectDTO(project);
    }

    public Collection<GetProjectDTO> findAll() {
        Collection<Project> projects = projectRepository.findAll();
        Collection<GetProjectDTO> getProjectDTOS = new HashSet<>();

        projects
                .forEach(project -> {
                    ProjectProcessor.getInstance().resolveProject(project);
                    GetProjectDTO getProjectDTO = transformToGetProjectDTO(project);
                    getProjectDTOS.add(getProjectDTO);
                });

        return getProjectDTOS;
    }

    public GetProjectDTO create(PostProjectDTO projectDTO) {
        Project project = new Project(projectDTO);
        Project projectSaved = projectRepository.save(project);

        setCreator(projectDTO.getCreator(), projectSaved);
        createProjectChat(projectSaved);
        setDefaultProfileAccess(projectSaved);

        return transformToGetProjectDTO(treatAndSave(projectSaved));
    }

    private void setCreator(User user, Project project){
        Role role = roleService.getRoleByName("PROJECT_CREATOR");
        UserProject userProject = new UserProject(user.getId(), project.getId(), role);
        userProject.setManager(true);
        userProjectService.create(userProject);
    }

    private ProjectChatService projectChatService;

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


    public Project findProjectById(Long projectId){
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if(optionalProject.isEmpty()) throw new NoSuchElementException();
        return optionalProject.get();
    }

    public GetProjectDTO patchName(Long projectId, String name) throws InvalidAttributeValueException {
        if(name == null) throw new InvalidAttributeValueException("Name on project cannot be null");
        Project project = findProjectById(projectId);
        project.setName(name);
        return transformToGetProjectDTO(treatAndSave(project));
    }

    public GetProjectDTO patchDescription(Long projectId, String description) throws InvalidAttributeValueException {
        if(description == null) throw new InvalidAttributeValueException("Description on project cannot be null");
        Project project = findProjectById(projectId);
        project.setDescription(description);
        return transformToGetProjectDTO(treatAndSave(project));
    }

    public GetProjectDTO patchImage(Long projectId, MultipartFile image) throws InvalidAttributeValueException {
        if(image == null) throw new InvalidAttributeValueException("Image on project cannot be null");
        Project project = findProjectById(projectId);
        project.setImage(FileUtils.buildFileFromMultipartFile(image));
        return transformToGetProjectDTO(treatAndSave(project));
    }

    public GetProjectDTO patchImageRemove(Long projectId){
        Project project = findProjectById(projectId);
        project.setImage(null);
        return transformToGetProjectDTO(treatAndSave(project));
    }

    public GetProjectDTO patchImageColor(Long projectId, String imageColor) throws InvalidAttributeValueException {
        if(imageColor == null || !ColorUtils.isHexColorValid(imageColor)) throw new InvalidAttributeValueException("Not valid format for imageColor on Project. Expecting a hexCode");
        Project project = findProjectById(projectId);
        project.setImageColor((imageColor));
        return transformToGetProjectDTO(treatAndSave(project));
    }

    public GetProjectDTO patchFinalDate(Long projectId, LocalDateTime finalDate) throws InvalidAttributeValueException {
        Project project = findProjectById(projectId);
        if(finalDate == null || finalDate.isBefore(project.getCreationDate())) throw new InvalidAttributeValueException("Image on project cannot be null");
        project.setFinalDate(finalDate);
        return transformToGetProjectDTO(treatAndSave(project));
    }

    public GetProjectDTO patchProperties(Long projectId, Collection<Property> properties) throws InvalidAttributeValueException {
        //falta converter oq é ecebido do front (acho que recebe uma record)
        if(properties == null) throw new InvalidAttributeValueException("Properties on project cannot be null");
        Project project = findProjectById(projectId);
        project.setProperties(properties);
        propertyService.createProjectPropertiesIfNotExists(project);
        return transformToGetProjectDTO(treatAndSave(project));
    }

    public GetProjectDTO patchStatusList(Long projectId, Collection<Status> statusList) throws InvalidAttributeValueException {
        if(statusList == null) throw new InvalidAttributeValueException("StatusList on project cannot be null");
        Project project = findProjectById(projectId);
        project.setStatusList(statusList);
        return transformToGetProjectDTO(treatAndSave(project));
    }

    public GetProjectDTO patchStatusListRemove(Long projectId, Long statusId) {
        Project project = findProjectById(projectId);
        Status status = statusService.findStatusById(statusId);
        if(!project.getStatusList().remove(status)) throw new NoSuchElementException("The project specified does not contains any status with id: " + statusId + " in statusList");
        return transformToGetProjectDTO(treatAndSave(project));
    }

    public GetProjectDTO patchMembers(Long projectId, Collection<UserProject> members) throws InvalidAttributeValueException {
        if(members == null) throw new InvalidAttributeValueException("Members on project cannot be null");
        Project project = findProjectById(projectId);
        Collection<UserProject> filteredMembers = syncUserProjectTable(project, members);
        project.setMembers(filteredMembers);
        updateProjectChat(project);
        return transformToGetProjectDTO(treatAndSave(project));
    }

    public GetProjectDTO patchTasks(Long projectId,Collection<Task> tasks) throws InvalidAttributeValueException {
        if(tasks == null) throw new InvalidAttributeValueException("Tasks on project cannot be null");
        Project project = findProjectById(projectId);
        project.setTasks(tasks);
        return transformToGetProjectDTO(treatAndSave(project));
    }

    public GetProjectDTO patchTasksRemove(Long projectId, Long taskId) {
        Project project = findProjectById(projectId);
        Task task = taskService.findTaskById(taskId);
        if(!project.getTasks().remove(task)) throw new NoSuchElementException("The especified project does not have task of id: " + taskId + " in tasks");
        return transformToGetProjectDTO(treatAndSave(project));
    }

    public GetProjectDTO patchDefaultRole(Long projectId, Role defaultRole) throws InvalidAttributeValueException {
        if(defaultRole == null) throw new InvalidAttributeValueException("DefaultRole on project cannot be null");
        Project project = findProjectById(projectId);
        project.setDefaultRole(defaultRole);
        return transformToGetProjectDTO(treatAndSave(project));
    }




    private void setDefaultProfileAccess(Project project){
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
//        if (project.getMembers() != null) {
//            project.getMembers().stream()
//                    .filter(userProject -> !doesUserProjectTableExists(userProject.getUser(), project))
//                    .forEach(userProject -> createDefaultUserProject(userProject.getUser(), project));
//        }
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
        Project savedProject = projectRepository.save(project);
        ProjectProcessor.getInstance().resolveProject(savedProject);
        return savedProject;
    }

    private GetProjectDTO transformToGetProjectDTO(Project project) {
        GetProjectDTO getProjectDTO = new GetProjectDTO();
        Collection<GetTaskDTO> getTaskDTOS = new HashSet<>();

        BeanUtils.copyProperties(project, getProjectDTO);

        if (project.getTasks() != null) {
            project.getTasks().forEach((task -> {
                GetTaskDTO getTaskDTO = new GetTaskDTO();
                PriorityRecord priorityRecord = new PriorityRecord(task.getPriority().name(), task.getPriority().backgroundColor);
                BeanUtils.copyProperties(task, getTaskDTO);
                getTaskDTO.setPriority(priorityRecord);
                getTaskDTOS.add(getTaskDTO);
            }));
        }

        getProjectDTO.setTasks(getTaskDTOS);
        return getProjectDTO;
    }

    //endregion

}