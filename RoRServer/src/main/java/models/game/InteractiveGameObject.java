package models.game;

import java.util.UUID;

import models.base.ModelBase;
import models.session.RoRSession;

/**
 * Abstrakte Klasse fuer alle Objekte, mit denen interagiert werden kann
 *
 */
public abstract class InteractiveGameObject extends ModelBase {
	protected final String className;
	private int xPos; // So lange, bis nur noch die id ausreicht
	private int yPos; // So lange, bis nur noch die id ausreicht
	protected Square square;
    
	public InteractiveGameObject(String sessionName, Square square) {
		super(sessionName);
		this.className = getClass().getName();
		this.xPos = square.getXIndex();
		this.yPos = square.getYIndex();
		this.square = square;
	}
	
	public String getClassName() {
		return className;
	}
	
	public int getXPos() {
		return xPos;
	}
	
	public int getYPos() {
		return yPos;
	}
	
	public UUID getSquareId() {
		return this.square.getId();
	}
	
	public void setXPos(int xPos) {
		this.xPos = xPos;
	}
	public void setYPos(int yPos) {
		this.yPos = yPos;
	}
}
