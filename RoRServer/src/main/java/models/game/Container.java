package models.game;


import exceptions.ContainerException;
import models.helper.StringConverter;

public class Container extends InteractiveGameObject implements PlaceableOnSquare {

    private Resource resource;

    public Container(String resourceTypeString, int quantity) {
        if (StringConverter.convertToResource(resourceTypeString).equals(ResourceType.COAL)) {
            this.resource = new Coal(quantity);
        }

        if (StringConverter.convertToResource(resourceTypeString).equals(ResourceType.GOLD)) {
            this.resource = new Gold(quantity);
        }

        throw new ContainerException("Could not create Container");
    }

    public Resource getResource() {
        return resource;
    }

    @Override
    public PlaceableOnSquare loadFromMap() {
        return null;
    }
}
