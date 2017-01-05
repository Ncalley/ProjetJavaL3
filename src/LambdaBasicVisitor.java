

import java.util.HashMap;

/**
 * <code>LambdaBasicVisitor</code> est une implémentation basique du Visitor généré par
 * ANTLR4.
 * Les méthodes de cette classe peuvent être <code>Override</code> par les classes héritantes.
 */
public class LambdaBasicVisitor extends LambdaBaseVisitor<Value> {

	private static Value expressionType;
	protected static HashMap<String,String> rules;
	protected static ruleChecker checker;
    /**
     * Implémentation basique d'une application qui renvoie une application telle quelle.
     *
     * @param ctx Le noeud courant
     * @return Une application
     */
    @Override
    public Value visitApplication(LambdaParser.ApplicationContext ctx) {
        Value left = this.visit(ctx.expression(0));
        Value right = this.visit(ctx.expression(1));
		Value result;
        if (ctx.parent instanceof LambdaParser.ParenExpressionContext) {
			expressionType= new Value(new Apply(left, right, true));
            result = new Value(new Apply(left, right, true));
        } else {
			expressionType= new Value(new Apply(left, right, false));
            result = new Value(new Apply(left, right, false));
        }
		
		Value[] data= {left,right,result};
		checker.check("Apply", rules, data);
		return result;
    }

    /**
     * Méthode permettant d'évaluer une abstraction
     *
     * @param ctx Représente le noeud courant
     * @return Renvoie une abstraction bien parenthésée
     */
    @Override
    public Value visitAbstraction(LambdaParser.AbstractionContext ctx) {
        String varUnderLambda = ctx.VAR().getText();
        Value expression = this.visit(ctx.expression());
		Value result;
		
        if (ctx.parent instanceof LambdaParser.ParenExpressionContext) {
			expressionType= new Value(new Abstraction(varUnderLambda, expression, false));
            result = new Value(new Abstraction(varUnderLambda, expression, false));
        } else {
			expressionType= new Value(new Abstraction(varUnderLambda, expression, true));
            result = new Value(new Abstraction(varUnderLambda, expression, true));
        }
		Value[] data= {new Value(varUnderLambda),expression,result};
		checker.check("Abstraction", rules, data);
		return result;
    }

    /**
     * @param ctx Le noeud courant
     * @return Renvoie une variable
     */
    @Override
    public Value visitVariable(LambdaParser.VariableContext ctx) {
		Variable v = new Variable(ctx.getText());
		if(ctx.getText().matches("^[a-zA-Z]+$")){
			v.setType("String");
		}
		if(expressionType==null){expressionType = new Value(v);}
		Value result = new Value(v);
		Value[] data= {new Value(v),result};
		checker.check("Variable", rules, data);
        return result;
    }

    /**
     * La réduction d'une <code>IfRule</code> est faite ici.
     * On évalue des égalités uniquement si les deux termes sont des <code>Integer</code>
     * Tous les opérateurs classiques de comparaison sont supportés.
     *
     * @param ctx Le noeud courant.
     * @return Renvoie le résultat sous le then ou le else ou une ifRule entière si
     * les termes comaprés ne sont pas des <code>Integer</code>.
     */
    @Override
    public Value visitIfRule(LambdaParser.IfRuleContext ctx) {

        Value leftToCheck = this.visit(ctx.expression(0));
        Value rightToCheck = this.visit(ctx.expression(1));
		Value trueReturn = this.visit(ctx.expression(2));
		Value falseReturn = this.visit(ctx.expression(3));
		Value result;

		expressionType = new Value(new IfStat(leftToCheck, rightToCheck, this.visit(ctx.expression(2)), this.visit(ctx.expression(3)), ctx.op.getText()));
		
		if (leftToCheck.isInteger() && rightToCheck.isInteger()) {
            int left = leftToCheck.asInteger();
            int right = rightToCheck.asInteger();
            switch (ctx.op.getType()) {
                case LambdaParser.EQ:
                    if (left == right) {
                        result = new Value(trueReturn.value);
                    } else {
                        result = new Value(falseReturn.value);
                    }
					break;

                case LambdaParser.GT:
                    if (left > right) {
                        result = new Value(trueReturn.value);
                    } else {
                        result = new Value(falseReturn.value);
                    }
					break;

                case LambdaParser.LT:
                    if (left < right) {
                        result = new Value(trueReturn.value);
                    } else {
                        result = new Value(falseReturn.value);
                    }
					break;

                case LambdaParser.GTEQ:
                    if (left >= right) {
                        result = new Value(trueReturn.value);
                    } else {
                        result = new Value(falseReturn.value);
                    }
					break;

                case LambdaParser.LTEQ:
                    if (left <= right) {
                        result = new Value(trueReturn.value);
                    } else {
                        result = new Value(falseReturn.value);
                    }
					break;
                case LambdaParser.NEQ:
                    if (left != right) {
                        result = new Value(trueReturn.value);
                    } else {
                        result = new Value(falseReturn.value);
                    }
					break;
                default:
                    throw new RuntimeException("L'opérateur de l'expression est inconnu (mais cela ne devrait pas arriver)");
            }

        } else {
            result = new Value(new IfStat(leftToCheck, rightToCheck, this.visit(ctx.expression(2)), this.visit(ctx.expression(3)), ctx.op.getText()));
        }
		Value[] data= {leftToCheck,rightToCheck,new Value(trueReturn.value),new Value(falseReturn.value),result};
		checker.check("IfStat", rules, data);
        return result;
    }

