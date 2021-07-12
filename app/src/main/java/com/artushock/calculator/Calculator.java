package com.artushock.calculator;

import android.os.Parcel;
import android.os.Parcelable;

public class Calculator implements Parcelable {

    private final CalculatorScreen calculatorScreen;
    private Signs sign;

    private String firstStringArgument;
    private String secondStringArgument;

    private double firstArgument;
    private double secondArgument;

    private boolean isResultOnScreen;

    protected Calculator(CalculatorScreen calculatorScreen) {
        this.calculatorScreen = calculatorScreen;
        this.firstStringArgument = "";
        this.secondStringArgument = "";

        this.firstArgument = 0;
        this.secondArgument = 0;

        this.isResultOnScreen = false;

        this.sign = null;
    }

    protected Calculator(Parcel in) {
        calculatorScreen = in.readParcelable(CalculatorScreen.class.getClassLoader());
        firstStringArgument = in.readString();
        secondStringArgument = in.readString();
        firstArgument = in.readDouble();
        secondArgument = in.readDouble();
        isResultOnScreen = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(calculatorScreen, flags);
        dest.writeString(firstStringArgument);
        dest.writeString(secondStringArgument);
        dest.writeDouble(firstArgument);
        dest.writeDouble(secondArgument);
        dest.writeByte((byte) (isResultOnScreen ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Calculator> CREATOR = new Creator<Calculator>() {
        @Override
        public Calculator createFromParcel(Parcel in) {
            return new Calculator(in);
        }

        @Override
        public Calculator[] newArray(int size) {
            return new Calculator[size];
        }
    };


    public void putNumber(Numbers number) {
        if (isResultOnScreen) {
            clearAll();
            isResultOnScreen = false;
        }

        String s = number.getNumberString();
        if (isSignWaited()) {
            firstStringArgument += s;
        } else {
            secondStringArgument += s;
        }
        calculatorScreen.addToScreen(s);
    }

    public void putSign(Signs sign) {
        if (wasNotCalculatorUsed()) return;
        if (isResultOnScreen) isResultOnScreen = false;
        if (isSignWaited()) {
            this.sign = sign;
            calculatorScreen.addToScreen(sign.getSignString());
        } else if (secondStringArgument.equals("")) {
            this.sign = sign;
            calculatorScreen.backSpace();
            calculatorScreen.addToScreen(sign.getSignString());
        } else {
            calculate(Signs.EQUALLY);
            isResultOnScreen = false;
            calculatorScreen.addToScreen(sign.getSignString());
            this.sign = sign;
        }
    }

    public void putComma(Signs sign) {
        if (wasNotCalculatorUsed()) {
            firstStringArgument = Numbers.ZERO.getNumberString() + Signs.COMMA.getSignString();
            calculatorScreen.addToScreen(firstStringArgument);
        }
        String commaStr = sign.getSignString();

        if (isSignWaited()) {
            if (firstStringArgument.contains(commaStr)) return;
            firstStringArgument += commaStr;
        } else {
            if (secondStringArgument.contains(commaStr) || secondStringArgument.equals("")) return;
            secondStringArgument += commaStr;
        }
        calculatorScreen.addToScreen(commaStr);
    }


    public void calculate(Signs sign) {
        if (isNotCalculatorReady()) return;
        switch (sign) {
            case EQUALLY:
                firstArgument = getArithmeticResult();
                break;
            case PERCENT:
                firstArgument = getPercentResult();
        }
        secondArgument = 0;
        String s = getDisplayString(String.valueOf(firstArgument));
        firstStringArgument = s;
        secondStringArgument = "";

        calculatorScreen.setScreenText(s);
        isResultOnScreen = true;
        this.sign = null;
    }


    private double getArithmeticResult() {
        parseArguments();
        switch (sign) {
            case PLUS:
                return getPlusResult();
            case MINUS:
                return getMinusResult();
            case DIVIDE:
                return getDivideResult();
            case MULTIPLY:
                return getMultiplyResult();
            default:
                return 0;
        }
    }

    private double getPlusResult() {
        return firstArgument += secondArgument;
    }

    private double getMinusResult() {
        return firstArgument -= secondArgument;
    }

    private double getMultiplyResult() {
        return firstArgument *= secondArgument;
    }

    private double getDivideResult() {
        return firstArgument /= secondArgument;
    }


    private double getPercentResult() {
        parseArguments();
        switch (sign) {
            case PLUS:
                return getPlusPercent();
            case MINUS:
                return getMinusPercent();
            case MULTIPLY:
                return getMultiplyPercent();
            case DIVIDE:
                return getDividePercent();
            default:
                return 0;
        }
    }

    private double getPlusPercent() {
        return firstArgument + (firstArgument / 100 * secondArgument);
    }

    private double getMinusPercent() {
        return firstArgument - (firstArgument / 100 * secondArgument);
    }

    private double getMultiplyPercent() {
        return firstArgument * (secondArgument / 100);
    }

    private double getDividePercent() {
        return firstArgument / (secondArgument / 100);
    }


    public void clearAll() {
        calculatorScreen.clearScreen();
        this.firstStringArgument = "";
        this.secondStringArgument = "";

        this.firstArgument = 0;
        this.secondArgument = 0;

        this.sign = null;
    }

    public void backSpace() {
        if (wasNotCalculatorUsed()) return;
        if (isResultOnScreen) {
            clearAll();
            return;
        }
        if (isSignWaited()) {
            firstStringArgument = firstStringArgument.substring(0, firstStringArgument.length() - 1);
            calculatorScreen.backSpace();
        } else if (secondStringArgument.equals("")) {
            calculatorScreen.backSpace();
            this.sign = null;
        } else {
            secondStringArgument = secondStringArgument.substring(0, secondStringArgument.length() - 1);
            calculatorScreen.backSpace();
        }
    }

    private void parseArguments() {
        firstArgument = Double.parseDouble(firstStringArgument);
        secondArgument = Double.parseDouble(secondStringArgument);
    }

    private String getDisplayString(String s) {
        if (s.contains(Signs.COMMA.getSignString())) {
            if (s.endsWith(".0")) {
                return s.substring(0, s.length() - 2);
            } else {
                if (s.substring(s.indexOf(".")).length() > 3) {
                    return s.substring(0, s.lastIndexOf(Signs.COMMA.getSignString()) + 3);
                } else {
                    return s;
                }
            }
        }
        return s;
    }


    private boolean wasNotCalculatorUsed() {
        return firstStringArgument.equals("");
    }

    private boolean isNotCalculatorReady() {
        return firstStringArgument.equals("") || secondStringArgument.equals("") || sign == null;
    }

    private boolean isSignWaited() {
        return sign == null;
    }
}
