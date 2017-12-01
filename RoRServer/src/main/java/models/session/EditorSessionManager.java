package models.session;

import models.editor.EditorSession;

import java.util.HashMap;
import java.util.UUID;

public class EditorSessionManager {

    private static EditorSessionManager editorSessionManager;
    private HashMap<UUID, EditorSession> editorSessionMap = new HashMap<>();

    private EditorSessionManager(){}

    public static EditorSessionManager getInstance(){
        if(editorSessionManager == null) {
            editorSessionManager = new EditorSessionManager();
        }
        return editorSessionManager;
    }

    //TODO: BaseModelID über EditorSession abrufen anstatt von random UUID
    public EditorSession createNewEditorSession(String editorName){
        EditorSession editorSession = new EditorSession(editorName);
        editorSessionMap.put(UUID.randomUUID(), editorSession);
        return editorSession;
    }

    //TODO: BaseModelID über EditorSession abrufen anstatt von random UUID
    public void removeEditorSession(EditorSession editorSession){
        editorSessionMap.remove(UUID.randomUUID());
    }
    public EditorSession getEditorSession(){
        EditorSession editorArray [];
        if(!editorSessionMap.values().isEmpty() ) {
            editorArray =  (EditorSession[]) editorSessionMap.values().toArray();
            return editorArray[0];
        }
        return null;
    }

}
