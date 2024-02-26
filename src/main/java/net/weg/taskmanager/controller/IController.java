package net.weg.taskmanager.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

public interface IController <T>{

    @GetMapping("/{id}")
    public ResponseEntity<T> findById(@PathVariable Long id);
    @GetMapping
    public ResponseEntity<Collection<T>> findAll();
    @DeleteMapping("/{id}")
    public ResponseEntity<T> delete(@PathVariable Long id);
    @PostMapping
    public ResponseEntity<T> create(@RequestBody T obj);
    @PutMapping
    public ResponseEntity<T> update(@RequestBody T obj);

}
