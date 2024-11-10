package org.example.db.services;


import org.example.api.geocode.Coordinates;
import org.example.db.daos.GenericDao;
import org.example.db.models.Location;

import java.io.Serializable;
import java.util.List;

public class GenericService <T, ID extends Serializable> {
    private final GenericDao<T, ID> genericDao;

    public GenericService(GenericDao<T, ID> genericDao) {
        this.genericDao = genericDao;
    }

    public T save(T entity){
        genericDao.save(entity);

        return entity;
    }

    public T getById(ID id){
        return genericDao.getById(id);
    }

    public T getByCoords(Coordinates coordinates){
        return genericDao.getByCoords(coordinates);
    }

    public List<T> getByLocation(Location location){
        return genericDao.getByLocation(location);
    }

    public List<T> getAll() { return genericDao.getAll(); }

    public void update(T entity){
        genericDao.update(entity);
    }

    public void delete(T entity){
        genericDao.delete(entity);
    }
}
