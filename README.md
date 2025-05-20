# Introductie
Het doel van deze opgave is dat jullie na het lezen van de beschrijving stapsgewijs
unittesten voor het nieuwe product op gaan zetten. Het doel is niet om het product te maken.
Op het einde mogen alle testen in de testexplorer falen. Maar ze moeten wel compileren. In
de solution is reeds een unittest project en een applicatie project aangemaakt. De beschrijving
van welke applicatie er getest moet worden is te vinden in "Requirements.md".


# Opdrachten
Tijdens deze opdrachten is het de bedoeling om de requirements te analyseren. De verschillende
opdrachten bouwen op elkaar voort. Het resultaat van een vorige opdracht wordt gebruikt voor
de daarop volgende opdracht. Er is tijd ingeruimd om iedere opdracht te doen. Mocht je tijdens
een opdracht nog tijd over hebben, dan kan je terug gaan naar een vorige opdracht om je testsuite
aan te vullen.

Het is niet nodig dat aan het eind van de opdracht alle testen slagen met een groen vinkje.
Maar het is wel de bedoeling dat alles compileert.



## Opdracht 1
Je zal zelf een testclass en testfuncties aan moeten maken. Het doel is dat je goed nadenkt
over de functienamen van je testscenario's. Deze moeten duidelijk de flow van je testen beschrijven.

Het is nog niet nodig om ook al de body van de testen te programmeren. Daarnaast mag je al 
je testen ook in een enkele class gooien. De naam van de testclass is nu nog niet van belang.
Die zaken komen allemaal bij de volgende opdrachten aanbod. 

Bij het opstellen van de testmethode naam moet je er op letten dat het duidelijk is voor
een ander wat de precondities zijn, welke actie de trigger is voor de test, en wat er
uiteindelijk verwacht wordt om de test te doen slagen.

**Belangrijk**: Meestal wordt de methodenaam gebruikt voor de volledige beschrijving. Je bent
dan wel gebonden aan de beperkingen voor functienamen, wat de leesbaarheid niet ten goede
komt. Het voordeel is dat deze namen in het verlengde van classnamen gebruikt kunnen worden.
Dezelfde stijl zorgt ervoor dat de tekst binnen de boom als een geheel gelezen kan worden.

Je kan ook `displayName` zetten op een test, wat een parameter van het type `string` aan 
de attribuut voor de test meegegeven kan worden. Het voordeel van deze aanpak is dat punctuatie
en spatie gebruik in de tekst opgenomen kunnen worden, wat het scenario veel leesbaarder maakt.
Maar dit kan vaak niet gedaan worden voor de classes, folders en namespaces.

### .Net C#
Het testproject is opgezet met MSTest. Je zal dus je classes en functies moeten decoreren
met respectievelijk de `TestClass` en `TestMethod` attributen. Zodat ze zichtbaar zijn in
de TestExplorer. Bijvoorbeeld:

```csharp
[TestClass]
public class MyTests
{
    [TestMethod]
    public void <MyFullTestMethodName>()
    {
        
    }

    [TestMethod(displayName: """
    Given my situation,
    when I take action,
    then I expect result
    """)]
    public void <MySimpleTestMethodName>()
    {
        
    }
}
```

### Java
Voor het uitvoeren van de testen in Java moet een Java SDK geinstalleerd zijn. Minimaal versie 17. Voor
Windows kan deze hier gevonden worden: https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html.
Verder moet het build programma maven geinstalleerd zijn. Deze kan voor Windows hier gevonden worden: https://maven.apache.org/download.cgi.
Als IDE kan Intellij van Jetbrains gebruikt worden: https://www.jetbrains.com/idea/download/?section=windows. Selecteer de comunity versie.
Alle testen worden uitgevoerd met het Junit5 framework
Een geinitieerd project kan gevonden in de repository, Het pad is .../java/testmodule2
Het test project is opgezet met maven. Een goed voorbeeld:

```java
package com.alfa1;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MyTests {

    @Test
    void myFullTestMethodName() { 
        // Test implementation goes here
    }

    @Test
    @DisplayName("""
            Given my situation,
            when I take action,
            then I expect result
            """)
    void mySimpleTestMethodName() { 
        // Test implementation goes here
    }
}
```


