package models.base;

import states.ModelStateInterface;
import states.RoRState;

import java.util.UUID;


/**
 * Base-Klasse für alle spielrelevanten Models
 * Haben alle eine einzigartige UUID
 * Sind Observable
 * Sind serialisierbar (ohne Observer)
 */
public abstract class ModelBase implements ModelStateInterface {
    private RoRState state;
    private UUID id;


    public ModelBase() {
        this.id = UUID.randomUUID();
    }

    /**
     * Wird benötigt, wenn beispielsweise Map deserialisiert/geladen wird.
     * @param id
     */
    public ModelBase(UUID id) {
        this.id = id;
    }

    public UUID getID() {
        return id;
    }


    public RoRState getState() {
        return state;
    }

    public void setState(RoRState state) {
        this.state = state;
    }
}
