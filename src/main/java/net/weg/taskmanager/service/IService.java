package net.weg.taskmanager.service;

import java.util.Collection;

public interface IService <T> {

    T findById(Integer id);
    Collection<T> findAll();
    void delete(Integer id);
    T create(T obj);
    T update(T obj);
}
