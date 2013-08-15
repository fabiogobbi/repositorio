package com.kwikemart.cliente.exception;

public class DAOException extends Exception{

	private static final long serialVersionUID = 3287352965771999685L;

	public DAOException(){
		super();
	}
	
	public DAOException(Throwable t){
		super("Erro ao acessar banco de dados.", t);
	}
	
	public DAOException(String message, Throwable t){
		super(message, t);
	}
	
	public DAOException(String message){
		super(message);
	}
	
	
}
