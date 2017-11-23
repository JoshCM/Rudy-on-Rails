package communication.queue;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.Session;

import communication.connection.ServerConnection;

public abstract class QueueBase {
	
	protected ServerConnection connection;
	protected Session session;
	protected Queue queue;
	protected String queueName;

	protected void createQueue() {
	
		try {
			
			session = ServerConnection.getInstance().getSession();
			queue = session.createQueue(queueName);
			
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
