package com.example.calc;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button b1;
    private Button b2;
    private Button b3;
    private Button b4;
    private Button b5;
    private Button b6;
    private Button b7;
    private Button b8;
    private Button b9;
    private Button b0;
    private Button bPlus;
    private Button bPoint;
    private Button bMinus;
    private Button bDiv;
    private Button bMul;
    private Button bRes;
    private TextView textView;
    private TextView textViewRes;
    private Double operand1;
    private final DecimalFormat df = new DecimalFormat("#");
    private boolean removeTexts = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("popo", "onCreate: ");

        findViews();
        setOnClickListeners();

        df.setMaximumFractionDigits(8);
    }

    private void findViews() {
        b1 = findViewById(R.id.button_1);
        b2 = findViewById(R.id.button_2);
        b3 = findViewById(R.id.button_3);
        b4 = findViewById(R.id.button_4);
        b5 = findViewById(R.id.button_5);
        b6 = findViewById(R.id.button_6);
        b7 = findViewById(R.id.button_7);
        b8 = findViewById(R.id.button_8);
        b9 = findViewById(R.id.button_9);
        b0 = findViewById(R.id.button_0);
        bPlus = findViewById(R.id.button_plus);
        bPoint = findViewById(R.id.button_toc);
        bMinus = findViewById(R.id.button_sub);
        bDiv = findViewById(R.id.button_div);
        bMul = findViewById(R.id.button_mult);
        bRes = findViewById(R.id.button_rez);
        textView = findViewById(R.id.textview);
        textViewRes = findViewById(R.id.textview_res);
    }

    private void setOnClickListeners() {
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        b5.setOnClickListener(this);
        b6.setOnClickListener(this);
        b7.setOnClickListener(this);
        b8.setOnClickListener(this);
        b9.setOnClickListener(this);
        b0.setOnClickListener(this);
        bPoint.setOnClickListener(this);
        bPlus.setOnClickListener(this);
        bMinus.setOnClickListener(this);
        bDiv.setOnClickListener(this);
        bMul.setOnClickListener(this);
        bRes.setOnClickListener(this);
    }

    private void appendText(CharSequence s) {
        if (removeTexts) {
            textView.setText("");
            textViewRes.setText("");
            operand1 = null;
        }
        textView.append(s);
        removeTexts = false;
    }

    private Double evaluateAnswer(char sign, Double operand2) {
        double result = 0.0;
        switch (sign) {
            case '+':
                result = operand1 + operand2;
                break;
            case '-':
                result = operand1 - operand2;
                break;
            case '/':
                result = operand1 / operand2;
                break;
            case '*':
                result = operand1 * operand2;
                break;
        }
        return result;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_toc:
                CharSequence point = ((Button) v).getText();
                if (!textView.getText().toString().contains(point)) {
                    appendText(point);
                }
                break;
            case R.id.button_1:
            case R.id.button_2:
            case R.id.button_3:
            case R.id.button_4:
            case R.id.button_5:
            case R.id.button_6:
            case R.id.button_7:
            case R.id.button_8:
            case R.id.button_9:
            case R.id.button_0:
                appendText(((Button) v).getText());
                break;
            case R.id.button_plus:
            case R.id.button_mult:
            case R.id.button_div:
            case R.id.button_sub:
                char sign2 = ((Button) v).getText().charAt(0);
                if (operand1 == null) {
                    operand1 = Double.valueOf(textView.getText().toString()); //String -> Double

                } else {
                    CharSequence text = textView.getText();
                    if (text.toString().equals("")) {
                        break;
                    }
                    if (!removeTexts) {
                        CharSequence mem = textViewRes.getText();
                        sign2 = mem.charAt(mem.length() - 1);
                        operand1 = evaluateAnswer(sign2, Double.valueOf(text.toString()));
                    }
                    //textViewRes.setText(df.format(operand1) + sign);
                }
                textViewRes.setText(df.format(operand1) + sign2); //форматирование чисел
                textView.setText("");
                removeTexts = false;
                break;
            case R.id.button_rez:

                CharSequence mem = textViewRes.getText();
                if (mem.toString().equals("")) {
                    break;
                }
                char sign = mem.charAt(mem.length() - 1);
                if (sign != '+' && sign != '-' && sign != '/' && sign != '*') {
                    break;
                }
                CharSequence mem2 = textView.getText();
                if (mem2.toString().equals("")) {
                    break;
                }
                Double operand2 = Double.valueOf(mem2.toString());
                operand1 = evaluateAnswer(sign, operand2);
                textViewRes.append(df.format(operand2) + ((TextView) v).getText());
                textView.setText(df.format(operand1));
                removeTexts = true;
                break;
        }
    }


}
