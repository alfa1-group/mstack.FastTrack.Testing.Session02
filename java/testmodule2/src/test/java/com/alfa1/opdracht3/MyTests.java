package com.alfa1.opdracht3;

import com.alfa1.opdracht2.MyOutput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@ExtendWith(MockitoExtension.class)
@DisplayName("SystemUnderTest Advanced Scenarios")
public class MyTests {

    @Mock
    private MyRepository repositoryMock;

    private SystemUnderTest sut;

    @Captor
    private ArgumentCaptor<String> stringArgumentCaptor; // For capturing string arguments

    @BeforeEach
    void setUp() {
        sut = new SystemUnderTest(repositoryMock);
    }
    
    @Test
    @DisplayName("getStringForNumber should return 'bob' when input is 6")
    void getStringForNumber_givenInput6_returnsBob() {
        // Arrange
        String expectedValue = "bob";
        int inputNumber = 6;
        String assertionMessage = "The SUT should return 'bob' for the number 6, as per the defined sequence.";

        // Act
        String actualValue = sut.getStringForNumber(inputNumber);

        // Assert
        assertEquals(expectedValue, actualValue, assertionMessage);
    }

    // Data provider method for the parameterized test verifying mock interaction
    static Stream<Arguments> numericInputToRepositoryKeySource() {
        return Stream.of(
                arguments(1, "Jan", "Input 1 should map to key 'Jan'"),
                arguments(42, "DeepThought", "Input 42 should map to key 'DeepThought'")
        );
    }

    @ParameterizedTest(name = "[{index}] {2}") // Uses the 3rd argument from MethodSource as test name
    @MethodSource("numericInputToRepositoryKeySource")
    @DisplayName("processNumericInput: verifies correct key is passed to repository")
    void processNumericInput_whenCalled_passesCorrectlyDerivedKeyToRepository(
            int numericInput, String expectedKeyForRepository, String testCaseDescription) {
        // Arrange
        // Mock repository.get(String) to return a dummy MyOutput, as its return value isn't asserted here.
        MyOutput dummyOutput = MyOutput.builder().value("irrelevant mock response").build();
        when(repositoryMock.get(anyString())).thenReturn(dummyOutput);

        // Act
        sut.processNumericInput(numericInput);

        // Assert
        // Verify that repositoryMock.get was called once with the expected string argument.
        verify(repositoryMock).get(eq(expectedKeyForRepository));
    }

    // Data provider for the test that captures and asserts the argument value
    // Can reuse numericInputToRepositoryKeySource if arguments are identical,
    // or create a new one if more/different data is needed.
    // For this example, we can reuse it.

    @ParameterizedTest(name = "[{index}] {2}")
    @MethodSource("numericInputToRepositoryKeySource") // Reusing the same data source
    @DisplayName("processNumericInput: captures and verifies argument passed to repository")
    void processNumericInput_whenCalled_passesCorrectArgumentToRepository_captured(
            int numericInput, String expectedCapturedValue, String testCaseDescription) {

        // Arrange
        MyOutput dummyOutput = MyOutput.builder().value("any response").build();
        when(repositoryMock.get(anyString())).thenReturn(dummyOutput);

        // Act
        sut.processNumericInput(numericInput);

        // Assert
        verify(repositoryMock).get(stringArgumentCaptor.capture());
        String actualCapturedValue = stringArgumentCaptor.getValue();

        assertEquals(expectedCapturedValue, actualCapturedValue,
                "The argument passed to repository.get() should match the expected derived value.");
    }

    @Test
    @DisplayName("triggerErrorAction should throw IllegalStateException with the expected message")
    void triggerErrorAction_whenInvoked_throwsIllegalStateExceptionWithCorrectMessage() {
        // Arrange
        String expectedErrorMessage = "Expected error message";

        // Act & Assert
        IllegalStateException thrownException = assertThrows(IllegalStateException.class, () -> {
            sut.triggerErrorAction();
        }, "Expected triggerErrorAction to throw an IllegalStateException.");

        assertEquals(expectedErrorMessage, thrownException.getMessage(),
                "The exception message should match the defined error.");
    }
}
