package com.alfa1.opdracht2;

// Input for the SUT's TakeAction method
public class MyInput {
    private String name;

    // Private constructor for builder
    private MyInput(Builder builder) {
        this.name = builder.name;
    }

    public String getName() { return name; }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        public Builder name(String name) {
            this.name = name;
            return this;
        }
        public MyInput build() {
            return new MyInput(this);
        }
    }

    @Override
    public String toString() {
        return "MyInput{name='" + name + "'}";
    }
}