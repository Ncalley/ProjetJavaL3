
import java.util.HashMap;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Classe qui s'occupe de vérifier si le typage est correct selon les règles que l'on a donné dans le fichier
 * @author Nicolas
 */
public class ruleChecker {
	
	private boolean respected=false;
	
	public void check(String expression, HashMap<String,String> rules, Value[] data){
		
		String[] inputs = rules.get(expression+"Inputs").split(",");
		String outputs = rules.get(expression+"Outputs");
		String typeA=""; String typeB="";
		boolean check = false;
		
		for(int i=0;i<inputs.length-1;i++){
			if(inputs[i].equals("TypeA")){ typeA = data[i].getType(); }
			else{if(inputs[i].equals("TypeB")){ typeB = data[i].getType();}}
		}
		
		if(outputs.equals("TypeA")){ check = data[data.length-1].getType().equals(typeA);}
		else{if(outputs.equals("TypeB")){ check = data[data.length-1].getType().equals(typeB);}
		else{ check = outputs.equals(data[data.length-1].getType());}}
		
		respected = check;
	}

	public boolean isRespected() {
		return respected;
	}
	
}
