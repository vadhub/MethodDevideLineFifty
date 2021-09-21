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

    public double recurse(final String expression) {
        //implement
        List<Lexeme> tokens = new ArrayList<>();
        int countOperation = lexAnalise(tokens, expression);

        // получение и вывод результата
        LexemeBuffer tokenList = new LexemeBuffer(tokens);
        double result = Math.round(expression(tokenList) * 100) / 100.0;

        return result;
    }

    public static int lexAnalise(List<Lexeme> lexemes, String expression) {
        int countOperation = 0;

        char[] expr = expression.toCharArray();
        int i = 0;
        while (i < expr.length) {
            switch (expr[i]) {
                case ' ':
                    i++;
                    break;
                case '+':
                    lexemes.add(new Lexeme(LexemeType.OP_PLUS, expr[i]));
                    i++;
                    countOperation++;
                    break;
                case '-':
                    lexemes.add(new Lexeme(LexemeType.OP_MINUS, expr[i]));
                    i++;
                    countOperation++;
                    break;
                case '*':
                    lexemes.add(new Lexeme(LexemeType.OP_MUL, expr[i]));
                    i++;
                    countOperation++;
                    break;
                case '/':
                    lexemes.add(new Lexeme(LexemeType.OP_DIV, expr[i]));
                    i++;
                    countOperation++;
                    break;
                case '^':
                    lexemes.add(new Lexeme(LexemeType.OP_POW, expr[i]));
                    i++;
                    countOperation++;
                    break;
                case '(':
                    lexemes.add(new Lexeme(LexemeType.LEFT_BRACKET, expr[i]));
                    i++;
                    break;
                case ')':
                    lexemes.add(new Lexeme(LexemeType.RIGHT_BRACKET, expr[i]));
                    i++;
                    break;
                case '.':
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    if (expr[i] == '.' &&                                       // если первый символ десятичный разделитель и
                            (i + 1 >= expr.length ||                            // это последний символ выражения или
                                    expr[i + 1] < '0' || expr[i + 1] > '9'))    // следующий символ не цифра
                        throw new RuntimeException("Unexpected character: " + expr[i] + " at position " + i);
                    double value = 0.0;
                    for (; i < expr.length && expr[i] >= '0' && expr[i] <= '9'; i++)
                        value = 10 * value + (expr[i] - '0');                   // накопление целой части
                    if (i < expr.length && expr[i] == '.') {
                        i++;
                        double factor = 1.0;                                    // множитель для десятичных разрядов
                        for (; i < expr.length && expr[i] >= '0' && expr[i] <= '9'; i++) {
                            factor *= 0.1;                                      // уменьшение множителя в 10 раз
                            value += (expr[i] - '0') * factor;                  // добавление десятичной позиции
                        }
                    }
                    lexemes.add(new Lexeme(LexemeType.NUMBER, value));
                    break;
                default:
                    if (i + 3 < expr.length &&
                            expr[i] == 'c' && expr[i + 1] == 'o' && expr[i + 2] == 's' && expr[i + 3] == '(') {
                        lexemes.add(new Lexeme(LexemeType.COS, "cos("));
                        i += 4;
                        countOperation++;
                    } else if (i + 3 < expr.length &&
                            expr[i] == 's' && expr[i + 1] == 'i' && expr[i + 2] == 'n' && expr[i + 3] == '(') {
                        lexemes.add(new Lexeme(LexemeType.SIN, "sin("));
                        i += 4;
                        countOperation++;
                    } else if (i + 3 < expr.length &&
                            expr[i] == 't' && expr[i + 1] == 'a' && expr[i + 2] == 'n' && expr[i + 3] == '(') {
                        lexemes.add(new Lexeme(LexemeType.TAN, "tan("));
                        i += 4;
                        countOperation++;
                    } else
                        throw new RuntimeException("Unexpected character: " + expr[i] + " at position " + i);
                    break;
            }
        }
        if (lexemes.size() == 0)                                                 // если выражение пустое
            lexemes.add(new Lexeme(LexemeType.NUMBER, 0.0));                  // оно равно нулю
        lexemes.add(new Lexeme(LexemeType.EOF, ""));

        return countOperation;
    }
    // Грамматика:
    // expression : add_sub EOF
    // add_sub : mul_div ( ( '+' | '-' ) mul_div )*
    // mul_div : pow ( ( '*' | '/' ) pow )*
    // pow : primary ( '^' primary )*
    // primary : NUM | '-' pow | ( '(' | 'cos(' | 'sin(' | 'tan(' ) add_sub ')'
    public static double expression(LexemeBuffer tokenList) {
        double value = add_sub(tokenList);
        Lexeme token = tokenList.next();
        if (token.lexemeType != LexemeType.EOF)
            throw new RuntimeException("Unexpected token: " + token.title + " at position " + tokenList.getPos());
        return value;
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
                    value = Math.pow(value, primary(tokenList));
                    break;
                default:
                    tokenList.back();
                    return value;
            }
        }
    }

    public static double primary(LexemeBuffer tokenList) {
        Lexeme token = tokenList.next();
        switch (token.lexemeType) {
            case NUMBER:
                return token.value;
            case OP_MINUS:
                return - pow(tokenList);
            case LEFT_BRACKET:
            case COS: case SIN: case TAN:
                double value = 0.0;
                if (token.lexemeType == LexemeType.LEFT_BRACKET)
                    value = add_sub(tokenList);
                else if (token.lexemeType == LexemeType.COS)
                    value = Math.cos(Math.toRadians(add_sub(tokenList)));
                else if (token.lexemeType == LexemeType.SIN)
                    value = Math.sin(Math.toRadians(add_sub(tokenList)));
                else if (token.lexemeType == LexemeType.TAN)
                    value = Math.tan(Math.toRadians(add_sub(tokenList)));
                token = tokenList.next();
                if (token.lexemeType != LexemeType.RIGHT_BRACKET)
                    throw new RuntimeException("Unexpected token: " + token.title + " at position " + tokenList.getPos());
                return value;
            default:
                throw new RuntimeException("Unexpected token: " + token.title + " at position " + tokenList.getPos());
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
