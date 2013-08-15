package com.kwikemart.cliente.repository;

import java.util.Set;

import com.kwikemart.cliente.exception.DAOException;
import com.kwikemart.cliente.repository.entity.Cliente;

public interface ClienteDAO {
    public void persist(Cliente transientInstance) throws DAOException ;
    public void remove(Cliente persistentInstance) throws DAOException ;
    public void atualizaSenha(String email, String senha) throws DAOException ;
    public Cliente merge(Cliente detachedInstance) throws DAOException ;
    public Cliente findById(String id) throws DAOException ;
    public Set<Cliente> findAll() throws DAOException ;
}
