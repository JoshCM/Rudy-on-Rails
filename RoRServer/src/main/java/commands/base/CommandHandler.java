package commands.base;

import commands.editor.EditorCommandHandler;
import commands.game.GameCommandHandler;
import commands.lobby.LobbyCommandHandler;


/**
 * TODO: Eventuell zu Singleton machen... ODER Jede Session bekommt eigenen CommandHandler...
 * CommandHandler erhält die Commands einer Session und leitet diese dann an die richtigen Handler weiter.
 */
public class CommandHandler {
    EditorCommandHandler editorCommandHandler;
    GameCommandHandler gameCommandHandler;
    LobbyCommandHandler lobbyCommandHandler;

    public CommandHandler() {
        editorCommandHandler = new EditorCommandHandler();
        gameCommandHandler = new GameCommandHandler();
        lobbyCommandHandler = new LobbyCommandHandler();
    }

    public void handle(HandlerType handlerType, Action action, String json) {
        if (handlerType == HandlerType.EDITOR) {
            editorCommandHandler.handle(action, json);
        }

        if (handlerType == HandlerType.GAME) {
            gameCommandHandler.handle(action, json);
        }

        if (handlerType == HandlerType.LOBBY) {
            lobbyCommandHandler.handle(action, json);
        }

    }
}
