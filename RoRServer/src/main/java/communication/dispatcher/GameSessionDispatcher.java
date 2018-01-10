package communication.dispatcher;
import models.session.GameSession;

public class GameSessionDispatcher extends SessionDispatcher {

	public GameSessionDispatcher() {
		super( "commands.game.");
	}
}
