package com.kwikemart.cliente.controller;


import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookProfile;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kwikemart.cliente.ejb.ClienteRemote;
import com.kwikemart.cliente.exception.NegocioException;
import com.kwikemart.cliente.repository.entity.Cliente;
import com.kwikemart.cliente.repository.entity.SexoEnum;
import com.kwikemart.cliente.repository.entity.TipoAcesso;
import com.kwikemart.cliente.util.ExceptionUtil;
import com.kwikemart.cliente.util.FacebookUtils.FacebookConstantes;
import com.kwikemart.cliente.util.SessionAttributes;
import com.kwikemart.cliente.util.Views;
 
@Controller
@RequestMapping(value="/cliente")
public class ClienteController{
	
    final Logger logger = LoggerFactory.getLogger(ClienteController.class);
      
    //CDI
    @EJB(mappedName="java:global/cadastro-cliente-ejb/ClienteEJB!com.kwikemart.cliente.ejb.ClienteRemote")
    ClienteRemote clienteRemote;
    
    @Autowired
    private HttpServletRequest request;
    
    @RequestMapping(value="/clienteHomeLogin", method = RequestMethod.POST)
    public String clienteHomeLogin(){
    	return Views.REDIRECT_SPRING + Views.CLIENTE_HOME;
    } 
    
    @RequestMapping(value="/clienteHome", method = RequestMethod.GET)
    public ModelAndView clienteHome(){
        logger.info("ClienteController.clienteHome: Inicio");
        ModelAndView mav = null;
        try{
	        Cliente cliente = clienteRemote.obterClientePorEmail(request.getUserPrincipal().getName(), false);
	        request.getSession().setAttribute(SessionAttributes.CLIENTE, cliente);
	        mav = new ModelAndView(Views.CLIENTE_HOME);
	        logger.info("ClienteController.clienteHome: Fim");
        }catch(NegocioException ne){
        	 logger.error("ClienteController.clienteHome: Erro: ", ne);
        	 mav = new ModelAndView(Views.CLIENTE_HOME);
             mav.addObject(SessionAttributes.TITULO_MENSAGEM, "Erro Inesperado.");
             mav.addObject(SessionAttributes.MENSAGEM, ExceptionUtil.obtemErroOriginal(ne).getMessage());
        }catch (Exception e) {
        	 logger.error("ClienteController.clienteHome: Erro: ", e);
	       	 mav = new ModelAndView(Views.CLIENTE_HOME);
	         mav.addObject(SessionAttributes.TITULO_MENSAGEM, "Erro Inesperado.");
	         mav.addObject(SessionAttributes.MENSAGEM, ExceptionUtil.obtemErroOriginal(e).getMessage());
		}
        return mav;
    } 
    
    @RequestMapping(value="/clienteHomeFacebook", method = RequestMethod.GET)
    public ModelAndView clienteHomeFacebook(@RequestParam(value="code") String code){
        logger.info("ClienteController.clienteHome: Inicio");
      	ModelAndView mav = null;
        try {
        	Cliente cliente = null;
            if (code != null && !code.isEmpty()) {
                FacebookConnectionFactory connFactory = new FacebookConnectionFactory(FacebookConstantes.APP_ID.getCodigo(), FacebookConstantes.APP_SECRET.getCodigo());
                AccessGrant accessGrant = connFactory.getOAuthOperations().exchangeForAccess(code, Views.URL_HOME_FULL, null);
                Connection<Facebook> connection = connFactory.createConnection(accessGrant);
                Facebook facebook = connection.getApi();
                if (facebook.isAuthorized()) {
                    FacebookProfile facebookProfile = facebook.userOperations().getUserProfile();
                    if (facebookProfile.getUsername() != null && !facebookProfile.getUsername().isEmpty()) {
                    	cliente = clienteRemote.obterClientePorEmail(facebookProfile.getUsername(), false);
                    } else {
                        throw new Exception();
                    }
                    if(cliente == null){
	                    cliente = new Cliente();
                    }
                    String[] nomes = facebookProfile.getName().split(" ");
                    cliente.setEmail(facebookProfile.getUsername());
                    cliente.setSenha(SessionAttributes.SENHA_FACEBOOK);
                    cliente.setNome(nomes[0]);
                    cliente.setSobreNome(nomes[nomes.length-1]);
                    if(facebookProfile.getGender().equals("male")){
                    	cliente.setSexo(SexoEnum.MASCULINO.getSexo());
                    }else if(facebookProfile.getGender().equals("male")){
                    	cliente.setSexo(SexoEnum.FEMININO.getSexo());
                    }
					cliente.setTipoAcesso(TipoAcesso.ACESSO_FACEBOOK.getTipoAcesso());
                    cliente.setDataNascimento(new SimpleDateFormat("MM/dd/yyyy").parse(facebookProfile.getBirthday()));
                    clienteRemote.atualizaClienteConsumidor(cliente);
                }
            }
            request.getSession().setAttribute(SessionAttributes.CLIENTE, cliente);
            mav = new ModelAndView(Views.CLIENTE_HOME);
            logger.info("ClienteController.clienteHome: Fim");
        } catch (Exception ex) {
        	mav = new ModelAndView(Views.LOGIN);
	        mav.addObject(SessionAttributes.TITULO_MENSAGEM, "Erro Inesperado.");
	        mav.addObject(SessionAttributes.MENSAGEM, ExceptionUtil.obtemErroOriginal(ex).getMessage());
        	logger.error("ClienteController.clienteHomeFacebook: Erro ao logar com facebook: ", ex);
        }
        logger.info("ClienteController.clienteHome: Fim");
        return mav;
    }
        
        
    @RequestMapping(value="/clienteForm", method = RequestMethod.GET)
    public ModelAndView clienteForm(){
        logger.info("ClienteController.clienteForm: Inicio");
        ModelAndView mav = null;
        try{
	        Cliente cliente = new Cliente();
	        cliente.setEmail(request.getParameter(SessionAttributes.J_USERNAME));
	        mav = new ModelAndView(Views.CLIENTE_FORM, SessionAttributes.CLIENTE, cliente);
	        mav.addObject(SessionAttributes.URL_CANCELAR, Views.CONTEXT + Views.PREFIXO_CLIENTE + Views.CLIENTE_HOME);
	        mav.addObject(SessionAttributes.TIPO_ACAO, SessionAttributes.TIPO_ACAO_INCLUI);
	        logger.info("ClienteController.clienteForm: Fim");
       }catch (Exception e) {
    	    logger.error("ClienteController.clienteForm: Erro: ", e);
	      	mav = new ModelAndView(Views.CLIENTE_HOME);
	        mav.addObject(SessionAttributes.TITULO_MENSAGEM, "Erro Inesperado.");
	        mav.addObject(SessionAttributes.MENSAGEM, ExceptionUtil.obtemErroOriginal(e).getMessage());
		}
        return mav;
    } 
    
