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

        if (expression.equals("")) {
            view.showError("Enter expression");
        } else {
            view.showResult(viewResultCalculate(a, b, eps, expression));
        }
    }

    public String viewResultCalculate(float a, float b, float eps, String expression){
        // xck = (a + b)/2
        //
        //L = b - a
        //
        //yk = a + L/4
        //
        //zk = b - L/4
        List<DividingResult> dividingResult = dividingLineHalf.divideOfLineHalf(a, b ,eps, expression);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("step 1").append('\n');
        stringBuilder.append("a = ").append(a).append('\n')
                .append("b = ").append(b).append('\n')
                .append("eps = ").append(eps).append('\n').append('\n');
        stringBuilder.append("step 2").append('\n');
        stringBuilder.append("k = ").append(0).append('\n').append('\n');
        stringBuilder.append("step 3").append('\n');
        stringBuilder.append("Xck = ( ").append(a).append(" + ").append(b).append(" )").append("/2").append(" = ").append((a+b)/2).append('\n');
        stringBuilder.append("L = ").append(b).append(" - ").append(a).append(" = ").append(b - a).append('\n').append('\n');
        for (DividingResult r : dividingResult) {
            stringBuilder
                    .append("k = ").append(r.getK()).append('\n')
                    .append("Xck = ").append(r.getfFromYk()).append('\n')
                    .append("Yk = ").append(r.getYk()).append('\n')
                    .append("Zk = ").append(r.getZk()).append('\n')
                    .append("f(Xck) = ").append(expression.replace("x", r.getXck()+"")).append(" = ").append(r.getfFromXck()).append("").append('\n')
                    .append("f(Yk) = ").append(expression.replace("x", r.getYk()+"")).append(" = ").append(r.getfFromYk()).append('\n')
                    .append("f(Zk) = ").append(expression.replace("x", r.getZk()+"")).append(" = ").append(r.getfFromZk()).append('\n')
                    .append("L = ").append(r.getL()).append('\n').append('\n');
            if (r.getfFromYk()<r.getfFromXck()) {
                stringBuilder
                        .append(r.getfFromYk()).append(" < ").append(r.getfFromXck()).append('\n')
                        .append('\t').append("ak = ").append(r.getXck()).append('\n')
                        .append('\t').append("Xck = ").append(r.getYk()).append('\n');
            } else {
                if (r.getfFromZk()<r.getfFromXck()) {
                    stringBuilder
                            .append(r.getfFromYk()).append(" > ").append(r.getfFromXck()).append('\n')
                            .append('\t').append(r.getfFromZk()).append(" < ").append(r.getfFromXck()).append('\n')
                            .append('\t').append('\t').append("ak = ").append(r.getXck()).append('\n')
                            .append('\t').append('\t').append("Xck = ").append(r.getZk()).append('\n');
                } else {
                    stringBuilder
                            .append(r.getfFromYk()).append(" > ").append(r.getfFromXck()).append('\n')
                            .append('\t').append(r.getfFromZk()).append(" > ").append(r.getfFromXck()).append('\n')
                            .append('\t').append('\t').append("ak = ").append(r.getYk()).append('\n')
                            .append('\t').append('\t').append("bk = ").append(r.getZk());
                }
            }
            if (r.getL() > eps) {
                stringBuilder.append(r.getL()).append(">=").append(eps).append('\n')
                        .append('\t').append("k =").append(r.getK()).append(" + 1").append('\n');
            }
            stringBuilder.append('\n');
        }

        stringBuilder
                .append(dividingResult.get(dividingResult.size()-1).getL()).append("<").append(eps).append('\n')
                .append('\t').append("result = ").append(dividingResult.get(dividingResult.size()-1).getXck()).append('\n')
                .append('\t').append("result(fraction) = ").append(parseFunctions.getFraction(dividingResult.get(dividingResult.size()-1).getXck(), 0.01)).append('\n');

        return stringBuilder.toString();
    }
}
