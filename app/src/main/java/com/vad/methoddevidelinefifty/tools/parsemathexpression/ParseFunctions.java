package com.vad.methoddevidelinefifty.tools.parsemathexpression;

import java.util.ArrayList;
import java.util.List;

public class ParseFunctions {

//    public List<Lexeme> lexAnalise(String expression) {
//        List<Lexeme> lexemes = new ArrayList<>();
//        int pos = 0;
//        char c;
//        while (pos < expression.length()) {
//            c = expression.charAt(pos);
//            switch (c) {
//                case '(':
//                    lexemes.add(new Lexeme(LexemeType.LEFT_BRACKET, c));
//                    pos++;
//                    continue;
//                case ')':
//                    lexemes.add(new Lexeme(LexemeType.RIGHT_BRACKET, c));
//                    pos++;
//                    continue;
//                case '+':
//                    lexemes.add(new Lexeme(LexemeType.OP_PLUS, c));
//                    pos++;
//                    continue;
//                case '-':
//                    lexemes.add(new Lexeme(LexemeType.OP_MINUS, c));
//                    pos++;
//                    continue;
//                case '*':
//                    lexemes.add(new Lexeme(LexemeType.OP_MUL, c));
//                    pos++;
//                    continue;
//                case '/':
//                    lexemes.add(new Lexeme(LexemeType.OP_DIV, c));
//                    pos++;
//                    continue;
//                case 'x':
//                    lexemes.add(new Lexeme(LexemeType.VAR, c));
//                    pos++;
//                    continue;
//                default:
//                    if(c <= '9' && c >= '0'){
//                        StringBuilder sb = new StringBuilder();
//                        do{
//                            sb.append(c);
//                            pos++;
//                            if (pos >= expression.length()){
//                                break;
//                            }
//                            c = expression.charAt(pos);
//                        } while (c <= '9' && c >= '0');
//                        lexemes.add(new Lexeme(LexemeType.NUMBER, sb.toString()));
//                    } else {
//                        if (c != ' ') {
//                            throw new RuntimeException("Unrealble read" + c);
//                        }
//                        pos++;
//                    }
//            }
//        }
//        lexemes.add(new Lexeme(LexemeType.EOF, ""));
//        return lexemes;
//    }

//    public double recurse(final String expression) {
//        //implement
//        List<Lexeme> tokens = new ArrayList<>();
//        int countOperation = lexAnalise(tokens, expression);
//
//        // получение и вывод результата
//        LexemeBuffer tokenList = new LexemeBuffer(tokens);
//        double result = Math.round(expression(tokenList) * 100) / 100.0;
//
//        return result;
//    }

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
    // Грамматика:
    // expression : add_sub EOF
    // add_sub : mul_div ( ( '+' | '-' ) mul_div )*
    // mul_div : pow ( ( '*' | '/' ) pow )*
    // pow : primary ( '^' primary )*
    // primary : NUM | '-' pow | ( '(' | 'cos(' | 'sin(' | 'tan(' ) add_sub ')'

    public static double expr(LexemeBuffer lexemes) {
        Lexeme lexeme = lexemes.next();

        if (lexeme.lexemeType == LexemeType.EOF){
            return 0;
        } else {
            lexemes.back();
            return add_sub(lexemes);
        }
    }

    public static double add_sub(LexemeBuffer tokenList) {
        double value = mul_div(tokenList);
        while (true) {
            Lexeme token = tokenList.next();
            switch (token.lexemeType) {
                case OP_PLUS:
                    value += mul_div(tokenList);
                    break;
                case OP_MINUS:
                    value -= mul_div(tokenList);
                    break;
                default:
                    tokenList.back();
                    return value;
            }
        }
    }

    public static double mul_div(LexemeBuffer tokenList) {
        double value = pow(tokenList);
        while (true) {
            Lexeme token = tokenList.next();
            switch (token.lexemeType) {
                case OP_MUL:
                    value *= pow(tokenList);
                    break;
                case OP_DIV:
                    value /= pow(tokenList);
                    break;
                default:
                    tokenList.back();
                    return value;
            }
        }
    }

    public static double pow(LexemeBuffer tokenList) {
        double value = primary(tokenList);
        while (true) {
            Lexeme token = tokenList.next();
            switch (token.lexemeType) {
                case OP_POW:
                    System.out.println("pow "+value+" "+ primary(tokenList));
                    value = Math.pow(value, primary(tokenList));
                    break;
                default:
                    tokenList.back();
                    return value;
            }
        }
    }

    public static double primary(LexemeBuffer lexemes) {
        Lexeme lexeme = lexemes.next();
        switch (lexeme.lexemeType) {
            case NUMBER:
                return Double.parseDouble(lexeme.value);
            case LEFT_BRACKET:
                double value = expr(lexemes);
                lexeme = lexemes.next();
                if (lexeme.lexemeType != LexemeType.LEFT_BRACKET) {
                    throw new IllegalStateException("expected ( " + lexeme.value
                            + " position " + lexemes.getPos());
                }
                return value;
            default:
                    throw new IllegalStateException("expected ( " + lexeme.value
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
