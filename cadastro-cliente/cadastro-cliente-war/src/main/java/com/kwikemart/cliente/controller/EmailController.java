package com.kwikemart.cliente.controller;


import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kwikemart.cliente.ejb.EmailRemote;
import com.kwikemart.cliente.exception.NegocioException;
import com.kwikemart.cliente.util.ExceptionUtil;
import com.kwikemart.cliente.util.SessionAttributes;
import com.kwikemart.cliente.util.Views;

@Controller
@RequestMapping(value="/email")
public class EmailController{
	
    final Logger logger = LoggerFactory.getLogger(EmailController.class);
      
    //CDI
    @EJB(mappedName="java:global/cadastro-cliente-ejb/EmailEJB!com.kwikemart.cliente.ejb.EmailRemote")
    EmailRemote emailRemote;
    
    @Autowired
    private HttpServletRequest request;
    
    @RequestMapping(value="/esqueciSenha", method = RequestMethod.GET)
    public ModelAndView esqueciSenha(@RequestParam(value=SessionAttributes.J_USERNAME) String emailUsuario){
        logger.info("EmailController.esqueciSenha: Inicio");
        ModelAndView mav = null;
        mav = new ModelAndView(Views.LOGIN);
        try{
		    emailRemote.enviarEmailSenha(emailUsuario);
		    mav.addObject(SessionAttributes.TITULO_MENSAGEM, "Email Enviado.");
		    mav.addObject(SessionAttributes.SUCESSO, "Envio de senha por email concluido.");
		    logger.info("EmailController.esqueciSenha: Fim");
		    
        }catch(NegocioException ne){
        	logger.error("EmailController.esqueciSenha: Erro: ", ne);
            mav.addObject(SessionAttributes.TITULO_MENSAGEM, "Erro Inesperado.");
            mav.addObject(SessionAttributes.MENSAGEM, ExceptionUtil.obtemErroOriginal(ne).getMessage());
       }catch (Exception e) {
    	   logger.error("EmailController.esqueciSenha: Erro: ", e);
	         mav.addObject(SessionAttributes.TITULO_MENSAGEM, "Erro Inesperado.");
	         mav.addObject(SessionAttributes.MENSAGEM, ExceptionUtil.obtemErroOriginal(e).getMessage());
		}
        return mav;
    } 
    
    
    @RequestMapping(value="/resetSenha", method = RequestMethod.GET)
    public ModelAndView resetSenha(@RequestParam(value=SessionAttributes.J_USERNAME) String emailUsuario){
        logger.info("EmailController.esqueciSenha: Inicio");
        ModelAndView mav = null;
        mav = new ModelAndView(Views.LOGIN);
        try{
		    emailRemote.resetarSenha(emailUsuario);
		    mav.addObject(SessionAttributes.TITULO_MENSAGEM, "Email Enviado.");
		    mav.addObject(SessionAttributes.SUCESSO, "Envio de solictação de nova senha por email concluido.");
		    logger.info("EmailController.esqueciSenha: Fim");
		    
        }catch(NegocioException ne){
        	logger.error("EmailController.resetSenha: Erro: ", ne);
            mav.addObject(SessionAttributes.TITULO_MENSAGEM, "Erro Inesperado.");
            mav.addObject(SessionAttributes.MENSAGEM,ExceptionUtil.obtemErroOriginal(ne).getMessage());
       }catch (Exception e) {
    	    logger.error("EmailController.resetSenha: Erro: ", e);
	         mav.addObject(SessionAttributes.TITULO_MENSAGEM, "Erro Inesperado.");
	         mav.addObject(SessionAttributes.MENSAGEM, ExceptionUtil.obtemErroOriginal(e).getMessage());
		}
        return mav;
    } 
}