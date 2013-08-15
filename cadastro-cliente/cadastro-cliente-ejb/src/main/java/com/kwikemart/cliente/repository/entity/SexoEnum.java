package com.kwikemart.cliente.repository.entity;

import java.io.Serializable;

public enum SexoEnum implements Serializable {
    
    MASCULINO(1), FEMININO(2);
    
    private static final long serialVersionUID = 5064852947632395334L;
    private int sexo;

    private SexoEnum(int sexo){
        this.sexo = sexo;
    }
    public int getSexo() {
        return sexo;
    }
    public void setSexo(int sexo) {
        this.sexo = sexo;
    }

    
    
    
}
