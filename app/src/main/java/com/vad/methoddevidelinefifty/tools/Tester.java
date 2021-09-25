package com.vad.methoddevidelinefifty.tools;

import com.vad.methoddevidelinefifty.methods.methoddevidebylinefifty.DividingLineHalf;
import com.vad.methoddevidelinefifty.methods.methoddevidebylinefifty.DividingResult;
import com.vad.methoddevidelinefifty.tools.parsemathexpression.ParseFunctions;

import java.util.HashMap;

public class Tester {
    public static void main(String[] args) {
        String expression = "pow(x,2) - 2*x -1";
        DividingLineHalf dividingLineHalf = new DividingLineHalf();
        ParseFunctions parseFunctions = new ParseFunctions();

        //System.out.println(dividingLineHalf.divideOfLineHalf(-1, 2, 0.5f, expression));

        for (DividingResult r : dividingLineHalf.divideOfLineHalf(-1, 2, 0.5f, expression)) {
            System.out.println(
                    "k: " + r.getK() + "; a: " + r.getAk()
                    + "; b: " + r.getBk()
                    + "; xck: " + r.getXck()
                    + "; yk: " + r.getYk()
                    + "; zk: " + parseFunctions.getFraction(r.getZk(), 0.01)
                    + "; l: " + r.getL()
                    + "; f(xck): " + r.getfFromXck()
                    + "; f(yk): " + r.getfFromYk()
                    + "; f(zk): " + r.getfFromZk());
        }

//        expression = expression.replace("x", newChar+"");
        //expression = expression.replace("x", "0.875");
        //System.out.println(parseFunctions.getFraction(parseFunctions.parseExpression(expression), 0.01));


//        Tester tester = new Tester();
//        try {
//            System.out.println(getFraction(tester.parse(expression), 0.01));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    private HashMap<String, Double> variables;

    public void setVariable(String variableName, Double variableValue) {
        variables.put(variableName, variableValue);
    }

    public Double getVariable(String variableName) {
        if (!variables.containsKey(variableName)) {
            System.err.println( "Error: Try get unexists variable '"+variableName+"'" );
            return 0.0;
        }
        return variables.get(variableName);
    }

    public double parse(String s) throws Exception {
        Result result = plusMinus(s);
        if (!result.rest.isEmpty()) {
            System.err.println("Error: can't full parse");
            System.err.println("rest: " + result.rest);
        }
        return result.acc;
    }

    private Result plusMinus(String s) throws Exception {
        Result current = mulDiv(s);
        double acc = current.acc;

        while (current.rest.length() > 0) {
            if (!(current.rest.charAt(0) == '+' || current.rest.charAt(0) == '-')) break;

            char sign = current.rest.charAt(0);
            String next = current.rest.substring(1);

            current = mulDiv(next);
            if (sign == '+') {
                acc += current.acc;
            } else {
                acc -= current.acc;
            }
        }
        return new Result(acc, current.rest);
    }

    private Result bracket(String s) throws Exception {
        char zeroChar = s.charAt(0);
        if (zeroChar == '(') {
            Result r = plusMinus(s.substring(1));
            if (!r.rest.isEmpty() && r.rest.charAt(0) == ')') {
                r.rest = r.rest.substring(1);
            } else {
                System.out.println(r.rest);
                System.err.println("Error: not close bracket");
            }
            return r;
        }
        return functionVariable(s);
    }

    private Result functionVariable(String s) throws Exception {
        String f = "";
        int i = 0;
        // ищем название функции или переменной
        // имя обязательно должна начинаться с буквы
        while (i < s.length() && (Character.isLetter(s.charAt(i)) || (Character.isDigit(s.charAt(i)) && i > 0 ))) {
            f += s.charAt(i);
            i++;
        }
        if (!f.isEmpty()) { // если что-нибудь нашли
            if (s.length() > i && s.charAt( i ) == '(') { // и следующий символ скобка значит - это функция
                Result r = bracket(s.substring(f.length()));
                return processFunction(f, r);
            } else { // иначе - это переменная
                return new Result(getVariable(f), s.substring(f.length()));
            }
        }
        return num(s);
    }

    private Result mulDiv(String s) throws Exception {
        Result current = bracket(s);

        double acc = current.acc;
        while (true) {
            if (current.rest.length() == 0) {
                return current;
            }
            char sign = current.rest.charAt(0);
            if ((sign != '*' && sign != '/')) return current;

            String next = current.rest.substring(1);
            Result right = bracket(next);

            if (sign == '*') {
                acc *= right.acc;
            } else {
                acc /= right.acc;
            }

            current = new Result(acc, right.rest);
        }
    }

    private Result num(String s) throws Exception {
        int i = 0;
        int dot_cnt = 0;
        boolean negative = false;
        // число также может начинаться с минуса
        if(s.charAt(0) == '-'){
            negative = true;
            s = s.substring(1);
        }
        // разрешаем только цифры и точку
        while (i < s.length() && (Character.isDigit(s.charAt(i)) || s.charAt(i) == '.')) {
            // но также проверям, что в числе может быть только одна точка!
            if (s.charAt(i) == '.' && ++dot_cnt > 1) {
                throw new Exception("not valid number '" + s.substring(0, i + 1) + "'");
            }
            i++;
        }
        if(i == 0){ // что-либо похожее на число мы не нашли
            throw new Exception( "can't get valid number in '" + s + "'" );
        }

        double dPart = Double.parseDouble(s.substring(0, i));
        if( negative ) dPart = -dPart;
        String restPart = s.substring(i);

        return new Result(dPart, restPart);
    }

    // Тут определяем все нашие функции, которыми мы можем пользоватся в формулах
    private Result processFunction(String func, Result r) {
        if (func.equals("sin")) {
            return new Result(Math.sin(Math.toRadians(r.acc)), r.rest);
        } else if (func.equals("cos")) {
            return new Result(Math.cos(Math.toRadians(r.acc)), r.rest);
        } else if (func.equals("tan")) {
            return new Result(Math.tan(Math.toRadians(r.acc)), r.rest);
        } else if (func.equals("pow")) {
            int pow = 0;
            for(int i = 0; i < r.rest.length(); i++){
                if(r.rest.charAt(i)==','){
                    pow = Character.getNumericValue(r.rest.charAt(i+1));
                    r.rest=r.rest.substring(3);
                }
            }
            return new Result(Math.pow(r.acc, pow), r.rest);
        } else {
            System.err.println("function '" + func + "' is not defined");
        }
        return r;
    }

    public static String getFraction(double val, double ratio) {
        for (int i = 1;; i++) {
            double tem = val / (1D / i);
            if (Math.abs(tem - Math.round(tem)) < ratio)
                return Math.round(tem) + "/" + i;
        }
    }
}
