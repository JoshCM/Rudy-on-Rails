package exceptions;

import java.io.FileNotFoundException;

public class MapNotFoundException extends FileNotFoundException{
	private static final long serialVersionUID = -2967984911717078734L;

	public MapNotFoundException(String message) {
		super(message);
	}
}
