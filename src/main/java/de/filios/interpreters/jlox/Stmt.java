package de.filios.interpreters.jlox;

import java.util.List;
import de.filios.interpreters.jlox.Token;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

abstract class Stmt {

	abstract <R> R accept (Visitor<R> visitor);

	public String toString(){
		return "Stmt: " + this.getClass().getSimpleName();
	}

	interface Visitor<R> {
		 R visitBlockStmt( Block stmt);
		 R visitExpressionStmt( Expression stmt);
		 R visitIfStmt( If stmt);
		 R visitFunctionStmt( Function stmt);
		 R visitClassStmt( Class stmt);
		 R visitPrintStmt( Print stmt);
		 R visitReturnStmt( Return stmt);
		 R visitWhileStmt( While stmt);
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

	//    Function       -> Token name, List<Token> params, List<Stmt> body
	static class Function extends Stmt {

		Function( Token name, List<Token> params, List<Stmt> body) {
			this.name = name;
			this.params = params;
			this.body = body;
		}
		final Token name;
		final List<Token> params;
		final List<Stmt> body;

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitFunctionStmt(this);
		}
	}

	//    Class          -> Token name, Expr.Variable superclass, List<Stmt.Function> methods
	static class Class extends Stmt {

		Class( Token name, Expr.Variable superclass, List<Stmt.Function> methods) {
			this.name = name;
			this.superclass = superclass;
			this.methods = methods;
		}
		final Token name;
		final Expr.Variable superclass;
		final List<Stmt.Function> methods;

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitClassStmt(this);
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

	//    Return         -> Token name, Expr value
	static class Return extends Stmt {

		Return( Token name, Expr value) {
			this.name = name;
			this.value = value;
		}
		final Token name;
		final Expr value;

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitReturnStmt(this);
		}
	}

	//    While          -> Expr condition, Stmt body
	static class While extends Stmt {

		While( Expr condition, Stmt body) {
			this.condition = condition;
			this.body = body;
		}
		final Expr condition;
		final Stmt body;

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitWhileStmt(this);
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
