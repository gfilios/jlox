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
		 R visitPrintStmt( Print stmt);
		 R visitWhileStmt( While stmt);
		 R visitVarStmt( Var stmt);
	}

	//    Block          -> List<Stmt> statements
	static class Block extends Stmt {

		private static Logger logger = LogManager.getLogger( Block.class );

		Block( List<Stmt> statements) {
			this.statements = statements;
		}
		final List<Stmt> statements;

		@Override
		<R> R accept(Visitor<R> visitor) {
			logger.debug( this.toString() + ", statements=" + ( statements==null?"nil":statements.toString()));
			return visitor.visitBlockStmt(this);
		}
	}

	//    Expression     -> Expr expression
	static class Expression extends Stmt {

		private static Logger logger = LogManager.getLogger( Expression.class );

		Expression( Expr expression) {
			this.expression = expression;
		}
		final Expr expression;

		@Override
		<R> R accept(Visitor<R> visitor) {
			logger.debug( this.toString() + ", expression=" + ( expression==null?"nil":expression.toString()));
			return visitor.visitExpressionStmt(this);
		}
	}

	//    If             -> Expr condition, Stmt thenBranch, Stmt elseBranch
	static class If extends Stmt {

		private static Logger logger = LogManager.getLogger( If.class );

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
			logger.debug( this.toString() + ", condition=" + ( condition==null?"nil":condition.toString()) + ", thenBranch=" + ( thenBranch==null?"nil":thenBranch.toString()) + ", elseBranch=" + ( elseBranch==null?"nil":elseBranch.toString()));
			return visitor.visitIfStmt(this);
		}
	}

	//    Print          -> Expr expression
	static class Print extends Stmt {

		private static Logger logger = LogManager.getLogger( Print.class );

		Print( Expr expression) {
			this.expression = expression;
		}
		final Expr expression;

		@Override
		<R> R accept(Visitor<R> visitor) {
			logger.debug( this.toString() + ", expression=" + ( expression==null?"nil":expression.toString()));
			return visitor.visitPrintStmt(this);
		}
	}

	//    While          -> Expr condition, Stmt body
	static class While extends Stmt {

		private static Logger logger = LogManager.getLogger( While.class );

		While( Expr condition, Stmt body) {
			this.condition = condition;
			this.body = body;
		}
		final Expr condition;
		final Stmt body;

		@Override
		<R> R accept(Visitor<R> visitor) {
			logger.debug( this.toString() + ", condition=" + ( condition==null?"nil":condition.toString()) + ", body=" + ( body==null?"nil":body.toString()));
			return visitor.visitWhileStmt(this);
		}
	}

	//    Var            -> Token name, Expr initializer
	static class Var extends Stmt {

		private static Logger logger = LogManager.getLogger( Var.class );

		Var( Token name, Expr initializer) {
			this.name = name;
			this.initializer = initializer;
		}
		final Token name;
		final Expr initializer;

		@Override
		<R> R accept(Visitor<R> visitor) {
			logger.debug( this.toString() + ", name=" + ( name==null?"nil":name.toString()) + ", initializer=" + ( initializer==null?"nil":initializer.toString()));
			return visitor.visitVarStmt(this);
		}
	}

}
