package de.filios.interpreters.jlox;

import javax.swing.*;

import java.util.List;

import static de.filios.interpreters.jlox.TokenType.*;

public class Interpreter implements Expr.Visitor<Object>, Stmt.Visitor<Void> {

    private Environment environment = new Environment();

    public String interpret(List<Stmt> statements) {

        try {
            for (Stmt statement : statements) {
                execute(statement);
            }
        } catch (RuntimeError exception) {
            Lox.runtimeError(exception);
        }
        return "";
    }

    private void execute(Stmt stmt) {
        stmt.accept(this);
    }

    private void executeBlock(List<Stmt> statements, Environment environment){
        Environment previous = this.environment;
        try {
            this.environment = environment;
            for (Stmt statememt: statements) {
                execute(statememt);
            }
        } finally {
            this.environment = previous;
        }
    }

    @Override
    public Void visitExpressionStmt(Stmt.Expression stmt) {
        evaluate(stmt.expression);
        return null;
    }

    @Override
    public Void visitPrintStmt(Stmt.Print stmt) {
        Object value = evaluate(stmt.expression);
        System.out.println(stringify(value));
        return null;
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
                checkDivideByZero(expr.operator, left, right);
                return (double) left / (double) right;
            case STAR:
                checkNumberOperands(expr.operator, left, right);
                return (double) left * (double) right;
            case PLUS:
                if (left instanceof Double && right instanceof Double) {
                    return (double) left + (double) right;
                }
                if (left instanceof String || right instanceof String) {
                    return stringify(left) + stringify(right);
                }

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
                return !isEqual(left, right);
            case EQUAL_EQUAL:
                return isEqual(left, right);
        }
        return evaluate(expr);
    }

    @Override
    public Object visitVariableExpr(Expr.Variable expr) {
        return environment.get(expr.name);
    }

    @Override
    public Void visitVarStmt(Stmt.Var stmt) {
        Object value = null;
        if (stmt.initializer != null) {
            value = evaluate(stmt.initializer);
        }

        environment.define(stmt.name, value);
        return null;

    }

    @Override
    public Object visitAssignExpr(Expr.Assign expr) {
        Object value = evaluate(expr.value);
        environment.assign(expr.name, value);
        return value;
    }

    @Override
    public Void visitBlockStmt(Stmt.Block stmt) {
        executeBlock(stmt.statements, new Environment(environment));
        return null;
    }

    @Override
    public Void visitIfStmt(Stmt.If stmt) {
        Object condition = evaluate(stmt.condition);
        if (isTruthy(condition)) {
            execute(stmt.thenBranch);
        } else if (stmt.elseBranch!=null){
            execute(stmt.elseBranch);
        }
        return null;
    }

    @Override
    public Object visitLogicalExpr(Expr.Logical expr) {
        Object left = evaluate(expr.left);
        if (expr.operator.type == OR) {
            if (isTruthy(left)) return left;
            else return evaluate(expr.right);

        }

        if (expr.operator.type == AND) {
            if (isTruthy(left)) return evaluate(expr.right);
            else return left;
        }

        return evaluate(expr.right);
    }

    private String stringify(Object object) {
        if (object == null) return "nil";

        if (object instanceof Double) {
            String text = object.toString();
            if (text.endsWith(".0")) {
                return text.substring(0, text.length() - 2);
            } else {
                return text;
            }
        }
        return object.toString();
    }

    private boolean isEqual(Object a, Object b) {
        if (a == null && b == null) return true;
        else if (a == null) return false;
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

    private void checkNumberOperand(Token operator, Object a) {
        if (a instanceof Double) return;
        throw new RuntimeError(operator, " Operand must be a number");
    }


    private void checkDivideByZero(Token operator, Object a, Object b) {
        if (a instanceof Double && b instanceof Double && ((Double) b).doubleValue() == 0.0) ;
        throw new RuntimeError(operator, " Divide by Zero");
    }

    private void checkNumberOperands(Token operator, Object a, Object b) {
        if (a instanceof Double && b instanceof Double) return;
        throw new RuntimeError(operator, " Operands must be numbers");
    }


}
