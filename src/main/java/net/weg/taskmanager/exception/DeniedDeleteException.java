package net.weg.taskmanager.exception;

public class DeniedDeleteException extends RuntimeException{
    public DeniedDeleteException(){
        super("Access Denied");
    }

}
