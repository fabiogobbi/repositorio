package com.kwikemart.cliente.repository.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table(schema="kwikemart", name="perfil")
public class Perfil implements Serializable {
    
    private static final long serialVersionUID = 4770617033549321130L;
    public static final String ADMINISTRADOR = "1";
    public static final String CONSUMIDOR = "2";
    
    
    private String codPerfil;
    private String nomePerfil;
    List<Cliente> clientes;
    
    @Id
    @Column(name="codPerfil", nullable=false, columnDefinition="INT")
    public String getCodPerfil() {
        return codPerfil;
    }

    public void setCodPerfil(String codPerfil) {
        this.codPerfil = codPerfil;
    }

    @Column(name="nomePerfil", nullable=false, columnDefinition="VARCHAR(20)")
    public String getNomePerfil() {
        return nomePerfil;
    }

    public void setNomePerfil(String nomePerfil) {
        this.nomePerfil = nomePerfil;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "perfil", fetch = FetchType.LAZY)
    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }
    
    
    
}
