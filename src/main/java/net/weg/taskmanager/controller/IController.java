package net.weg.taskmanager.controller;

import net.weg.taskmanager.model.Equipe;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

public interface IController <T>{

    @GetMapping("/{id}")
    public ResponseEntity<T> findById(@PathVariable Integer id);
    @GetMapping
    public ResponseEntity<Collection<T>> findAll();
    @DeleteMapping("/{id}")
    public ResponseEntity<T> delete(@PathVariable Integer id);
    @PostMapping
    public ResponseEntity<T> create(@RequestBody T obj);
    @PutMapping
    public ResponseEntity<T> update(@RequestBody T obj);

}
