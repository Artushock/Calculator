package com.artushock.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import static com.artushock.calculator.Constants.CURRENT_THEME;
import static com.artushock.calculator.Constants.Calculator_Theme_Light;
import static com.artushock.calculator.Constants.Calculator_Theme_Night;

public class SettingsActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        int currentThemeID = getIntent().getExtras().getInt(CURRENT_THEME);
        setCurrentRadio(currentThemeID);

        findViewById(R.id.save_settings_btn).setOnClickListener(v -> {
            RadioGroup radioGroup =  findViewById(R.id.theme_settings_radio_group);
            int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
            Intent resultIntent = new Intent();
            resultIntent.putExtra(CURRENT_THEME, getCurrentCheckedID(checkedRadioButtonId));
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }

    private int getCurrentCheckedID(int checkedRadioButtonId) {
        if (checkedRadioButtonId == R.id.dark_theme_radio_button) return Calculator_Theme_Night;
        return Calculator_Theme_Light;
    }

    private void setCurrentRadio(int currentThemeID) {
         switch (currentThemeID){
             case Calculator_Theme_Light:
                 RadioButton lightRadioButton = findViewById(R.id.light_theme_radio_button);
                 lightRadioButton.setChecked(true);
                 break;
             case Calculator_Theme_Night:
                 RadioButton darkRadioButton = findViewById(R.id.dark_theme_radio_button);
                 darkRadioButton.setChecked(true);
                 break;
         }
    }


}