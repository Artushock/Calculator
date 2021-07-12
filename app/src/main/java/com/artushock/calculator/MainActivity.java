package com.artushock.calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String CALCULATOR_KEY = "CALCULATOR_KEY";
    private static final String CALCULATOR_SCREEN_KEY = "CALCULATOR_SCREEN_KEY";

    private Button zeroButton;
    private Button oneButton;
    private Button twoButton;
    private Button threeButton;
    private Button fourButton;
    private Button fiveButton;
    private Button sixButton;
    private Button sevenButton;
    private Button eightButton;
    private Button nineButton;

    private Button plusButton;
    private Button minusButton;
    private Button divideButton;
    private Button multiplyButton;
    private Button percentButton;
    private Button equallyButton;

    private Button clearAllButton;
    private Button backSpaceButton;
    private Button commaButton;

    Calculator calculator;
    CalculatorScreen calculatorScreen;
    TextView textScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator_constraint_layout);

        buttonsInit();
        setOnButtonsClickListeners();

        screenInit();

        calculatorScreen = new CalculatorScreen();
        calculator = new Calculator(calculatorScreen);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(CALCULATOR_KEY, calculator);
        outState.putParcelable(CALCULATOR_SCREEN_KEY, calculatorScreen);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        calculator = savedInstanceState.getParcelable(CALCULATOR_KEY);
        calculatorScreen = savedInstanceState.getParcelable(CALCULATOR_SCREEN_KEY);
        updateScreen();
    }

    private void screenInit() {
        textScreen = findViewById(R.id.text_screen);
    }

    private void buttonsInit() {
        zeroButton = findViewById(R.id.zero_button);
        oneButton = findViewById(R.id.one_button);
        twoButton = findViewById(R.id.two_button);
        threeButton = findViewById(R.id.three_button);
        fourButton = findViewById(R.id.four_button);
        fiveButton = findViewById(R.id.five_button);
        sixButton = findViewById(R.id.six_button);
        sevenButton = findViewById(R.id.seven_button);
        eightButton = findViewById(R.id.eight_button);
        nineButton = findViewById(R.id.nine_button);

        plusButton = findViewById(R.id.plus_button);
        minusButton = findViewById(R.id.minus_button);
        divideButton = findViewById(R.id.divide_button);
        multiplyButton = findViewById(R.id.multiply_button);
        percentButton = findViewById(R.id.percent_button);
        equallyButton = findViewById(R.id.equally_button);

        clearAllButton = findViewById(R.id.clear_all_button);
        backSpaceButton = findViewById(R.id.backspace_button);
        commaButton = findViewById(R.id.comma_button);
    }

    private void setOnButtonsClickListeners() {
        zeroButton.setOnClickListener(v -> {
            calculator.putNumber(Numbers.ZERO);
            updateScreen();
        });
        oneButton.setOnClickListener(v -> {
            calculator.putNumber(Numbers.ONE);
            updateScreen();
        });
        twoButton.setOnClickListener(v -> {
            calculator.putNumber(Numbers.TWO);
            updateScreen();
        });
        threeButton.setOnClickListener(v -> {
            calculator.putNumber(Numbers.THREE);
            updateScreen();
        });
        fourButton.setOnClickListener(v -> {
            calculator.putNumber(Numbers.FOUR);
            updateScreen();
        });
        fiveButton.setOnClickListener(v -> {
            calculator.putNumber(Numbers.FIVE);
            updateScreen();
        });
        sixButton.setOnClickListener(v -> {
            calculator.putNumber(Numbers.SIX);
            updateScreen();
        });
        sevenButton.setOnClickListener(v -> {
            calculator.putNumber(Numbers.SEVEN);
            updateScreen();
        });
        eightButton.setOnClickListener(v -> {
            calculator.putNumber(Numbers.EIGHT);
            updateScreen();
        });
        nineButton.setOnClickListener(v -> {
            calculator.putNumber(Numbers.NINE);
            updateScreen();
        });

        plusButton.setOnClickListener(v -> {
            calculator.putSign(Signs.PLUS);
            updateScreen();
        });
        minusButton.setOnClickListener(v -> {
            calculator.putSign(Signs.MINUS);
            onResume();
            updateScreen();
        });
        divideButton.setOnClickListener(v -> {
            calculator.putSign(Signs.DIVIDE);
            updateScreen();
        });
        multiplyButton.setOnClickListener(v -> {
            calculator.putSign(Signs.MULTIPLY);
            updateScreen();
        });
        percentButton.setOnClickListener(v -> {
            calculator.calculate(Signs.PERCENT);
            updateScreen();
        });

        equallyButton.setOnClickListener(v -> {
            calculator.calculate(Signs.EQUALLY);
            updateScreen();
        });

        clearAllButton.setOnClickListener(v -> {
            calculator.clearAll();
            updateScreen();
        });
        backSpaceButton.setOnClickListener(v -> {
            calculator.backSpace();
            updateScreen();
        });
        commaButton.setOnClickListener(v -> {
            calculator.putComma(Signs.COMMA);
            updateScreen();
        });
    }

    public void updateScreen() {
        textScreen.setText(calculatorScreen.getScreenText());
    }
}