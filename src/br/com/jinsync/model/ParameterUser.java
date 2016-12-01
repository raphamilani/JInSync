//    This file is part of JInSync, developed by Rodrigo Augusto Silva dos Santos
//
//    JInSync is free software: you can redistribute it and/or modify it under 
//    the terms of the GNU Less General Public License as published by the Free 
//    Software Foundation, either version 3 of the License, or (at your option) 
//    any later version.
//
//    JInSync is distributed in the hope that it will be useful, but WITHOUT ANY 
//    WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS 
//    FOR A PARTICULAR PURPOSE.  See the GNU Less General Public License for more 
//    details.
//
//    You should have received a copy of the GNU Less General Public License 
//    along with JInSync.  If not, see <http://www.gnu.org/licenses/>.    
//
//    -------------------------------------------------------------------------
//
//    Este arquivo � parte do programa JInSync, desenvolvido por Rodrigo Augusto
//    Silva dos Santos
//
//    JInSync � um software livre; voc� pode redistribu�-lo e/ou modific�-lo 
//    dentro dos termos da Licen�a P�blica Geral Menor GNU como publicada 
//    pela Funda��o do Software Livre (FSF); na vers�o 3 da Licen�a, ou 
//    (na sua opini�o) qualquer vers�o.
//
//    Este programa � distribu�do na esperan�a de que possa ser  �til, mas 
//    SEM NENHUMA GARANTIA; sem uma garantia impl�cita de ADEQUA��O a qualquer 
//    MERCADO ou APLICA��O EM PARTICULAR. Veja a Licen�a P�blica Geral Menor 
//    GNU para maiores detalhes.
//
//    Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral Menor GNU 
//    junto com este programa, Se n�o, veja <http://www.gnu.org/licenses/>.

package br.com.jinsync.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import br.com.jinsync.controller.PasswordSecret;

public class ParameterUser {

	private static final String userDirProp = System.getProperty("user.dir") + "\\config";
	private static final String userProp = System.getProperty("user.dir") + "\\config\\user.properties";
	
	private String user;
	private String password;
	
	public void setUsuar(String usuar, String sen){

		Properties prop = new Properties();
		prop.setProperty("user", usuar);
		prop.setProperty("password", crypt(sen));
		
		File arqProp = new File(userDirProp);			
		if (!arqProp.exists()) {
			arqProp.mkdirs();
		}			
			
		try {
			//Criamos um objeto FileOutputStream            
			FileOutputStream fos = new FileOutputStream(userProp);
			//grava os dados no arquivo
			prop.store(fos, "USER FILE:");
			//fecha o arquivo
			fos.close();
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}    	
		
	}
		
    public String getUsuar(){
    	
    	user="";    	
    	Properties prop = new Properties();

		File arqProp = new File(userProp);		
		if (arqProp.exists()) {
			try {
				FileInputStream fis = new FileInputStream(userProp);
				prop.load(fis);
				user = prop.getProperty("user");
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}	
		
		return user;
		
    }
    
    public String getPassword(){
    	
    	password="";    	
    	Properties prop = new Properties();

		File arqProp = new File(userProp);		
		if (arqProp.exists()) {
			try {
				FileInputStream fis = new FileInputStream(userProp);
				prop.load(fis);
				password = decrypt(prop.getProperty("password")); 
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}

		return password;
		
    }


    private static String crypt(String sen){
    	
    	String encoded="";
    	
        try {
			encoded = PasswordSecret.encrypt (sen);
	        
		} catch (InvalidKeyException | BadPaddingException | NoSuchPaddingException | IllegalBlockSizeException
				| NoSuchAlgorithmException | InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return encoded;
        
    }
    

    private String decrypt(String sen){
    	
    	String decoded="";
    	
        try {
        	decoded = PasswordSecret.decrypt (sen);
	        
		} catch (InvalidKeyException | BadPaddingException | NoSuchPaddingException | IllegalBlockSizeException
				| NoSuchAlgorithmException | InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return decoded;
        
    }
    
	
}
