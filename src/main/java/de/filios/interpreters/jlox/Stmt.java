package de.filios.interpreters.jlox;

import java.util.List;
import de.filios.interpreters.jlox.Token;

abstract class Stmt {

	abstract <R> R accept (Visitor<R> visitor);


	interface Visitor<R> {
		 R visitBlockStmt( Block stmt);
		 R visitExpressionStmt( Expression stmt);
		 R visitIfStmt( If stmt);
		 R visitPrintStmt( Print stmt);
		 R visitVarStmt( Var stmt);
	}

	//    Block          -> List<Stmt> statements
	static class Block extends Stmt {

		Block( List<Stmt> statements) {
			this.statements = statements;
		}
		final List<Stmt> statements;

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitBlockStmt(this);
		}
	}

	//    Expression     -> Expr expression
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

	//    If             -> Expr condition, Stmt thenBranch, Stmt elseBranch
	static class If extends Stmt {

		If( Expr condition, Stmt thenBranch, Stmt elseBranch) {
			this.condition = condition;
			this.thenBranch = thenBranch;
			this.elseBranch = elseBranch;
		}
		final Expr condition;
		final Stmt thenBranch;
		final Stmt elseBranch;

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitIfStmt(this);
		}
	}

	//    Print          -> Expr expression
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

	//    Var            -> Token name, Expr initializer
	static class Var extends Stmt {

		Var( Token name, Expr initializer) {
			this.name = name;
			this.initializer = initializer;
		}
		final Token name;
		final Expr initializer;

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitVarStmt(this);
		}
	}

}
