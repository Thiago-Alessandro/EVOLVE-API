package net.weg.service;

import java.util.Collection;

public interface IService <T> {

    public T findById(Integer id);
    public Collection<T> findAll();
    public void delete(Integer id);
    public T create(T obj);
    public T update(T obj);
}
