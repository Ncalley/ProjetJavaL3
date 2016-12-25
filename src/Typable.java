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
	 * @return 
	 */
	public String getType();
}
