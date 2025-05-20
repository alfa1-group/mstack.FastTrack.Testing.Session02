package com.alfa1.opdracht3;

import com.alfa1.opdracht2.MyInput;

import java.util.Objects;

class SystemUnderTest { /* ... as defined before ... */
    private MyRepository repository;

    public SystemUnderTest(MyRepository repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    public void takeAction(MyInput input) { /* ... */ }

    public String getStringForNumber(int number) {
        if (number == 6) return "bob";
        return "default_string_for_" + number;
    }

    public void processNumericInput(int input) {
        String derivedKey;
        if (input == 1) derivedKey = "Jan";
        else if (input == 42) derivedKey = "DeepThought";
        else derivedKey = "KeyFor_" + input;
        this.repository.get(derivedKey);
    }

    public void triggerErrorAction() {
        throw new IllegalStateException("Expected error message");
    }
}
