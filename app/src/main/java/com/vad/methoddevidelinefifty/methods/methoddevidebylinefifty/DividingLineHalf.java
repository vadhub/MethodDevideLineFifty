package com.vad.methoddevidelinefifty.methods.methoddevidebylinefifty;

import com.vad.methoddevidelinefifty.tools.parsemathexpression.ParseFunctions;

public class DividingLineHalf {

    private final ParseFunctions parseFunctions = new ParseFunctions();

    public float divideOfLineHalf(float a, float b, float eps, String expression){

        //step 1
        float ak = a;
        float bk = b;

        //step 2
        int k = 0;

        //step 3
        float xck = (ak + bk)/2;
        float l = bk - ak;

        String expres = expression.replace("x", xck+"");
        float fFromXck = parseFunctions.parseExpression(expres);

        float yk = 0;
        float zk = 0;
        float fFromZk = 0;
        float fFromYk = 0;

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
