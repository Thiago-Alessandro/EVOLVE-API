package net.weg.taskmanager.service;

import java.util.Collection;

public interface IService <T> {

    T findById(Long id);
    Collection<T> findAll();
    void delete(Long id);
    T create(T obj);
    T update(T obj);
}
