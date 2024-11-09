package com.example.midproject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView display;
    private String currentDisplay = "";
    private String operator = "";
    private double firstOperand = 0;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.display);
        display.setText("Enter Number");

        View.OnClickListener buttonClickListener = v -> {
            Button button = (Button) v;
            String buttonText = button.getText().toString();

            switch (buttonText) {
                case "C":
                    clear();
                    break;
                case "⌫":
                    backspace();
                    break;
                case "+":
                case "-":
                case "*":
                case "/":
                    setOperator(buttonText);
                    break;
                case "=":
                    calculateResult();
                    break;
                case ".":
                    appendDecimal();
                    break;
                default:
                    appendNumber(buttonText);
                    break;
            }
        };

        int[] buttonIds = {
                R.id.button_0, R.id.button_1, R.id.button_2, R.id.button_3, R.id.button_4,
                R.id.button_5, R.id.button_6, R.id.button_7, R.id.button_8, R.id.button_9,
                R.id.button_plus, R.id.button_minus, R.id.button_multiply, R.id.button_divide,
                R.id.button_clear, R.id.button_equals, R.id.button_backspace, R.id.button_dot
        };

        for (int id : buttonIds) {
            findViewById(id).setOnClickListener(buttonClickListener);
        }
    }

    private void appendNumber(String number) {
        currentDisplay += number;
        display.setText(currentDisplay);
    }

    private void appendDecimal() {
        if (!currentDisplay.contains(".")) {
            currentDisplay += ".";
            display.setText(currentDisplay);
        }
    }

    private void clear() {
        currentDisplay = "";
        operator = "";
        firstOperand = 0;
        display.setText("0");
    }

    private void backspace() {
        if (!currentDisplay.isEmpty()) {
            currentDisplay = currentDisplay.substring(0, currentDisplay.length() - 1);
            display.setText(currentDisplay.isEmpty() ? "0" : currentDisplay);
        }
    }

    private void setOperator(String op) {
        if (!currentDisplay.isEmpty()) {
            firstOperand = Double.parseDouble(currentDisplay);
            operator = op;
            currentDisplay = "";
        }
    }

    @SuppressLint("SetTextI18n")
    private void calculateResult() {
        if (!currentDisplay.isEmpty()) {
            double secondOperand = Double.parseDouble(currentDisplay);
            double result = 0;

            switch (operator) {
                case "+":
                    result = firstOperand + secondOperand;
                    break;
                case "-":
                    result = firstOperand - secondOperand;
                    break;
                case "*":
                    result = firstOperand * secondOperand;
                    break;
                case "/":
                    if (secondOperand != 0) {
                        result = firstOperand / secondOperand;
                    } else {
                        display.setText("Error");
                        return;
                    }
                    break;
            }

            display.setText(String.valueOf(result));
            currentDisplay = String.valueOf(result);
            operator = "";
        }
    }
}
