def init(proxy):
    pass
    
def update(proxy):
    object = proxy.getObjectOnSquare()
    if "Switch" in object:
        proxy.changeSwitch()