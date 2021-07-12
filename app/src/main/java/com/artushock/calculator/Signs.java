package com.artushock.calculator;

public enum Signs {
    PLUS("+"),
    MINUS("-"),
    DIVIDE("/"),
    MULTIPLY("x"),
    PERCENT("%"),
    EQUALLY("="),
    COMMA(".");

    private final String signString;

    Signs(String signString) {
        this.signString = signString;
    }

    public String getSignString() {
        return signString;
    }
}
