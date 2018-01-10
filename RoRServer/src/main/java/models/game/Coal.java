package models.game;

import models.base.ModelObservable;

/**
 * @author Andreas Pöhler, Isabell Rott, Juliane Lies
 * Klasse für Ressource Kohle
 */
public class Coal extends Resource {

    public Coal(int quantity) {
        super(ResourceType.COAL, quantity);
    }

    @Override
    public void update(ModelObservable o, Object arg) {

    }
}
