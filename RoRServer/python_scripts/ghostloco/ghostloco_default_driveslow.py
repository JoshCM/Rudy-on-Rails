# proxy.setPicksUpCoalContainerNextToRails(True|False)
# proxy.setPicksUpGoldContainerNextToRails(True|False)
# proxy.setStealsGoldContainerFromOtherPlayers(True|False)
# proxy.setStealsCoalContainerFromOtherPlayers(True|False)
# proxy.isPicksUpCoalContainerNextToRails()
# proxy.isPicksUpGoldContainerNextToRails()
# proxy.isStealsGoldContainerFromOtherPlayers()
# proxy.isStealsCoalContainerFromOtherPlayers()
# proxy.changeSpeed(zwischen -1 und 5)
# proxy.getCoalCount()
# proxy.getGoldCount()
# proxy.getPointCount()

def init(proxy):
	proxy.setPicksUpCoalContainerNextToRails(False)
	proxy.setPicksUpGoldContainerNextToRails(False)
	proxy.changeSpeed(2)


def update(proxy):
	objectsOnSquare = proxy.getObjectsOnSquare(0, 1);

	# Halte, wenn die Strecke zuende ist
	if "Rail" not in objectsOnSquare:
		proxy.changeSpeed(0)
	else:
		# Halte, wenn ein Signal eingeschaltet ist
		if "InactiveSignal" in objectsOnSquare:
			proxy.changeSpeed(2)
		# Fahre weiter, wenn ein Signal ausgeschaltet ist
		elif "ActiveSignal" in objectsOnSquare:
			proxy.changeSpeed(0)

	if proxy.getCoalCount() <= 5:
		proxy.setPicksUpCoalContainerNextToRails(True)
	else:
		proxy.setPicksUpCoalContainerNextToRails(False)