package com.kwikemart.cliente.repository.dao;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kwikemart.cliente.ejb.EnderecoEJB;
import com.kwikemart.cliente.exception.DAOException;
import com.kwikemart.cliente.repository.EnderecoDAO;
import com.kwikemart.cliente.repository.entity.Endereco;

public class EnderecoOracleDAOImpl implements EnderecoDAO{
    
	final Logger logger = LoggerFactory.getLogger(EnderecoEJB.class);
    protected EntityManager entityManager;
    
    public EnderecoOracleDAOImpl(EntityManager entityManager){
    	this.entityManager = entityManager;
    }
    
    public Set<Endereco> findAll()  throws DAOException {
    	logger.info("EnderecoOracleDAOImpl.findAll: INI");
    	try{
	        Query query = entityManager.createQuery("FROM " + Endereco.class.getName());
	        Set<Endereco> retorno = new HashSet<Endereco>();
	        while(query.getResultList().iterator().hasNext()){
	            retorno.add((Endereco)query.getResultList().iterator().next());
	        }
	        logger.info("EnderecoOracleDAOImpl.findAll: FIM");
	        return retorno;
    	}catch(RuntimeException re){
    		logger.error("findAll failed", re);
    		throw new DAOException(re);
    	}
    }
    
    
    public void persist(Endereco transientInstance)  throws DAOException {
    	logger.info("EnderecoOracleDAOImpl.persist: INI");
        try {
            entityManager.persist(transientInstance);
            logger.info("EnderecoOracleDAOImpl.persist: FIM");
        } catch (RuntimeException re) {
        	logger.error("persist failed", re);
        	throw new DAOException(re);
        }
    }
    
    public void remove(Endereco persistentInstance)  throws DAOException {
    	logger.info("EnderecoOracleDAOImpl.remove: INI");
        try {
            entityManager.remove(persistentInstance);
            logger.info("EnderecoOracleDAOImpl.remove: FIM");
        } catch (RuntimeException re) {
        	logger.error("remove failed", re);
        	throw new DAOException(re);
        }
    }
    
    public Endereco merge(Endereco detachedInstance)  throws DAOException {
    	logger.info("EnderecoOracleDAOImpl.merge: INI");
        try {
            Endereco result = entityManager.merge(detachedInstance);
            logger.info("EnderecoOracleDAOImpl.merge: FIM");
            return result;
        } catch (RuntimeException re) {
        	logger.error("merge failed", re);
        	throw new DAOException(re);
        }
    }

    public Set<Endereco> findById(String idEndereco, String email)  throws DAOException {
    	logger.info("EnderecoOracleDAOImpl.findById: INI");
    	logger.debug("getting Cliente instance with id: " + idEndereco + ", " + email);
        StringBuffer sb = new StringBuffer();
    	try {
    		sb.append("SELECT e FROM Endereco e WHERE ");
    		if(email != null){
    			sb.append("e.cliente = '" + email + "' ");
    		}
    		if(idEndereco != null && email != null){
    			sb.append("AND ");
    		}
    		if(idEndereco != null){
    			sb.append("e.id = '" + idEndereco + "'");
    		}
        	 Query query = entityManager.createQuery(sb.toString());
             Set<Endereco> retorno = new HashSet<Endereco>();
             for (int i = 0; i < query.getResultList().size(); i++ ) {  
                 retorno.add((Endereco)query.getResultList().get(i));
             } 
             logger.info("EnderecoOracleDAOImpl.findById: FIM");
            return retorno;
        } catch (RuntimeException re) {
            logger.error("get failed", re);
            throw re;
        }
    }
}
    
