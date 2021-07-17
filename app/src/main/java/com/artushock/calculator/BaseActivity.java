package com.artushock.calculator;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    private static final String THEME_SHARED_PREFERENCES = "THEME_SHARED_PREFERENCES";
    private static final String THEME_SHARED_KEY = "THEME_SHARED_KEY";

    protected static final int Calculator_Theme_Light = 0;
    protected static final int Calculator_Theme_Night = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(getAppTheme(R.style.Calculator_Theme_Light));
    }

    private int getAppTheme(int defaultTheme) {
        return codeStyleToStyleId(getCodeStyle(defaultTheme));
    }

    private int codeStyleToStyleId(int codeStyle) {
        switch (codeStyle) {
            case Calculator_Theme_Night:
                return R.style.Calculator_Theme_Dark;
            default:
                return R.style.Calculator_Theme_Light;
        }
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
