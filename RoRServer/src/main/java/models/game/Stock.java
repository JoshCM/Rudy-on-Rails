package models.game;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.apache.log4j.Logger;
import com.google.gson.JsonObject;
import communication.MessageInformation;
import communication.queue.receiver.QueueReceiver;
import models.session.RoRSession;

/**
 * Lager einer Trainstation Besitzt eine Ausrichtung, eine TrainstationId und
 * eine Liste von Containers
 *
 */
public class Stock extends InteractiveGameObject implements PlaceableOnSquare {
	static Logger log = Logger.getLogger(QueueReceiver.class.getName());
	private Compass alignment;
	private UUID trainstationId;
	private List<Resource> resources = new ArrayList<Resource>();

	public Stock(String sessionName, Square square, UUID trainstationId, Compass alignment) {
		super(sessionName, square);
		setAlignment(alignment);
		setTrainstationId(trainstationId);

		// zu testzwecken
		// addResource(new Coal(sessionName, square));
		notifyCreatedStock();
	}

	public Stock(String sessionName, Square square, UUID trainstationId, UUID id, Compass alignment) {
		super(sessionName, square, id);
		setAlignment(alignment);
		setTrainstationId(trainstationId);

		// zu testzwecken
		// addResource(new Coal(sessionName, square));
		notifyCreatedStock();
	}

	private void notifyCreatedStock() {
		MessageInformation messageInfo = new MessageInformation("CreateStock");
		messageInfo.putValue("stockId", getId());
		messageInfo.putValue("squareId", getSquareId());
		messageInfo.putValue("xPos", getXPos());
		messageInfo.putValue("yPos", getYPos());

		List<JsonObject> jsonResources = new ArrayList<JsonObject>();
		for (Resource resource : resources) {
			JsonObject json = new JsonObject();
			json.addProperty("resourceId", resource.getId().toString());
			jsonResources.add(json);
		}
		messageInfo.putValue("resourceIds", jsonResources);
		notifyChange(messageInfo);
	}

	@Override
	public PlaceableOnSquare loadFromMap(Square square, RoRSession session) {
		Stock stock = (Stock) square.getPlaceableOnSquare();
		Stock newStock = new Stock(session.getName(), square, stock.getTrainstationId(), stock.getId(),
				stock.getAlignment());

		// der sessionName muss neu gesetzt werden, damit der Observer Änderungen dieses
		// Objekts mitbekommen kann
		newStock.setName(session.getName());

		log.info("Stock erstellt: " + newStock.toString());

		// die Trainstation die den Stock beinhaltet muss den neuen Stock gesetzt
		// bekommen,#
		// sonst hat der Stock der Trainstation keine Observer
		((Trainstation) session.getMap().getPlaceableOnSquareById(getTrainstationId())).setStock(newStock);

		return newStock;
	}

	public UUID getTrainstationId() {
		return trainstationId;
	}

	public void setTrainstationId(UUID trainstationId) {
		this.trainstationId = trainstationId;
	}

	public void rotate(boolean right) {
		int newIndex;

		if (right) {
			newIndex = ((this.alignment.ordinal() + 1) % Compass.values().length);
		} else {
			newIndex = ((this.alignment.ordinal() - 1) % Compass.values().length);
			if (newIndex < 0) {
				newIndex += Compass.values().length;
			}
		}

		alignment = Compass.values()[newIndex];
		notifyStockAlignmentUpdated();
	}

	private void notifyStockAlignmentUpdated() {
		MessageInformation messageInformation = new MessageInformation("UpdateAlignmentOfStock");
		messageInformation.putValue("id", this.getId());
		messageInformation.putValue("alignment", this.alignment.toString());
		notifyChange(messageInformation);
	}

	public Compass getAlignment() {
		return alignment;
	}

	public void setAlignment(Compass alignment) {
		this.alignment = alignment;
	}

	public void changeSquare(Square newSquareOfStock) {
		this.setSquareId(newSquareOfStock.getId());
		this.setXPos(newSquareOfStock.getXIndex());
		this.setYPos(newSquareOfStock.getYIndex());
	}

	public List<Resource> getResources() {
		return resources;
	}

	public void addResource(Resource resource) {
		this.resources.add(resource);
	}

	@Override
	public void specificUpdate() {
		// TODO Auto-generated method stub

	}

}
