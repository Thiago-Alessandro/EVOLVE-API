package net.weg.taskmanager.service;

import net.weg.taskmanager.model.Equipe;

import java.util.Collection;

public interface IService <T> {

    public T findById(Integer id);
    public Collection<T> findAll();
    public void delete(Integer id);
    public T create(T obj);
    public T update(T obj);
}
