package com.kwikemart.cliente.repository.dao;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kwikemart.cliente.exception.DAOException;
import com.kwikemart.cliente.repository.ClienteDAO;
import com.kwikemart.cliente.repository.entity.Cliente;

public class ClienteOracleDAOImpl implements ClienteDAO{
    
    protected EntityManager entityManager;
    Logger logger = LoggerFactory.getLogger(ClienteOracleDAOImpl.class);
    
    
    public ClienteOracleDAOImpl(EntityManager entityManager){
    	this.entityManager = entityManager;
    }
    
    @SuppressWarnings("unchecked")
    public Set<Cliente> findAll()  throws DAOException {
    	logger.info("ClienteOracleDAOImpl.findAll: INI");
    	try{
	        Query query = entityManager.createQuery("FROM " + Cliente.class.getName());
	        Set<Cliente> retorno = new HashSet<Cliente>();
	        for (Iterator<Cliente> it = query.getResultList().iterator(); it.hasNext(); ) {  
	            retorno.add((Cliente)it.next());
	        }
	        logger.info("ClienteOracleDAOImpl.findAll: INI");
	        return retorno;
    	}catch(RuntimeException re){
    		logger.error("findAll failed", re);
    		throw new DAOException(re);
    	}
    }
    
    
    public void persist(Cliente transientInstance)   throws DAOException {
    	logger.info("ClienteOracleDAOImpl.persist: INI");
        try {
            entityManager.persist(transientInstance);
            logger.debug("persist successful");
        } catch (RuntimeException re) {
        	logger.error("persist failed", re);
        	throw new DAOException(re);
        }
        logger.info("ClienteOracleDAOImpl.persist: FIM");
    }
    
    public void remove(Cliente persistentInstance)  throws DAOException {
    	logger.info("ClienteOracleDAOImpl.persist: INI");
        try {
            entityManager.remove(persistentInstance);
            logger.debug("remove successful");
        } catch (RuntimeException re) {
        	logger.error("remove failed", re);
        	throw new DAOException(re);
        }
        logger.info("ClienteOracleDAOImpl.persist: INI");
    }
    
    public Cliente merge(Cliente detachedInstance)   throws DAOException {
    	logger.info("ClienteOracleDAOImpl.merge: INI");
        try {
            Cliente result = entityManager.merge(detachedInstance);
            logger.info("ClienteOracleDAOImpl.merge: FIM");
            return result;
        } catch (RuntimeException re) {
        	logger.error("merge failed", re);
        	throw new DAOException(re);
        }
    }
    
    public Cliente findById(String id)   throws DAOException {
    	logger.info("ClienteOracleDAOImpl.findById: INI");
        try {
            Cliente instance = entityManager.find(Cliente.class, id);
            logger.info("ClienteOracleDAOImpl.findById: FIM");
            return instance;
        } catch (RuntimeException re) {
        	logger.error("get failed", re);
        	throw new DAOException(re);
        }
    }

	@Override
	public void atualizaSenha(String email, String senha) throws DAOException {
    	logger.info("ClienteOracleDAOImpl.findAll: INI");
    	try{
	        Query query = entityManager.createQuery("update Cliente set senha='" + senha + "' where email = '" + email + "'");
	        query.executeUpdate();
	        logger.info("ClienteOracleDAOImpl.findAll: INI");
    	}catch(RuntimeException re){
    		logger.error("findAll failed", re);
    		throw new DAOException(re);
    	}
	}
}
    
