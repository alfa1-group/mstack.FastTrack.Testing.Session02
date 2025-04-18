# Beschrijving van het systeem
Tijdens deze sessie gaan wij unittesten opzetten voor een leerlingenadministratie systeem.
Het is een systeem dat informatie vastlegt en verschillende gebruikers moeten er mee om kunnen
gaan. De sessiebegeleider treedt op als PO / Projectleider en aan hem mag je vragen stellen
als er onduidelijkheden zijn.

## Wat de business wil
De business wil een systeem hebben waarmee de administratie van en over leerlingen bijgehouden
kan worden. Er zullen verschillende type gebruikers zijn die gegevens willen lezen, aanmaken,
bijwerken en verwijderen. Natuurlijk kan niet iedere type gebruiker alles uitvoeren. De apps
moeten via het internet op een PC, laptop of smartphone beschikbaar zijn. Maar niet de gehele
wereld moet er gebruik van kunnen maken. Gebruikers moeten inloggen om toegang tot het systeem
te krijgen.

Een gebruiker met een 'Systeem Beheerder' en/of 'Administratief Medewerker' rol moet een
lijst van leerlingen, rechtmatige verzorgers en/of leerkrachten van de gehele school kunnen
inzien. Dit zijn verschillende lijsten, want personen mogen niet gemixed in één overzicht
getoond worden. Hierbij moet in de lijst de volgende informatie zichtbaar zijn: 
- De volledige naam van de persoon
- Geboortedatum
- Unieke gebruikersnaam voor het systeem.
- Voor leerlingen de naam van de klas waarin de leerling is toegevoegd
- Voor leerkrachten de namen van de klassen waar de leerkracht aan toegewezen is.
- Voor rechtmatige verzorgers de namen van de leerlingen waar ze over zorgdragen.

Een gebruiker met een 'Leerkracht' rol kan alleen een lijst met leerlingen ophalen die in
de klas van de leerkracht zitten. Hierbij volstaat het tonen van de volledige naam. Al moet
de lijst nog wel gefiltered kunnen worden op basis van klas als een leerkracht meerdere klassen 
heeft.

Een gerechtmatige verzorger mag alleen de gegevens ophalen van de leerlingen waarover deze
zorgdraagt. Hierbij volstaat het om alleen een naam weer te geven. Er moet wel opgelet worden
dat medewerkers van de school ook hun kinderen als leerling op de school hebben.
De functionaliteit van een rechtmatige verzorger mag niet mixen met de functionaliteit als
medewerker van de school. Maar een medewerker zal niet opnieuw in hoeven te loggen als deze
de informatie van zijn eigen kinderen wil inzien.

Een leerling mag niet de gegevens van andere leerlingen zien, deze heeft alleen toegang tot
de leerling's eigen informatie.

Alleen systeem beheerders en administratief medewerkers mogen leerlingen en rechtmatige verzorgers
aan het systeem toevoegen en verwijderen. Van de leerling wordt de volgende persoonlijke informatie
in het systeem opgeslagen:
- De voornamen, de roepnaam, de achternamen en optioneel tussenvoegsels.
- De geboortedatum van de leerling. (Een leerling is minimaal 3 jaar oud op het moment van registratie)
- Pasfoto van de leerling
- Het woonadres (straatnaam, huisnummer, postcode en plaatsnaam) waar de leerling woont.
- Wie de rechtmatige verzorgers (en of het gaat om een voogd of biologische ouder) zijn van de leerling. (minimaal 1)
- Optionele contactgegevens van de leerling zoals telefoonnummer en/of emailaddres.
- Optionele Medische en/of gerechtelijke notities over de leerling.
- Het leerniveau van de leerling.

Van rechtmatige verzorgers worden de volgende gegevens opgeslagen:
- De roepnaam, de achternamen en optioneel tussenvoegsels.
- Pasfoto van de rechtmatige verzorger.
- Contact informatie zoals telefoonummer en emailadres.
- Optioneel woonadres van de persoon.
- Welke leerlingen de persoon zorg over draagt.

Als een leerling aan het systeem toegevoegd wordt dan wordt tevens het proces om een gebruikers
account aan te maken voor de leerling in gang gezet. Als een rechtmatige verzorger toegevoegd
wordt en er wordt niet een bestaande account meegegeven, dan wordt er ook een account voor
deze gebruiker aangemaakt.

Systeem beheerders, administratief medewerkers en leerkrachten mogen de gegevens van de leerlingen
waar ze toegang tot hebben wijzigen. Dit is niet alleen de hierboven genoemde gegevens, maar
ook de volgende acties:
- Het toevoegen, wijzigen en verwijderen van een extra woonadressen, zolang er altijd minimaal één primair adres bekend is.
- Het toevoegen, wijzigen en verwijderen van een administratieve notitie die alleen door de schoolmedewerkers gezien mogen worden.
- Het toevoegen, wijzigen en verwijderen van een publieke notitie.
- Het toevoegen, wijzigen en verwijderen van prestaties zoals cijfers voor toetsen en voor welk vak/categorie deze toets gedaan is.

