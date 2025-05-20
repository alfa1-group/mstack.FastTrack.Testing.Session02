package com.alfa1.opdracht2;

public class RepositoryResponseBuilder { // Renamed from MyReturnInputBuilder
    private String valueForOutput;

    private RepositoryResponseBuilder() {}

    public static RepositoryResponseBuilder validState() {
        return new RepositoryResponseBuilder();
    }

    public RepositoryResponseBuilder withValue(String value) {
        this.valueForOutput = value;
        return this;
    }

    public MyOutput build() {
        return MyOutput.builder().value(this.valueForOutput).build();
    }
}

