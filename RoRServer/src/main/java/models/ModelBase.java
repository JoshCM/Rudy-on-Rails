package models;

import java.util.UUID;

public abstract class ModelBase implements Model{
	
	private UUID id;
	
	public ModelBase() {
		this.id = UUID.randomUUID();
	}
	
	public UUID Id() {
		return id;
	}
}
