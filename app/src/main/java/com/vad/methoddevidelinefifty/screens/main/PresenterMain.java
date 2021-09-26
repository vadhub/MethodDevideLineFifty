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
        for (DividingResult r : dividingResult) {
            stringBuilder
                    .append("k = " + r.getK()).append('\n')
                    .append("Xck = " + r.getfFromYk()).append('\n')
                    .append("Yk = " + r.getYk()).append('\n')
                    .append("Zk = " + r.getZk()).append('\n')
                    .append("f(Xck) = " + r.getfFromXck()).append('\n')
                    .append("f(Yk) = " + r.getfFromYk()).append('\n')
                    .append("f(Zk) = " + r.getfFromZk()).append('\n')
                    .append("l = " + r.getL()).append('\n');

            if (r.getfFromYk()<r.getfFromXck()) {
                stringBuilder
                        .append(r.getfFromYk() + "<" + r.getfFromXck()).append('\n')
                        .append("ak = " + r.getXck()).append('\n')
                        .append("Xck = " + r.getYk()).append('\n');
            } else {
                if (r.getfFromZk()<r.getfFromXck()) {
                    stringBuilder
                            .append(r.getfFromYk() + ">" + r.getfFromXck()).append('\n')
                            .append(r.getfFromZk() + "<" + r.getfFromXck()).append('\n')
                            .append("ak = " + r.getXck()).append('\n')
                            .append("Xck = " + r.getZk()).append('\n');
                } else {
                    stringBuilder
                            .append(r.getfFromYk() + ">" + r.getfFromXck()).append('\n')
                            .append(r.getfFromZk() + ">" + r.getfFromXck()).append('\n')
                            .append("ak = " + r.getYk()).append('\n')
                            .append("bk = " + r.getZk());
                }
            }
            if (r.getL() > eps) {
                stringBuilder.append(r.getL() + ">=" + eps).append('\n').append("k = k + 1").append('\n');
            }
            stringBuilder.append('\n');
        }

        stringBuilder
                .append(dividingResult.get(dividingResult.size()-1).getL() + "<" + eps)
                .append("result = " + dividingResult.get(dividingResult.size()-1).getXck()).append('\n')
                .append("result(fraction) = " + parseFunctions.getFraction(dividingResult.get(dividingResult.size()-1).getXck(), 0.01)).append('\n');
        view.showResult(stringBuilder.toString());
    }
}
