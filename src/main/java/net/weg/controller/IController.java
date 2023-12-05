package net.weg.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

interface IController<T> {

    @GetMapping("/{id}")
    ResponseEntity<T> findById(@PathVariable Integer id);

    @GetMapping
    ResponseEntity<Collection<T>> findAll();

    @DeleteMapping("/{id}")
    ResponseEntity<T> delete(@PathVariable Integer id);

    @PostMapping
    ResponseEntity<T> create(@RequestBody T obj);

    @PutMapping
    ResponseEntity<T> update(@RequestBody T obj);
}