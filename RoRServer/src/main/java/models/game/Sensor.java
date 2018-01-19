package models.game;

import java.util.UUID;

import communication.MessageInformation;

/**
 * Model für Sensor
 * @author Andreas Pöhler
 *
 */
public class Sensor extends TickableGameObject {
	
	private UUID railId;

	public Sensor(String sessionName, UUID railId) {
		super(sessionName);
		this.railId = railId;
		notifySensorActivated();		
	}

	@Override
	public void specificUpdate() {
				
	}
	
	public void notifySensorActivated() {
		MessageInformation message = new MessageInformation("UpdateSensor");
		message.putValue("railId", railId);
		notifyChange(message);		
	}
}
