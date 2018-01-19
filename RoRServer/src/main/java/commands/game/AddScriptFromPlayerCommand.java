package commands.game;

import java.util.UUID;

import commands.base.CommandBase;
import communication.MessageInformation;
import helper.ScriptFileWriter;
import models.game.Script;
import models.session.GameSession;
import models.session.RoRSession;

/**
 * Ein vom Client geschicktes Python-Script wird als Datei gespeichert 
 * Dieses wird in einem neuen Script-Objekt verlinkt und den GhostLocoScripts hinzugefügt
 */
public class AddScriptFromPlayerCommand extends CommandBase {
	private UUID playerId;
	private String scriptName;
	private String scriptType;
	private String scriptContent;
	
	public AddScriptFromPlayerCommand(RoRSession session, MessageInformation messageInfo) {
		super(session, messageInfo);
		
		playerId = messageInfo.getValueAsUUID("playerId");
		scriptName = messageInfo.getValueAsString("scriptName");
		scriptType = messageInfo.getValueAsString("scriptType");
		scriptContent = messageInfo.getValueAsString("scriptContent");
	}

	@Override
	public void execute() {
		String playerIdAsString = playerId.toString().replace("-", "_");
		String newIdAsString = UUID.randomUUID().toString().replace("-", "_");
		// Eindeutiger Filename wird generiert aus playerId und einer zufälligen UUID
		String filename = "ghostloco_" + playerIdAsString + "_" + newIdAsString;
		// Python-Script wird gespeichert
		ScriptFileWriter.writeStringToFile(filename, scriptContent);
		
		GameSession gameSession = (GameSession)session;
		Script script = new Script(gameSession.getSessionName(), scriptName, scriptType, filename, playerId);
		gameSession.getScripts().addScript(script);
	}
}
