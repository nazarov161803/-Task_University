package com.foxminded.university.dao;

import java.util.List;
import java.util.Optional;

public abstract class AbstractDao implements Dao {


    protected abstract Object doSave(Object object);

    protected abstract void doRemoveAll();

    protected abstract void doRemove(Object object);

    protected abstract Optional doGetOne(int id);

    protected abstract void doUpdate(Object object);

    protected abstract List doGetAll();

    @Override
    public void removeAll() {
        doRemoveAll();
    }

    @Override
    public Object save (Object object) {
        return doSave(object);
    }

    @Override
    public void remove(Object object) {
        doRemove(object);
    }

    @Override
    public Optional getOne(int id) {
        return doGetOne(id);

    }

    @Override
    public void update(Object object) {
        doUpdate(object);

    }

    @Override
    public List getAll() {
        return doGetAll();
    }
}
