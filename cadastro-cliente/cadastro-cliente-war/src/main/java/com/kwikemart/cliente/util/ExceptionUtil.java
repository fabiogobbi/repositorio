package com.kwikemart.cliente.util;

public class ExceptionUtil {
	public static Throwable obtemErroOriginal(Throwable t){
		Throwable retorno = t;
		while (retorno.getCause() != null){
			retorno  = retorno.getCause(); 
		}
		return retorno;
	}
}
