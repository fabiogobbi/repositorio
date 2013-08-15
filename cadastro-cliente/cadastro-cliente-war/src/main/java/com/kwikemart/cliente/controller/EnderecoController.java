package com.kwikemart.cliente.controller;


import java.util.Set;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kwikemart.cliente.ejb.EnderecoRemote;
import com.kwikemart.cliente.exception.NegocioException;
import com.kwikemart.cliente.repository.entity.Cliente;
import com.kwikemart.cliente.repository.entity.Endereco;
import com.kwikemart.cliente.util.ExceptionUtil;
import com.kwikemart.cliente.util.SessionAttributes;
import com.kwikemart.cliente.util.Views;

@Controller
@RequestMapping(value="/endereco")
public class EnderecoController{
	
    //CDI
    @EJB(mappedName="java:global/cadastro-cliente-ejb/EnderecoEJB!com.kwikemart.cliente.ejb.EnderecoRemote")
    EnderecoRemote enderecoRemote;

    @Autowired
    private HttpServletRequest request;
    
    final Logger logger = LoggerFactory.getLogger(EnderecoController.class);
      
    @RequestMapping(value="/enderecoLista", method = RequestMethod.GET)
    public ModelAndView enderecoLista(){
        logger.info("EnderecoController.enderecoLista: Inicio");
        ModelAndView mav = null;
        try{
	        Cliente cliente = (Cliente) request.getSession().getAttribute(SessionAttributes.CLIENTE);
	        Set<Endereco> enderecos = enderecoRemote.obterEnderecosPorEmailId(null, cliente.getEmail());
	        cliente.setEnderecos(enderecos);
	        mav = new ModelAndView(Views.ENDERECO_LISTA);
	        logger.info("EnderecoController.enderecoLista: Fim");
        }catch(NegocioException ne){
        	logger.error("EnderecoController.enderecoLista: Erro: ", ne);
       	    mav = new ModelAndView(Views.CLIENTE_HOME);
            mav.addObject(SessionAttributes.TITULO_MENSAGEM, "Erro Inesperado.");
            mav.addObject(SessionAttributes.MENSAGEM, ExceptionUtil.obtemErroOriginal(ne).getMessage());
       }catch (Exception e) {
    	    logger.error("EnderecoController.enderecoLista: Erro: ", e);
	       	mav = new ModelAndView(Views.CLIENTE_HOME);
	        mav.addObject(SessionAttributes.TITULO_MENSAGEM, "Erro Inesperado.");
	        mav.addObject(SessionAttributes.MENSAGEM, ExceptionUtil.obtemErroOriginal(e).getMessage());
		}
        return mav;
    } 
    
    @RequestMapping(value="/enderecoForm", method = RequestMethod.GET)
    public ModelAndView enderecoForm(){
        logger.info("EnderecoController.enderecoForm: Inicio");
        ModelAndView mav = new ModelAndView(Views.ENDERECO_FORM, SessionAttributes.ENDERECO, new Endereco());
        mav.addObject(SessionAttributes.TIPO_ACAO, SessionAttributes.TIPO_ACAO_INCLUI);
        logger.info("EnderecoController.enderecoForm: Fim");
        return mav;
    } 
    
    
    @RequestMapping(value="/enderecoEfetiva", method = RequestMethod.GET)
    public String enderecoEfetiva(@ModelAttribute("endereco")
									    @Valid Endereco endereco, 
									    BindingResult result,
									    @RequestParam(SessionAttributes.TIPO_ACAO) String tipoAcao){
        logger.info("EnderecoController.enderecoEfetiva: Inicio");
        Cliente cliente = null;
        Set<Endereco> enderecos = null;
        try{
        	if(result.hasErrors()){
        		request.setAttribute(SessionAttributes.TIPO_ACAO, tipoAcao);
        		request.setAttribute(SessionAttributes.TITULO_MENSAGEM, "Erros.");
        		request.setAttribute(SessionAttributes.ERRORS, result.getFieldErrors());
        		request.setAttribute(SessionAttributes.URL_CANCELAR, Views.CONTEXT + Views.PREFIXO_CLIENTE + Views.CLIENTE_HOME);
        		return Views.ENDERECO_FORM;
        	}
	        cliente = (Cliente) request.getSession().getAttribute(SessionAttributes.CLIENTE);
	        endereco.setCliente(cliente);
	        if(SessionAttributes.TIPO_ACAO_INCLUI.equals(tipoAcao)){
	        	this.enderecoRemote.incluirEndereco(endereco);
	            request.setAttribute(SessionAttributes.SUCESSO, "Endereco adicionado com sucesso");
	        }else if(SessionAttributes.TIPO_ACAO_ATUALIZA.equals(tipoAcao)){
	        	this.enderecoRemote.atualizaEndereco(endereco);
	            request.setAttribute(SessionAttributes.SUCESSO, "Endereco atualizado com sucesso");
	        }
	        enderecos = enderecoRemote.obterEnderecosPorEmailId(null, cliente.getEmail());
	        cliente.setEnderecos(enderecos);
	        request.setAttribute(SessionAttributes.TITULO_MENSAGEM, "Sucesso.");
	        logger.info("EnderecoController.enderecoEfetiva: Fim");
	        return Views.ENDERECO_LISTA;
        }catch(NegocioException ne){
        	logger.error("EnderecoController.enderecoEfetiva: Erro: ", ne);
            request.setAttribute(SessionAttributes.TITULO_MENSAGEM, "Erro Inesperado.");
            request.setAttribute(SessionAttributes.MENSAGEM, ExceptionUtil.obtemErroOriginal(ne).getMessage());
            request.setAttribute(SessionAttributes.TIPO_ACAO, tipoAcao);
            return Views.ENDERECO_FORM;
       }catch (Exception e) {
    	    logger.error("EnderecoController.enderecoEfetiva: Erro: ", e);
    	   	request.setAttribute(SessionAttributes.TITULO_MENSAGEM, "Erro Inesperado.");
    	   	request.setAttribute(SessionAttributes.MENSAGEM, ExceptionUtil.obtemErroOriginal(e).getMessage());
    	   	request.setAttribute(SessionAttributes.TIPO_ACAO, tipoAcao);
    	   	return Views.ENDERECO_FORM;
		}
        
    } 
    
