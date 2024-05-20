package net.weg.taskmanager.exception;

public class DeniedPutException extends RuntimeException{
    public DeniedPutException(){
        super("Access Denied");
    }
}
//