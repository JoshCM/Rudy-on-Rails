def init(proxy):
    pass
    
def update(proxy):
    objects = proxy.getSquaresAroundSensor()
    for x in objects:
        proxy.changeSwitchAroundSensor(x)