package net.weg.taskmanager.exception;

import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;

public class DeniedGetExecption extends RuntimeException {

    public DeniedGetExecption(){
        super("Access Denied");
    }
}
