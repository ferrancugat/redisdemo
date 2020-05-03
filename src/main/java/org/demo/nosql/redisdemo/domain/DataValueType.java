package org.demo.nosql.redisdemo.domain;

public enum DataValueType {

    STRING("string"),       //
    LIST("list"),           // Redis Lists are implemented with linked lists because for a database system it is crucial to be able to add elements to a very long list in a very fast way.
    SORTEDSET("sortedset"), //
    HASH("hash");           //

    private String text;

    private DataValueType(String text) {
        this.text = text;
    }

    public String text() {
        return text;
    }

}
