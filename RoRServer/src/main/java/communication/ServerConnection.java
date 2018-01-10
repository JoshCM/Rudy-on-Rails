package communication;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;

import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;
import resources.PropertyManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Die ServerConnection kümmert sich um den Verbindungsaufbau zum BrokerService
 * Wenn ein Receiver oder Sender für Queue/Topic angelegt wird, dann wird die Connection und die Session
 * aus dieser Klasse verwendet, damit für den ganzen Server nur jeweils eine Connection und eine Session genutzt werden
 *
 * TODO: Eine Connection ist okay... aber EINE Session ist ziemlich falsch!!!
 */
public class ServerConnection {
	
	private static ServerConnection serverConnection = null;
	private ActiveMQConnectionFactory connectionFactory;
	private Connection connection;
	private Session defaultSession;
	private Map<String, Session> sessionMap;
	static Logger log = Logger.getLogger(ServerConnection.class.getName());
	
	private ServerConnection() throws JMSException {
		connectionFactory = new ActiveMQConnectionFactory();
		connection = connectionFactory.createConnection();
		sessionMap = new HashMap<>();
        initializeDefaultSession();
		connection.start();
		log.info("ServerConnection.ServerConnection(): create a Connection");
	}
	
	public static ServerConnection getInstance() throws JMSException {
		if (serverConnection == null) {
			serverConnection = new ServerConnection();
		}
		return serverConnection;
	}


	// SESSION

    /**
     * @return Erstellt eine Session
     * @throws JMSException
     */
	private Session createSession() throws JMSException {
	    return connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }

    private void initializeDefaultSession() throws JMSException {
	    defaultSession = createSession();
	    sessionMap.put(PropertyManager.getProperty("defaultsession_name"), defaultSession);
    }

    /**
     * Erstellt eine Session und added sie mit dem Key sessionName in die sessionMap
     * @param sessionName
     * @throws JMSException
     */
    public void addSessionToMap(String sessionName) throws JMSException {
	    this.sessionMap.put(sessionName, createSession());
    }

    private void removeSessionFromMap(String sessionName) throws JMSException {
        closeSession(sessionName);
        sessionMap.remove(sessionName);
    }

    public void closeSession(String sessionName) throws JMSException {
        getSessionFromList(sessionName).close();
    }

    /**
     * @param sessionName
     * @return Gibt Session mit passendem Namen zurück
     */
    public Session getSessionFromList(String sessionName) {
	    return sessionMap.get(sessionName);
    }
	
	public Session getDefaultSession() {
		return defaultSession;
	}


	// CONNECTION
	
	public Connection getConnection() {
		return connection;
	}

	/**
	 * Schließt die Verbindung von connection
	 * @throws JMSException
	 */
	public void closeConnection() throws JMSException {
		connection.close();
	}


}