    @RequestMapping(value="/clienteEfetiva", method = RequestMethod.GET)
    public String ModelAndView(@ModelAttribute("cliente")
                                 @Valid Cliente cliente, 
                                 BindingResult result,
                                 @RequestParam(SessionAttributes.TIPO_ACAO) String tipoAcao){
    	
        logger.info("ClienteController.clienteEfetiva: Inicio");
        try{ 
        	if(result.hasErrors()){
        		request.setAttribute(SessionAttributes.TIPO_ACAO, tipoAcao);
        		request.setAttribute(SessionAttributes.TITULO_MENSAGEM, "Erros.");
        		request.setAttribute(SessionAttributes.ERRORS, result.getFieldErrors());
        		request.setAttribute(SessionAttributes.URL_CANCELAR, Views.CONTEXT + Views.PREFIXO_CLIENTE + Views.CLIENTE_HOME);
        		 if(SessionAttributes.TIPO_ACAO_ATUALIZA.equals(tipoAcao)){
        			 return Views.CLIENTE_FORM_BASE;
        		 }else{
        			 return Views.CLIENTE_FORM;
        		 }
        		
        	}
        	
	        Cliente clienteSessao = (Cliente) request.getSession().getAttribute(SessionAttributes.CLIENTE);

	        if(SessionAttributes.TIPO_ACAO_INCLUI.equals(tipoAcao)){
	        	cliente.setTipoAcesso(TipoAcesso.ACESSO_NORMAL.getTipoAcesso());
	        	this.clienteRemote.adicionarClienteConsumidor(cliente);
	            request.setAttribute(SessionAttributes.SUCESSO, "Cliente adicionado com sucesso");
	            return Views.LOGIN;
	        }else if(SessionAttributes.TIPO_ACAO_ATUALIZA.equals(tipoAcao)){
	        	if(cliente.getSenha() == null ||  "".equals(cliente.getSenha())){
	        		cliente.setSenha(clienteSessao.getSenha());
	        	}
	        	this.clienteRemote.atualizaClienteConsumidor(cliente);
	            request.setAttribute(SessionAttributes.SUCESSO, "Cliente atualizado com sucesso.");
	            request.getSession().setAttribute(SessionAttributes.CLIENTE, cliente);
	        }
	        request.setAttribute(SessionAttributes.TITULO_MENSAGEM, "Sucesso.");
	        logger.info("ClienteController.clienteEfetiva: Fim");
	        return Views.CLIENTE_HOME;
       }catch(NegocioException ne){
    	    logger.error("ClienteController.clienteEfetiva: Erro: ", ne);
            request.setAttribute(SessionAttributes.TITULO_MENSAGEM, "Erro Inesperado.");
            request.setAttribute(SessionAttributes.MENSAGEM, ExceptionUtil.obtemErroOriginal(ne).getMessage());
       }catch (Exception e) {
    	    logger.error("ClienteController.clienteEfetiva: Erro: ", e);
    	   	request.setAttribute(SessionAttributes.TITULO_MENSAGEM, "Erro Inesperado.");
    	   	request.setAttribute(SessionAttributes.MENSAGEM, ExceptionUtil.obtemErroOriginal(e).getMessage());
		}
	   	request.setAttribute(SessionAttributes.TIPO_ACAO, tipoAcao);
	   	request.setAttribute(SessionAttributes.URL_CANCELAR, Views.CONTEXT + Views.PREFIXO_CLIENTE + Views.CLIENTE_HOME);
		if(SessionAttributes.TIPO_ACAO_ATUALIZA.equals(tipoAcao)){
			 return Views.CLIENTE_FORM_BASE;
		}else{
			 return Views.CLIENTE_FORM;
		}
    }
    
    
    @RequestMapping(value="/clienteAtualiza", method = RequestMethod.GET)
    public ModelAndView clienteAtualiza(){
        logger.info("ClienteController.clienteAtualiza: Inicio");
        ModelAndView mav = null;
        try{
	        Cliente cliente = (Cliente) request.getSession().getAttribute(SessionAttributes.CLIENTE);
	        mav = new ModelAndView(Views.CLIENTE_FORM_BASE, SessionAttributes.CLIENTE, cliente);
	        mav.addObject(SessionAttributes.URL_CANCELAR, Views.CONTEXT + Views.PREFIXO_CLIENTE + Views.CLIENTE_HOME);
	        mav.addObject(SessionAttributes.TIPO_ACAO, SessionAttributes.TIPO_ACAO_ATUALIZA);
	        logger.info("ClienteController.clienteAtualiza: Fim");
        }catch (Exception e) {
        	logger.error("ClienteController.clienteAtualiza: Erro: ", e);
	      	mav = new ModelAndView(Views.CLIENTE_HOME);
	        mav.addObject(SessionAttributes.TITULO_MENSAGEM, "Erro Inesperado.");
	        mav.addObject(SessionAttributes.MENSAGEM, ExceptionUtil.obtemErroOriginal(e).getMessage());
		} 
        return mav;
    }
    
    
    @RequestMapping(value="/clienteTrocaSenha", method = RequestMethod.GET)
    public ModelAndView clienteTrocaSenha(@RequestParam(value="id") String id){
        logger.info("ClienteController.clienteTrocaSenha: Inicio");
        ModelAndView mav = null;
        try{
	        Cliente cliente = clienteRemote.obterClientePorEmail(id, true);
	        mav = new ModelAndView(Views.CLIENTE_RESET_SENHA);
	        mav.addObject(SessionAttributes.EMAIL, cliente.getEmail());
	        logger.info("ClienteController.clienteTrocaSenha: Fim");
        }catch (Exception e) {
        	logger.error("ClienteController.clienteTrocaSenha: Erro: ", e);
	      	mav = new ModelAndView(Views.LOGIN);
	        mav.addObject(SessionAttributes.TITULO_MENSAGEM, "Erro Inesperado.");
	        mav.addObject(SessionAttributes.MENSAGEM, e.getMessage());
		} 
        return mav;
    }
    
