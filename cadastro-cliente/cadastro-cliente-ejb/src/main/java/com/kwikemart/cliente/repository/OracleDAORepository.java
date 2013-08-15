package com.kwikemart.cliente.repository;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.kwikemart.cliente.repository.dao.ClienteOracleDAOImpl;
import com.kwikemart.cliente.repository.dao.EnderecoOracleDAOImpl;
import com.kwikemart.cliente.repository.dao.PerfilOracleDAOImpl;

@Stateless
public class OracleDAORepository {
	
    @PersistenceContext(unitName = "kwikemart_persistence")
	protected EntityManager entityManager;
    
    public ClienteDAO getClienteDAO() {
        return new ClienteOracleDAOImpl(this.entityManager);
    }
    
    public PerfilDAO getPerfilDAO() {
        return new PerfilOracleDAOImpl(this.entityManager);
    }

    public EnderecoDAO getEnderecoDAO() {
        return new EnderecoOracleDAOImpl(this.entityManager);
    }

    
    
}
