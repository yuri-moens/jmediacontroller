## JMediaController

### Installatie

#### Build

1. Zorg dat maven geinstalleerd is.
2. Voer het volgende commando uit in de root directory (waar de pom.xml staat): mvn clean compile assembly:single

#### Installeer

1. Zet de aangemaakte jar die in de target map te vinden is op de mediacontroller.
2. Start de mediacontroller om een standaard configuratiebestand aan te maken.
3. Voeg de YouTube API key toe aan het configuratiebestand (~/.mediacontroller/mc.conf)
4. Start de mediacontroller opnieuw (liefst in een screen).
5. Verbind met de mediacontroller via netcat en voer het commando "admin update" uit om alle dependencies te installeren.

### Code toevoegen

1. Maak een fork van het project en pull deze via git. (in een map in jouw workspace directory, standaard: ~/workspace/naam-van-project)
2. In Eclipse: File > Import > Maven > Existing Maven Projects en duid de juiste root directory aan. Indien Maven nog niet geinstalleerd is kan dit makkelijk via de Eclipse Marketplace gedaan worden.
3. Schrijf code
4. Schrijf betere code
5. Push code
6. Maak merge/pull request aan.

Tutorials voor zaken als commando's en plugins staan op de wiki van dit project.

Pull requests worden niet aanvaardt als de code ondermaats is of als de code de Google Java Style guide niet volgt.

De style guide is hier te vinden: https://google-styleguide.googlecode.com/svn/trunk/javaguide.html

Om de style guide makkelijk te volgen kan men in Eclipse de XML importeren voor deze coding style: https://code.google.com/p/google-styleguide/source/browse/trunk/eclipse-java-google-style.xml

#### Versioning

In dit project wordt Semantic Versioning gebruikt. Hier volgt snel hoe de versioning gebeurt in dit project.

De MediaController zelf heeft een versie nummer in de MediaController klasse en in de pom.xml. Beide nummers moeten **altijd** gelijk zijn aan elkaar. Daarnaast hebben alle plugins zelf ook een versienummer. Al deze nummers worden op dezelfde manier aangepast.

Stel dat we beginnen bij versienummer 1.0.0 bij alle plugins en de MediaController.

1. Er gebeurt een bugfix in de MediaController. De versie van de MediaController gaat naar 1.0.1.
2. Er wordt nieuwe functionaliteit toegevoegd aan de MediaController. De versie gaat naar 1.1.1.
3. Er gebeurt een bugfix in een van de commando's van de StandardPlugin. De versie van de StandardPlugin gaat naar 1.0.1. De versie van de MediaController gaat naar 1.1.2.
4. Er wordt een nieuw commando geschreven voor de StandardPlugin of een bestaand commando krijgt extra functionaliteiten. De versie van de StandardPlugin gaat naar 1.1.1. De versie van de MediaController gaat naar 1.2.2.

Het eerste getal van de versie gaat enkel omhoog als een grote update gebeurd die mogelijk backwards compatibility breekt.