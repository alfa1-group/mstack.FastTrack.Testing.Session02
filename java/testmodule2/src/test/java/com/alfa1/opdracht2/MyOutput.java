package com.alfa1.opdracht2;

import java.util.Objects;

public class MyOutput {
    private String value;

    private MyOutput(Builder builder) {
        this.value = builder.value;
    }

    public String getValue() {
        return value;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String value;

        public Builder value(String value) {
            this.value = value;
            return this;
        }

        public MyOutput build() {
            return new MyOutput(this);
        }
    }

    @Override
    public boolean equals(Object o) { // Useful for assertions
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyOutput myOutput = (MyOutput) o;
        return Objects.equals(value, myOutput.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "MyOutput{value='" + value + "'}";
    }
}
