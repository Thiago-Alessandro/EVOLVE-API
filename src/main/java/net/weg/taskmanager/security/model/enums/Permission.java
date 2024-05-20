package net.weg.taskmanager.security.model.enums;


import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;


public enum Permission implements GrantedAuthority {

    EDIT_TEAM_INFO,
    MANAGE_PARTICIPANTS,
    CREATE_PROJECT,
    TEAM_CREATOR,
    TEAM_VIEW,
    EDIT_PROJECT_INFO,
    MANAGE_MEMBERS,
    CREATE_TASK,
    PROJECT_CREATOR,
    PROJECT_VIEW;


    @Override
    public String getAuthority() {
        return name();
    }
//    public static Autorizacao

    public Permission getAuth(HttpServletRequest request) {
//        Auth.valueOf()
       return Permission.valueOf(request.getMethod());
    }

}