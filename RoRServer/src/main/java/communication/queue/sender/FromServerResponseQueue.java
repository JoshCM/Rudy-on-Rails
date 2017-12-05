package communication.queue.sender;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.TextMessage;

public class FromServerResponseQueue extends QueueSender{

    public FromServerResponseQueue(String queueName) {
        super(queueName);
    }

    @Override
    protected void createQueue() {
    	super.createQueue();
    }
}
