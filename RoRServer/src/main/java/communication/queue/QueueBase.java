package communication.queue;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.Session;

import communication.ServerConnection;

/**
<<<<<<< Updated upstream
 * Die Base-Klasse für QueueReceiver und QueueSender
=======
 * Von der QueueBase erben QueueSender und QueueReceiver, die beide eine (ActiveMQ)Queue verwenden
>>>>>>> Stashed changes
 */
public abstract class QueueBase {
	protected ServerConnection connection;
	protected Session session;
	protected Queue queue;
	protected String queueName;

	public void setup() {
		try {
			session = ServerConnection.getInstance().getSession();
			queue = session.createQueue(queueName);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
