package commands.game;

import commands.base.Action;
import commands.base.SessionCommandHandler;

public class GameCommandHandler implements SessionCommandHandler {
    @Override
    public void handle(Action action, String json) {
        if (action == Action.CREATE) handleCreate(json);
        if (action == Action.READ) handleCreate(json);
        if (action == Action.UPDATE) handleUpdate(json);
        if (action == Action.DELETE) handleDelete(json);
    }

    @Override
    public void handleCreate(String json) {

    }

    @Override
    public void handleRead(String json) {

    }

    @Override
    public void handleUpdate(String json) {

    }

    @Override
    public void handleDelete(String json) {

    }
}
