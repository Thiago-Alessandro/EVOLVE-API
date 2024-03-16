package net.weg.taskmanager.security.model.enums;


    import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
public enum Auth implements GrantedAuthority {

    GET("Get"),
    POST("Post"),
    PUT("Put"),
    DELETE("Delete");
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }
//    public static Autorizacao
}
