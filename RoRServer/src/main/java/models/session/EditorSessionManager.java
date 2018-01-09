package models.session;

import models.game.Map;
import models.game.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import models.config.GameSettings;

/**
 * Hier werden alle EditorSessions erzeugt und gehalten
 * EditorSessions duerfen ausschliesslich ueber diesen Manager erzeugt werden!
 * PATTERN: Singleton
 * TODO: Wenn dem so ist, müssten hier auch die Stati überprüft und aktualisiert werden.
 */
public class EditorSessionManager {

    private static EditorSessionManager editorSessionManager;
    private HashMap<String, EditorSession> editorSessionMap = new HashMap<>();

    private EditorSessionManager() {
    }

    public static EditorSessionManager getInstance() {
        if (editorSessionManager == null) {
            editorSessionManager = new EditorSessionManager();
        }
        return editorSessionManager;
    }

    public EditorSession createEditorSession(String sessionName, Map map, Player hostPlayer) {
        EditorSession editorSession = new EditorSession(sessionName, map, hostPlayer);
        editorSessionMap.put(editorSession.getSessionName(), editorSession);
        return editorSession;
    }

    public EditorSession createEditorSession(Map map, Player hostPlayer) {
        return createEditorSession(UUID.randomUUID().toString(), map, hostPlayer);
    }

    public EditorSession createEditorSession(String sessionName, Map map) {
        EditorSession editorSession = new EditorSession(sessionName, map);
        return editorSessionMap.put(editorSession.getSessionName(), editorSession);
    }

    public EditorSession createEditorSession(Map map) {
        return createEditorSession( UUID.randomUUID().toString(), map);
    }

    public EditorSession createEditorSession() {
        Map map = new Map(GameSettings.DEF_MAP_NAME);
        return createEditorSession(map);
    }

    public void removeEditorSession(EditorSession editorSession) {
        editorSessionMap.remove(editorSession.getSessionName());
    }

    public EditorSession getEditorSessionByName(String name) {
        return editorSessionMap.get(name);
    }

    public List<EditorSession> getEditorSessionsAsList() {
        List<EditorSession> result = new ArrayList<EditorSession>();
        for (EditorSession session : editorSessionMap.values()) {
            result.add(session);
        }
        return result;
    }
}
