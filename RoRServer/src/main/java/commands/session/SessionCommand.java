package commands.session;



public interface SessionCommand {
    void execute(String sessionName, String command, Object argument);
}
