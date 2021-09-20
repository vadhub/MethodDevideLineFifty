package com.vad.methoddevidelinefifty.screens.main;

import com.vad.methoddevidelinefifty.tools.parsemathexpression.ParseFunctions;

public class PresenterMain {

    private ViewMain view;
    private ParseFunctions parseFunctions = new ParseFunctions();
    public PresenterMain(ViewMain view) {
        this.view = view;
    }

    public void calculateFunction(String expression){
        view.showResult(parseFunctions.getFraction(parseFunctions.recurse(expression), 0.01)+"");
    }
}
