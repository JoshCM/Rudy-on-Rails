def update(proxy):
	#squareList = proxy.getObjectsOnVisibleSquares()

	#for objList in squareList:
	#	for value in objList:
	#		print(value)

	objectsOnSquare = proxy.getObjectsOnSquare(0, 1);

	#for value in objectsOnSquare:
		#print(value)

	# Halte, wenn die Strecke zuende ist
	if "Rail" not in objectsOnSquare:
		proxy.changeSpeed(0)
	else:
		proxy.changeSpeed(5)
		# Halte, wenn ein Signal eingeschaltet ist
		if "InactiveSignal" in objectsOnSquare:
			proxy.changeSpeed(0)
		# Fahre weiter, wenn ein Signal ausgeschaltet ist
		elif "ActiveSignal" in objectsOnSquare:
			proxy.changeSpeed(5)