package net.weg.taskmanager.security.config;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.UserProject;
import net.weg.taskmanager.model.dto.UserTeamDTO;
import net.weg.taskmanager.model.dto.get.GetUserDTO;
import net.weg.taskmanager.model.dto.post.PostProjectDTO;
import net.weg.taskmanager.model.dto.post.PostTaskDTO;
import net.weg.taskmanager.model.dto.post.PostTeamDTO;
import net.weg.taskmanager.model.dto.post.PostUserDTO;
import net.weg.taskmanager.model.dto.shortDTOs.ShortTeamDTO;
import net.weg.taskmanager.model.dto.shortDTOs.ShortUserDTO;
import net.weg.taskmanager.model.entity.*;
import net.weg.taskmanager.model.enums.Priority;
import net.weg.taskmanager.model.record.PriorityRecord;
import net.weg.taskmanager.repository.ProjectRepository;
import net.weg.taskmanager.repository.TeamRepository;
import net.weg.taskmanager.repository.UserRepository;
import net.weg.taskmanager.security.model.entity.Role;
import net.weg.taskmanager.security.model.enums.Permission;
import net.weg.taskmanager.security.repository.RoleRepository;
import net.weg.taskmanager.security.repository.UserDetailsEntityRepository;
import net.weg.taskmanager.service.ProjectService;
import net.weg.taskmanager.service.TaskService;
import net.weg.taskmanager.service.TeamService;
import net.weg.taskmanager.service.UserService;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Configuration
@AllArgsConstructor
public class DataBaseConfig {
    private final UserDetailsEntityRepository repository;
    private final RoleRepository roleRepository;

    private final UserService userService;
    private final ProjectService projectService;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final ProjectRepository projectRepository;

    @PostConstruct
    public void init() {
        createTeamProfileAccess();
        createProjectProfileAccess();
        loadDataSet();
    }

    private void createTeamProfileAccess() {
        if(roleRepository.findByName("TEAM_CREATOR")!=null) return;
        roleRepository.saveAll(List.of(
                new Role("TEAM_CREATOR", List.of(Permission.EDIT_TEAM_INFO, Permission.MANAGE_PARTICIPANTS, Permission.CREATE_PROJECT, Permission.TEAM_CREATOR, Permission.TEAM_VIEW)),
                new Role("TEAM_ADM", List.of(Permission.EDIT_TEAM_INFO, Permission.MANAGE_PARTICIPANTS, Permission.CREATE_PROJECT, Permission.TEAM_VIEW)),
                new Role("TEAM_COLABORATOR", List.of(Permission.CREATE_PROJECT, Permission.TEAM_VIEW)),
                new Role("TEAM_VIEWER", List.of(Permission.TEAM_VIEW))
        ));
    }

    private void createProjectProfileAccess() {
        if(roleRepository.findByName("PROJECT_CREATOR")!=null) return;
        roleRepository.saveAll(List.of(
                new Role("PROJECT_CREATOR", List.of(Permission.EDIT_PROJECT_INFO, Permission.MANAGE_MEMBERS, Permission.CREATE_TASK, Permission.PROJECT_CREATOR, Permission.PROJECT_VIEW)),
                new Role("PROJECT_ADM", List.of(Permission.EDIT_PROJECT_INFO, Permission.MANAGE_MEMBERS, Permission.CREATE_TASK, Permission.PROJECT_VIEW)),
                new Role("PROJECT_COLABORATOR", List.of(Permission.CREATE_TASK, Permission.PROJECT_VIEW)),
                new Role("PROJECT_VIEWER", List.of(Permission.PROJECT_VIEW))
        ));
    }

    private void loadDataSet(){
        createDeborah();
        createThiago();
        createGorges();
        createFelipe();

 /*       createDeborahTeamProject();
        createThiagoTeamProject();
        createGorgesTeamProject();
        createFelipeTeamProject();

        createThiagoTeamProject2();
        createThiagoTeamProject3();
        createWEGTeam();
        createWEGProject();*/
    }


    private void createDeborah() {
        try {
            repository.findByUsername("deborah@gmail.com").get();
        } catch (Exception e) {
            PostUserDTO postUserDTO = new PostUserDTO("Deborah", "deborah@gmail.com", "deborah123", "#8260d1", false);
            userService.create2(postUserDTO);
        }
    }

    private void createThiago() {
        try {
            repository.findByUsername("thiago@gmail.com").get();
        } catch (Exception e) {
            PostUserDTO postUserDTO = new PostUserDTO("Thiago", "thiago@gmail.com", "thiago123", "#f5d112", false);
            userService.create2(postUserDTO);
        }
    }

