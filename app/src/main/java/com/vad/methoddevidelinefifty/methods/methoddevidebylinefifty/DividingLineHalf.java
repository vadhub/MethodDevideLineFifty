package com.vad.methoddevidelinefifty.methods.methoddevidebylinefifty;

import com.vad.methoddevidelinefifty.tools.parsemathexpression.ParseFunctions;

public class DividingLineHalf {
    private int k;
    private int a;
    private int b;
    private String expression;
    private ParseFunctions parseFunctions = new ParseFunctions();
    public DividingLineHalf(int k, int a, int b, String expression) {
        this.k = k;
        this.a = a;
        this.b = b;
        this.expression=expression;
    }

    //for step 3
    private float midpoint(){
        return (float) (a+b)/2;
    }

    private float l2K(){
        return b-a;
    }

    private float functionFromXkc(){
        return 0;
    }

}
