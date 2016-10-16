package com.example.user.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public TextView Result, ExpressionR;
    private String expression = new String();
    private boolean lastEqv = false;
    public boolean checkBracket = true;
    public int balanceBracket = 0;
    public boolean checkPoint = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        Result = (TextView) findViewById(R.id.Result);
        ExpressionR = (TextView) findViewById(R.id.ExpressionR);
    }

    public void onDigitClick(View v) {
        CheckExceptions check = new CheckExceptions(expression);
        int id = v.getId();
        Button curr = (Button) v;
        if (check.SizeCheck(balanceBracket)) {
            if (curr.getText().toString().charAt(0) == '.'){
                if (checkPoint) {
                    if (expression.length()>0) {
                        char k = expression.charAt(expression.length() - 1);
                        if (k < '0' || k > '9') {
                            if (k == ')') expression += '*';
                            expression += "0";
                        }
                    }else{
                        expression += '0';
                    }
                    expression += curr.getText().toString();
                    checkPoint = false;
                }
            }else {
                if (expression.length()>0 && expression.charAt(expression.length()-1) == ')'){
                    expression += '*';
                }
                expression += curr.getText().toString();
            }
        }else{
            error();
        }
        checkBracket = false;
        ExpressionR.setText(expression);
    }

    public void onOpClick(View v) {
        checkPoint = true;
        int id = v.getId();
        Button curr = (Button) v;
        if (expression.length()>0) {
            CheckExceptions check = new CheckExceptions(expression);
            if (check.SizeCheck(balanceBracket)) {
                expression = check.check(curr.getText().toString().charAt(0));
                if (expression.charAt(expression.length() - 1) == 'B') {
                    balanceBracket++;
                    expression = expression.substring(0, expression.length() - 1);
                }
                checkBracket = true;
            } else {
                error();
            }
            ExpressionR.setText(expression);
        }else{
            if (curr.getText().toString().charAt(0) == '-'){
                expression += "0-";
                ExpressionR.setText(expression);
            }
        }
    }


    public void onBracketClick(View v) {
        checkPoint = true;
        CheckExceptions check = new CheckExceptions(expression);
        if (check.SizeCheck(balanceBracket)) {
            if (checkBracket) {
                if (check.checkBrackets()) {
                    balanceBracket++;

                    if (expression.length()>0 && expression.charAt(expression.length()-1) == ')'){
                        expression += "*";
                    }
                    expression += "(";
                }
            } else {
                if (balanceBracket > 0) {
                    balanceBracket--;
                    expression += ")";
                    if (balanceBracket == 0) checkBracket = true;
                }
            }
        }else{
            error();
        }
        ExpressionR.setText(expression);
    }

    public void onFullClearClick(View v) {
        checkPoint = true;
        expression = new String();
        checkBracket = true;
        balanceBracket = 0;
        ExpressionR.setText(" ");
        Result.setText(" ");
    }

    public void onClearClick(View v) {
        switch (expression.length()) {
            case 1:
                onFullClearClick(v);
            case 0:
                return;
            default: {
                if (expression.charAt(expression.length() - 1) == '(') balanceBracket--;
                if (expression.charAt(expression.length() - 1) == ')') balanceBracket++;
                if (expression.charAt(expression.length() - 1) == '.') checkPoint = true;
                expression = expression.substring(0, expression.length() - 1);
                ExpressionR.setText(expression);
            }
        }
    }
    public void error(){
        Toast toast = Toast.makeText(getApplicationContext(),
                "TooManySymbols", Toast.LENGTH_SHORT);
        toast.show();
    }


    public void onEqvClick(View v) {
        if (expression.length()>0) {
            CheckExceptions check = new CheckExceptions(expression);
            expression = check.EqvCheck();
            balanceBracket = 0;
            ExpressionR.setText(expression);
            ParsExpression parser = new ParsExpression(expression);
            Result.setText(parser.Parse());
        }
    }
}
