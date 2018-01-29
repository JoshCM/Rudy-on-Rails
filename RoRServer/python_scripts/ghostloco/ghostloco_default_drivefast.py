#!/usr/bin/python
# -*- coding: UTF-8 -*-

# Mögliche Methoden:

# proxy.getObjectsOnSquare(int sideways, int forward)
# Gibt eine Liste mit Strings zurück, in denen die Objekte namentlich stehen, die sich auf dem angegebenen Feld befinden
# sideways: Wieviele Felder der Geisterzug nach links bzw. rechts schauen soll; Bereich: -2 - +2
# forward: Wieviele Felder der Geisterzug nach vorne schauen soll; Bereich: 0-5
# Mögliche Fehler-Strings in der Liste: ("NotOnMap", "NotVisible")
# Mögliche Objekt-Strings in der Liste: ("OwnTrainstation", "OtherTrainstation", "Rail", "ActiveSignal", "InactiveSignal", "Mine", "Loco")

# proxy.getObjectsOnSquareBehindLastCart()
# Gleiches Verhalten wie getObjectsOnSquare, bloß handelt es sich hier um das Feld hinter dem letzten Waggon
# Kann zum Beispiel genutzt werden, um herauszufinden, ob hinter dem Geisterzug ein Cart zum ankoppeln bereitsteht

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

default_speed = 4


# Hier können Startwerte gesetzt werden
# Wird bei Aktivierung des Scripts genau einmal aufgerufen
def init(proxy):
	global default_speed
	proxy.setPicksUpCoalContainerNextToRails(False)
	proxy.setPicksUpGoldContainerNextToRails(True)
	proxy.setStealsGoldContainerFromOtherPlayers(True)
	proxy.changeSpeed(default_speed)


# Hier kann das Verhalten des Geisterzugs gescriptet werden
# Wird in regelmäßigen Abständen aufgerufen
def update(proxy):
	global default_speed

	# Waggon ankoppeln
	if "Cart" in proxy.getObjectsOnSquareBehindLastCart():
		proxy.changeSpeed(-1)
		import time 
		time.sleep(3)
		proxy.changeSpeed(default_speed)

	# Halte zum Abladen von Resourcen und fahre anschließend weiter
	if "OwnTrainstation" in proxy.getObjectsOnSquare(1, 0) or "OwnTrainstation" in proxy.getObjectsOnSquare(-1, 0):
		if proxy.hasResourcesOnCarts():
			proxy.changeSpeed(0)
		else:
			proxy.changeSpeed(default_speed)

	# Schaue genau ein Feld vor dich
	objectsOnSquare = proxy.getObjectsOnSquare(0, 1)

	# Fahre rückwärts, wenn die Strecke zuende ist
	if "Rail" not in objectsOnSquare:
		proxy.changeSpeed(-1)
	else:
		# Fahre weiter, wenn ein Signal ausgeschaltet ist
		if "InactiveSignal" in objectsOnSquare:
			proxy.changeSpeed(default_speed)
		# Halte, wenn ein eingeschaltetes Signal vor dem Geisterzug ist
		elif "ActiveSignal" in objectsOnSquare:
			proxy.changeSpeed(0)

	# Wenn der Spieler wenig Kohle hat, dann sollte er Kohle von neben den Gleisen mitnehmen bzw. klauen
	if proxy.getCoalCount() <= 20:
		proxy.setPicksUpCoalContainerNextToRails(True)
		proxy.setPicksUpGoldContainerNextToRails(False)
		proxy.setStealsCoalContainerFromOtherPlayers(True)
		proxy.setStealsGoldContainerFromOtherPlayers(False)
	# Falls der Spieler genug Kohle hat, dann sollte der Geisterzug lieber Gold aufsammeln bzw. klauen
	else:
		proxy.setPicksUpCoalContainerNextToRails(False)
		proxy.setPicksUpGoldContainerNextToRails(True)
		proxy.setStealsCoalContainerFromOtherPlayers(False)
		proxy.setStealsGoldContainerFromOtherPlayers(True)