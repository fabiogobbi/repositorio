package com.kwikemart.cliente.repository.dao;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kwikemart.cliente.exception.DAOException;
import com.kwikemart.cliente.repository.PerfilDAO;
import com.kwikemart.cliente.repository.entity.Perfil;

public class PerfilOracleDAOImpl implements PerfilDAO{
    
    protected EntityManager entityManager;
    
    private final Logger logger = LoggerFactory.getLogger(PerfilOracleDAOImpl.class);
    
    public PerfilOracleDAOImpl(EntityManager entityManager) {
	      this.entityManager = entityManager;
	}
    
    public Set<Perfil> findAll() throws DAOException {
    	logger.info("PerfilOracleDAOImpl.findAll: INI");
    	try{
	        Query query = entityManager.createQuery("FROM " + Perfil.class.getName());
	        Set<Perfil> retorno = new HashSet<Perfil>();
	        while(query.getResultList().iterator().hasNext()){
	            retorno.add((Perfil)query.getResultList().iterator().next());
	        }
	        logger.info("PerfilOracleDAOImpl.findAll: FIM");
	        return retorno;
    	}catch(RuntimeException re){
    		logger.error("findAll failed", re);
    		throw new DAOException(re);
    	}
    }
    
    
    public void persist(Perfil transientInstance)  throws DAOException {
    	logger.info("PerfilOracleDAOImpl.persist: INI");
        try {
            entityManager.persist(transientInstance);
            logger.debug("persist successful");
        } catch (RuntimeException re) {
        	logger.error("persist failed", re);
        	throw new DAOException(re);
        }
        logger.info("PerfilOracleDAOImpl.persist: FIM");
    }
    
    public void remove(Perfil persistentInstance)  throws DAOException {
    	logger.info("PerfilOracleDAOImpl.remove: INI");
        try {
            entityManager.remove(persistentInstance);
            logger.debug("remove successful");
        } catch (RuntimeException re) {
        	logger.error("remove failed", re);
        	throw new DAOException(re);
        }
        logger.info("PerfilOracleDAOImpl.remove: FIM");
    }
    
    public Perfil merge(Perfil detachedInstance)  throws DAOException {
    	logger.info("PerfilOracleDAOImpl.merge: INI");
        try {
            Perfil result = entityManager.merge(detachedInstance);
            logger.info("PerfilOracleDAOImpl.merge: FIM");
            return result;
        } catch (RuntimeException re) {
        	logger.error("merge failed", re);
        	throw new DAOException(re);
        }
        
    }
    
    public Perfil findById(String id)  throws DAOException {
    	logger.info("PerfilOracleDAOImpl.findById: INI");
        try {
            Perfil instance = entityManager.find(Perfil.class, id);
            logger.info("PerfilOracleDAOImpl.findById: FIM");
            return instance;
        } catch (RuntimeException re) {
        	logger.error("get failed", re);
        	throw new DAOException(re);
        }
    }
}
    
