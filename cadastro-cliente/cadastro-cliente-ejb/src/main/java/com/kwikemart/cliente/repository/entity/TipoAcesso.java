package com.kwikemart.cliente.repository.entity;

import java.io.Serializable;

public enum TipoAcesso implements Serializable {
    
    ACESSO_NORMAL(1), ACESSO_FACEBOOK(2);
    
    private static final long serialVersionUID = 5064352247682391314L;
    private int tipoAcesso;

    private TipoAcesso(int sexo){
        this.tipoAcesso = sexo;
    }
    public int getTipoAcesso() {
        return tipoAcesso;
    }
    public void setTipoAcesso(int tipoAcesso) {
        this.tipoAcesso = tipoAcesso;
    }

    
    
    
}
