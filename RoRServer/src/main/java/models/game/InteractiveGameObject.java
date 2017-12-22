package models.game;

import java.util.UUID;

import models.base.ModelBase;

/**
 * Abstrakte Klasse fuer alle Objekte, mit denen interagiert werden kann
 *
 */
public abstract class InteractiveGameObject extends ModelBase {
	protected final String className;
	private UUID squareId;
	private int xPos; // So lange, bis nur noch die id ausreicht
	private int yPos; // So lange, bis nur noch die id ausreicht
    
	public InteractiveGameObject(String sessionName, Square square) {
		super(sessionName);
		this.className = getClass().getName();
		this.squareId = square.getId();
		this.xPos = square.getXIndex();
		this.yPos = square.getYIndex();
	}
	
	public InteractiveGameObject(String sessionName, Square square, UUID id) {
		super(sessionName, id);
		this.className = getClass().getName();
		squareId = square.getId();
		this.xPos = square.getXIndex();
		this.yPos = square.getYIndex();
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
		return squareId;
	}
	
	public void setXPos(int xPos) {
		this.xPos = xPos;
	}
	public void setYPos(int yPos) {
		this.yPos = yPos;
	}
	
	public void updateSquare(Square s) {
		this.squareId = s.getId();
		this.setXPos(s.getXIndex());
		this.setYPos(s.getYIndex());
	}
}