Alleen systeem beheerders en administratief medewerkers mogen de volgende wijzigingen aan
de gegevens van een leerling aanbrengen.
- De leerling toewijzen en verwijderen aan één of meerdere klassen.
- Extra rechtmatige verzorgers toevoegen, wijzigen en verwijderen aan een leerling.

Systeem beheerders en administratief medewerkers mogen de gegevens van rechtmatige verzorgers
wijzigen. Dit is het wijzigen van de eerder genoemde gegevens, maar ook de volgende acties:
- Het toevoegen en verwijderen van een extra leerling waar de persoon zorg over draagt.
- Het toevoegen, wijzigen en verwijderen van administratieve notities van de desbestreffende persoon.
- Het toevoegen en verwijderen van extra contactgegevens.
- Het toevoegen en verwijderen van extra woonadressen.

Leerkrachten mogen de gegevens van de rechtmatige verzorgers van hun leerlingen inzien, maar
deze mogen niet door de leerkracht aangepast worden. Een leerkracht mag alleen een administratieve
notitie toevoegen aan rechtmatige verzorger.

Alleen systeem beheerders mogen andere medewerkers van de school als nieuwe gebruikers toevoegen,
wijzigen en verwijderen, maar de persoonlijke informatie hoeft niet in het leerlingen administratie
systeem opgeslagen te worden. Systeembeheerders en administratief medewerkers moeten wel
leerkrachten aan klassen kunnen koppelen en ontkoppelen.

Het is vanzelfsprekend dat gegevens die een gebruiker moet kunnen wijzigen ook ingezien moeten
worden door de gebruiker. Een rechtmatige verzorger moet zijn eigen gegevens en van de bijbehorende
leerlingen in kunnnen zien. Bij de leerlingen mag een verzorger de namen van andere verzorgers
zien, maar niet hun persoonlijke informatie. Publieke en medische notities die bij de rechtmatige
verzorger en de leerling horen mag hij zien, maar niet administratieve en gerechtelijke notities.

Een leerling kan ook niet zijn gegevens wijzigen, maar deze mag wel bijna alle informatie
over zichzelf inzien. Administratieve en gerechtelijke notities zijn niet zichtbaar voor de
leerling. Maar van de rechtmatige verzorgers en leerkrachten zijn alleen de namen zichtbaar.



## Notities van de solution architect
Dit team werkt alleen aan de backend. Er zijn verschillende andere teams die de website en
de apps zullen ontwikkelen. Houdt de API operaties atomair. De frontend mag ook wel het een
en ander doen. De backend hoeft niet alle data in een enkele response klaar te zetten of in
een request mee te krijgen.

Leerlingen, rechtmatige verzorgers en leerkrachten kunnen als zichzelf bestaande entiteiten
bestaan. Leerlingen en leerkrachten kunnen technisch ook zonder een klas in het systeem bestaan.
Ze moeten wel terug komen als een systeem beheerder of administratief medewerker alle leerlingen,
rechtmatige verzorgers en/of leerkrachten opvraagt. En ze moeten ook opvraagbaar zijn als
je zoekt op naam.

De backend is niet publiekelijk toegankelijk. Er is geen endpoint waarvoor er geen authorizatie
nodig is. Benodigde informatie van de ingelogde gebruiker wordt uit het authenticatie token
gehaald en zal nooit in de body of url van een webrequest verpakt worden.

Over de beveiliging gaat de security officer met het security en frontend teams in gesprek.
Wij hoeven er alleen op te letten dat een request een geldige authorizatie token bevat. Zo
niet dan wordt een "401 Unauthorized" als antwoord verwacht. 

De volgende status codes zijn ook afgesproken met de frontend die de backend terug kan geven:

| Code | Reden                                                                                                                    |
|------|--------------------------------------------------------------------------------------------------------------------------|
| 200  | (OK) Dit is als een GET, POST of een HEAD request zonder problemen is verlopen.                                          |
| 204  | (No Content) Dit is als een PUT of DELETE request met success is uitgevoerd.                                             |
| 400  | (Bad Request) Data meegeleverd in het request bevat fouten waardoor het request niet afgehandeld kan worden.             |
| 403  | (Forbidden) De gebruiker probeert een actie uit te voeren of data op te halen, waar de gebruiker geen toegang tot heeft. |
| 404  | (Not Found) Data dat opgevraagd of bewerkt wordt door de gebruiker is niet gevonden in het systeem.                      |
| 500  | (Internal Server Error) Er is een fout opgetreden tijdens het verwerken van het request, dat gerepareerd moet worden.    |

Alle andere status codes die de frontend kan verwachten, worden door de infrastructuur gegenereerd.

Als er vanuit een request een processen gestart moeten worden, dan is er een messaging bus
waar je events op kunt zetten. Voor het aanmaken van een account voor gebruikers is de volgende
informatie nodig:
- De naam van de gebruiker.
- Wat voorgebruiker het is: leerkracht, administratief medewerker, systeembeheerder, leerling of rechtmatige verzorger.
- Geboortedatum (leerlingen beneden een bepaalde leeftijd krijgen een apart inlogsysteem, gebaseerd op plaatjes, of een pincode).
- Een emailadres om een bevestigingsmail naar toe te sturen. 


