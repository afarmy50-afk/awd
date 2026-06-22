package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

/**
 * صفحه اصلی ماشین‌حساب اندروید.
 * منطق این کلاس دقیقاً مشابه نسخه دسکتاپ (Calculator.java) است،
 * فقط رابط کاربری به‌جای Swing از ویجت‌های اندروید استفاده می‌کند.
 */
public class MainActivity extends AppCompatActivity {

    private TextView display;
    private double firstNumber = 0;
    private String operator = "";
    private boolean startNewNumber = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.display);

        // اتصال دکمه‌های عدد (0 تا 9)
        int[] numberButtonIds = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        };
        for (int id : numberButtonIds) {
            Button button = findViewById(id);
            button.setOnClickListener(v -> appendDigit(((Button) v).getText().toString()));
        }

        // دکمه نقطه اعشار
        findViewById(R.id.btnDot).setOnClickListener(v -> appendDecimalPoint());

        // دکمه‌های عملگر
        findViewById(R.id.btnAdd).setOnClickListener(v -> chooseOperator("+"));
        findViewById(R.id.btnSubtract).setOnClickListener(v -> chooseOperator("-"));
        findViewById(R.id.btnMultiply).setOnClickListener(v -> chooseOperator("*"));
        findViewById(R.id.btnDivide).setOnClickListener(v -> chooseOperator("/"));

        // مساوی، پاک کردن، بک‌اسپیس، درصد
        findViewById(R.id.btnEquals).setOnClickListener(v -> calculateResult());
        findViewById(R.id.btnClear).setOnClickListener(v -> clearAll());
        findViewById(R.id.btnBackspace).setOnClickListener(v -> backspace());
        findViewById(R.id.btnPercent).setOnClickListener(v -> applyPercent());
    }

    private void appendDigit(String digit) {
        if (startNewNumber) {
            display.setText(digit);
            startNewNumber = false;
        } else if (display.getText().toString().equals("0")) {
            display.setText(digit);
        } else {
            display.setText(display.getText() + digit);
        }
    }

    private void appendDecimalPoint() {
        if (startNewNumber) {
            display.setText("0.");
            startNewNumber = false;
        } else if (!display.getText().toString().contains(".")) {
            display.setText(display.getText() + ".");
        }
    }

    private void chooseOperator(String op) {
        firstNumber = Double.parseDouble(display.getText().toString());
        operator = op;
        startNewNumber = true;
    }

    private void calculateResult() {
        if (operator.isEmpty()) return;

        double secondNumber = Double.parseDouble(display.getText().toString());
        double result = 0;

        switch (operator) {
            case "+": result = firstNumber + secondNumber; break;
            case "-": result = firstNumber - secondNumber; break;
            case "*": result = firstNumber * secondNumber; break;
            case "/":
                if (secondNumber == 0) {
                    display.setText("خطا");
                    startNewNumber = true;
                    operator = "";
                    return;
                }
                result = firstNumber / secondNumber;
                break;
        }

        if (result == (long) result) {
            display.setText(String.valueOf((long) result));
        } else {
            display.setText(String.valueOf(result));
        }

        operator = "";
        startNewNumber = true;
    }

    private void clearAll() {
        display.setText("0");
        firstNumber = 0;
        operator = "";
        startNewNumber = true;
    }

    private void backspace() {
        String text = display.getText().toString();
        if (text.length() > 1) {
            display.setText(text.substring(0, text.length() - 1));
        } else {
            display.setText("0");
            startNewNumber = true;
        }
    }

    private void applyPercent() {
        double value = Double.parseDouble(display.getText().toString());
        value = value / 100;
        display.setText(String.valueOf(value));
    }
}
