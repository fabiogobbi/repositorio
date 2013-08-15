package com.kwikemart.cliente.repository.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@Entity
@Table(schema="kwikemart", name="endereco")
public class Endereco implements Serializable {
    
    private static final long serialVersionUID = -4609275264389691130L;

    
    private Long id;
    
    @NotNull (message="Preencha a descricao.")
    @Pattern(regexp="(?i)^[a-z A-Z0-9éúíóáÉÚÍÓÁèùìòàÈÙÌÒÀõãñÕÃÑêûîôâÊÛÎÔÂëÿüïöäËYÜÏÖÄ\\s\\'\\-]{0,40}", message="Descricao invalido.")
    private String descricao = null;
    
    @NotNull (message="Preencha o logradouro.")
    @Pattern(regexp="(?i)^[a-z A-Z0-9éúíóáÉÚÍÓÁèùìòàÈÙÌÒÀõãñÕÃÑêûîôâÊÛÎÔÂëÿüïöäËYÜÏÖÄ\\s\\'\\-]{0,100}", message="Logradouro invalido.")
    private String logradouro = null;
    
    @NotNull (message="Preencha o numero.")
    @Min(value=1L) @Max(value=Long.MAX_VALUE)
    private Long numero = null;
    
    @Pattern(regexp="(?i)^[a-z A-Z0-9éúíóáÉÚÍÓÁèùìòàÈÙÌÒÀõãñÕÃÑêûîôâÊÛÎÔÂëÿüïöäËYÜÏÖÄ\\s\\'\\-]{0,40}", message="Complemento invalido.")
    private String complemento = null;
    
    @NotNull (message="Preencha o cep.")
    @Pattern(regexp="(?i)^[0-9]{0,8}", message="Cep invalido. Digite apenas numeros.")
    private String cep= null;
    
    @NotNull (message="Preencha o bairro.")
    @Pattern(regexp="(?i)^[a-z A-Z0-9éúíóáÉÚÍÓÁèùìòàÈÙÌÒÀõãñÕÃÑêûîôâÊÛÎÔÂëÿüïöäËYÜÏÖÄ\\s\\'\\-]{0,20}", message="Bairro invalido.")
    private String bairro= null;
    
    @NotNull (message="Preencha a cidade.")
    @Pattern(regexp="(?i)^[a-z A-Z0-9éúíóáÉÚÍÓÁèùìòàÈÙÌÒÀõãñÕÃÑêûîôâÊÛÎÔÂëÿüïöäËYÜÏÖÄ\\s\\'\\-]{0,20}", message="Cidade invalido.")
    private String cidade= null;
    
    @NotNull (message="Preencha o estado.")
    @Pattern(regexp="(?i)^[A-Za-z0-9 ]{0,2}", message="Estado invalido.")
    private String estado= null;
    
    private Cliente cliente = null;
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @Column(name="descricao", nullable=false, columnDefinition="VARCHAR(20)")
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Column(name="logradouro", nullable=false, columnDefinition="VARCHAR(100)")
    public String getLogradouro() {
        return logradouro;
    }
    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    @Column(name="numero", nullable=false, columnDefinition="INT")
    public Long getNumero() {
        return numero;
    }
    public void setNumero(Long numero) {
        this.numero = numero;
    }

    @Column(name="complemento", nullable=true, columnDefinition="VARCHAR(40)")
    public String getComplemento() {
        return complemento;
    }
    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    @Column(name="cep", nullable=false, columnDefinition="VARCHAR(8)")
    public String getCep() {
        return cep;
    }
    public void setCep(String cep) {
        this.cep = cep;
    }

    @Column(name="bairro", nullable=false, columnDefinition="VARCHAR(20)")
    public String getBairro() {
        return bairro;
    }
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    @Column(name="cidade", nullable=false, columnDefinition="VARCHAR(20)")
    public String getCidade() {
        return cidade;
    }
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    @Column(name="estado", nullable=false, columnDefinition="VARCHAR(2)")
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }

    @ManyToOne(fetch = FetchType.LAZY)  
    @JoinColumn(name="email")
    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
}
