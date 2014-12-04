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