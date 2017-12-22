package models.game;

public abstract class Resource extends InteractiveGameObject{
	
	protected int quantity;

	protected Resource(String sessionName, Square square) {
		super(sessionName, square);
		
	}

	protected int getQuantity() {
		return quantity;
	}

	protected void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
