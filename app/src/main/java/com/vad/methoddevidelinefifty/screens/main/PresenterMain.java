package com.vad.methoddevidelinefifty.screens.main;

import com.vad.methoddevidelinefifty.methods.methoddevidebylinefifty.DividingLineHalf;
import com.vad.methoddevidelinefifty.methods.methoddevidebylinefifty.DividingResult;
import com.vad.methoddevidelinefifty.tools.parsemathexpression.ParseFunctions;

import java.util.List;

public class PresenterMain {

    private ViewMain view;
    private ParseFunctions parseFunctions = new ParseFunctions();
    private DividingLineHalf dividingLineHalf = new DividingLineHalf();
    public PresenterMain(ViewMain view) {
        this.view = view;
    }

    public void calculateFunction(float a, float b, float eps, String expression) {
        List<DividingResult> dividingResult = dividingLineHalf.divideOfLineHalf(a, b ,eps, expression);
        StringBuilder stringBuilder = new StringBuilder();

        // xck = (a + b)/2
        //
        //l = b - a
        stringBuilder.append("step 1").append('\n');
        stringBuilder.append("a = ").append(a).append('\n')
                .append("b = ").append(a).append('\n')
                .append("eps = ").append(eps).append('\n');
        stringBuilder.append("step 2").append('\n');
        stringBuilder.append("k = ").append(0).append('\n');
        stringBuilder.append("step 3").append('\n');
        stringBuilder.append("Xck = ( ").append(a).append(" + ").append(b).append(" )").append('\n');
        stringBuilder.append("L = ").append(b).append(" - ").append(a).append('\n');
        for (DividingResult r : dividingResult) {
            stringBuilder
                    .append("k = ").append(r.getK()).append('\n')
                    .append("Xck = ").append(r.getfFromYk()).append('\n')
                    .append("Yk = ").append(r.getYk()).append('\n')
                    .append("Zk = ").append(r.getZk()).append('\n')
                    .append("f(Xck) = ").append(r.getfFromXck()).append("").append('\n')
                    .append("f(Yk) = ").append(r.getfFromYk()).append('\n')
                    .append("f(Zk) = ").append(r.getfFromZk()).append('\n')
                    .append("L = ").append(r.getL()).append('\n');
            if (r.getfFromYk()<r.getfFromXck()) {
                stringBuilder
                        .append(r.getfFromYk()).append("<").append(r.getfFromXck()).append('\n')
                        .append("ak = ").append(r.getXck()).append('\n')
                        .append("Xck = ").append(r.getYk()).append('\n');
            } else {
                if (r.getfFromZk()<r.getfFromXck()) {
                    stringBuilder
                            .append(r.getfFromYk()).append(">").append(r.getfFromXck()).append('\n')
                            .append(r.getfFromZk()).append("<").append(r.getfFromXck()).append('\n')
                            .append("ak = ").append(r.getXck()).append('\n')
                            .append("Xck = ").append(r.getZk()).append('\n');
                } else {
                    stringBuilder
                            .append(r.getfFromYk()).append(">").append(r.getfFromXck()).append('\n')
                            .append(r.getfFromZk()).append(">").append(r.getfFromXck()).append('\n')
                            .append("ak = ").append(r.getYk()).append('\n')
                            .append("bk = ").append(r.getZk());
                }
            }
            if (r.getL() > eps) {
                stringBuilder.append(r.getL()).append(">=").append(eps).append('\n').append("k = k + 1").append('\n');
            }
            stringBuilder.append('\n');
        }

        stringBuilder
                .append(dividingResult.get(dividingResult.size()-1).getL()).append("<").append(eps)
                .append("result = ").append(dividingResult.get(dividingResult.size()-1).getXck()).append('\n')
                .append("result(fraction) = ").append(parseFunctions.getFraction(dividingResult.get(dividingResult.size()-1).getXck(), 0.01)).append('\n');
        view.showResult(stringBuilder.toString());
    }
}
