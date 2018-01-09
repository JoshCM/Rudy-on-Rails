package models.base;

import java.util.UUID;

import communication.topic.TopicMessageQueue;

/**
 * Base-Klasse für alle spielrelevanten Models
 * Haben alle eine einzigartige UUID
 * Sind Observable
 * Sind serialisierbar (ohne Observer)
 */
public abstract class ModelBase extends ObservableModel {

    private UUID id;

    public ModelBase() {
        this.registerObserver(TopicMessageQueue.getInstance());
        this.id = UUID.randomUUID();
    }

    /**
     * Wird benötigt, wenn beispielsweise Map deserialisiert/geladen wird.
     * @param id
     */
    public ModelBase(UUID id) {
        this.registerObserver(TopicMessageQueue.getInstance());
        this.id = id;
    }

    public UUID getUUID() {
        return id;
    }


}
