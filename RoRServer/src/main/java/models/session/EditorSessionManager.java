package models.session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Hier werden alle EditorSessions erzeugt und gehalten
 * EditorSessions duerfen ausschliesslich ueber diesen Manager erzeugt werden!
 */
public class EditorSessionManager {

    private static EditorSessionManager editorSessionManager;
    private HashMap<String, EditorSession> editorSessionMap = new HashMap<>();

    private EditorSessionManager(){}

    public static EditorSessionManager getInstance(){
        if(editorSessionManager == null) {
            editorSessionManager = new EditorSessionManager();
        }
        return editorSessionManager;
    }
    /**
     * EditorSession mit editorName wird erstellt und wird mit der UUID in eine HashMap gespeichert.
     * @param editorName
     * @return
     */
    public EditorSession createNewEditorSession(String editorName){
        EditorSession editorSession = new EditorSession(editorName);
        editorSessionMap.put(editorName, editorSession);
        return editorSession;
    }
    /**
     * Entfernt spezifische EditorSession aus HashMap
     * @param editorSession
     */
    public void removeEditorSession(EditorSession editorSession){
        editorSessionMap.remove(editorSession.getSessionName());
    }
    
    //aktuell wird immer die erste EditorSession in der HashMap zurueckgegeben
    //TODO: sobald wir eine Lobby haben in der man sich einen Editor aussuchen kann, muss auch dieser zurueckgegeben werden.
    public EditorSession getFirstEditorSession(){
        EditorSession editor;
        if(!editorSessionMap.values().isEmpty() ) {
            editor =  (EditorSession) editorSessionMap.values().toArray()[0];
            return editor;
        }
        return null;
    }

    public EditorSession getEditorSessionByName(String name) {
    	return editorSessionMap.get(name);
    }

    
    public List<EditorSession> getEditorSessionsAsList(){
    	List<EditorSession> result = new ArrayList<EditorSession>();
    	for(EditorSession session : editorSessionMap.values()) {
    		result.add(session);
    	}
    	return result;
    }
}
