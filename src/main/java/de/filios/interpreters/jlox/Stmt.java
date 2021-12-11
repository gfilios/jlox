package de.filios.interpreters.jlox;

import java.util.List;
import de.filios.interpreters.jlox.Token;

abstract class Stmt {

	abstract <R> R accept (Visitor<R> visitor);


	interface Visitor<R> {
		 R visitExpressionStmt( Expression stmt);
		 R visitPrintStmt( Print stmt);
	}

	//    Expression       -> Expr expression
	static class Expression extends Stmt {

		Expression( Expr expression) {
			this.expression = expression;
		}
		final Expr expression;

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitExpressionStmt(this);
		}
	}

	//    Print            -> Expr expression
	static class Print extends Stmt {

		Print( Expr expression) {
			this.expression = expression;
		}
		final Expr expression;

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitPrintStmt(this);
		}
	}

}
