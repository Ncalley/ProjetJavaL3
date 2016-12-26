import java.util.Objects;

/**
 * IfStat est la classe "type" qui représente un if statement.
 * On prend garde à bien spécifier l'opérateur, en effet, toutes les opérations de comparaison sont supportées
 * par le programme.
 */
public class IfStat implements Typable{

    private Value leftToEq;
    private Value rightToEq;
    private Value thenReturn;
    private Value elseReturn;
    private String operator;

    public IfStat(Value leftToEq, Value rightToEq, Value thenReturn, Value elseReturn, String operator) {
        this.leftToEq = leftToEq;
        this.rightToEq = rightToEq;
        this.thenReturn = thenReturn;
        this.elseReturn = elseReturn;
        this.operator = operator;
    }

    public Value getLeftToEq() {
        return leftToEq;
    }

    public Value getRightToEq() {
        return rightToEq;
    }

    public Value getThenReturn() {
        return thenReturn;
    }

    public Value getElseReturn() {
        return elseReturn;
    }

    public String getOperator() {
        return operator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IfStat ifStat = (IfStat) o;
        return Objects.equals(getLeftToEq(), ifStat.getLeftToEq()) &&
                Objects.equals(getRightToEq(), ifStat.getRightToEq()) &&
                Objects.equals(getThenReturn(), ifStat.getThenReturn()) &&
                Objects.equals(getElseReturn(), ifStat.getElseReturn()) &&
                Objects.equals(getOperator(), ifStat.getOperator());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLeftToEq(), getRightToEq(), getThenReturn(), getElseReturn(), getOperator());
    }

    @Override
    public String toString() {
        return "if(" + leftToEq + operator + rightToEq + ")then{" + thenReturn + "}else{" + elseReturn + "}";
    }

	/**
	 * Typage suivant l'énoncé, si les deux opérateurs sont de même type on renvoie le type de la sortie then
	 * sinon on renvoie le type de la sortie else
	 * @return String
	 */
	@Override
	public String getType() {
		/*System.out.println("left: "+leftToEq.getType());
		System.out.println("right: "+rightToEq.getType());
		System.out.println("then output: "+thenReturn.getType());
		System.out.println("else output: "+elseReturn.getType());*/
		if( leftToEq.getType().equals(rightToEq.getType()) ){ return thenReturn.getType(); }
		return elseReturn.getType();
	}
}
