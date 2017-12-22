package models.game;

public abstract class Resource extends InteractiveGameObject{
	
	private int quantity;

	public Resource(String sessionName, Square square) {
		super(sessionName, square);
		
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
