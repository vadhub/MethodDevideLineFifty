package com.vad.methoddevidelinefifty.methods.methoddevidebylinefifty;

import com.vad.methoddevidelinefifty.tools.parsemathexpression.ParseFunctions;

public class DividingLineHalf {

    private final ParseFunctions parseFunctions = new ParseFunctions();

    public double divideOfLineHalf(double a, double b, double eps, String expression){
        //step 1
        double ak = a;
        double bk = b;

        //step 2
        int k = 0;

        //step 3
        double xck = (ak + bk)/2;
        double l = bk - ak;

        String expres = expression.replace("x", xck+"");
        double fFromXck = parseFunctions.parseExpression(expres);

        double yk = 0;
        double zk = 0;
        double fFromZk = 0;
        double fFromYk = 0;

        while(true){
            //step4
            yk = ak + l/4;
            zk = bk - l/4;

            expres = expression.replace("x", yk+"");
            fFromYk = parseFunctions.parseExpression(expres);

            expres = expression.replace("x", zk+"");
            fFromZk = parseFunctions.parseExpression(expres);

            expres = expression.replace("x", xck+"");
            fFromXck = parseFunctions.parseExpression(expres);

            //step5
            if(fFromYk<fFromXck){
                bk = xck;
                xck = yk;

            } else {
                //step 6
                expres = expression.replace("x", xck+"");
                fFromXck = parseFunctions.parseExpression(expres);
                if(fFromZk<fFromXck){
                    ak = xck;
                    xck = zk;
                }else{
                    ak = yk;
                    bk = zk;
                }
            }

            //step 7
            l = bk  - ak;
            if(l<eps){
                return xck;
            }else{
                k++;
            }
        }

    }



}
