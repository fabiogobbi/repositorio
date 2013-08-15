package com.kwikemart.cliente.ejb;

import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kwikemart.cliente.exception.DAOException;
import com.kwikemart.cliente.exception.NegocioException;
import com.kwikemart.cliente.repository.EnderecoDAO;
import com.kwikemart.cliente.repository.OracleDAORepository;
import com.kwikemart.cliente.repository.entity.Endereco;

@Stateless(name="EnderecoEJB")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class EnderecoEJB implements EnderecoRemote{
    
	@EJB
	protected OracleDAORepository daoRepository;
    
	final Logger logger = LoggerFactory.getLogger(EnderecoEJB.class);
    
    public void incluirEndereco(Endereco endereco) throws NegocioException{
        EnderecoDAO enderecoDAO = null;
        logger.info("EnderecoEJB.incluirEndereco: Inicio");
        try{
        	enderecoDAO = daoRepository.getEnderecoDAO();
            enderecoDAO.persist(endereco);
        }catch(DAOException e){
            throw new NegocioException(e);
        }catch (Exception e) {
        	throw new NegocioException("Erro ao incluirEndereco: ", e);
		}
    }
    
    
    public void excluirEndereco(Long idEndereco) throws NegocioException{
        EnderecoDAO enderecoDAO = null;
        Endereco endereco = null;
        logger.info("EnderecoEJB.excluirEndereco: Inicio");
        try{
        	enderecoDAO = daoRepository.getEnderecoDAO();
        	endereco = enderecoDAO.findById(idEndereco.toString(), null).iterator().next();
        	enderecoDAO.remove(endereco);
        }catch(DAOException e){
            throw new NegocioException(e);
        }catch (Exception e) {
        	throw new NegocioException("Erro ao excluirEndereco: ", e);
		}
        logger.info("EnderecoEJB.excluirEndereco: FIM");
    }
    
    public void atualizaEndereco(Endereco endereco) throws NegocioException{
        EnderecoDAO enderecoDAO = null;
        logger.info("EnderecoEJB.excluirEndereco: Inicio");
        try{
        	enderecoDAO = daoRepository.getEnderecoDAO();
        	enderecoDAO.merge(endereco);
        }catch(DAOException e){
            throw new NegocioException(e);
        }catch (Exception e) {
        	throw new NegocioException("Erro ao atualizaEndereco: ", e);
		}
        logger.info("EnderecoEJB.atualizaEndereco: FIM");
    }
    
    
    public Set<Endereco> listarEnderecos() throws NegocioException{
        logger.info("EnderecoEJB.listarEnderecos: Inicio");
        EnderecoDAO enderecoDAO = null;
        Set<Endereco> enderecos = null;
        try{
        	enderecoDAO = daoRepository.getEnderecoDAO();
        	enderecos = enderecoDAO.findAll();
        }catch(DAOException e){
            throw new NegocioException(e);
        }catch (Exception e) {
        	throw new NegocioException("Erro ao listarEnderecos: ", e);
		}
        logger.info("EnderecoEJB.listarEnderecos: Fim");
        return enderecos;
    }
    
    public Set<Endereco> obterEnderecosPorEmailId(String id, String email) throws NegocioException{
        logger.info("EnderecoEJB.obterEnderecoPorEmail: Inicio");
        EnderecoDAO enderecoDAO = null;
        Set<Endereco> enderecos = null;
        try{
        	enderecoDAO = daoRepository.getEnderecoDAO();
        	enderecos = enderecoDAO.findById(id, email);
        }catch(DAOException e){
            throw new NegocioException(e);
        }catch (Exception e) {
        	throw new NegocioException("Erro ao obterEnderecosPorEmailId: ", e);
		}
        logger.info("EnderecoEJB.obterEnderecoPorEmail: Fim");
        return enderecos;
    }
    
}