package models.game;

import exceptions.InvalidModelOperationException;

/**
 * 
 * @author Isabel Rott, Michelle Le
 * Klasse fuer ein Cart (Waggon), das an einer Loco haengt
 * Auf einem Cart koennen Container geladen werden
 */
public class Cart extends InteractiveGameObject implements PlaceableOnRail {
	
	private Container container;

	/**
	 * Konstruktor eines Carts
	 * @param square auf dem der Wagon steht wird mitgegeben
	 */
	public Cart(String sessionName, Square square) {
		super(sessionName,square);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Container mit einer Ressource beladen
	 * @param resource zu beladene Resource
	 */
	public void loadResourceIntoContainer(Resource resource) {
		if (container.getResource() != null) {
			container.setResource(resource);
		} else {
			throw new InvalidModelOperationException("Container bereits voll.");
		}
	}
	
	/**
	 * Container wird geleert
	 */
	public void unloadResourceFromContainer() {
		container.setResource(null);
	}

}
