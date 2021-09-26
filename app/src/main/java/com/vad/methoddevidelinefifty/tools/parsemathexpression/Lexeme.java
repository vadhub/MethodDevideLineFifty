package com.vad.methoddevidelinefifty.tools.parsemathexpression;

public class Lexeme {
    LexemeType lexemeType;
    String value;

    public Lexeme(LexemeType lexemeType, Character value) {
        this.lexemeType = lexemeType;
        this.value = value.toString();
    }

    public Lexeme(LexemeType lexemeType, String value) {
        this.lexemeType = lexemeType;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Lexeme{" +
                "lexemeType=" + lexemeType +
                '}';
    }
}
