package net.weg.taskmanager.exception;

public class DeniedPostException extends RuntimeException{
    public DeniedPostException(){
        super("Access Denied");
    }
}
