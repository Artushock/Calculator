package com.artushock.calculator;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import static com.artushock.calculator.Constants.Calculator_Theme_Night;
import static com.artushock.calculator.Constants.THEME_SHARED_KEY;
import static com.artushock.calculator.Constants.THEME_SHARED_PREFERENCES;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(getAppTheme(R.style.Calculator_Theme_Light));
    }

    private int getAppTheme(int defaultTheme) {
        return codeStyleToStyleId(getCodeStyle(defaultTheme));
    }

    private int codeStyleToStyleId(int codeStyle) {
        if (codeStyle == Calculator_Theme_Night) {
            return R.style.Calculator_Theme_Dark;
        }
        return R.style.Calculator_Theme_Light;
    }

    protected int getCodeStyle(int defaultThemeId) {
        SharedPreferences sharedPreferences = getSharedPreferences(THEME_SHARED_PREFERENCES, MODE_PRIVATE);
        return sharedPreferences.getInt(THEME_SHARED_KEY, defaultThemeId);
    }

    protected void setAppTheme(int codeStyle) {
        SharedPreferences sharedPreferences = getSharedPreferences(THEME_SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(THEME_SHARED_KEY, codeStyle);
        editor.apply();
    }
}
