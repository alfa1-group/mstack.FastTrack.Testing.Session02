package com.alfa1.opdracht2;

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

import java.util.UUID;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@ExtendWith(MockitoExtension.class) // Integrates Mockito with JUnit 5
@DisplayName("SystemUnderTest Tests")
public class MyTests {

    @Mock
    private MyRepository repositoryMock;

    private SystemUnderTest sut;

    // ArgumentCaptor can be useful for capturing arguments passed to mocks
    @Captor
    private ArgumentCaptor<UUID> uuidArgumentCaptor;

    @BeforeEach
    void setUp() {
        sut = new SystemUnderTest(repositoryMock);
    }

    @Test
    @DisplayName("should successfully process action when repository provides valid data")
    void takeActionSuccessfullyWhenRepositoryReturnsValidData() {
        // Arrange
        MyInput validInput = MyInput.builder().name("validName").build();
        MyOutput expectedRepositoryOutput = MyOutput.builder().value("valid data from repo").build();

        // Mocking the Get method for any UUID
        when(repositoryMock.get(any(UUID.class))).thenReturn(expectedRepositoryOutput);

        // Act
        sut.takeAction(validInput);

        // Assert
        // Verify that the repository's get method was called once
        verify(repositoryMock, times(1)).get(any(UUID.class));

        // Verify internal state of SUT if it changes based on repository output
        assertEquals(expectedRepositoryOutput, sut.getLastProcessedOutput(),
                "SUT should store the output received from the repository.");
    }


    // Method source for parameterized test data
    static Stream<Arguments> stringInputScenariosForMockResponse() {
        return Stream.of(
                arguments("null value in response", null),
                arguments("empty value in response", ""),
                arguments("whitespace value in response", "   "),
                arguments("line break value in response", "\n"),
                arguments("meaningful value in response", "specific data")
        );
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @MethodSource("stringInputScenariosForMockResponse")
    @DisplayName("should handle various responses from repository")
    void takeActionWithVariousRepositoryResponses(String scenarioDescription, String valueForMockedResponse) {
        // Arrange
        MyOutput mockedRepositoryResponse = RepositoryResponseBuilder.validState()
                .withValue(valueForMockedResponse)
                .build();

        when(repositoryMock.get(any(UUID.class))).thenReturn(mockedRepositoryResponse);

        MyInput fixedActionInput = MyInput.builder().name("consistentSUTInput").build();

        // Act
        sut.takeAction(fixedActionInput);

        // Assert
        verify(repositoryMock).get(any(UUID.class)); // Verify interaction

        // Assert the SUT's state based on the mocked response
        assertEquals(mockedRepositoryResponse, sut.getLastProcessedOutput(),
                "SUT's last processed output should match the mock's response for scenario: " + scenarioDescription);

        if (valueForMockedResponse == null) {
            assertNull(sut.getLastProcessedOutput().getValue(), "Value within output should be null if mock returned it as null");
        }
    }


    @Test
    @DisplayName("should correctly propagate or handle exceptions from repository")
    void takeActionHandlesRepositoryException() {
        // Arrange
        MyInput actionInput = MyInput.builder().name("inputLeadingToException").build();
        RuntimeException repositoryException = new RuntimeException("Database connection failed");

        when(repositoryMock.get(any(UUID.class))).thenThrow(repositoryException);

        // Act & Assert
        RuntimeException thrownException = assertThrows(RuntimeException.class, () -> {
            sut.takeAction(actionInput);
        }, "A RuntimeException should be thrown when the repository fails.");

        // Assert that the thrown exception is the one we configured the mock with
        assertEquals(repositoryException.getMessage(), thrownException.getMessage(),
                "The exception message should match the repository's error.");


        // Verify the mock was indeed called
        verify(repositoryMock).get(any(UUID.class));

        // Optionally, assert that no data was processed if an exception occurred early
        assertNull(sut.getLastProcessedOutput(),
                "Last processed output should be null if an exception occurred during repository interaction.");
    }
}