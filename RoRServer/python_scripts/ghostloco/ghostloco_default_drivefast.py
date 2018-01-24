def init(proxy):
	proxy.setPicksUpCoalContainerNextToRails(False)
	proxy.setPicksUpGoldContainerNextToRails(True)
	proxy.changeSpeed(5)


def update(proxy):
	objectsOnSquare = proxy.getObjectsOnSquare(0, 1);

	# Halte, wenn die Strecke zuende ist
	if "Rail" not in objectsOnSquare:
		proxy.changeSpeed(0)
	else:
		# Halte, wenn ein Signal eingeschaltet ist
		if "InactiveSignal" in objectsOnSquare:
			proxy.changeSpeed(0)
		# Fahre weiter, wenn ein Signal ausgeschaltet ist
		elif "ActiveSignal" in objectsOnSquare:
			proxy.changeSpeed(5)