import java.util.ArrayList;
import java.util.Objects;

/**
 * La classe Value est une simple classe Wrapper de tous les autres types.
 * Les méthodes des Visitors crée par ANTLR4 devant tous retourner le même type,
 * il est nécessaire d'englober tout nos types dans ce type.
 * 
 * Objet clé dans le typage du programme
 */
public class Value implements Typable{

    final Object value;
	private static ArrayList<String> acceptedTypes;

    public Value(Object value) {
        this.value = value;
		//System.out.println("On crée une Value avec comme objet : "+value.toString()+" De classe : "+value.getClass().getName());
    }

    public Integer asInteger() {
        return (Integer) value;
    }

    public String asString() {
        return String.valueOf(value);
    }

    public Abstraction asAbstraction() {
        return ((Abstraction) value);
    }

    public RecFunction asRec() {
        return (RecFunction) value;
    }

    public IfStat asIfStat() {
        return (IfStat) value;
    }

    public Calcul asCalcul() {
        return (Calcul) value;
    }

    public boolean isInteger() {
        return value instanceof Integer;
    }

    public boolean isAbstraction() {
        return value instanceof Abstraction;
    }

    public boolean isVariable() {
        return value instanceof Variable;
    }

    public boolean isString() {
        return value instanceof String;
    }

    public boolean isRec() {
        return value instanceof RecFunction;
    }

    public boolean isCalcul() {
        return value instanceof Calcul;
    }
	
	public boolean isIfStat() {
        return value instanceof IfStat;
    }
	
	public boolean isTypeValid() {
		for(String elt:acceptedTypes){
			if(value.getClass().getName().equals(elt)){return true;}
		}
		return false;
	}
	
	/**
	 * Renvoie vrai si la valeur est typable (par défaut les chaines et entiers sont typables)
	 * @return boolean
	 */
	public boolean isTypable() {
		return Typable.class.isInstance(value) || isInteger() || isString();
	}


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Value value1 = (Value) o;
        return Objects.equals(value, value1.value);
    }


    @Override
    public int hashCode() {

        if (value == null) {
            return 0;
        }

        return this.value.hashCode();
    }


    @Override
    public String toString() {
        return String.valueOf(value);
    }

	/**
	 * Méthode qui renvoie le type contenu dans la Value 
	 * Int et String étant des types de base, si on les voit on renvoie leur type
	 * sinon on renvoie le type de l'objet contenu dans la value (cas d'une récursion ou d'un calcul par exemple)
	 * enfin si l'objet n'est pas typable on renvoie invalide.
	 * @return String
	 */
	@Override
	public String getType() {
		if(isTypeValid()){ return new SimpleTypes().nameChange(value.getClass().getName()); }
		if(isTypable()){ return ((Typable) value).getType(); }
		return "Invalide";
	}
	
    /**
     * Cette méthode est la pour vérifier si la boucle du parser/lexer doit s'areter (dans le cas ou on a une abstraction ou
     * une valeur)
     *
     * @return <code>true</code> si on s'arrête, <code>false</code> si l'on doit continuer.
     */
    public boolean checkContinue() {
        return !(this.isAbstraction() || this.isVariable() || this.isInteger());
    }

	/**
	 * Retourne le type absolu de ce qui est contenu dans Value
	 * @return String
	 */
	@Override
	public String getAbsoluteType() {
		if(isTypeValid()){ return new SimpleTypes().nameChange(value.getClass().getName());}
		if(isTypable()){ return ((Typable) value).getAbsoluteType(); }
		return "Invalide";
	}

	public static void setAcceptedTypes(ArrayList<String> acceptedTypes) {
		Value.acceptedTypes = acceptedTypes;
	}
	

}
