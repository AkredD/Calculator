package com.example.user.calculator;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * Created by user on 21.09.2016.
 */

public class ParsExpression {
    String local;
    public ParsExpression(String expression){
        this.local = expression;
    }

    public String Parse(){
        String p = new String();
        ArrayList <String> tokens = new ArrayList<String>();
        Map<String, Integer> key = new HashMap <String, Integer>();
        key.put("+",1); key.put("-",1); key.put("*",2); key.put("/",2); key.put("^",3); key.put("(",1); key.put(")",1);
        Log.d("TestFirstExpression",this.local);
        tokens = split(this.local);
        ArrayList <String> res = new ArrayList<String>();
        Stack <String> oper = new Stack<String>();
        for (int i = 0; i < tokens.size(); ++i){
            String k = new String();
            k += tokens.get(i).charAt(0);
            if (tokens.get(i).charAt(tokens.get(i).length()-1) >= '0' && tokens.get(i).charAt(tokens.get(i).length()-1) <= '9'){
                res.add(tokens.get(i));
            }else{
                if (oper.size()!=0){
                    if (tokens.get(i).charAt(0) == '('){
                        oper.push(k);
                        continue;
                    }
                    if (tokens.get(i).charAt(0) == ')'){
                        while(oper.peek().charAt(0) != '('){
                            res.add(oper.pop());
                        }
                        oper.pop();
                        continue;
                    }
                    if (key.get(oper.peek()) >= (key.get(tokens.get(i)))){
                        if (oper.peek().charAt(0)!='(') {
                            res.add(oper.pop());
                            oper.push(k);
                        }else{
                            oper.push(k);
                        }
                    }else{
                        oper.push(k);
                    }
                }else{
                    oper.push(k);
                }
            }
        }
        int n = oper.size();
        for (int i = 0; i<n;++i){
            res.add(oper.pop());
        }
        for (int i=0; i < res.size();++i) {
            Log.d("CheckPostExpr", res.get(i));
        }
        p = express(res);
        return p;
    }

    String express(ArrayList <String> res){
        String Res = new String();
        Stack <Double> expr = new Stack<Double>();
        for (int i = 0;i < res.size();++i){
            if (res.get(i).charAt(res.get(i).length()-1)>='0' && res.get(i).charAt(res.get(i).length()-1)<='9' ){
                expr.push(Double.parseDouble(res.get(i)));
                Log.d("CheckParse",expr.peek().toString());
            }else{
                //Log.d("I:", );
                Double a = expr.pop();

                Double b = expr.pop();
                //Log.d("2",expr.peek().toString());
                //Log.d("3", res.get(i));
                Log.d("CheckParse",res.get(i));
                switch(res.get(i).charAt(0)){
                    case '+': expr.push(a+b); break;
                    case '-': expr.push(b-a); break;
                    case '*': expr.push(a*b); break;
                    case '/': expr.push(b/a); break;
                    case '^': expr.push(Math.pow(b,a));
                }
                Log.d("CheckExprParse",expr.peek().toString());
            }
        }
        Log.d("CheckParse",expr.peek().toString());
        return expr.pop().toString();
    }

    ArrayList <String> split(String expr){
        String loc = new String();
        ArrayList <String> tokens = new ArrayList<String>();
        for (int i = 0; i < expr.length();++i){
            if (expr.charAt(i) != '+' && expr.charAt(i) != '*' && expr.charAt(i) != '/'
                    && expr.charAt(i) != '^' && expr.charAt(i) != '(' && expr.charAt(i) != ')'){
                if (expr.charAt(i) == '-'){
                    if(i==0 || !(expr.charAt(i-1) >= '0' && expr.charAt(i-1) <= '9')){
                        loc += expr.charAt(i);
                    }else{
                        if (loc.length() > 0) {
                            tokens.add(loc);
                        }
                        Log.d("CheckLoc",loc);
                        String k = new String();
                        k += expr.charAt(i);
                        tokens.add(k);
                        Log.d("CheckLoc",k);
                        loc = new String();
                    }
                }else {
                    loc += expr.charAt(i);
                }

            }else{
                if (loc.length() > 0) {
                    tokens.add(loc);
                }
                Log.d("CheckLoc",loc);
                String k = new String();
                k += expr.charAt(i);
                tokens.add(k);
                Log.d("CheckLoc",k);
                loc = new String();
            }
        }
        if (loc.length() > 0) tokens.add(loc);
        Log.d("CheckLoc",loc);
        return tokens;
    }
}


