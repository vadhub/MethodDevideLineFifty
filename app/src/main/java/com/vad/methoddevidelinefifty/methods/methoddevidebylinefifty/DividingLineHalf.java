package com.vad.methoddevidelinefifty.methods.methoddevidebylinefifty;

import com.vad.methoddevidelinefifty.tools.parsemathexpression.ParseFunctions;

public class DividingLineHalf {

    private final ParseFunctions parseFunctions = new ParseFunctions();


//    //step 3
//    private float xck(){
//        return (float) (a+b)/2;
//    }
//
//    private float l2K(){
//        return b-a;
//    }
//
//    private float functionFromXkc(){
//        String expres = expression.replace("x", xck()+"");
//        return (float) parseFunctions.recurse(expres);
//    }
//
//    //step 4
//    private float yk(){
//        return a + (l2K()/4);
//    }
//
//    private float zk(){
//        return b - (l2K()/4);
//    }
//
//    private float functionFromYk(){
//        String expres = expression.replace("x", yk()+"");
//        return (float) parseFunctions.recurse(expres);
//    }
//
//    private float functionFromZk(){
//        String expres = expression.replace("x", zk()+"");
//        return (float) parseFunctions.recurse(expres);
//    }
//
//    //step 5
//    public void compareAndNext(){
//        if(functionFromYk()<functionFromXkc()){
//            b = xck();
//            x = yk();
//            System.out.println(compareAndFinish());
//        }else{
//            compareAndSwap();
//        }
//    }
//
//    //step 6
//    private void compareAndSwap(){
//        if(functionFromZk()<functionFromXkc()){
//            a = xck();
//            x=zk();
//        }else{
//            a = yk();
//            b = zk();
//        }
//    }
//
//    //step 7
//    private float compareAndFinish(){
//        if(l2K()<eps){
//            return xck();
//        }else{
//            k++;
//            compareAndNext();
//            System.out.println(k);
//        }
//
//        return 0;
//    }

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
        System.out.println(fFromXck+" "+xck);

        float yk = 0;
        float zk = 0;
        float fFromZk = 0;
        float fFromYk = 0;

        while(true){
            //step4
            yk = ak + l/4;
            zk = bk - l/4;

            expres = expression.replace("x", yk+"");
            fFromYk = (float) parseFunctions.recurse(expres);

            expres = expression.replace("x", zk+"");
            fFromZk = (float) parseFunctions.recurse(expres);

            System.out.println(
                    k+" "+
                    "ak "+parseFunctions.getFraction(ak, 0.01)+" "+
                    "bk "+parseFunctions.getFraction(bk, 0.01)+" "+
                    "f(yk) "+parseFunctions.getFraction(fFromYk, 0.01)+" "+
                    "xck "+parseFunctions.getFraction(xck,0.01)+" "+
                    "f(xck) "+parseFunctions.getFraction(fFromXck, 0.01)+" "+
                    "yk  "+parseFunctions.getFraction(yk, 0.01)+" "+
                    "zk "+parseFunctions.getFraction(zk,0.01)+" "+
                    "f(zk) "+parseFunctions.getFraction(fFromZk, 0.01)+" "+
                    "l "+parseFunctions.getFraction(l, 0.01)
                    );
            //step5
            if(fFromYk<fFromXck){
                bk = xck;
                xck = yk;
            } else {
                //step 6
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
                System.out.println(k +" "+ xck);
                return xck;
            }else{
                k++;
            }

//            System.out.println(
//                    k+" "+
//                            "ak "+parseFunctions.getFraction(ak, 0.01)+" "+
//                            "bk "+parseFunctions.getFraction(bk, 0.01)+" "+
//                            "f(yk) "+parseFunctions.getFraction(fFromYk, 0.01)+" "+
//                            "xck "+parseFunctions.getFraction(xck,0.01)+" "+
//                            "f(xck) "+parseFunctions.getFraction(fFromXck, 0.01)+" "+
//                            "yk  "+parseFunctions.getFraction(yk, 0.01)+" "+
//                            "zk "+parseFunctions.getFraction(zk,0.01)+" "+
//                            "f(zk) "+parseFunctions.getFraction(fFromZk, 0.01)+" "+
//                            "l "+parseFunctions.getFraction(l, 0.01)
//            );
        }

    }



}
