package com.example.fusmobilni.fragments.items.service;

public enum DurationType {
    FIXED("FIXED"),
    VARIABLE("VARIABLE");
    private final String value;

    // Constructor
    DurationType(String value) {
        this.value = value;
    }

    // Getter
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
