package communication.dispatcher;
import models.session.EditorSession;

public class EditorSessionDispatcher extends SessionDispatcher {
	
	public EditorSessionDispatcher(EditorSession editorSession) {
		super(editorSession, "commands.editor.");
	}
}
