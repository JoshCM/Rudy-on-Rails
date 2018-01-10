package communication;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;

import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;

/**
 * Die ServerConnection kümmert sich um den Verbindungsaufbau zum BrokerService
 * Wenn ein Receiver oder Sender für Queue/Topic angelegt wird, dann wird die Connection und die Session
 * aus dieser Klasse verwendet, damit für den ganzen Server nur jeweils eine Connection und eine Session genutzt werden
 *
 * TODO: Eine Connection ist okay... aber EINE Session ist ziemlich falsch!!!
 */
public class ServerConnection {
	
	private static ServerConnection serverConnection = null;
	private ActiveMQConnectionFactory factory;
	private Connection connection;
	private Session session;
	static Logger log = Logger.getLogger(ServerConnection.class.getName());
	
	private ServerConnection() throws JMSException {
		factory = new ActiveMQConnectionFactory();
		connection = factory.createConnection();
		session = connection.createSession(false,  Session.AUTO_ACKNOWLEDGE);
		connection.start();
		log.info("ServerConnection.ServerConnection(): create a Connection");
	}
	
	public static ServerConnection getInstance() throws JMSException {
		if (serverConnection == null) {
			serverConnection = new ServerConnection();
		}
		return serverConnection;
	}
	
	public Session getSession() {
		return session;
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	/**
	 * Schließt die Verbindung von connection und session
	 * @throws JMSException
	 */
	public void closeConnectionAndSession() throws JMSException {
		connection.close();
		session.close();
	}
}

