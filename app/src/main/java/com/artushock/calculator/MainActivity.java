package com.artushock.calculator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.artushock.calculator.Constants.CALCULATOR_KEY;
import static com.artushock.calculator.Constants.CALCULATOR_SCREEN_KEY;
import static com.artushock.calculator.Constants.CURRENT_THEME;
import static com.artushock.calculator.Constants.Calculator_Theme_Light;
import static com.artushock.calculator.Constants.REQUEST_SETTINGS_CODE;

public class MainActivity extends BaseActivity{
    private final int[] numberButtonsIDs = {
            R.id.zero_button,
            R.id.one_button,
            R.id.two_button,
            R.id.three_button,
            R.id.four_button,
            R.id.five_button,
            R.id.six_button,
            R.id.seven_button,
            R.id.eight_button,
            R.id.nine_button,
    };

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_settings:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                settingsIntent.putExtra(CURRENT_THEME, getCodeStyle(Calculator_Theme_Light));
                startActivityForResult(settingsIntent, REQUEST_SETTINGS_CODE);
                return true;
            case R.id.menu_exit:
                finishAndRemoveTask();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode != REQUEST_SETTINGS_CODE){
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }

        if(resultCode == RESULT_OK){
            setAppTheme(data.getIntExtra(CURRENT_THEME, Calculator_Theme_Light));
            recreate();
        }

    }

    private void screenInit() {
        textScreen = findViewById(R.id.text_screen);
    }

    private void buttonsInit() {
        setNumberButtonsListeners();

        plusButton = findViewById(R.id.plus_button);
        minusButton = findViewById(R.id.minus_button);
        divideButton = findViewById(R.id.divide_button);
        multiplyButton = findViewById(R.id.multiply_button);
        percentButton = findViewById(R.id.percent_button);
        equallyButton = findViewById(R.id.equally_button);

        clearAllButton = findViewById(R.id.clear_all_button);
        backSpaceButton = findViewById(R.id.backspace_button);
        commaButton = findViewById(R.id.comma_button);

        setOnButtonsClickListeners();
    }

    private void setNumberButtonsListeners() {
        for (int i = 0; i < numberButtonsIDs.length; i++) {
            findViewById(numberButtonsIDs[i]).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button button = (Button) v;
                    calculator.putNumber(button.getText().toString());
                    updateScreen();
                }
            });
        }
    }

    private void setOnButtonsClickListeners() {

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