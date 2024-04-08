package net.weg.taskmanager.security.model.enums;


import jakarta.persistence.Entity;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.net.http.HttpRequest;

@AllArgsConstructor
@NoArgsConstructor
//@Entity
public enum Auth implements GrantedAuthority {
    GET("GET"),
    POST("Post"),
    PUT("Put"),
    DELETE("Delete"),
    PATCH("Patch");
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }
//    public static Autorizacao

    public Auth getAuth(HttpServletRequest request) {
//        Auth.valueOf()
       return Auth.valueOf(request.getMethod());
    }

}