package com.vad.methoddevidelinefifty.tools.parsemathexpression;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ParseFunctions {

    public static HashMap<String, Function> functionHashMap = getFunctionMap();

    public float parseExpression(String expr){
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
                case ',':
                    lexemes.add(new Lexeme(LexemeType.COMMA, c));
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
                            if ( c >= 'a' && c <= 'z'
                                    || c >= 'A' && c <= 'Z') {
                                StringBuilder sb = new StringBuilder();
                                do {
                                    sb.append(c);
                                    pos++;
                                    if (pos >= expression.length()) {
                                        break;
                                    }
                                    c = expression.charAt(pos);
                                } while (c >= 'a' && c <= 'z'
                                        || c >= 'A' && c <= 'Z');

                                if (functionHashMap.containsKey(sb.toString())) {
                                    lexemes.add(new Lexeme(LexemeType.NAME, sb.toString()));
                                } else {
                                    throw new IllegalStateException("Unexpected character " + c);
                                }
                            }
                        } else {
                            pos++;
                        }
                    }
            }
        }
        lexemes.add(new Lexeme(LexemeType.EOF, ""));
        return lexemes;
    }

    private static HashMap<String, Function> getFunctionMap(){
        HashMap<String, Function> functionHashMap = new HashMap<>();
        functionHashMap.put("pow", args -> {
            if (args.size() != 2) {
                throw new IllegalStateException("Min arguments not is 2; args " + args.size());
            }
           return (float) Math.pow(args.get(0), args.get(1));
        });

        functionHashMap.put("sin", args -> {
            if (args.size() != 1) {
                throw new IllegalStateException("Min arguments not is 1" + args.size());
            }
            return (float)  Math.sin(Math.toRadians(args.get(0)));
        });

        functionHashMap.put("cos", args -> {
            if (args.size() != 1) {
                throw new IllegalStateException("Min arguments not is 1" + args.size());
            }
            return (float) Math.cos(Math.toRadians(args.get(0)));
        });

        functionHashMap.put("tan", args -> {
            if (args.size() != 1) {
                throw new IllegalStateException("Min arguments not is 1" + args.size());
            }
            return (float) Math.tan(Math.toRadians(args.get(0)));
        });



        return functionHashMap;
    }

    // expr : add_sub EOF
    //
    // plusMinus : mulDiv ( ( '+' | '-' ) mulDiv )*
    //
    // mulDiv : pow ( ( '*' | '/' ) pow )*
    //
    // pow : factor ( '^' factor )*
    //
    // factor : func | unary | NUMBER | '(' expr ')' ;
    //
    // unary : '-' factor

    // func : NAME '(' (expr (,expr)+)? ')'

    private static float expr(LexemeBuffer lexemes) {
        Lexeme lexeme = lexemes.next();

        if (lexeme.lexemeType == LexemeType.EOF){
            return 0;
        } else {
            lexemes.back();
            return plusMinus(lexemes);
        }
    }

    private static float plusMinus(LexemeBuffer lexemes) {
        float value = mulDiv(lexemes);
        while (true) {
            Lexeme token = lexemes.next();
            switch (token.lexemeType) {
                case OP_PLUS:
                    value += mulDiv(lexemes);
                    break;
                case OP_MINUS:
                    value -= mulDiv(lexemes);
                    break;
                case EOF:
                case RIGHT_BRACKET:
                case COMMA:
                    lexemes.back();
                    return value;
                default:
                    throw new IllegalStateException("unexpected " + value
                            + " position " + lexemes.getPos());
            }
        }
    }

    private static float mulDiv(LexemeBuffer lexemes) {
        float value = factor(lexemes);
        while (true) {
            Lexeme token = lexemes.next();
            switch (token.lexemeType) {
                case OP_MUL:
                    value *= factor(lexemes);
                    break;
                case OP_DIV:
                    value /= factor(lexemes);
                    break;
                case EOF:
                case RIGHT_BRACKET:
                case OP_PLUS:
                case COMMA:
                case OP_MINUS:
                    lexemes.back();
                    return value;
                default:
                    throw new IllegalStateException("unexpected " + value
                            + " position " + lexemes.getPos());
            }
        }
    }

    private static float factor(LexemeBuffer lexemes) {
        Lexeme lexeme = lexemes.next();
        float value = 0;
        switch (lexeme.lexemeType) {
            case NAME:
                lexemes.back();
                return func(lexemes);
            case OP_MINUS:
                value = -factor(lexemes);
                return value;
            case NUMBER:
                return Float.parseFloat(lexeme.value);
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

    private static float func(LexemeBuffer lexemes) {
        String name = lexemes.next().value;
        Lexeme lexeme = lexemes.next();

        if (lexeme.lexemeType != LexemeType.LEFT_BRACKET) {
            throw new IllegalStateException("Wrong function call " + lexeme.value
                    + " position " + lexemes.getPos());
        }

        ArrayList<Float> args = new ArrayList<>();

        lexeme = lexemes.next();
        if (lexeme.lexemeType != LexemeType.RIGHT_BRACKET) {
            lexemes.back();
            do {
                args.add(expr(lexemes));
                lexeme = lexemes.next();

                if (lexeme.lexemeType != LexemeType.COMMA && lexeme.lexemeType != LexemeType.RIGHT_BRACKET) {
                    throw new IllegalStateException("Wrong function call " + lexeme.value
                            + " position " + lexemes.getPos());
                }

            } while (lexeme.lexemeType == LexemeType.COMMA);
        }

        return functionHashMap.get(name).apply(args);
    }

    public String getFraction(double val, double ratio) {
        for (int i = 1;; i++) {
            double tem = val / (1D / i);
            if (Math.abs(tem - Math.round(tem)) < ratio)
                return Math.round(tem) + "/" + i;
        }
    }

}
