package com.kwikemart.cliente.exception;

public class CriptografiaException extends Exception{

	private static final long serialVersionUID = 3287352965771999685L;

	public CriptografiaException(){
		super();
	}
	
	public CriptografiaException(Throwable t){
		super("Erro ao criptografar / descriptografar dados.", t);
	}
	
	public CriptografiaException(String message, Throwable t){
		super(message, t);
	}
	
	public CriptografiaException(String message){
		super(message);
	}
	
	
}
