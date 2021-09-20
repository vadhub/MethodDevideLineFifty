package com.vad.methoddevidelinefifty.tools.parsemathexpression;

public class Lexeme {
    LexemeType lexemeType;
    double value;
    String title;

    public Lexeme(LexemeType lexemeType, Character title) {
        this.lexemeType = lexemeType;
        value = Double.NaN;
        this.title = title.toString();
    }

    public Lexeme(LexemeType lexemeType, String title) {
        this.lexemeType = lexemeType;
        this.value = Double.NaN;
        this.title = title;
    }

    public Lexeme(LexemeType lexemeType, Double value) {
        this.lexemeType = lexemeType;
        this.value = value;
        this.title = String.valueOf(value);
    }

    @Override
    public String toString() {
        return "Lexeme{" +
                "lexemeType=" + lexemeType +
                ", value='" + value + '\'' +
                '}';
    }
}
