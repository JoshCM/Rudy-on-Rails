package models.game;

import communication.MessageInformation;


/**
 * @author Andreas Pöhler, Juliane Lies, Isabell Rott
 * Oberklasse für Ressourcen (Aktuell: Kohle und Gold)
 */
public abstract class Resource extends InteractiveGameObject implements PlaceableOnSquare {
    // TODO: Sollten ENUMs sein statt Namen für x-beliebige Ressourcen.
    protected int quantity;
    protected ResourceType resourceType;

    protected Resource(ResourceType resourceType, int quantity) {
        this.resourceType = resourceType;
        this.quantity = quantity;
        notifyObservers();
    }

    protected Resource(String resourceTypeString, int quanitty) {
        this(convertToResourceType(resourceTypeString), quanitty);
    }

    protected Resource(String resourceTypeString) {
        this(convertToResourceType(resourceTypeString), 0);
    }

    private static ResourceType convertToResourceType(String resourceTypeString) {

    }

    protected Resource(ResourceType ressourceType) {
        this(ressourceType, 0);
    }

    /**
     * Erstellen einer neuen Message an die Clients
     */
    private void notifyCreatedResource() {
        MessageInformation message = new MessageInformation("CreateResource");
        message.putValue("resourceId", getUUID());
        message.putValue("quantity", getQuantity());
        message.putValue("resource", getName());
        notifyObservers();
        // notifyChange(message);
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void increaseQuantity(int increaseValue) {
        this.quantity += increaseValue;
    }

    public void decreaseQuantity(int decreaseValue) {
        this.quantity -= decreaseValue;
    }

    public String getName() {
        return ressourceType;
    }

    public void setName(String name) {
        this.ressourceType = name;
    }
}
