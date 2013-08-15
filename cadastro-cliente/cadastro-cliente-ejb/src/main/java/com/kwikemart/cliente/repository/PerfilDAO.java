package com.kwikemart.cliente.repository;

import java.util.Set;

import com.kwikemart.cliente.exception.DAOException;
import com.kwikemart.cliente.repository.entity.Perfil;

public interface PerfilDAO {
    public void persist(Perfil transientInstance)  throws DAOException ;
    public void remove(Perfil persistentInstance)  throws DAOException ;
    public Perfil merge(Perfil detachedInstance)  throws DAOException ;
    public Perfil findById(String id)  throws DAOException ;
    public Set<Perfil> findAll()  throws DAOException ;
}
