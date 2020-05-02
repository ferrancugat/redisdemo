package org.demo.nosql.redisdemo.domain;

public enum DataValueType {

    STRING("string"), LIST("list"), SORTEDSET("sortedset"), HASH("hash");

    private String text;

    private DataValueType(String text) {
        this.text = text;
    }

    public String text() {
        return text;
    }

}
