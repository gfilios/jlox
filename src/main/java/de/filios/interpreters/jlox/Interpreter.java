package de.filios.interpreters.jlox;

import javax.swing.*;

import static de.filios.interpreters.jlox.TokenType.*;

public class Interpreter implements Expr.Visitor<Object> {


    public String interpret(Expr expression){
        try {
            Object value = evaluate(expression);
            return stringify(value);

        } catch (RuntimeError exception) {
            Lox.runtimeError(exception);
        }
        return "";
    }

    private String stringify(Object object){
        if (object == null) return "nil";

        if (object instanceof Double) {
            String text = object.toString();
            if (text.endsWith(".0")) {
                return text.substring(0,text.length()-1);
            } else {
                return text;
            }
        }
        return object.toString();
    }

    @Override
    public Object visitLiteralExpr(Expr.Literal expr) {
        return expr.value;
    }

    @Override
    public Object visitGroupingExpr(Expr.Grouping expr) {

        return evaluate(expr.expression);
    }

    @Override
    public Object visitUnaryExpr(Expr.Unary expr) {
        // unary → ( "!" | "-" ) unary | primary ;
        Object right = evaluate(expr.right);

        switch (expr.operator.type) {
            case MINUS:
                checkNumberOperand(expr.operator, right);
                return -(double) right;
            case BANG:
                return !isTruthy(right);
        }

        return null;

    }

    @Override
    public Object visitBinaryExpr(Expr.Binary expr) {
        Object left = evaluate(expr.left);
        Object right = evaluate(expr.right);

        switch (expr.operator.type) {
            case MINUS:
                checkNumberOperands(expr.operator, left, right);
                return (double) left - (double) right;
            case SLASH:
                checkNumberOperands(expr.operator, left, right);
                return (double) left / (double) right;
            case STAR:
                checkNumberOperands(expr.operator, left, right);
                return (double) left * (double) right;
            case PLUS:
                if (left instanceof Double && right instanceof Double) {
                    return (double) left + (double) right;
                }
                if (left instanceof String && right instanceof String) {
                    return (String) left + (String) right;
                }
                throw new RuntimeError(expr.operator, " Operand must be a either two number or two strings" );

            case GREATER:
                checkNumberOperands(expr.operator, left, right);
                return (double) left > (double) right;
            case GREATER_EQUAL:
                checkNumberOperands(expr.operator, left, right);
                return (double) left >= (double) right;
            case LESS:
                checkNumberOperands(expr.operator, left, right);
                return (double) left < (double) right;
            case LESS_EQUAL:
                checkNumberOperands(expr.operator, left, right);
                return (double) left <= (double) right;
            case BANG_EQUAL:
                return !isEqual(left,right);
            case EQUAL_EQUAL:
                return isEqual(left,right);
        }
        return evaluate(expr);
    }

    private boolean isEqual(Object a,Object b){
        if (a==null && b== null) return true;
        else if (a==null) return false;
        return a.equals(b);
    }

    private boolean isTruthy(Object object) {
        if (object == null) return false;
        if (object instanceof Boolean) {
            return ((Boolean) object).booleanValue();
        }
        return true;
    }

    private Object evaluate(Expr expr) {
        return expr.accept(this);
    }

    private void checkNumberOperand(Token operator, Object a){
        if (a instanceof Double) return;
        throw new RuntimeError(operator, " Operand must be a number" );
    }

    private void checkNumberOperands(Token operator, Object a, Object b){
        if (a instanceof Double && b instanceof Double) return;
        throw new RuntimeError(operator, " Operands must be numbers" );
    }
}
