package models.game;

import communication.MessageInformation;
import models.helper.StringConverter;

/**
 * @author Andreas Pöhler, Juliane Lies, Isabell Rott
 * Oberklasse für Ressourcen (Aktuell: Kohle und Gold)
 */
public abstract class Resource extends InteractiveGameObject implements PlaceableOnSquare {
    protected int quantity;
    protected ResourceType resourceType;

    protected Resource(ResourceType resourceType, int quantity) {
        this.resourceType = resourceType;
        this.quantity = quantity;
        notifyObservers();
    }

    protected Resource(String resourceTypeString, int quanitty) {
        this(convertStringToResourceType(resourceTypeString), quanitty);
    }

    protected Resource(String resourceTypeString) {
        this(convertStringToResourceType(resourceTypeString), 0);
    }

    private static ResourceType convertStringToResourceType(String resourceTypeString) {
        return StringConverter.convertToResource(resourceTypeString);
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
        message.putValue("resource", getResourceTypeString());
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

    public String getResourceTypeString() {
        return StringConverter.toString(resourceType);
    }

    public PlaceableOnSquare loadFromMap() {
        return null;
    }

}
