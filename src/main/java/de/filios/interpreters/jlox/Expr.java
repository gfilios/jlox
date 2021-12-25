package de.filios.interpreters.jlox;

import java.util.List;
import de.filios.interpreters.jlox.Token;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

abstract class Expr {

	abstract <R> R accept (Visitor<R> visitor);

	public String toString(){
		return "Expr: " + this.getClass().getSimpleName();
	}

	interface Visitor<R> {
		 R visitAssignExpr( Assign expr);
		 R visitBinaryExpr( Binary expr);
		 R visitCallExpr( Call expr);
		 R visitGroupingExpr( Grouping expr);
		 R visitLiteralExpr( Literal expr);
		 R visitLogicalExpr( Logical expr);
		 R visitUnaryExpr( Unary expr);
		 R visitVariableExpr( Variable expr);
	}

	//    Assign       -> Token name, Expr value
	static class Assign extends Expr {

		private static Logger logger = LogManager.getLogger( Assign.class );

		Assign( Token name, Expr value) {
			this.name = name;
			this.value = value;
		}
		final Token name;
		final Expr value;

		@Override
		<R> R accept(Visitor<R> visitor) {
			if(logger.isDebugEnabled()) {
				logger.debug( this.toString() + ", name=" + ( name==null?"nil":name.toString()) + ", value=" + ( value==null?"nil":value.toString()));
			}
			return visitor.visitAssignExpr(this);
		}
	}

	//    Binary       -> Expr left, Token operator, Expr right
	static class Binary extends Expr {

		private static Logger logger = LogManager.getLogger( Binary.class );

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
			if(logger.isDebugEnabled()) {
				logger.debug( this.toString() + ", left=" + ( left==null?"nil":left.toString()) + ", operator=" + ( operator==null?"nil":operator.toString()) + ", right=" + ( right==null?"nil":right.toString()));
			}
			return visitor.visitBinaryExpr(this);
		}
	}

	//    Call         -> Expr callee, Token paren, List<Expr> arguments
	static class Call extends Expr {

		private static Logger logger = LogManager.getLogger( Call.class );

		Call( Expr callee, Token paren, List<Expr> arguments) {
			this.callee = callee;
			this.paren = paren;
			this.arguments = arguments;
		}
		final Expr callee;
		final Token paren;
		final List<Expr> arguments;

		@Override
		<R> R accept(Visitor<R> visitor) {
			if(logger.isDebugEnabled()) {
				logger.debug( this.toString() + ", callee=" + ( callee==null?"nil":callee.toString()) + ", paren=" + ( paren==null?"nil":paren.toString()) + ", arguments=" + ( arguments==null?"nil":arguments.toString()));
			}
			return visitor.visitCallExpr(this);
		}
	}

	//    Grouping     -> Expr expression
	static class Grouping extends Expr {

		private static Logger logger = LogManager.getLogger( Grouping.class );

		Grouping( Expr expression) {
			this.expression = expression;
		}
		final Expr expression;

		@Override
		<R> R accept(Visitor<R> visitor) {
			if(logger.isDebugEnabled()) {
				logger.debug( this.toString() + ", expression=" + ( expression==null?"nil":expression.toString()));
			}
			return visitor.visitGroupingExpr(this);
		}
	}

	//    Literal      -> Object value
	static class Literal extends Expr {

		private static Logger logger = LogManager.getLogger( Literal.class );

		Literal( Object value) {
			this.value = value;
		}
		final Object value;

		@Override
		<R> R accept(Visitor<R> visitor) {
			if(logger.isDebugEnabled()) {
				logger.debug( this.toString() + ", value=" + ( value==null?"nil":value.toString()));
			}
			return visitor.visitLiteralExpr(this);
		}
	}

	//    Logical      -> Expr left, Token operator, Expr right
	static class Logical extends Expr {

		private static Logger logger = LogManager.getLogger( Logical.class );

		Logical( Expr left, Token operator, Expr right) {
			this.left = left;
			this.operator = operator;
			this.right = right;
		}
		final Expr left;
		final Token operator;
		final Expr right;

		@Override
		<R> R accept(Visitor<R> visitor) {
			if(logger.isDebugEnabled()) {
				logger.debug( this.toString() + ", left=" + ( left==null?"nil":left.toString()) + ", operator=" + ( operator==null?"nil":operator.toString()) + ", right=" + ( right==null?"nil":right.toString()));
			}
			return visitor.visitLogicalExpr(this);
		}
	}

	//    Unary        -> Token operator, Expr right
	static class Unary extends Expr {

		private static Logger logger = LogManager.getLogger( Unary.class );

		Unary( Token operator, Expr right) {
			this.operator = operator;
			this.right = right;
		}
		final Token operator;
		final Expr right;

		@Override
		<R> R accept(Visitor<R> visitor) {
			if(logger.isDebugEnabled()) {
				logger.debug( this.toString() + ", operator=" + ( operator==null?"nil":operator.toString()) + ", right=" + ( right==null?"nil":right.toString()));
			}
			return visitor.visitUnaryExpr(this);
		}
	}

	//    Variable     -> Token name
	static class Variable extends Expr {

		private static Logger logger = LogManager.getLogger( Variable.class );

		Variable( Token name) {
			this.name = name;
		}
		final Token name;

		@Override
		<R> R accept(Visitor<R> visitor) {
			if(logger.isDebugEnabled()) {
				logger.debug( this.toString() + ", name=" + ( name==null?"nil":name.toString()));
			}
			return visitor.visitVariableExpr(this);
		}
	}

}
