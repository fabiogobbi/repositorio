package com.kwikemart.cliente.exception;

public class NegocioException extends Exception{

	private static final long serialVersionUID = 3287352965771999685L;

	public NegocioException(){
		super();
	}
	
	public NegocioException(Throwable t){
		super(t);
	}
	
	public NegocioException(String message, Throwable t){
		super(message, t);
	}
	
	public NegocioException(String message){
		super(message);
	}
	
	
}
