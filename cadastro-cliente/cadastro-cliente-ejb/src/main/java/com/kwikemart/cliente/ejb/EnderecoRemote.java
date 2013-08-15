package com.kwikemart.cliente.ejb;

import java.util.Set;

import javax.ejb.Remote;

import com.kwikemart.cliente.exception.NegocioException;
import com.kwikemart.cliente.repository.entity.Endereco;

@Remote
public interface EnderecoRemote{
    public void incluirEndereco(Endereco endereco) throws NegocioException;
    public void atualizaEndereco(Endereco endereco) throws NegocioException;
    public void excluirEndereco(Long idEndereco) throws NegocioException;
    public Set<Endereco> obterEnderecosPorEmailId(String id, String email) throws NegocioException;
    public Set<Endereco> listarEnderecos() throws NegocioException;
}