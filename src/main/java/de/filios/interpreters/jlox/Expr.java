package de.filios.interpreters.jlox;

import java.util.List;
import de.filios.interpreters.jlox.Token;

abstract class Expr {

	abstract <R> R accept (Visitor<R> visitor);


	interface Visitor<R> {
		 R visitAssignExpr( Assign expr);
		 R visitBinaryExpr( Binary expr);
		 R visitGroupingExpr( Grouping expr);
		 R visitLiteralExpr( Literal expr);
		 R visitUnaryExpr( Unary expr);
		 R visitVariableExpr( Variable expr);
	}

	//    Assign       -> Token name, Expr value
	static class Assign extends Expr {

		Assign( Token name, Expr value) {
			this.name = name;
			this.value = value;
		}
		final Token name;
		final Expr value;

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitAssignExpr(this);
		}
	}

	//    Binary       -> Expr left, Token operator, Expr right
	static class Binary extends Expr {

		Binary( Expr left, Token operator, Expr right) {
			this.left = left;
			this.operator = operator;
			this.right = right;
		}
		final Expr left;
		final Token operator;
		final Expr right;

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitBinaryExpr(this);
		}
	}

	//    Grouping     -> Expr expression
	static class Grouping extends Expr {

		Grouping( Expr expression) {
			this.expression = expression;
		}
		final Expr expression;

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitGroupingExpr(this);
		}
	}

	//    Literal      -> Object value
	static class Literal extends Expr {

		Literal( Object value) {
			this.value = value;
		}
		final Object value;

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitLiteralExpr(this);
		}
	}

	//    Unary        -> Token operator, Expr right
	static class Unary extends Expr {

		Unary( Token operator, Expr right) {
			this.operator = operator;
			this.right = right;
		}
		final Token operator;
		final Expr right;

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitUnaryExpr(this);
		}
	}

	//    Variable     -> Token name
	static class Variable extends Expr {

		Variable( Token name) {
			this.name = name;
		}
		final Token name;

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitVariableExpr(this);
		}
	}

}