    /**
     * @param ctx Le noeud courant
     * @return Une <code>RecRule</code>
     */
    @Override
    public Value visitRecRule(LambdaParser.RecRuleContext ctx) {
        String funcName = ctx.VAR(0).getText();
        String applyVar = ctx.VAR(1).getText();
        Value function = this.visit(ctx.expression());
		Value v = new Value(new RecFunction(new Variable(funcName), new Variable(applyVar), function));
		expressionType = v;
		Value[] data= {new Value(funcName),new Value(applyVar),function,v};
		checker.check("RecFunction", rules, data);
        return v;

    }

    /**
     * @param ctx Le noeud courant
     * @return L'epression dans la parenthèse.
     */
    @Override
    public Value visitParenExpression(LambdaParser.ParenExpressionContext ctx) {
        return this.visit(ctx.expression());
    }

    /**
     * @param ctx Le noeud courant
     * @return Un <code>Integer</code>
     */
    @Override
    public Value visitInteger(LambdaParser.IntegerContext ctx) {
        return new Value(Integer.valueOf(ctx.getText()));
    }


    /**
     * La méthode qui fait les multiplications
     * On ne calcule deux termes que si ils sont tous les deux des <code>Integer</code>
     *
     * @param ctx Le noeud courant
     * @return Un <code>Integer</code> résultant, ou un <code>Calcul</code> de l'opération.
     */
    @Override
    public Value visitMult(LambdaParser.MultContext ctx) {
        Value left = this.visit(ctx.expression(0));
        Value right = this.visit(ctx.expression(1));
		Value result;

        if(left.isInteger() && right.isInteger()){
			if(expressionType!=null){ 
				if(expressionType.isCalcul() && (expressionType.asCalcul().getOperateur()=='-' || expressionType.asCalcul().getOperateur()=='+')){
					expressionType =  new Value(new Calcul(new Value(left.asInteger()),'*',expressionType));
				}else{ expressionType =  new Value(new Calcul(expressionType, '*', new Value(right.asInteger()))); }}
			else{ expressionType = new Value(new Calcul(new Value(left), '*', new Value(right))); }
            result = new Value(left.asInteger() * right.asInteger()); 	
		}else{
			expressionType =  new Value(new Calcul(new Value(left), '+', new Value(right)));
			result = new Value(new Calcul(new Value(left), '*', new Value(right)));
		}
		Value[] data= {left,right,result};
		checker.check("Calcul", rules, data);
        return result;
    }

    /**
     * La méthode qui fait les additions
     * On ne calcule deux termes que si ils sont tous les deux des <code>Integer</code>
     *
     * @param ctx Le noeud courant
     * @return Un <code>Integer</code> résultant, ou un <code>Calcul</code> de l'opération.
     */
    @Override
    public Value visitAdd(LambdaParser.AddContext ctx) {
        Value left = this.visit(ctx.expression(0));
        Value right = this.visit(ctx.expression(1));
		Value result=new Value(1);
		
        switch (ctx.op.getType()) {
            case LambdaParser.PLUS:
                if(left.isInteger() && right.isInteger()){
					if(expressionType!=null){ expressionType =  new Value(new Calcul(expressionType, '+', new Value(right.asInteger()))); }
					else{ expressionType = new Value(new Calcul(new Value(left), '+', new Value(right))); }
                    result = new Value(left.asInteger() + right.asInteger());
				}else{
					expressionType =  new Value(new Calcul(new Value(left), '+', new Value(right)));
					result = new Value(new Calcul(new Value(left), '+', new Value(right)));
				}break;
            case LambdaParser.MINUS:
                if(left.isInteger() && right.isInteger()){
					if(expressionType!=null){ expressionType =  new Value(new Calcul(expressionType, '-', new Value(right))); }
					else{ expressionType =  new Value(new Calcul(new Value(left), '-', new Value(right))); }
                    result = new Value(left.asInteger() - right.asInteger());
				}else{
					expressionType =  new Value(new Calcul(new Value(left), '-', new Value(right)));
					result = new Value(new Calcul(new Value(left), '-', new Value(right)));
				}break;
            default:
                throw new RuntimeException("L'opérateur de l'expression est inconnu (mais cela ne devrait pas arriver)");

        }
		Value[] data= {left,right,result};
		checker.check("Calcul", rules, data);
        return result;
    }

	public static Value getExpressionType() {
		return expressionType;
	}
	
	public static void clearType(){
		expressionType = null;
	}
	
	public static void setRules(HashMap<String,String> rules) {
		LambdaBasicVisitor.rules = rules;
		checker = new ruleChecker();
	}
	
	public static boolean isCorrectlyTyped(){
		return checker.isRespected();
	}
}
