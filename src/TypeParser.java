
import java.util.ArrayList;
import java.util.HashMap;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Nicolas
 */
public class TypeParser {
	
	private String content;
	private String[] scrapes;
	
	public TypeParser(String content){
		this.content = content;
	}
	
	private void toScrapes(String separator){
		scrapes = content.split(separator);
	}
	
	/**
	 * Retourne un liste contenant tous les types acceptés par le programme
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getAcceptedTypes(){
		
		toScrapes("\n");
		boolean state = false;
		ArrayList<String> acceptedTypes = new ArrayList<String>();
		
		for(String elt:scrapes){
			if(elt.matches("^ValidTypes\\{$")){ state=true; }
			if(elt.matches("\\}")){ state = false; }
			if(state==true && !elt.matches("^ValidTypes\\{$")){ acceptedTypes.add(elt); }
		}
		
		return acceptedTypes;
	}
	
	public HashMap<String,String> getEvaluationRules(){
		
		toScrapes("\n");
		short state = 0;
		String expression="";
		HashMap<String,String> rules = new HashMap<String,String>();
		
		for(String elt:scrapes){
			
			if(state==1){state=2;}
			if(elt.matches("^.*\\[$")){ 
				state=1; 
				expression=elt.substring(0, elt.length()-1); 
			}
			if(elt.matches("\\]")){ state = 0; }
			if(state==3){ rules.put(expression+"Outputs", elt); }
			if(state==2){ rules.put(expression+"Inputs", elt); state=3;}
			
		}
		
		return rules;
	}
	
	public void test(){
		for(String elt:getAcceptedTypes()){
			System.out.println(elt);
		}
	}
	
	@Override
	public String toString(){
		return content;
	}
}
