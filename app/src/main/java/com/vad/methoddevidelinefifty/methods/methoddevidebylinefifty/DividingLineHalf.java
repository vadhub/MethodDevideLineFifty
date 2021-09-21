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
        double fFromXck = parseFunctions.recurse(expres);
        System.out.println(fFromXck+" "+xck);

        double yk = 0;
        double zk = 0;
        double fFromZk = 0;
        double fFromYk = 0;

        while(true){
            //step4
            yk = ak + l/4;
            zk = bk - l/4;

            expres = expression.replace("x", yk+"");
            fFromYk = parseFunctions.recurse(expres);

            expres = expression.replace("x", zk+"");
            fFromZk = parseFunctions.recurse(expres);

            expres = expression.replace("x", xck+"");
            fFromXck = parseFunctions.recurse(expres);

            System.out.println(zk +" "+ fFromZk);
            System.out.println("start "+k +" xck: "+parseFunctions.getFraction(xck, 0.01) +"  y "+parseFunctions.getFraction(yk, 0.01));
            //step5
            if(fFromYk<fFromXck){
                bk = xck;
                xck = yk;
                System.out.println("step5 "+k +parseFunctions.getFraction(xck, 0.01) +"  y "+parseFunctions.getFraction(yk, 0.01));

            } else {
                //step 6
                expres = expression.replace("x", xck+"");
                fFromXck = parseFunctions.recurse(expres);

                System.out.println("step6 "+k +"  fxck "+fFromXck+"  y "+fFromZk);
                if(fFromZk<fFromXck){
                    ak = xck;
                    xck = zk;
                    System.out.println("step6 cond "+k+parseFunctions.getFraction(xck, 0.01) +"  y "+parseFunctions.getFraction(yk, 0.01));
                }else{
                    ak = yk;
                    bk = zk;
                }
            }

            //step 7
            l = bk  - ak;
            System.out.println(l +" "+ak+" "+bk);
            if(l<eps){
                System.out.println("endd "+k+parseFunctions.getFraction(xck, 0.01) +"  y "+parseFunctions.getFraction(yk, 0.01));
                return xck;
            }else{
                k++;
            }

            System.out.println("end "+k +" xck: "+parseFunctions.getFraction(xck, 0.01) +"  y "+parseFunctions.getFraction(yk, 0.01));

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
