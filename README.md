Game of Life
============

Zusammenfassung meiner gewonnenen Ideen beim Coderetreat (Game of Life) des
 [5. Hackcamp in Wolfsburg](http://hackcamp-wolfsburg.de/hackcamp5).

# Features #
- Modellieren einer Population per GUI
- Speichern / Laden des Spielstands
- Navigation per Toolbarbutton
- Schrittweise Populationsentwicklung
- Automatische Populationsentwicklung
    * konfiguierbare Geschwindigkeit
- Zoomen per Mausrad
- Unterschiedliche Bretttypen
	* Brett mit fester Breite und Höhe
	* Torusförmiges Brett mit fester Breite und Höhe
	* Unendlich großes Brett
- einige vordefinierte Populationen

# Frameworks #
- JUnit
- JavaFX
    - GUI Erstellung mit JavaFX Scene Builder
    - CSS Styling (Toolbarbuttons)
    - Zeichnen auf Canvas
- Jaxb zum Speichern und Laden des Spielstands

Icons von [famfamfam](http://www.famfamfam.com/lab/icons/silk/).

![GameOfLife](GameOfLife.png)