package com.artushock.calculator;

import android.os.Parcel;
import android.os.Parcelable;

public class CalculatorScreen implements Parcelable {

    private String screenText;

    protected CalculatorScreen() {
        this.screenText = "";
    }

    protected CalculatorScreen(Parcel in) {
        screenText = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(screenText);
    }

    public static final Creator<CalculatorScreen> CREATOR = new Creator<CalculatorScreen>() {
        @Override
        public CalculatorScreen createFromParcel(Parcel in) {
            return new CalculatorScreen(in);
        }

        @Override
        public CalculatorScreen[] newArray(int size) {
            return new CalculatorScreen[size];
        }
    };

    public void setScreenText(String screenText) {
        this.screenText = screenText;
    }

    public void addToScreen(String s) {
        screenText += s;
    }

    public String getScreenText() {
        return screenText;
    }

    public void backSpace() {
        if ((screenText != null) && (screenText.length() > 0)) {
            screenText = screenText.substring(0, screenText.length() - 1);
        }
    }

    public void clearScreen() {
        screenText = "";
    }
}

