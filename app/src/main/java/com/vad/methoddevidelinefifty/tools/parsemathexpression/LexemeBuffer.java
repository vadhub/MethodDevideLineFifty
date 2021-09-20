package com.vad.methoddevidelinefifty.tools.parsemathexpression;

import java.util.List;

public class LexemeBuffer {
    private int pos;
    public List<Lexeme> lexemes;

    public LexemeBuffer(List<Lexeme> lexemes) {
        this.lexemes = lexemes;
    }

    public Lexeme next(){
        return lexemes.get(pos++);
    }

    public Lexeme back(){
        return lexemes.get(pos--);
    }

    public int getPos(){
        return pos;
    }
}
