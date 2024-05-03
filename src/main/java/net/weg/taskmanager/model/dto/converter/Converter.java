package net.weg.taskmanager.model.dto.converter;

import java.util.Collection;

public interface Converter<T, S> {
    T convertOne(S obj);
    Collection<T> convertAll(Collection<S> objs);
}
