package com.vad.methoddevidelinefifty.tools.parsemathexpression;

import java.util.ArrayList;
import java.util.List;

public class ParseFunctions {

    public double parseExpression(String expr){
        List<Lexeme> lexemes = lexAnalise(expr);
        LexemeBuffer buffer = new LexemeBuffer(lexemes);
        return expr(buffer);
    }

    public static List<Lexeme> lexAnalise(String expression) {
        List<Lexeme> lexemes = new ArrayList<>();
        int pos = 0;
        while (pos < expression.length()) {
            char c = expression.charAt(pos);
            switch (c) {
                case '+':
                    lexemes.add(new Lexeme(LexemeType.OP_PLUS, c));
                    pos++;
                    continue;
                case '-':
                    lexemes.add(new Lexeme(LexemeType.OP_MINUS, c));
                    pos++;
                    continue;
                case '*':
                    lexemes.add(new Lexeme(LexemeType.OP_MUL, c));
                    pos++;
                    continue;
                case '/':
                    lexemes.add(new Lexeme(LexemeType.OP_DIV, c));
                    pos++;
                    continue;
                case '^':
                    lexemes.add(new Lexeme(LexemeType.OP_POW, c));
                    pos++;
                    continue;
                case '(':
                    lexemes.add(new Lexeme(LexemeType.LEFT_BRACKET, c));
                    pos++;
                    continue;
                case ')':
                    lexemes.add(new Lexeme(LexemeType.RIGHT_BRACKET, c));
                    pos++;
                    continue;
                default:
                    if ((c <= '9' && c >= '0') || c == '.') {
                        StringBuilder sb = new StringBuilder();
                        int countDot = 0;
                        do {
                            if (c == '.') {
                                countDot++;
                                if (countDot>1) {
                                    throw new IllegalStateException("count dots is "+countDot);
                                }
                            }
                            sb.append(c);
                            pos++;
                            if (pos >= expression.length()) {
                                break;
                            }
                            c = expression.charAt(pos);
                        } while ((c <= '9' && c >= '0') || c == '.');
                        lexemes.add(new Lexeme(LexemeType.NUMBER, sb.toString()));
                    }else{
                        if (c != ' ') {
                            System.out.println("so");
                            break;
                        }
                        pos++;
                    }
            }
        }
        lexemes.add(new Lexeme(LexemeType.EOF, ""));
        return lexemes;
    }

    // expr : add_sub EOF
    //
    // plusMinus : mulDiv ( ( '+' | '-' ) mulDiv )*
    //
    // mulDiv : pow ( ( '*' | '/' ) pow )*
    //
    // pow : factor ( '^' factor )*
    //
    // factor : unary | NUMBER | '(' expr ')' ;
    //
    // unary : '-' factor

    // func : NAME '(' (expr (,expr)+)? ')'

    private static double expr(LexemeBuffer lexemes) {
        Lexeme lexeme = lexemes.next();

        if (lexeme.lexemeType == LexemeType.EOF){
            return 0;
        } else {
            lexemes.back();
            return plusMinus(lexemes);
        }
    }

    private static double plusMinus(LexemeBuffer lexemes) {
        double value = mulDiv(lexemes);
        while (true) {
            Lexeme token = lexemes.next();
            switch (token.lexemeType) {
                case OP_PLUS:
                    value += mulDiv(lexemes);
                    break;
                case OP_MINUS:
                    value -= mulDiv(lexemes);
                    break;
                default:
                    lexemes.back();
                    return value;
            }
        }
    }

    private static double mulDiv(LexemeBuffer lexemes) {
        double value = pow(lexemes);
        while (true) {
            Lexeme token = lexemes.next();
            switch (token.lexemeType) {
                case OP_MUL:
                    value *= pow(lexemes);
                    break;
                case OP_DIV:
                    value /= pow(lexemes);
                    break;
                default:
                    lexemes.back();
                    return value;
            }
        }
    }

    private static double pow(LexemeBuffer lexemes) {
        double value = factor(lexemes);
        while (true) {
            Lexeme token = lexemes.next();
            switch (token.lexemeType) {
                case OP_POW:
                    System.out.println("pow "+value+" "+ factor(lexemes));
                    value = Math.pow(value, factor(lexemes));
                    break;
                default:
                    lexemes.back();
                    return value;
            }
        }
    }

    private static double factor(LexemeBuffer lexemes) {
        Lexeme lexeme = lexemes.next();
        double value = 0;
        switch (lexeme.lexemeType) {
            case OP_MINUS:
                value = -factor(lexemes);
                return value;
            case NUMBER:
                return Double.parseDouble(lexeme.value);
            case LEFT_BRACKET:
                value = plusMinus(lexemes);
                lexeme = lexemes.next();
                if (lexeme.lexemeType != LexemeType.RIGHT_BRACKET) {
                    throw new IllegalStateException("expected ( " + lexeme.value
                            + " position " + lexemes.getPos());
                }
                return value;
            default:
                    throw new IllegalStateException("unexpected " + lexeme.value
                            + " position " + lexemes.getPos());
        }
    }

    public String getFraction(double val, double ratio) {
        for (int i = 1;; i++) {
            double tem = val / (1D / i);
            if (Math.abs(tem - Math.round(tem)) < ratio)
                return Math.round(tem) + "/" + i;
        }
    }

}
