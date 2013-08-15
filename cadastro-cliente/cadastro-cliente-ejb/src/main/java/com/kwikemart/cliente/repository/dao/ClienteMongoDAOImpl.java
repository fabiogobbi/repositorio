package com.kwikemart.cliente.repository.dao;

import java.util.Set;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kwikemart.cliente.exception.DAOException;
import com.kwikemart.cliente.repository.ClienteDAO;
import com.kwikemart.cliente.repository.entity.Cliente;

public class ClienteMongoDAOImpl implements ClienteDAO{
    
    protected EntityManager entityManager;
    Logger logger = LoggerFactory.getLogger(ClienteMongoDAOImpl.class);
    
    
    public ClienteMongoDAOImpl(){
    }
    
    public Set<Cliente> findAll()  throws DAOException {
    	return null;
    }
    
    
    public void persist(Cliente transientInstance)   throws DAOException {
    }
    
    public void remove(Cliente persistentInstance)  throws DAOException {
    }
    
    public Cliente merge(Cliente detachedInstance)   throws DAOException {
    	return null;
    }
    
    public Cliente findById(String id)   throws DAOException {
    	return null;
    }

	public void atualizaSenha(String email, String senha) throws DAOException {
	}

}    
