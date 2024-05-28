package net.weg.taskmanager.security.config;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.UserProject;
import net.weg.taskmanager.model.dto.post.PostProjectDTO;
import net.weg.taskmanager.model.dto.post.PostUserDTO;
import net.weg.taskmanager.model.entity.Status;
import net.weg.taskmanager.model.entity.Team;
import net.weg.taskmanager.model.entity.User;
import net.weg.taskmanager.model.entity.UserTeam;
import net.weg.taskmanager.repository.ProjectRepository;
import net.weg.taskmanager.repository.TeamRepository;
import net.weg.taskmanager.repository.UserRepository;
import net.weg.taskmanager.security.model.entity.Role;
import net.weg.taskmanager.security.model.entity.UserDetailsEntity;
import net.weg.taskmanager.security.model.enums.Permission;
import net.weg.taskmanager.security.repository.RoleRepository;
import net.weg.taskmanager.security.repository.UserDetailsEntityRepository;
import net.weg.taskmanager.service.ProjectService;
import net.weg.taskmanager.service.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Configuration
@AllArgsConstructor
public class DataBaseConfig {
    private final UserDetailsEntityRepository repository;
    private final RoleRepository roleRepository;

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

        createDeborahTeamProject();
        createThiagoTeamProject();
        createGorgesTeamProject();
        createFelipeTeamProject();


    }

    private final UserService userService;

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
            PostUserDTO postUserDTO = new PostUserDTO("Gustavo Gorges", "gorges@gmail.com", "gorges123", "#b50707", false);
            userService.create2(postUserDTO);
        }
    }

    private void createFelipe() {
        try {
            repository.findByUsername("felipe@gmail.com").get();
        } catch (Exception e) {
            PostUserDTO postUserDTO = new PostUserDTO("Felipe", "felipe@gmail.com", "felipe123", "#591496", false);
            userService.create2(postUserDTO);
        }
    }
    private final ProjectService projectService;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final ProjectRepository projectRepository;
    private void createDeborahTeamProject(){
        User creator = userRepository.findByEmail("deborah@gmail.com");
        Team team = teamRepository.findTeamsByName("Equipe Deborah").stream().findFirst().get();
        if(projectRepository.findAllByTeam_Id(team.getId()).get().isEmpty()){
            PostProjectDTO postProjectDTO = new PostProjectDTO(creator, team , "App Aurora", "Projeto realizado na aula de desenvolvimento mobile, aplicativo de hábitos", "#ff4775", null, LocalDate.of(2024, 6, 14), new ArrayList<>(),getDefaultStatus());
            projectService.create2(postProjectDTO);
        }
    }

    private void createThiagoTeamProject(){
        User creator = userRepository.findByEmail("thiago@gmail.com");
        Team team = teamRepository.findTeamsByName("Equipe Thiago").stream().findFirst().get();
        if(projectRepository.findAllByTeam_Id(team.getId()).get().isEmpty()){
            PostProjectDTO postProjectDTO = new PostProjectDTO(creator, team , "App Aurora", "Projeto realizado na aula de desenvolvimento mobile, aplicativo de hábitos", "#ff4775", null, LocalDate.of(2024, 6, 14), new ArrayList<>(),getDefaultStatus());
            projectService.create2(postProjectDTO);
        }
    }

    private void createGorgesTeamProject(){
        User creator = userRepository.findByEmail("gorges@gmail.com");
        Team team = teamRepository.findTeamsByName("Equipe Gustavo Gorges").stream().findFirst().get();
        if(projectRepository.findAllByTeam_Id(team.getId()).get().isEmpty()){
            PostProjectDTO postProjectDTO = new PostProjectDTO(creator, team , "App Aurora", "Projeto realizado na aula de desenvolvimento mobile, aplicativo de hábitos", "#ff4775", null, LocalDate.of(2024, 6, 14), new ArrayList<>(),getDefaultStatus());
            projectService.create2(postProjectDTO);
        }
    }

    private void createFelipeTeamProject(){
        User creator = userRepository.findByEmail("felipe@gmail.com");
        Team team = teamRepository.findTeamsByName("Equipe Felipe").stream().findFirst().get();;
        if(projectRepository.findAllByTeam_Id(team.getId()).get().isEmpty()){
            PostProjectDTO postProjectDTO = new PostProjectDTO(creator, team , "App Aurora", "Projeto realizado na aula de desenvolvimento mobile, aplicativo de hábitos", "#ff4775", null, LocalDate.of(2024, 6, 14), new ArrayList<>(),getDefaultStatus());
            projectService.create2(postProjectDTO);
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