package models.game;

import java.util.ArrayList;

/**
 * 
 * @author Isabel Rott, Michelle Le
 * Klasse f�r eine Lok, zu der eine Reihe von Wagons gehoert
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
	
	/**
	 * steuert das spezifische verhalten der Lok 
	 * beim eintreten eines Ticks
	 */
	@Override
	public void specificUpdate() {
		// TODO Auto-generated method stub
		
	}
	
	
	//Getter und Setter
	public ArrayList<Cart> getCarts() {
		return carts;
	}

	public void setCarts(ArrayList<Cart> carts) {
		this.carts = carts;
	}

}
