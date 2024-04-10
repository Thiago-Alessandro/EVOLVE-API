package net.weg.taskmanager.security.route.task.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.User;
import net.weg.taskmanager.model.dto.post.PostTaskDTO;
import net.weg.taskmanager.repository.TaskRepository;
import net.weg.taskmanager.repository.UserTaskRepository;
import net.weg.taskmanager.security.model.entity.UserDetailsEntity;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.function.Supplier;

@AllArgsConstructor
@Component
public class PermissionRouteTaskPOST implements AuthorizationManager<RequestAuthorizationContext> {
    private final TaskRepository taskRepository;
    private final UserTaskRepository userTaskRepository;

    @Override
    public void verify(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        AuthorizationManager.super.verify(authentication, object);
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> supplier, RequestAuthorizationContext object) {
        Authentication authentication = supplier.get();
        HttpServletRequest request = object.getRequest();

        StringBuilder requestBody = new StringBuilder();
        requestBody.append("{");
        try (BufferedReader reader = request.getReader()) {
            String line;
            while (true) {
                line = reader.readLine();
                System.out.println(line);
                if (line == null) break;
                if(line.contains("project")){
//                    line.charAt(line.length());
                    line = line.replace(",","");
                    requestBody.append(line);
                }
            }
            reader.close();
        } catch (IOException ignore) {
            throw new RuntimeException();
        }
        requestBody.append("}");
        String requestBodyString = requestBody.toString();
        System.out.println("request body string"+ requestBodyString);
        ObjectMapper objectMapper = new ObjectMapper();
        PostTaskDTO task;
        try {
           task = objectMapper.readValue(requestBodyString, PostTaskDTO.class);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("cai no catch authorizacao");
            throw  new AuthorizationServiceException("access denied!");
        }
        Long projectid = task.getProject().getId();
        System.out.println("teste : "+projectid);
            UserDetailsEntity userDetails = (UserDetailsEntity) authentication.getPrincipal();
            User user = userDetails.getUser();

//        Map<String, String> mapper = object.getVariables();
            return new AuthorizationDecision(true);
        }

//    private boolean isUserAuthorized(Long projectId, User user, Auth auth) {
//        return user.getProjectsAcess()
//                .stream().filter(projectAcess -> projectAcess.getProjectId().equals(projectId))
//                .anyMatch(projectAcess -> projectAcess.getAcessProfile().getAuths().contains(auth)
//                );
//    }
    }
