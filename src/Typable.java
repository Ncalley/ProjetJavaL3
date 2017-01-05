/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *	Interface qui donne un type à quelque chose (on va l'utiliser sur value)
 * @author Nicolas
 */
public interface Typable {
	
	/**
	 * Méthode qui va renvoyer le type de l'élément en question,
	 * par défaut String ou Int ou Invalide
	 * @return String
	 */
	public abstract String getType();
	
	/**
	 * Méthode qui va renvoyer le type exact de l'élément et non celui de son contenu
	 * et permet de typer l'expression complète et pas juste le résultat.
	 * par exemple: 
	 *		variable	->	Var
	 *		IfStat		->	if(Int=Int)then{Var}else{String}
	 * @return String
	 */
	public abstract String getAbsoluteType();
}
