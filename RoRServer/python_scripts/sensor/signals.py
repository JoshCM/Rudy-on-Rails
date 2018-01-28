def init(proxy):
    pass
    
def update(proxy):
    object = proxy.getObjectOnSquare()
    if "Signals" in object:
        proxy.changeSwitchInterval(1)