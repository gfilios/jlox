package de.filios.interpreters.jlox;

public class AstPrinter implements Expr.Visitor <String>{

    String print(Expr expr){
        return expr.accept(this);
    }

    @Override
    public String visitGroupingExpr(Expr.Grouping expr){
        return parenthesize("group", expr.expression);
    }

    @Override
    public String visitBinaryExpr(Expr.Binary expr){
        return parenthesize(expr.operator.lexeme, expr.left, expr.right);
    }

    @Override
    public String visitLiteralExpr(Expr.Literal expr){
        if (expr.value==null) return "nil";
        return expr.value.toString();
    }

    @Override
    public String visitUnaryExpr(Expr.Unary expr) {
        return parenthesize(expr.operator.lexeme,  expr.right);
    }

    private String parenthesize(String name, Expr... exprs) {
        StringBuilder builder = new StringBuilder();
        builder.append("(").append(name);
        for (Expr expr:exprs) {
            builder.append (" ");
            builder.append(expr.accept(this));
        }
        builder.append(")");
        return builder.toString();
    }

    @Override
    public String visitVariableExpr(Expr.Variable expr) {
        return expr.name.lexeme;
    }

    @Override
    public String visitAssignExpr(Expr.Assign expr) {
        StringBuilder builder = new StringBuilder();
        builder.append("(").append("=");
        builder.append(expr.name.lexeme).append(" ").append(expr.value);
        return builder.toString();
    }

    @Override
    public String visitLogicalExpr(Expr.Logical expr) {
        StringBuilder builder = new StringBuilder();
        builder.append("(").append(expr.left).append(" ");
        builder.append(expr.operator.lexeme).append(" ");
        builder.append(expr.right).append(")");
        return builder.toString();
    }

    @Override
    public String visitCallExpr(Expr.Call expr) {
        StringBuilder builder = new StringBuilder();
        builder.append(expr.callee.toString());
        return builder.toString();
    }


    @Override
    public String visitGetExpr(Expr.Get expr) {
        return null;
    }

    @Override
    public String visitSetExpr(Expr.Set expr) {
        return null;
    }

    @Override
    public String visitThisExpr(Expr.This expr) {
        return null;
    }

    @Override
    public String visitSuperExpr(Expr.Super expr) {
        return null;
    }
}
