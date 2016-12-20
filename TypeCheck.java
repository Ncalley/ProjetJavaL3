/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Cette classe permet de donner le type d'une expression
 * Elle s'appelle récursivement en fonction de la taille et la complexité de l'opération.
 * son argument est la chaine qu'elle va typer
 * 
 * /!\ Cette classe est provisoire et sera sûrement changée pour entrer dans l'arbre AST déjà créé (plus simple je pense) /!\
 * @author Nicolas
 */
public class TypeCheck extends Thread{
	private String term;

	public TypeCheck(String term) {
		this.term = term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	private String checkType(String term){
		return "int";
	} 
	
	@Override
	public String toString() {
		return term;
	}

	@Override
	public void run() {
		//TODO : everything
		term=checkType(term);
	}
	
}
