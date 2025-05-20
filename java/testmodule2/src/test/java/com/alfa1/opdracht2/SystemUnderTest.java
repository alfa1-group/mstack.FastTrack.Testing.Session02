package com.alfa1.opdracht2;

import java.util.Objects;
import java.util.UUID;

class SystemUnderTest {
    private MyRepository repository;
    private MyOutput lastProcessedOutput; // Example internal state for assertion

    public SystemUnderTest(MyRepository repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    public void takeAction(MyInput input) {
        System.out.println("SUT: Received call to takeAction with input: " + input.getName());
        if (input == null || input.getName() == null || input.getName().trim().isEmpty()) {
            System.out.println("SUT: Input or input name is invalid, skipping repository call.");
            return;
        }

        try {
            // Example: SUT calls the repository.
            MyOutput result = repository.get(UUID.randomUUID()); // Using a random UUID for example
            this.lastProcessedOutput = result; // Store for potential state assertion
            System.out.println("SUT: Received from repository: " + (result != null ? result.getValue() : "null"));
            // Further processing based on 'result' could happen here.
        } catch (Exception e) {
            System.err.println("SUT: Caught exception from repository: " + e.getMessage());
            // Decide whether to re-throw, wrap, or handle.
            // For the exception test, we expect it to propagate or be asserted.
            throw e; // Re-throwing for the exception test to catch it.
        }
    }

    public MyOutput getLastProcessedOutput() { // Getter for state assertion
        return lastProcessedOutput;
    }
}