## Opdracht 2
Nu gaan wij de test methoden van de vorige opdracht uitbreiden met de begin situatie van de
test. Dit representeert de input voor de test. Definieer de objecten en waardes die de test
nodig heeft om tot het gewenste resultaat te komen. In het AAA patroon gaat het hier om de
`Arrange` en `Act` stap.

Wederom hoef je geen zorgen te maken over het schoonhouden van je code en kopieer de regels
code naar hartelust van de ene naar de andere body. Het refactoren komt later nog aanbod.

**C# .Net**
```csharp

[TestMethod]
public void <MyTestMethodName>()
{
    // Arrange
    var inputValue = new MyInput()
    {
        Name = "something"
    };

    _repositoryMock.Setup(mock => mock.Get(It.IsAny<Guid>()))
        .Returns(new MyOutput { Value = "something valid" });

    // Act
    _sut.TakeAction(inputValue);

    // Assert
}

[DataTestMethod]
[DataRow("null", null)]
[DataRow("empty", "")]
[DataRow("whitespace", "   ")]
[DataRow("line break", "\n")]
public void <MyTestMethodName>(string _, string inputValue)
{
    // Arrange
    var input = MyReturnInputBuilder.ValidState
                .WithName(inputValue)
                .Build();

    _repositoryMock.Setup(mock => mock.Get(It.IsAny<Guid>()))
        .Returns(input);

    // Act
    _sut.TakeAction(new MyInput() { Name = "something" });

    // Assert
}

[TestMethod]
public void <MyTestMethodName>()
{
    // Arrange
    var input = new Exception("Test exception");

    _repositoryMock.Setup(mock => mock.Get(It.IsAny<Guid>()))
        .Throws(input);

    // Act
    _sut.TakeAction(new MyInput() { Name = "something" });

    // Assert
}
```


### Java
Een aantal constructen die `MSTest` beschikbaar stelt zijn niet standaard beschikbaar in Java. 
Daarom is er in de repository extra code aanwezig om deze te simuleren. Onderstaande code gebruit het `mock` framework in combinatie met het Junit5 framework om de testen uit te voeren.

```java
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

```

Meestal houdt het in dat je data definieert dat relevant is voor het resultaat van de test.
Denk hierbij aan data dat aan je unit meegegeven wordt bij de action om te verwerken. Maar
het is ook data dat door een mock aangeleverd wordt dat nodig is voor de verwerking binnen
een unit. Bedenk dat een exception ook een vorm van input is.

Als de test bij een mock begint dan is het belangrijk dat bij de aanroep je action ook valide
input meegegeven wordt, waarmee de mock bereikt kan worden. Als de test bij een mock eindigt
en het resultaat van de mock is nodig om de executie te laten eindigen dan is het belangrijk
om ook hier een valide waarde op te geven. Ook al zijn deze inputs niet van belang voor de 
verificatie stap van de test. Het is wel belangrijk voor een geldige executie van de test.

*Let op:* Als je nog tijd over hebt ga dan terug naar de vorige opdracht en maak extra 
scenario's aan waar je de input voor kan definiëren.



## Opdracht 3
Nu je de begin situatie opgezet hebt is het tijd om te kijken of het resultaat correct is.
In deze opdracht gaan jullie verifiëren of het gewenste resultaat ook behaald is. In het 
AAA patroon houdt dit de `Assert` stap in. 

Wederom hoef je geen zorgen te maken over het schoonhouden van je code en kopieer de regels
code naar hartelust van de ene naar de andere body. Het refactoren komt later nog aanbod.

