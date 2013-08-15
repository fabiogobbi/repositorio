package com.kwikemart.cliente.ejb;

import java.util.Calendar;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kwikemart.cliente.exception.DAOException;
import com.kwikemart.cliente.exception.NegocioException;
import com.kwikemart.cliente.repository.ClienteDAO;
import com.kwikemart.cliente.repository.EmailSender;
import com.kwikemart.cliente.repository.OracleDAORepository;
import com.kwikemart.cliente.repository.entity.Cliente;
import com.kwikemart.cliente.util.CryptoHelper;

@Stateless(name="EmailEJB")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class EmailEJB implements EmailRemote{
    
	@EJB
	EmailSender sender;
	
	@EJB
	protected OracleDAORepository daoRepository;
	
	final Logger logger = LoggerFactory.getLogger(EmailEJB.class);
    
	public void enviarEmailSenha(String emailUsuario) throws NegocioException{
		StringBuffer sbContent = new StringBuffer();    
		Cliente cliente = null;
		ClienteDAO clienteDAO = null;
		
        try{
        	clienteDAO = daoRepository.getClienteDAO();
        	cliente = clienteDAO.findById(emailUsuario);
        	
            sbContent.append("Kwik-E-Mart e-Commerce informa: Sua solicitação de senha chegou.");
            sbContent.append("\n\n");
            sbContent.append("Usuário: " + cliente.getEmail());
            sbContent.append("\n");
            sbContent.append("Senha: " + cliente.getSenha());
            sbContent.append("\n\n");
            sbContent.append("Lembramos a importancia de alterar sua senha regularmente.");
            sbContent.append("\n\n");
            sbContent.append("Atenciosamente,");
            sbContent.append("\n");
            sbContent.append("Equipe Kwik-E-Mart e-Commerce.");
            
        	sender.enviarEmail(cliente.getEmail(), sbContent.toString());
        } catch (DAOException e) {
        	logger.error("Erro ao enviar Email: ", e);
        	throw new NegocioException(e);
        } catch (Exception e) {
        	logger.error("Erro ao montar Email: ", e);
        	throw new NegocioException("Erro ao montar Email.", e);
        }
        logger.info("EmailEJB.enviarSenhaEmail: Fim");
    }
	
	
	public void resetarSenha(String emailUsuario) throws NegocioException{
		StringBuffer sbContent = new StringBuffer();    
		Cliente cliente = null;
		ClienteDAO clienteDAO = null;
		
        try{
        	clienteDAO = daoRepository.getClienteDAO();
        	cliente = clienteDAO.findById(emailUsuario);
        	
            sbContent.append("Kwik-E-Mart e-Commerce informa: Sua solicitação de senha chegou.");
            sbContent.append("\n\n");
            sbContent.append("Usuário: " + cliente.getEmail());
            sbContent.append("\n\n");
            sbContent.append("Para resetar sua senha, clique no link abaixo:");
            sbContent.append("\n\n");
            sbContent.append("http://localhost:8080/cadastro_cliente/cliente/clienteTrocaSenha?id=" + CryptoHelper.criptografiaBase64(cliente.getEmail() + "||" + Calendar.getInstance().getTimeInMillis()));
            sbContent.append("\n\n");
            sbContent.append("Atenciosamente,");
            sbContent.append("\n");
            sbContent.append("Equipe Kwik-E-Mart e-Commerce.");
            
        	sender.enviarEmail(cliente.getEmail(), sbContent.toString());
        } catch (DAOException e) {
        	logger.error("Erro ao enviar Email: ", e);
        	throw new NegocioException(e);
        } catch (Exception e) {
        	logger.error("Erro ao montar Email: ", e);
        	throw new NegocioException("Erro ao montar Email.", e);
        }
        logger.info("EmailEJB.enviarSenhaEmail: Fim");
    }
    
}