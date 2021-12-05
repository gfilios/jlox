package de.filios.interpreters.jlox.ast;

import java.util.List;
import de.filios.interpreters.jlox.Token;

abstract class Expr {

	/**
	    Generated Class from following rule
	    Binary       -> Expr left, Token operator, Expr right
	**/
	public static class Binary extends Expr {

		Binary( Expr left, Token operator, Expr right) {
			this.left = left;
			this.operator = operator;
			this.right = right;
		}
		final Expr left;
		final Token operator;
		final Expr right;
	}

	/**
	    Generated Class from following rule
	    Grouping     -> Expr expression
	**/
	public static class Grouping extends Expr {

		Grouping( Expr expression) {
			this.expression = expression;
		}
		final Expr expression;
	}

	/**
	    Generated Class from following rule
	    Literal      -> Object value
	**/
	public static class Literal extends Expr {

		Literal( Object value) {
			this.value = value;
		}
		final Object value;
	}

	/**
	    Generated Class from following rule
	    Unary        -> Token operator, Expr right
	**/
	public static class Unary extends Expr {

		Unary( Token operator, Expr right) {
			this.operator = operator;
			this.right = right;
		}
		final Token operator;
		final Expr right;
	}

}
