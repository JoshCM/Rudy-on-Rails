def update(proxy):
	proxy.changeRandomSpeed()
	squareList = proxy.getObjectsOnVisibleSquares()

	for objList in squareList:
		for value in objList:
			print(value)
