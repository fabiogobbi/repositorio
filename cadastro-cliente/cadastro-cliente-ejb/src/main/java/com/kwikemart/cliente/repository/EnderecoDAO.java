package com.kwikemart.cliente.repository;

import java.util.Set;

import com.kwikemart.cliente.exception.DAOException;
import com.kwikemart.cliente.repository.entity.Endereco;

public interface EnderecoDAO {
    public void persist(Endereco transientInstance)  throws DAOException ;
    public void remove(Endereco persistentInstance)  throws DAOException ;
    public Endereco merge(Endereco detachedInstance)  throws DAOException ;
    public Set<Endereco> findById(String idEndereco, String email) throws DAOException ;
    public Set<Endereco> findAll()  throws DAOException ;
}
