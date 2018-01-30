#!/usr/bin/python
# -*- coding: UTF-8 -*-

# Mögliche Methoden:

# proxy.getObjectsOnSquare(int sideways, int forward)
# Gibt eine Liste mit Strings zurück, in denen die Objekte namentlich stehen, die sich auf dem angegebenen Feld befinden
# sideways: Wieviele Felder der Geisterzug nach links bzw. rechts schauen soll; Bereich: -2 - +2
# forward: Wieviele Felder der Geisterzug nach vorne schauen soll; Bereich: 0-5
# Mögliche Fehler-Strings in der Liste: ("NotOnMap", "NotVisible")
# Mögliche Objekt-Strings in der Liste: ("OwnTrainstation", "OtherTrainstation", "Rail", "ActiveSignal", "InactiveSignal", "Mine", "Loco")

# proxy.setPicksUpCoalContainerNextToRails(True|False)
# Wenn true, dann nimmt der Geisterzug Kohle-Container, an denen er vorbeifährt, mit

# proxy.setPicksUpGoldContainerNextToRails(True|False)
# Wenn true, dann nimmt der Geisterzug Gold-Container, an denen er vorbeifährt, mit

# proxy.setStealsCoalContainerFromOtherPlayers(True|False)
# Wenn true, dann stiehlt der Geisterzug Kohle von Spielerbahnhöfen, an denen er vorbeifährt
# Falls mindestens einer der beiden steals-Werte auf true steht, dann besteht die Chance, dass stattdessen Punkte vom Spieler geklaut werden

# proxy.setStealsGoldContainerFromOtherPlayers(True|False)
# Wenn true, dann stiehlt der Geisterzug Gold von Spielerbahnhöfen, an denen er vorbeifährt
# Falls mindestens einer der beiden steals-Werte auf true steht, dann besteht die Chance, dass stattdessen Punkte vom Spieler geklaut werden

# proxy.isPicksUpCoalContainerNextToRails()
# Gibt true zurück, wenn der Geisterzug aktuell Kohle-Container, an denen er vorbeifährt, mitnehmen würde

# proxy.isPicksUpGoldContainerNextToRails()
# Gibt true zurück, wenn der Geisterzug aktuell Gold-Container, an denen er vorbeifährt, mitnehmen würde

# proxy.isStealsCoalContainerFromOtherPlayers()
# Gibt true zurück, wenn der Geisterzug aktuell Kohle von Spielerbahnhöfen klauen würde, an denen er vorbeifährt

# proxy.isStealsGoldContainerFromOtherPlayers()
# Gibt true zurück, wenn der Geisterzug aktuell Gold von Spielerbahnhöfen klauen würde, an denen er vorbeifährt

# proxy.changeSpeed(int)
# Ändert die Geschwindigkeit des Geisterzuges (-1: Rückwärts; 0: stehen bleiben; 1-5: Vorwärts)

# proxy.getSpeed()
# Gibt die aktuelle Geschwindigkeit des Geisterzugs zurück (-1: Rückwärts; 0: stehen bleiben; 1-5: Vorwärts)

# proxy.getCoalCount()
# Gibt die Anzahl an Kohle wieder, die der eigene Spieler aktuell besitzt

# proxy.getGoldCount()
# Gibt die Anzahl an Gold wieder, die der eigene Spieler aktuell besitzt

# proxy.getPointCount()
# Gibt die aktuelle Punktzahl des eigenen Spielers wieder

# proxy.hasResourcesOnCarts()
# Gibt true zurück, wenn mindestens einer der Waggons etwas geladen hat

default_speed = 0


# Hier können Startwerte gesetzt werden
# Wird bei Aktivierung des Scripts genau einmal aufgerufen
def init(proxy):
	global default_speed
	proxy.changeSpeed(default_speed)


# Hier kann das Verhalten des Geisterzugs gescriptet werden
# Wird in regelmäßigen Abständen aufgerufen
def update(proxy):
	pass