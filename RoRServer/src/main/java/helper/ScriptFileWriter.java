package helper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import models.scripts.Script.ScriptType;

public class ScriptFileWriter {
	public static void writeStringToFile(ScriptType scriptType, String filename, String content) {
		BufferedWriter writer = null;
		try {
			String fullFilename = "python_scripts\\" + scriptType.toString().toLowerCase() + "\\" +  filename + ".py";
			writer = new BufferedWriter(new FileWriter(fullFilename));
			writer.write(content);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (writer != null)
					writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