    @RequestMapping(value="/clienteSenhaEfetiva", method = RequestMethod.GET)
    public ModelAndView clienteSenhaEfetiva(@RequestParam(value="email") String email, 
    										@RequestParam(value="senha") String senha){
        logger.info("ClienteController.clienteTrocaSenha: Inicio");
        ModelAndView mav = new ModelAndView(Views.LOGIN);
        try{
	        clienteRemote.atualizaClienteSenha(email, senha);
            mav.addObject(SessionAttributes.TITULO_MENSAGEM, "Sucesso.");
            request.setAttribute(SessionAttributes.SUCESSO, "Senha alterada com sucesso.");
	        logger.info("ClienteController.clienteTrocaSenha: Fim");
        }catch (Exception e) {
        	logger.error("ClienteController.clienteSenhaEfetiva: Erro: ", e);
            mav.addObject(SessionAttributes.TITULO_MENSAGEM, "Erro Inesperado.");
            request.setAttribute(SessionAttributes.MENSAGEM, ExceptionUtil.obtemErroOriginal(e).getMessage());
		} 
        return mav;
    }
    
    
    
    @RequestMapping(value="/clienteToJSON", method = RequestMethod.GET)
    public @ResponseBody Cliente clienteToJSON(){
        logger.info("ClienteController.clienteLogin: Inicio");
        try{
        	Cliente cliente = clienteRemote.obterClientePorEmail("fabiogobbi@uol.com.br", false);
        	logger.info("ClienteController.clienteToJSON: Fim");
        	return cliente;
        }catch(NegocioException ne){
        	logger.error("ClienteController.clienteToJSON: Erro: ", ne);
       }catch (Exception e) {
    	   logger.error("ClienteController.clienteToJSON: Erro: ", e);		}
        return new Cliente();
    } 
    
    
    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
	     SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	     dateFormat.setLenient(false);
	     webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	     webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
     }

    
}