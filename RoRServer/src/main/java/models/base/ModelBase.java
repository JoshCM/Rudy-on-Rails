package models.base;

import java.util.UUID;

import communication.topic.TopicMessageQueue;

/**
 * Base-Klasse für alle spielrelevanten Models
 * Haben alle eine einzigartige UUID
 * Sind Observable
 * Sind serialisierbar (ohne Observer)
 */
public abstract class ModelBase extends ObservableModel implements Model {

    private UUID id;

    public ModelBase() {
        this.addObserver(TopicMessageQueue.getInstance());
        this.id = UUID.randomUUID();
    }

    /**
     * Wird benötigt, wenn beispielsweise Map deserialisiert/geladen wird.
     * @param id
     */
    public ModelBase(UUID id) {
        this.addObserver(TopicMessageQueue.getInstance());
        this.id = id;
    }

    public UUID getUUID() {
        return id;
    }


}
