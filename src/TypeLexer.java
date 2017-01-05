
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Classe qui va permettre de lire le fichier de typage pour que le parseur l'utilise
 * @author Nicolas
 */
public class TypeLexer {
	
	public String readFile(String fileName){
		
		StringBuffer content = new StringBuffer();
		
		try{
			InputStream ips=new FileInputStream(fileName); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String line;
			
			while ((line=br.readLine())!=null){ 
				content.append(line+"\n");
			}
			br.close(); 
		}		
		catch (Exception e){
			System.out.println(e.toString());
		}
		return content.toString();
	}
}