    private void createGorges() {
        try {
            repository.findByUsername("gorges@gmail.com").get();
        } catch (Exception e) {
            PostUserDTO postUserDTO = new PostUserDTO("Gustavo Gorges", "gorges@gmail.com", "gorges123", "#0ba4db", false);
            userService.create2(postUserDTO);
        }
    }

    private void createFelipe() {
        try {
            repository.findByUsername("felipe@gmail.com").get();
        } catch (Exception e) {
            PostUserDTO postUserDTO = new PostUserDTO("Felipe", "felipe@gmail.com", "felipe123", "#ffc1c2", false);
            userService.create2(postUserDTO);
        }
    }

    private void createDeborahTeamProject(){
        User creator = userRepository.findByEmail("deborah@gmail.com");
        Team team = teamRepository.findTeamsByName("Equipe Deborah").stream().findFirst().get();
        if(projectRepository.findAllByTeam_Id(team.getId()).get().isEmpty()){
            PostProjectDTO postProjectDTO = new PostProjectDTO(
                    creator,
                    team,
                    "App Aurora",
                    "Projeto realizado na aula de desenvolvimento mobile, aplicativo de hábitos",
                    "#ff4775",
                    null,
                    LocalDate.of(2024, 6, 14),
                    new ArrayList<>(),
                    getDefaultStatus());
            projectService.create2(postProjectDTO);
        }
    }

    private void createThiagoTeamProject(){
        User creator = userRepository.findByEmail("thiago@gmail.com");
        Team team = teamRepository.findTeamsByName("Equipe Thiago").stream().findFirst().get();
        if(projectRepository.findAllByTeam_Id(team.getId()).get().isEmpty()){
            PostProjectDTO postProjectDTO = new PostProjectDTO(
                    creator,
                    team,
                    "App Aurora",
                    "Projeto realizado na aula de desenvolvimento mobile, aplicativo de hábitos",
                    "#ff4775",
                    null,
                    LocalDate.of(2024, 6, 14),
                    new ArrayList<>(),
                    getDefaultStatus());
            addMembersToProject(projectService.create2(postProjectDTO));
            addMembersToTeam(team);
        }
    }

    private void createGorgesTeamProject(){
        User creator = userRepository.findByEmail("gorges@gmail.com");
        Team team = teamRepository.findTeamsByName("Equipe Gustavo Gorges").stream().findFirst().get();
        if(projectRepository.findAllByTeam_Id(team.getId()).get().isEmpty()){
            PostProjectDTO postProjectDTO = new PostProjectDTO(
                    creator,
                    team,
                    "App Aurora",
                    "Projeto realizado na aula de desenvolvimento mobile, aplicativo de hábitos",
                    "#ff4775",
                    null,
                    LocalDate.of(2024, 6, 14),
                    new ArrayList<>(),
                    getDefaultStatus());
            projectService.create2(postProjectDTO);
        }
    }

    private void createFelipeTeamProject(){
        User creator = userRepository.findByEmail("felipe@gmail.com");
        Team team = teamRepository.findTeamsByName("Equipe Felipe").stream().findFirst().get();;
        if(projectRepository.findAllByTeam_Id(team.getId()).get().isEmpty()){
            PostProjectDTO postProjectDTO = new PostProjectDTO(
                    creator,
                    team,
                    "App Aurora",
                    "Projeto realizado na aula de desenvolvimento mobile, aplicativo de hábitos",
                    "#ff4775",
                    null,
                    LocalDate.of(2024, 6, 14),
                    new ArrayList<>(),
                    getDefaultStatus());
            projectService.create2(postProjectDTO);
        }
    }

    private void createThiagoTeamProject2(){
        User creator = userRepository.findByEmail("thiago@gmail.com");
        Team team = teamRepository.findTeamsByName("Equipe Thiago").stream().findFirst().get();
        if(projectRepository.findAllByTeam_Id(team.getId()).get().size()<2){
            PostProjectDTO postProjectDTO = new PostProjectDTO(
                    creator,
                    team,
                    "Projeto 2",
                    "Projeto realizado na aula de desenvolvimento mobile, aplicativo de hábitos",
                    "#ff4775",
                    null,
                    LocalDate.of(2024, 6, 14),
                    new ArrayList<>(),
                    getDefaultStatus());
            addMembersToProject(projectService.create2(postProjectDTO));
        }
    }
    private void createThiagoTeamProject3(){
        User creator = userRepository.findByEmail("thiago@gmail.com");
        Team team = teamRepository.findTeamsByName("Equipe Thiago").stream().findFirst().get();
        if(projectRepository.findAllByTeam_Id(team.getId()).get().size()<3){
            PostProjectDTO postProjectDTO = new PostProjectDTO(
                    creator,
                    team,
                    "Projeto 3",
                    "Projeto realizado na aula de desenvolvimento mobile, aplicativo de hábitos",
                    "#ff4775",
                    null,
                    LocalDate.of(2024, 6, 14),
                    new ArrayList<>(),
                    getDefaultStatus());
            addMembersToProject(projectService.create2(postProjectDTO));
        }
    }

