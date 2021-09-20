package com.vad.methoddevidelinefifty.tools.parsemathexpression;

import java.util.ArrayList;
import java.util.List;

public class ParseFunctions {

    public List<Lexeme> lexAnalise(String expression) {
        List<Lexeme> lexemes = new ArrayList<>();
        int pos = 0;
        char c;
        while (pos < expression.length()) {
            c = expression.charAt(pos);
            switch (c) {
                case '(':
                    lexemes.add(new Lexeme(LexemeType.LEFT_BRACKET, c));
                    pos++;
                    continue;
                case ')':
                    lexemes.add(new Lexeme(LexemeType.RIGHT_BRACKET, c));
                    pos++;
                    continue;
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
                case 'x':
                    lexemes.add(new Lexeme(LexemeType.VAR, c));
                    pos++;
                    continue;
                default:
                    if(c <= '9' && c >= '0'){
                        StringBuilder sb = new StringBuilder();
                        do{
                            sb.append(c);
                            pos++;
                            if (pos >= expression.length()){
                                break;
                            }
                            c = expression.charAt(pos);
                        } while (c <= '9' && c >= '0');
                        lexemes.add(new Lexeme(LexemeType.NUMBER, sb.toString()));
                    } else {
                        if (c != ' ') {
                            throw new RuntimeException("Unrealble read" + c);
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


}
