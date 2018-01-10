package communication.queue.receiver;

import java.util.Date;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.TextMessage;

import communication.ServerConnection;
import communication.dispatcher.DispatcherBase;
import org.apache.log4j.Logger;

import static models.config.GameSettings.QUEUERECEIVER_LOGGING;

/**
 * Base-Klasse für alle spezifischen ReceiverQueue
 */
public class ReceiverQueue implements MessageListener {
    protected Logger logger = Logger.getLogger(ReceiverQueue.class.getName());

    private Queue queue;
    private String queueName;
    private MessageConsumer consumer;
    private DispatcherBase dispatcher;

    public ReceiverQueue(String sessionname, DispatcherBase dispatcher) {
        this.queueName = "QUEUE"+sessionname;
        this.dispatcher = dispatcher;
    }

    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            String request = message.getJMSType();

            log(request, textMessage);

            dispatcher.dispatch(request, textMessage.getText());
        } catch (JMSException e) {
            logger.error("FromClientRequestQueue.onMessage() : QueueSender konnte Nachricht nicht verschicken");
            e.printStackTrace();
        }
    }

    public String getQueueName() {
        return queueName;
    }

    /**
     * Erzeugt den Consumer und dessen Listener
     */
    public void setup() {
        try {
            session = ServerConnection.getInstance().getDefaultSession();
            queue = session.createQueue(queueName);
            consumer = session.createConsumer(queue);
            consumer.setMessageListener(this);
            logger.info("Waiting for Messages on Queue " + queueName + " :");
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void log(String request, TextMessage textMessage) throws JMSException {
        if (QUEUERECEIVER_LOGGING) {
            logger.info("FromClientRequestReceiver.onMessage(): ... Message received [" + new Date().toString() + "]: "
                    + request
                    + textMessage.getText());
        }

    }

}
