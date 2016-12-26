import java.util.Objects;

/**
 * Classe "type" pour les variables.
 */
public class Variable implements Typable{

    private String id;

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
	 * On considère qu'une variable renvoie toujours un entier (ceci est faux et conduit à divers problèmes notamment avec les if)
	 * /!\ GROS PROBLEME /!\
	 * @return String
	 */
	@Override
	public String getType() {
		return "Int";
	}


}
