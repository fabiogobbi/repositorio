package com.kwikemart.cliente.ejb;

import java.util.Set;

import javax.ejb.Remote;

import com.kwikemart.cliente.exception.NegocioException;
import com.kwikemart.cliente.repository.entity.Cliente;

@Remote
public interface ClienteRemote{
    public void adicionarClienteAdministrador(Cliente cliente) throws NegocioException;
    public void adicionarClienteConsumidor(Cliente cliente) throws NegocioException;
    public void atualizaClienteConsumidor(Cliente cliente) throws NegocioException;
    public void atualizaClienteAdministrador(Cliente cliente) throws NegocioException;
    public void atualizaClienteSenha(String email, String senha) throws NegocioException;
    public Cliente obterClientePorEmail(String email, boolean crypto) throws NegocioException;
    public Set<Cliente> listarClientes() throws NegocioException;
}