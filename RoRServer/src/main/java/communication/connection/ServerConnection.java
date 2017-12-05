package communication.connection;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;

import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;

// Singleton für Server-Connection (beinhaltet Factory, Connection, session)
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
	
	public void closeConnectionAndSession() throws JMSException {
		connection.close();
		session.close();
	}
}

