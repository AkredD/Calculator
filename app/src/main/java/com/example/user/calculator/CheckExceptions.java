package com.example.user.calculator;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 15.10.2016.
 */

public class CheckExceptions extends AppCompatActivity {
    String local;
    Map<String, Integer> key = new HashMap <String, Integer>();

    public CheckExceptions(String expression){
        this.local = expression;
        this.key.put("+",1); this.key.put("-",1); this.key.put("*",2);
        this.key.put("/",2); this.key.put("^",3); this.key.put("(",1);
        this.key.put(".",1);
    }

    public boolean SizeCheck(int balance){
        if (balance + this.local.length() < 110) {
            return true;
        }else{
            return false;
        }
    }
    public boolean checkBrackets(){
        if (this.local.length()>0 && (this.local.charAt(this.local.length()-1) >= '0'
                            && this.local.charAt(this.local.length()-1) <= '9')) return false;
        return true;
    }

    public String EqvCheck(){
        int balance = 0;
        String expression = this.local;
        char T = expression.charAt(expression.length()-1);
        CheckExceptions check = new CheckExceptions(expression);
        if (T == '-' || T == '+' || T == '*' || T =='/' || T =='^' || T=='.') {
            expression = expression.substring(0, expression.length() - 1);
        }
        for (int i=0; i<expression.length();++i){
            if (expression.charAt(i) == '(') balance++;
            if (expression.charAt(i) == ')') balance--;
        }
        for (int i=0; i<balance;++i){
            expression += ')';
        }
        return expression;
    }

    public String check(char Op){
        String loc = new String();
        String locR = new String();
        String res = this.local;
        loc += Op;
        locR += res.charAt(res.length()-1);
        if (this.key.containsKey(locR)){
            if (Op == '-'){
                res += "(0-";
                res += 'B';
            }else{
                if (res.charAt(res.length()-1)=='(') {
                    res += '1';
                }else {
                    res = res.substring(0, res.length() - 1);
                }
                res += Op;
            }
        }else{
            res += Op;
        }
        this.local = res;
        return this.local;
    }
}
