package models.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.apache.log4j.Logger;

import communication.MessageInformation;
import models.helper.CompassHelper;
import models.session.EditorSessionManager;
import models.session.GameSession;
import models.session.RoRSession;

public class Mine extends TickableGameObject implements PlaceableOnRail {
	private Logger log = Logger.getLogger(Mine.class.getName());
	private List<Resource> resources = new ArrayList<Resource>();
	private UUID railId;
	private Compass alignment;
	private final int maxNumberOfResource = 10;
	private Square square;
	private long timeDeltaCounter = 0;// Summe der Zeit zwischen den Ticks
	private final long SEC_IN_NANO = 1000000000;
	private Resource res = null;
	protected RoRSession session;
	int i=1;
	

	public Mine(String sessionName, Square square, Compass alignment, UUID railId) {
		super(sessionName, square);
		this.square = square;
		this.alignment = alignment;
		this.railId = railId;
		notifyCreatedMine();
	}

	public Compass getAlignment() {
		return alignment;
	}

	public void setAlignment(Compass alignment) {
		this.alignment = alignment;
	}

	@Override
	public PlaceableOnRail loadFromMap(Square square, RoRSession session) {

		// Neue Mine erstellen und damit an den Client schicken
		Mine newMine = new Mine(session.getName(), square, alignment, railId);
		((GameSession) session).addMine(newMine);
		

		// sessionName neu setzen, damit Observer �nderung dieses Objekts mitbekommen
		// kann
		newMine.setName(session.getName());
		Rail rail = (Rail) session.getMap().getPlaceableById(railId);
		rail.setPlaceableOnRail(newMine);

		return newMine;
	}

	/**
	 * Methode zum Drehen der Mine nach Links (Dreht die Rail darunter gleich mit)
	 */
	public void rotateLeft() {

		// Das Rail wird direkt mit verschoben
		Square square = EditorSessionManager.getInstance().getEditorSessionByName(sessionName).getMap()
				.getSquareById(getSquareId());
		Rail rail = (Rail) square.getPlaceableOnSquare();
		rail.rotate(false);
		alignment = CompassHelper.rotateCompass(false, alignment);
		notifyAlignmentUpdated();

	}

	/**
	 * Methode zum Drehen der Mine nach Rechts (Dreht die Rail darunter gleich mit)
	 */
	public void rotateRight() {

		// Das Rail wird direkt mit verschoben
		Square square = EditorSessionManager.getInstance().getEditorSessionByName(sessionName).getMap()
				.getSquareById(getSquareId());
		Rail rail = (Rail) square.getPlaceableOnSquare();
		rail.rotate(true);
		alignment = CompassHelper.rotateCompass(true, alignment);
		notifyAlignmentUpdated();
	}

	/**
	 * Methode zum Erstellen einer Nachrichtm wenn eine neue Mine erstellt wurde
	 */
	private void notifyCreatedMine() {
		MessageInformation message = new MessageInformation("CreateMine");
		message.putValue("xPos", getXPos());
		message.putValue("yPos", getYPos());
		message.putValue("mineId", getId());
		message.putValue("squareId", getSquareId());
		message.putValue("alignment", alignment.toString());
		notifyChange(message);

	}

	/**
	 * Schickt eine Nachricht an den Client, wenn sich die Richtung der Mine
	 * ge�ndert hat
	 */
	private void notifyAlignmentUpdated() {
		MessageInformation message = new MessageInformation("UpdateAlignmentOfMine");
		message.putValue("alignment", alignment);
		message.putValue("mineId", getId());
		message.putValue("railId", railId.toString());
		notifyChange(message);
	}

	@Override
	public void specificUpdate() {
		this.timeDeltaCounter += timeDeltaInNanoSeconds;
		if(this.timeDeltaCounter>=SEC_IN_NANO*4) {
			this.timeDeltaCounter=0;
			if (resources.size() < maxNumberOfResource) {
				res= minedResource();
				resources.add(res);
				log.info("res=" + res.getName()+i);
				i+=1;
			}
		}

	}

	public Resource minedResource() {
		Random random = new Random();
		if (random.nextFloat() <= 0.7) {
			res = new Coal(this.sessionName, this.square);
		} else {
			res = new Gold(this.sessionName, this.square);
		}
		return res;

	}
	
	public UUID getRailId() {
		return railId;
		
		
	}
}