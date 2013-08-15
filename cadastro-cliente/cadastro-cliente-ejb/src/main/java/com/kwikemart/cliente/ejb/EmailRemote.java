package com.kwikemart.cliente.ejb;

import javax.ejb.Remote;

import com.kwikemart.cliente.exception.NegocioException;

@Remote
public interface EmailRemote{
	public void enviarEmailSenha(String emailUsuario) throws NegocioException;
    public void resetarSenha(String email) throws NegocioException;
}