package models.game;


import exceptions.ContainerException;
import models.base.InterActiveGameModel;
import models.base.PlaceableModel;
import models.helper.StringConverter;

public class Container extends PlaceableModel {

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
    public void update(InterActiveGameModel o, Object arg) {
        // Ändere Inhalt?
        // Gib Observern Bescheid!
    }
}
