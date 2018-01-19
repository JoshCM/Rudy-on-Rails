package helper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ScriptFileWriter {
	public static void writeStringToFile(String filename, String content) {
		BufferedWriter writer = null;
		try {
			String fullFilename = "python_scripts\\ghostloco\\" + filename + ".py";
			writer = new BufferedWriter(new FileWriter(fullFilename));
			writer.write(content);
		} catch (IOException e) {
		} finally {
			try {
				if (writer != null)
					writer.close();
			} catch (IOException e) {
			}
		}
	}
}
