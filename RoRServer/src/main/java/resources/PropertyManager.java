package resources;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Der PropertyManager wird genutzt, um Konstanten aus der config.properties zu laden
 */
public class PropertyManager {

	private final static String PROPERTIES_PATH = "src\\main\\java\\resources\\config.properties";
	private static Properties properties = new Properties();

	static {
		try {
			properties.load(new FileInputStream(PROPERTIES_PATH));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getProperty(String key) {
		return properties.getProperty(key);
	}
}
