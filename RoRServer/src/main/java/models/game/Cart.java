package models.game;

import exceptions.InvalidModelOperationException;

/**
 * 
 * @author Isabel Rott, Michelle Le
 * Klasse fuer ein Cart (Waggon), das an einer Loco haengt
 * Auf einem Cart koennen Container geladen werden
 */
public class Cart extends InteractiveGameObject implements PlaceableOnRail {
	
	private Resource resource;

	/**
	 * Konstruktor eines Carts
	 * @param square auf dem der Wagon steht wird mitgegeben
	 */
	public Cart(String sessionName, Square square) {
		super(sessionName,square);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Cart mit einer Ressource beladen
	 * @param resource zu beladene Resource
	 */
	public void loadResourceOntoCart(Resource resource) {
		if (resource != null) {
			this.resource = resource;
		} else {
			throw new InvalidModelOperationException("Cart bereits beladen");
		}
	}
	
	/**
	 * Cart wird geleert
	 */
	public void removeResourceFromCart() {
		resource = null;
	}
	
	public Resource getResource() {
		return resource;
	}
	
	/**
	 * Entfernt die Ressource auf dem Cart und gibt diese zurück
	 * @return Ressource auf auf dem Cart
	 */
	public Resource unloadResourceFromCart() {
		Resource unloadedResource = resource;
		resource = null;
		return unloadedResource;
	}

}
