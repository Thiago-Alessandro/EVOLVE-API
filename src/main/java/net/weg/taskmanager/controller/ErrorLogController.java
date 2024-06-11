package net.weg.taskmanager.controller;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.Consumer;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/error/logs")
@AllArgsConstructor
public class ErrorLogController {

    private final Consumer consumer = new Consumer();

    @GetMapping()
    public Collection<String> getErrorLogs(){
        return consumer.getAllLogs();
    }

}
