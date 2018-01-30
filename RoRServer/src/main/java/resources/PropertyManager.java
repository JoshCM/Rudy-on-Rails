package resources;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Der PropertyManager wird genutzt, um Konstanten aus der config.properties zu laden
 */
public class PropertyManager {

	private final static String PROPERTIES_NAME = "src/main/java/resources/config.properties";
	// wird anstatt PROPERTIES_NAME bei einer exportierten Jar gebraucht
	// private final static String PROPERTIES_NAME_EXPORT = "config.properties";
	private static Properties properties = new Properties();

	static {
		try {
			properties.load(new FileInputStream(PROPERTIES_NAME)); // PROPERTIES_NAME_EXPORT hier anstatt PROPERTIES_NAME
		} catch (FileNotFoundException e) {
			try {
				properties.load(new FileInputStream(String.format("%s/%s", System.getProperty("user.dir"), PROPERTIES_NAME))); // PROPERTIES_NAME_EXPORT hier anstatt PROPERTIES_NAME
			} catch (FileNotFoundException eServer) {
				eServer.printStackTrace();
			} catch (IOException eServer) {
				eServer.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getProperty(String key) {
		return properties.getProperty(key);
	}
}
