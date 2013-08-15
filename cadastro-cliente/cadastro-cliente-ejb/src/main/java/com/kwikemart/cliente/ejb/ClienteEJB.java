package com.kwikemart.cliente.ejb;

import java.util.Calendar;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kwikemart.cliente.exception.DAOException;
import com.kwikemart.cliente.exception.NegocioException;
import com.kwikemart.cliente.repository.ClienteDAO;
import com.kwikemart.cliente.repository.OracleDAORepository;
import com.kwikemart.cliente.repository.PerfilDAO;
import com.kwikemart.cliente.repository.entity.Cliente;
import com.kwikemart.cliente.repository.entity.Perfil;
import com.kwikemart.cliente.repository.entity.TipoAcesso;
import com.kwikemart.cliente.util.CryptoHelper;

@Stateless(name="ClienteEJB")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ClienteEJB implements ClienteRemote{
    
    final Logger logger = LoggerFactory.getLogger(ClienteEJB.class);
    

	@EJB
	protected OracleDAORepository daoRepository;
	//protected MongoDAORepository daoRepository;
	
    public void adicionarClienteAdministrador(Cliente cliente) throws NegocioException{
        PerfilDAO perfilDAO = null;
        Perfil perfil = null;
        logger.info("ClienteEJB.adicionarClienteAdministrador: Inicio");
        try{
            perfilDAO = daoRepository.getPerfilDAO();
            perfil = perfilDAO.findById(Perfil.ADMINISTRADOR);
            cliente.setPerfil(perfil);
            this.adicionarCliente(cliente);
        }catch(DAOException e){
            throw new NegocioException(e);
        }catch (Exception e) {
        	throw new NegocioException("Erro ao adicionarClienteAdministrador: ", e);
		}
        logger.info("ClienteEJB.adicionarClienteAdministrador: FIM");
    }
    
    
    public void adicionarClienteConsumidor(Cliente cliente) throws NegocioException{
        PerfilDAO perfilDAO = null;
        Perfil perfil = null;
        logger.info("ClienteEJB.adicionarClienteConsumidor: Inicio");
        try{
            perfilDAO = daoRepository.getPerfilDAO();
            perfil = perfilDAO.findById(Perfil.CONSUMIDOR);
            cliente.setPerfil(perfil);
            this.adicionarCliente(cliente);
        }catch(DAOException e){
            throw new NegocioException(e);
        }catch (Exception e) {
        	throw new NegocioException("Erro ao adicionarClienteConsumidor: ", e);
		}
        logger.info("ClienteEJB.adicionarClienteConsumidor: FIM");
    }
    
    
    private void adicionarCliente(Cliente cliente) throws NegocioException{
        ClienteDAO clienteDAO = null;
        logger.info("ClienteEJB.adicionarCliente: Inicio");
        try{
        	if(TipoAcesso.ACESSO_NORMAL.getTipoAcesso() == cliente.getTipoAcesso()){
        		cliente.setAtivo(true);
        	}
        	clienteDAO = daoRepository.getClienteDAO();
            clienteDAO.persist(cliente);
        }catch(DAOException e){
            throw new NegocioException(e);
        }catch (Exception e) {
        	throw new NegocioException("Erro ao adicionarCliente: ", e);
		}
        logger.info("ClienteEJB.adicionarCliente: FIM");
    }
    
    public void atualizaClienteAdministrador(Cliente cliente) throws NegocioException{
        PerfilDAO perfilDAO = null;
        Perfil perfil = null;
        logger.info("ClienteEJB.atualizaClienteAdministrador: Inicio");
        try{
            perfilDAO = daoRepository.getPerfilDAO();
            perfil = perfilDAO.findById(Perfil.ADMINISTRADOR);
            cliente.setPerfil(perfil);
            this.atualizaCliente(cliente);
        }catch(DAOException e){
            throw new NegocioException(e);
        }catch (Exception e) {
        	throw new NegocioException("Erro ao atualizaClienteAdministrador: ", e);
		}
        logger.info("ClienteEJB.atualizaClienteAdministrador: FIM");	
    }
    
    public void atualizaClienteConsumidor(Cliente cliente) throws NegocioException{
        PerfilDAO perfilDAO = null;
        Perfil perfil = null;
        logger.info("ClienteEJB.atualizaClienteConsumidor: Inicio");
        try{
            perfilDAO = daoRepository.getPerfilDAO();
            perfil = perfilDAO.findById(Perfil.CONSUMIDOR);
            cliente.setPerfil(perfil);
            this.atualizaCliente(cliente);
        }catch(DAOException e){
            throw new NegocioException(e);
        }catch (Exception e) {
        	throw new NegocioException("Erro ao atualizaClienteConsumidor: ", e);
		}
        logger.info("ClienteEJB.atualizaClienteConsumidor: FIM");
    }
    
    private void atualizaCliente(Cliente cliente) throws NegocioException{
        ClienteDAO clienteDAO = null;
        logger.info("ClienteEJB.atualizaCliente: Inicio");
        try{
        	if(TipoAcesso.ACESSO_NORMAL.getTipoAcesso() == cliente.getTipoAcesso()){
        		cliente.setAtivo(true);
        	}
        	clienteDAO = daoRepository.getClienteDAO();
        	clienteDAO.merge(cliente);
        }catch(DAOException e){
            throw new NegocioException(e);
        }catch (Exception e) {
        	throw new NegocioException("Erro ao atualizaCliente: ", e);
		}
        logger.info("ClienteEJB.atualizaCliente: FIM");
    }
    
    public Set<Cliente> listarClientes() throws NegocioException{
        logger.info("ClienteEJB.listarClientes: Inicio");
        ClienteDAO clienteDAO = null;
        Set<Cliente> clientes = null;
        try{
            clienteDAO = daoRepository.getClienteDAO();
            clientes = clienteDAO.findAll();
        }catch(DAOException e){
            throw new NegocioException(e);
        }catch (Exception e) {
        	throw new NegocioException("Erro ao listarClientes: ", e);
		}
        logger.info("ClienteEJB.listarClientes: Fim");
        return clientes;
    }
    
    public Cliente obterClientePorEmail(String email, boolean crypto) throws NegocioException{
        logger.info("ClienteEJB.obterClientePorEmail: Inicio");
        ClienteDAO clienteDAO = null;
        Cliente cliente = null;
        try{
            clienteDAO = daoRepository.getClienteDAO();
            if(crypto){
            	email = CryptoHelper.descriptografiaBase64(email);
            	String[] temp = email.split("\\|\\|");
            	if(Calendar.getInstance().getTimeInMillis() - new Long(temp[1]).longValue() > 6000000L){
            		throw new NegocioException("Tempo para troca de senha Expirou. Solicite novamente a troca de senha.");
            	}else{
            		email = temp[0];
            	}
            }
            cliente = clienteDAO.findById(email);
            if(cliente == null){
            	throw new NegocioException("Cliente nao encontrado");
            }
        }catch(DAOException e){
            throw new NegocioException(e);
        }catch (Exception e) {
        	throw new NegocioException("Erro ao obterClientePorEmail: ", e);
		}
        logger.info("ClienteEJB.obterClientePorEmail: Fim");
        return cliente;
    }


	@Override
	public void atualizaClienteSenha(String email, String senha) throws NegocioException{
	    logger.info("ClienteEJB.atualizaClienteSenha: Inicio");
	    ClienteDAO clienteDAO = null;
	    try{
	        clienteDAO = daoRepository.getClienteDAO();
	        clienteDAO.atualizaSenha(email, senha);
	    }catch(DAOException e){
	        throw new NegocioException(e);
	    }catch (Exception e) {
	    	throw new NegocioException("Erro ao atualizaClienteSenha: ", e);
		}
	    logger.info("ClienteEJB.atualizaClienteSenha: Fim");
	}
    
    
}