package com.foxminded.university.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {

    void removeAll();

    T save(T t);

    void remove(T t);

    Optional getOne(int id);

    void update(T t);

    List<T> getAll();

}
