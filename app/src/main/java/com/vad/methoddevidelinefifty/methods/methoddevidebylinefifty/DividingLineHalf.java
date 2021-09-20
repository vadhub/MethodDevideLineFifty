package com.vad.methoddevidelinefifty.methods.methoddevidebylinefifty;

import com.vad.methoddevidelinefifty.tools.parsemathexpression.ParseFunctions;

public class DividingLineHalf {
    private int k;
    private float a;
    private float b;
    private float x;
    private float eps;
    private String expression;

    private ParseFunctions parseFunctions = new ParseFunctions();
    public DividingLineHalf(int k, float a, float b, float eps, String expression) {
        this.k = k;
        this.a = a;
        this.b = b;
        this.eps = eps;
        this.expression=expression;
    }

    //step 3
    private float xck(){
        return (float) (a+b)/2;
    }

    private float l2K(){
        return b-a;
    }

    private float functionFromXkc(){
        String expres = expression.replace("x", xck()+"");
        return (float) parseFunctions.recurse(expres);
    }

    //step 4
    private float yk(){
        return a + (l2K()/4);
    }

    private float zk(){
        return b - (l2K()/4);
    }

    private float functionFromYk(){
        String expres = expression.replace("x", yk()+"");
        return (float) parseFunctions.recurse(expres);
    }

    private float functionFromZk(){
        String expres = expression.replace("x", zk()+"");
        return (float) parseFunctions.recurse(expres);
    }

    //step 5
    public void compareAndNext(){
        if(functionFromYk()<functionFromXkc()){
            b = xck();
            x = yk();
            System.out.println(compareAndFinish());
        }else{
            compareAndSwap();
        }
    }

    //step 6
    private void compareAndSwap(){
        if(functionFromZk()<functionFromXkc()){
            a = xck();
            x=zk();
        }else{
            a = yk();
            b = zk();
        }
    }

    //step 7
    private float compareAndFinish(){
        if(l2K()<eps){
            return xck();
        }else{
            k++;
            compareAndNext();
            System.out.println(k);
        }

        return 0;
    }

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
        float fFromXck = (float) parseFunctions.recurse(expres);

        //step4
        float yk = ak + l/4;
        float zk = bk - l/4;
        float fFromZk = 0;

        expres = expression.replace("x", yk+"");
        float fFromYk = (float) parseFunctions.recurse(expres);

        //step5
        if(fFromYk<fFromXck){
            bk = xck;
            xck = yk;

            l = bk  - ak;

            if(l<eps){
                System.out.println(k);
                return xck;
            }else{
                k++;
                yk = ak + l/4;
                zk = bk - l/4;

                expres = expression.replace("x", yk+"");
                fFromYk = (float) parseFunctions.recurse(expres);
            }

        } else {
            expres = expression.replace("x", zk+"");
            fFromZk = (float) parseFunctions.recurse(expres);

            if(fFromZk<fFromXck){
                ak = xck;
                xck = zk;

                if(l<eps){
                    System.out.println(k);
                    return xck;
                }else{
                    k++;
                    yk = ak + l/4;
                    zk = bk - l/4;

                    expres = expression.replace("x", yk+"");
                    fFromYk = (float) parseFunctions.recurse(expres);
                }

            }else{
                ak = yk;
                bk = zk;
            }

        }

        return 0;
    }



}
