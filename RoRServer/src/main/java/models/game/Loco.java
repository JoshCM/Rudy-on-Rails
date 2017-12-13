package models.game;

import java.util.ArrayList;

/**
 * 
 * @author Isabel Rott, Michelle Le
 * Klasse für eine Lok, zu der eine Reihe von Wagons gehoert
 */
public class Loco extends TickableGameObject implements PlaceableOnRail  {

	private ArrayList<Cart> carts;
	
	/**
	 * Konstruktor
	 * @param square auf dem die Lok steht wird mitgegeben
	 */

	public Loco(String sessionName, Square square) {
		super(sessionName,square);
		this.setCarts(new ArrayList<Cart>());
	}

	public ArrayList<Cart> getCarts() {
		return carts;
	}

	public void setCarts(ArrayList<Cart> carts) {
		this.carts = carts;
	}

	@Override
	public void specificUpdate() {
		// TODO Auto-generated method stub
		
	}

}
