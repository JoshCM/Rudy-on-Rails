package models.base;

import java.util.Observable;
import java.util.UUID;

import communication.MessageEnvelope;
import communication.MessageInformation;
import communication.topic.TopicMessageQueue;

/**
 * Base-Klasse für alle Models, damit alle Models eine Id und die zugehörige
 * RoRSession kennen Die RoRSession wird aktuell gebraucht, damit die Models
 * Änderungen an den Client geben können über die Methode addMessage()
 */
public abstract class ModelBase extends ObservableModel implements Model {

    private UUID id;

    public ModelBase() {
        this.addObserver(TopicMessageQueue.getInstance());
        this.id = UUID.randomUUID();
    }

    public ModelBase(UUID id) {
        this.addObserver(TopicMessageQueue.getInstance());
        this.id = id;
    }

    // TODO: Wieso ein Setter? Wird doch entweder so mit ID konstruiert oder serialisiert? Durch Setter kann man id-Konflikte erzeugen -> Wäre böse!
    protected void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    // TODO: sessionName muss noch irgendwo anders hin gepackt werden.

    /**
     * Hier werden Nachrichten hinzugefügt, die an die verbundenen Clients geschickt
     * werden sollen
     *
     * @param messageInformation
     */
    // TODO: SessionName hier rausmachen -> Die Session sollte Map halten Map hält ModelBase Objekte
    protected void notifyChange(MessageInformation messageInformation) {
        MessageEnvelope messageEnvelope = new MessageEnvelope(sessionName, messageInformation.getMessageType(),
                messageInformation);
        setChanged();
        notifyObservers(messageEnvelope);
    }
}
