
def init(proxy):
	# Wird bei Erstellung des Scripts ausgeführt
	pass
	
def update(proxy):
	# Wird bei Ausführung des Scripts ausgeführt
    object = proxy.getObjectOnSquare()
    if "Switch" in object:
    	proxy.changeSwitch()