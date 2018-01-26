
def init(proxy):
	# Wird bei Erstellung des Scripts ausgeführt
	pass
	
def update(proxy):
	object = proxy.getObjectOnSquare()
	if "Signal" in object:
    	proxy.changeSwitchInterval(5)