package com.kwikemart.cliente.controller;


import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.kwikemart.cliente.util.ExceptionUtil;
import com.kwikemart.cliente.util.FacebookUtils.FacebookConstantes;
import com.kwikemart.cliente.util.SessionAttributes;
import com.kwikemart.cliente.util.Views;

@Controller
@RequestMapping(value="/login")
public class LoginController{
	
    final Logger logger = LoggerFactory.getLogger(LoginController.class);
      
    FacebookConnectionFactory connectionFactory;
    
    @Autowired
    private HttpServletRequest request;
    
    @RequestMapping(value="/clienteLogin", method = RequestMethod.GET)
    public ModelAndView clienteLogin(){
        logger.info("LoginController.clienteLogin: Inicio");
        ModelAndView mav = new ModelAndView(Views.LOGIN);
        logger.info("LoginController.clienteLogin: Fim");
        return mav;
    } 
    
    @RequestMapping(value="/acessoNegado", method = RequestMethod.GET)
    public ModelAndView acessoNegado(){
        logger.info("LoginController.acessoNegado: Inicio");
        ModelAndView mav = new ModelAndView(Views.LOGIN);
        mav.addObject(SessionAttributes.TITULO_MENSAGEM, "Acesso Negado.");
        mav.addObject(SessionAttributes.MENSAGEM, "Voc&ecirc; n&atilde;o tem permiss&atilde;o para acessar o Sistema.");
        logger.info("LoginController.acessoNegado: Fim");
        return mav;
    } 
    
    @RequestMapping(value="/loginErro", method = RequestMethod.GET)
    public ModelAndView loginErro(){
        logger.info("LoginController.loginErro: Inicio");
        ModelAndView mav = new ModelAndView(Views.LOGIN);
        mav.addObject(SessionAttributes.TITULO_MENSAGEM, "Erro ao Autenticar Usu&aacute;rio.");
        mav.addObject(SessionAttributes.MENSAGEM, "Ocorreu um erro ao autenticar o usu&aacute;rio. Verifique seu email e senha.");
        logger.info("LoginController.loginErro: Fim");
        return mav;
    } 
    
	@RequestMapping(value="/loginFacebook", method=RequestMethod.GET)
    public ModelAndView autenticaFacebook() {
		ModelAndView mav = null;
		try{
	        connectionFactory = new FacebookConnectionFactory(FacebookConstantes.APP_ID.getCodigo(), FacebookConstantes.APP_SECRET.getCodigo());
	        OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
	        OAuth2Parameters oAuth2Parameters = new OAuth2Parameters();
	        oAuth2Parameters.setScope("user_about_me,user_birthday,user_likes,user_status,publish_stream");
	        String serverPath  = Views.URL_HOME_FULL;
	        oAuth2Parameters.setRedirectUri(serverPath);
	        String urlAutorizada = oauthOperations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, oAuth2Parameters);
	        mav = new ModelAndView(Views.REDIRECT_LOGIN, SessionAttributes.URL_AUTORIZADA, urlAutorizada);
	        return mav; 
		}catch (Exception e){
			logger.error("LoginController.loginFacebook: Erro: ", e);
	      	mav = new ModelAndView(Views.LOGIN);
	        mav.addObject(SessionAttributes.TITULO_MENSAGEM, "Erro Inesperado.");
	        mav.addObject(SessionAttributes.MENSAGEM, ExceptionUtil.obtemErroOriginal(e).getMessage());
			return mav;
		}
                
	}
    
    
}