    @RequestMapping(value="/enderecoExcluir", method = RequestMethod.GET)
    public ModelAndView enderecoExcluir(){
        logger.info("EnderecoController.enderecoExcluir: Inicio");
        ModelAndView mav = new ModelAndView(Views.ENDERECO_LISTA);
        Cliente cliente = null;
        Set<Endereco> enderecos = null;
        try{
        	this.enderecoRemote.excluirEndereco(new Long(request.getParameter("idEndereco")));
        	cliente = (Cliente) request.getSession().getAttribute(SessionAttributes.CLIENTE);
        	enderecos = enderecoRemote.obterEnderecosPorEmailId(null, cliente.getEmail());
	        cliente.setEnderecos(enderecos);
            mav.addObject(SessionAttributes.TITULO_MENSAGEM, "Sucesso.");
            mav.addObject(SessionAttributes.SUCESSO, "Endereco excluido com sucesso.");
        	logger.info("EnderecoController.enderecoExcluir: Fim");
        }catch(NegocioException ne){
        	logger.error("EnderecoController.enderecoExcluir: Erro: ", ne);
        	mav.addObject(SessionAttributes.TITULO_MENSAGEM, "Erro Inesperado.");
        	mav.addObject(SessionAttributes.MENSAGEM, ExceptionUtil.obtemErroOriginal(ne).getMessage());
       }catch (Exception e) {
    	    logger.error("EnderecoController.enderecoExcluir: Erro: ", e);
    	    mav.addObject(SessionAttributes.TITULO_MENSAGEM, "Erro Inesperado.");
    	    mav.addObject(SessionAttributes.MENSAGEM, ExceptionUtil.obtemErroOriginal(e).getMessage());
		}
        return mav;
    } 
    
    
    @RequestMapping(value="/enderecoAlterar", method = RequestMethod.GET)
    public ModelAndView enderecoAlterar(){
        logger.info("EnderecoController.enderecoAlterar: Inicio");
        ModelAndView mav = null;
        try{
	        Endereco endereco  = this.enderecoRemote.obterEnderecosPorEmailId(new Long(request.getParameter("idEndereco")).toString(), null).iterator().next();
	        mav = new ModelAndView(Views.ENDERECO_FORM, SessionAttributes.ENDERECO, endereco);
	        mav.addObject(SessionAttributes.TIPO_ACAO, SessionAttributes.TIPO_ACAO_ATUALIZA);
	        logger.info("EnderecoController.enderecoAlterar: Fim");
        }catch(NegocioException ne){
        	logger.error("EnderecoController.enderecoAlterar: Erro: ", ne);
       	 	mav = new ModelAndView(Views.CLIENTE_HOME);
            mav.addObject(SessionAttributes.TITULO_MENSAGEM, "Erro Inesperado.");
            mav.addObject(SessionAttributes.MENSAGEM, ExceptionUtil.obtemErroOriginal(ne).getMessage());
       }catch (Exception e) {
    	    logger.error("EnderecoController.enderecoAlterar: Erro: ", e);
	        mav = new ModelAndView(Views.CLIENTE_HOME);
	        mav.addObject(SessionAttributes.TITULO_MENSAGEM, "Erro Inesperado.");
	        mav.addObject(SessionAttributes.MENSAGEM, ExceptionUtil.obtemErroOriginal(e).getMessage());
		}
        return mav;
    } 
    
    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
	     webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
     }

    
}