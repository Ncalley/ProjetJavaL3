

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Classe qui permet de rendre un nom de classe plus joli pour l'affichage (supprime le chemin de l'objet)
 * @author Nicolas
 */
public class SimpleTypes {
	
	public String nameChange(String completeTypeName){
		String[] parts = completeTypeName.split("\\.");
		return parts[parts.length-1];
	}
}
