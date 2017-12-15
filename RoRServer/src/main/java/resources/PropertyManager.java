package resources;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Der PropertyManager wird genutzt, um Konstanten aus der config.properties zu laden
 */
public class PropertyManager {

	private final static String PROPERTIES_NAME = "config.properties";
	private static Properties properties = new Properties();

	static {
		try {
			properties.load(new FileInputStream(PropertyManager.class.getResource(PROPERTIES_NAME).getPath()));
		} catch (FileNotFoundException e) {
			try {
				properties.load(new FileInputStream(String.format("%s/%s", System.getProperty("user.dir"), PROPERTIES_NAME)));
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
