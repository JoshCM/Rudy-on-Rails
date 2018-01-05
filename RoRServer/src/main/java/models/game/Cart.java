package models.game;

import exceptions.InvalidModelOperationException;

/**
 * @author Isabel Rott, Michelle Le
 * Klasse fuer ein Cart (Waggon), das an einer Loco haengt
 * Auf einem Cart koennen Container geladen werden
 */
public class Cart extends InteractiveGameObject implements PlaceableOnRail {

    private Resource resource;

    /**
     * TODO: Könnte folgende Parameter haben: Startposition, Zug, Spielerzugehörigkeit, Ressourcen, o.ä. -> MÜSSEN implementiert werden.
     * Konstruktor eines Carts
     *
     * @param
     */
    public Cart() {

    }

    /**
     * Cart mit einer Ressource beladen
     *
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
     *
     * @return Ressource auf auf dem Cart
     */
    public Resource unloadResourceFromCart() {
        Resource unloadedResource = resource;
        resource = null;
        return unloadedResource;
    }

}
