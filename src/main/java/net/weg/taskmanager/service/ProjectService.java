package net.weg.taskmanager.service;

import lombok.RequiredArgsConstructor;
import net.weg.taskmanager.model.*;
import net.weg.taskmanager.model.dto.get.GetProjectDTO;
import net.weg.taskmanager.model.dto.get.GetTaskDTO;
import net.weg.taskmanager.model.dto.post.PostProjectDTO;
import net.weg.taskmanager.model.dto.put.PutProjectDTO;
import net.weg.taskmanager.model.record.PriorityRecord;
import net.weg.taskmanager.repository.*;
import net.weg.taskmanager.security.model.entity.ProfileAcess;
import net.weg.taskmanager.security.service.ProfileAcessService;
import net.weg.taskmanager.service.processor.ProjectProcessor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

@Service
//@AllArgsConstructor
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    private final TaskService taskService;
    private final StatusService statusService;
    private final PropertyService propertyService;
    private final ProfileAcessService profileAcessService;
    private final ModelMapper modelMapper;


//    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    
    private final UserProjectService userProjectService;
    private final UserProjectRepository userProjectRepository;


    public GetProjectDTO updateStatusList(Long id, Status status) {
        Project project = projectRepository.findById(id).get();
        if (project.getStatusList() != null) {
            for (Status statusFor : project.getStatusList()) {
                if (Objects.equals(status.getId(), statusFor.getId())) {
                    BeanUtils.copyProperties(status, statusFor);
                    return transformToGetProjectDTO(treatAndSave(project));
                }
            }
            project.getStatusList().add(status);
        } else {
            project.setStatusList(new ArrayList<>());
            project.getStatusList().add(status);
        }
        return transformToGetProjectDTO(treatAndSave(project));
    }


    public GetProjectDTO findById(Long id) {
        Project project = projectRepository.findById(id).get();

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
        //Recupera projeto com id
        Project projectSaved = projectRepository.save(project);

        setCreatorProfileAcess(projectSaved);
        setDefaultProfileAccess(projectSaved);

        syncProjectInfos(projectSaved);

        return transformToGetProjectDTO(treatAndSave(projectSaved));
    }

    public GetProjectDTO update(PutProjectDTO projectDTO) {

        Project project = projectRepository.findById(projectDTO.getId()).get();
        modelMapper.map(projectDTO, project);
        syncProjectInfos(project);

        return transformToGetProjectDTO(treatAndSave(project));
    }

    public void delete(Long id) {
        Project project = projectRepository.findById(id).get();
        taskService.deleteAll(project.getTasks());
        statusService.deleteAll(project.getStatusList());
        projectRepository.deleteById(id);
    }




    private void setDefaultProfileAccess(Project project){
        ProfileAcess profileAcess = profileAcessService.getProfileAcessByName("PROJECT_COLABORATOR");
        project.setDefaultProfileAccess(profileAcess);
    }

    private void setCreatorProfileAcess(Project project) {
        Long creatorId = project.getCreator().getId();
        Long projectId = project.getId();
        ProfileAcess profileAcess = profileAcessService.getProfileAcessByName("PROJECT_CREATOR");
        UserProject userProject = new UserProject(creatorId, projectId, profileAcess);
        userProjectService.create(userProject);
    }

    private void syncProjectInfos(Project project){
        updateProjectChat(project);
        createProjectProperties(project);
        syncUserProjectTable(project);
    }

    private void syncUserProjectTable(Project project) {
        if (project.getMembers() != null) {
            project.getMembers().stream()
                    .filter(member -> !doesUserProjectTableExists(member, project))
                    .forEach(member -> createDefaultUserProject(member, project));

            deleteUserProjectIfUserIsNotAssociate(project);
        }
    }


    private void createDefaultUserProject(User member, Project project) {
        UserProject userProject = new UserProject(member, project);
        userProjectRepository.save(userProject);
    }

    private boolean doesUserProjectTableExists(User member, Project project) {
        return userProjectRepository.existsById(new UserProjectId(member.getId(), project.getId()));
    }

    private void deleteUserProjectIfUserIsNotAssociate(Project project) {
        userProjectRepository.findAll().stream()
                .filter(userProject -> Objects.equals(userProject.getProjectId(), project.getId()))
                .filter(userProject -> !project.getMembers().contains(userProject.getUser()))
                .forEach(userProjectRepository::delete);
    }


    private void createProjectProperties(Project project) {
        propertyService.createProjectPropertiesIfNotExists(project);
    }

    private void updateProjectChat(Project project) {
        project.getChat().setUsers(project.getMembers());
    }

    private boolean updateMemberProfileAcess(Long projectId, Long memberId, String profileAcessName) {
        Project project = projectRepository.findById(projectId).get();
        User member = userRepository.findById(memberId).get();
        UserProject userProject = userProjectRepository.findByUserIdAndProjectId(member.getId(), project.getId());
        ProfileAcess profileAcess = profileAcessService.getProfileAcessByName(profileAcessName);
        boolean existsOnProject = projectRepository.existsByIdAndProfileAcessesContaining(project.getId(), profileAcess);
        if (existsOnProject) {
            userProject.setAcessProfile(profileAcess);
            userProjectRepository.save(userProject);
            return true;
        }
        return false;
    }


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
