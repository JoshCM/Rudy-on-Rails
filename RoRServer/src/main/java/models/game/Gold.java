package models.game;


import models.base.ModelObservable;

/**
 * @author Andreas Pöhler, Isabell Rott, Juliane Lies
 * Klasse für Ressource Kohle
 */
public class Gold extends Resource {
    public Gold(int quantity) {
        super(ResourceType.GOLD, quantity);
        notifyObservers(this);
    }

    @Override
    public void update(ModelObservable o, Object arg) {

    }
}
