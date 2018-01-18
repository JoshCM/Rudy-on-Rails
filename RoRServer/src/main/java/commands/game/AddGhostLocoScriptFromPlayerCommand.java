package commands.game;

import java.util.UUID;

import commands.base.CommandBase;
import communication.MessageInformation;
import helper.ScriptFileWriter;
import models.game.Script;
import models.session.GameSession;
import models.session.RoRSession;

public class AddGhostLocoScriptFromPlayerCommand extends CommandBase {
	private UUID playerId;
	private String scriptName;
	private String scriptContent;
	
	public AddGhostLocoScriptFromPlayerCommand(RoRSession session, MessageInformation messageInfo) {
		super(session, messageInfo);
		
		playerId = messageInfo.getValueAsUUID("playerId");
		scriptName = messageInfo.getValueAsString("scriptName");
		scriptContent = messageInfo.getValueAsString("scriptContent");
	}

	@Override
	public void execute() {
		String playerIdAsString = playerId.toString().replace("-", "_");
		String correctedScriptName = scriptName.replace(" ", "_");
		String filename = "ghostloco_" + playerIdAsString + "_" + correctedScriptName;
		// Python-Script wird gespeichert
		ScriptFileWriter.writeStringToFile(filename, scriptContent);
		
		GameSession gameSession = (GameSession)session;
		Script script = new Script(gameSession.getSessionName(), scriptName, filename, playerId);
		gameSession.getScripts().addGhostLocoScript(script);
	}
}
