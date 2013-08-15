package com.kwikemart.cliente.repository;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kwikemart.cliente.exception.DAOException;

@Stateless
public class EmailSender {

	final Logger logger = LoggerFactory.getLogger(EmailSender.class);
	
    @Resource(mappedName="java:jboss/mail/EmailSenderDS")
    private Session mailSession;

    public void enviarEmail(String email, String conteudoEmail) throws DAOException{
    	MimeMessage m = null;
    	Address from = null;
    	Address[] to = null;
    		try{
                m = new MimeMessage(mailSession);
                from = new InternetAddress(email);
                to = new InternetAddress[]
                		{new InternetAddress(email) };

                m.setFrom(from);
                m.setRecipients(Message.RecipientType.TO, to);
                m.setSubject("Kwik-E-Mart - Solicitação de Senha");
                m.setSentDate(new java.util.Date());

                m.setContent(conteudoEmail, "text/plain");
                logger.debug("Email sendo eviado: \n" + conteudoEmail);
                Transport.send(m);
            } catch (javax.mail.MessagingException e){
                logger.error("Erro ao enviar email: ", e);
                throw new DAOException("Erro ao montar/enviar Email.", e);
            }
        }
}

