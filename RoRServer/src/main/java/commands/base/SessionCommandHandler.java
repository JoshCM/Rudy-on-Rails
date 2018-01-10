package commands.base;

public interface SessionCommandHandler {
    void handle(Action action, String json);
    void handleCreate(String json);
    void handleRead(String json);
    void handleUpdate(String json);
    void handleDelete(String json);
}
