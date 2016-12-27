import java.util.Objects;

/**
 * Classe "type" pour les variables.
 */
public class Variable implements Typable{

    private String id;
	private String type="Int";

    public Variable(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Variable variable = (Variable) o;
        return Objects.equals(getId(), variable.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

	/**
	 * Permet de définir le type contenu dans la variable
	 * @param type 
	 */
	public void setType(String type){
		this.type = type;
	}
	
	/**
	 * Par défaut une variable non définie sera comptée comme contenant un entier
	 * @return String
	 */
	@Override
	public String getType() {
		return type;
	}


}