**C# .Net**
```csharp
[TestMethod]
public void <MyTestMethodName>()
{
    // Arrange
    var expected = "bob";

    // Act
    var actual = _sut.TakeAction(6);

    // Assert
    Assert.AreEqual(expected, actual, "bob is always the 7th person in the sequence");
}

[TestMethod]
[DataRow(1, "Jan")]
[DataRow(42, "DeepThought")]
public void <MyTestMethodName>(int input, string expected)
{
    // Arrange    .
    _repositorryMock.Setup(mock => mock.Get(It.IsAny<string>()))
        .Returns(new MyOutput { Value = "something valid" });

    // Act
    var _ = _sut.TakeAction(input);

    // Assert
    _repositoryMock.Verify(
        mock => mock.Get(It.Is<string>(actual => actual == expected)),
        Times.Once);
}

[TestMethod]
[DataRow(1, "Jan")]
[DataRow(42, "DeepThought")]
public void <MyTestMethodName>(int input, string expected)
{
    // Arrange
    var actual = string.Empty;
    _repositoryMock.Setup(mock => mock.Get(It.IsAny<string>()))
        .Callback((string name) => actual = name);

    // Act
    var _ = _sut.TakeAction(input);

    // Assert
    Assert.AreEqual(expected, actual);
}

[TestMethod]
public void <MyTestMethodName>()
{
    // Arrange
    
    // Act
    var actual = Assert.ThrowsException<InvalidOperationException>(() => { _ = _sut.TakeAction(); })

    // Assert
    Assert.AreEqual("Expected error message", actual.Message);
}
```

**Java**
Zoals bij opdracht 2. zijn de hulpclasses aangemaakt. Waar mogelijk gebruikt de test de hulp classes 
van opdracht 2. Alleen die classesa die aangepast worden voor deze extra tests zijn aangepast in opdracht3.

```java
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

```

Het resultaat dat gecontroleerd moet worden zijn de waardes die je terugkrijgt van je unit
na de functie aanroep. Of de parameters waarmee een methode op een mock aangeroepen wordt.
Iets dat je nooit moet testen is of het mocking framework werkt, of dat de logica die je in
je mock hebt gedefinieerd ook correct is. Het eerste is aan de makers van je mocking framework
en de laatste betekend dat je meerdere units aan het testen bent. Mocks dienen alleen gebruikt
te worden voor het leveren van input en het opvangen van output van een test.

*let op:* heb je nog tijd over, ga dan terug naar de vorige opdrachten.


## Opdracht 4
Je hebt nu de testscenario's gemaakt. Je zal wel ontdekt hebben dat je een hele hoop code
dubble hebt gemaakt. Het is dan nu ook de tijd om je code te gaan opschonen. Het is de bedoeling
dat je je testscenario's verdeeld over testclasses en je testfixture opbouwt. Bij het maken
voor de namen van je testclasses moet je er goed opletten dat dit een goede beschrijving geeft
van waarom de testscenario's bij elkaar in een groep horen.

Het is nu de bedoeling om je testen klein te maken. Alle setup en cleanup code van je System
Under Test, en het definiëren van defaults verhuizen naar de testfixture. In de body van de
testscenario's blijft alleen de code over voor het definiëren van de inputs die relevant en/of
uniek zijn aan de test scenario, de aanroep van de act, en het verifiëren van de relevante
output van de testscenario.

**C# .Net**
```csharp
[TestClass]
public class <MyTestClass>
{
    private IMyProduction _sut;
    private Mock<IRepository> _repositoryMock;

    [TestInitialize]
    public void Initialize()
    {
        _repositoryMock = new Mock<IRepository>();
        _sut = MyProductionService(_repositoryMock.Object);
    }
}

[TestClass]
public class <MyTestClass>
{
    private IMyProduction _sut;
    private Mock<IRepository> _repositoryMock;

    [TestInitialize]
    public void Initialize()
    {
        _repositoryMock = new Mock<IRepository>();
        
        var services = new ServiceCollection();
        services.RegisterMyProductionApp();
        services.AddTransient<IRepository>(_ => _repositoryMock.Object);
        var provider = services.BuildProvider();

        _sut = provider.GetRequiredService<IMyProduction>();
    }
}
```

**Java**
Zoals opdracht 3 aantoont is deze scheiding al gemaakt in de hulp classes. Hierdoor zijn de effectieve testen al klein 

Ben je hier snel mee klaar dan mag je terug gaan naar de vorige opdracht om nog meer testen
erbij te maken.