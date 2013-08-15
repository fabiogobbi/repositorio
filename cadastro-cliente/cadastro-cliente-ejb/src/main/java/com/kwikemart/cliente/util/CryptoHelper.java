package com.kwikemart.cliente.util;

import org.bouncycastle.util.encoders.Base64;

import com.kwikemart.cliente.exception.CriptografiaException;


public final class CryptoHelper {

	public static String criptografiaBase64(String valor) throws CriptografiaException{
		try{
			return new String(Base64.encode(valor.getBytes()), "UTF-8");
		}catch (Exception e){
			throw new CriptografiaException(e);
		}
	}
	
	public static String descriptografiaBase64(String hash) throws CriptografiaException{
		try{
			return new String(Base64.decode(hash.getBytes()), "UTF-8");
		}catch (Exception e){
			throw new CriptografiaException(e);
		}
	}
}