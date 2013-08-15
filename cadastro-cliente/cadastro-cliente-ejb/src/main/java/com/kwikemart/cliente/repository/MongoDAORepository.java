package com.kwikemart.cliente.repository;

import javax.ejb.Stateless;

import com.kwikemart.cliente.repository.dao.ClienteMongoDAOImpl;

@Stateless
public class MongoDAORepository {
	
    
    public ClienteDAO getClienteDAO() {
        return new ClienteMongoDAOImpl();
    }
    
    public PerfilDAO getPerfilDAO() {
        return null;
    }

    public EnderecoDAO getEnderecoDAO() {
        return null;
    }

    
    
}
