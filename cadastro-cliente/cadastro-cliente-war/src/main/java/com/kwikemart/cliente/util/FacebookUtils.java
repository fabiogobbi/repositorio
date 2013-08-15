package com.kwikemart.cliente.util;

public class FacebookUtils {

	public enum FacebookConstantes {   
		
		APP_ID("1376579172569947"), 
		APP_SECRET("dc417fe184b42f407b4e4ed092148857");
	    
		private final String codigo;
	    
		FacebookConstantes(String cod){
	        codigo = cod;
	    }
	    public String getCodigo(){
	        return codigo;
	    }
	}
	
}