    private void addMembersToProject(Project p){
        try {
            projectService.patchMembers(p.getId(), List.of(
                    new UserProject(1L, p.getId(), roleRepository.findByName("PROJECT_ADM")),
                    new UserProject(2L, p.getId(), roleRepository.findByName("PROJECT_CREATOR")),
                    new UserProject(3L, p.getId(), roleRepository.findByName("PROJECT_COLABORATOR")),
                    new UserProject(4L,p.getId(), roleRepository.findByName("PROJECT_VIEWER"))
            ));
        } catch (Exception ignore){}
    }

    private final TeamService teamService;

    private void addMembersToTeam(Team p){
        try {
            teamService.patchParticipants(p.getId(), List.of(
                    new UserTeamDTO(1L, p.getId(), new GetUserDTO(1L), new ShortTeamDTO(1L), roleRepository.findByName("TEAM_ADM"), false),
                    new UserTeamDTO(2L, p.getId(), new GetUserDTO(2L), new ShortTeamDTO(2L), roleRepository.findByName("TEAM_CREATOR"), true),
                    new UserTeamDTO(3L, p.getId(), new GetUserDTO(3L), new ShortTeamDTO(3L), roleRepository.findByName("TEAM_COLABORATOR"), false),
                    new UserTeamDTO(4L,p.getId(), new GetUserDTO(4L), new ShortTeamDTO(4L), roleRepository.findByName("TEAM_VIEWER"), false)
            ));
        } catch (Exception ignore){}
    }

    private void createWEGTeam(){
        User creator = userService.findUserById(2L);
        Team team = teamService.findTeamById(teamService.create2(new Team("Equipe Evolve", "#185e77", List.of(new UserTeam(creator.getId(), null, creator, null, new Role(1L), true)) , UUID.randomUUID().toString())).getId());
        addMembersToTeam(team);
    }


    private void createWEGProject(){
        User creator = userRepository.findByEmail("thiago@gmail.com");
        Team team = teamRepository.findTeamsByName("Equipe Evolve").stream().findFirst().get();
        if(projectRepository.findAllByTeam_Id(team.getId()).get().isEmpty()){
            PostProjectDTO postProjectDTO = new PostProjectDTO(
                    creator,
                    team,
                    "Evolve",
                    "Projeto realizado na aula de desenvolvimento mobile, aplicativo de hábitos",
                    "#4c956c",
                    null,
                    LocalDate.of(2024, 6, 14),
                    new ArrayList<>(),
                    getDefaultStatus());
            addMembersToProject(projectService.create2(postProjectDTO));
        }
    }

    private void createWEGProject2(){
        User creator = userRepository.findByEmail("thiago@gmail.com");
        Team team = teamRepository.findTeamsByName("Equipe Evol").stream().findFirst().get();
        if(projectRepository.findAllByTeam_Id(team.getId()).get().isEmpty()){
            PostProjectDTO postProjectDTO = new PostProjectDTO(
                    creator,
                    team,
                    "Evolve",
                    "Projeto realizado na aula de desenvolvimento mobile, aplicativo de hábitos",
                    "#4c956c",
                    null,
                    LocalDate.of(2024, 6, 14),
                    new ArrayList<>(),
                    getDefaultStatus());
            addMembersToProject(projectService.create2(postProjectDTO));
        }
    }
    private void createWEGProject3(){
        User creator = userRepository.findByEmail("thiago@gmail.com");
        Team team = teamRepository.findTeamsByName("Equipe Evolve 3").stream().findFirst().get();
        if(projectRepository.findAllByTeam_Id(team.getId()).get().isEmpty()){
            PostProjectDTO postProjectDTO = new PostProjectDTO(
                    creator,
                    team,
                    "Evolve",
                    "Projeto realizado na aula de desenvolvimento mobile, aplicativo de hábitos",
                    "#4c956c",
                    null,
                    LocalDate.of(2024, 6, 14),
                    new ArrayList<>(),
                    getDefaultStatus());
            addMembersToProject(projectService.create2(postProjectDTO));
        }
    }


    private Collection<Status> getDefaultStatus(){
        return List.of(
                new Status("não atribuido", "#9ca3ae", "#000000", true),
                new Status("pendente", "#0ba4db", "#000000", true),
                new Status("em progresso", "#eddd4b", "#000000", true),
                new Status("concluido", "#4c956c", "#000000", true)
        );
    }

}