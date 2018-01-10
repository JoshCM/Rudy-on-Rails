package commands.editor;

import commands.base.Action;
import commands.base.SessionCommandHandler;

/**
 * Dispatches Commands and sends them to the right Command
 */
public class EditorCommandHandler implements SessionCommandHandler {

    @Override
    public void handle(Action action, String json) {
        if (action == Action.CREATE) handleCreate(json);
        if (action == Action.READ) handleRead(json);
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
