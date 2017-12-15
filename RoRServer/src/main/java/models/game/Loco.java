package models.game;

import java.util.ArrayList;

import communication.MessageInformation;

/**
 * 
 * @author Isabel Rott, Michelle Le
 * Klasse fuer eine Lok, zu der eine Liste von Carts gehoert
 */
public class Loco extends TickableGameObject implements PlaceableOnRail  {

	private final long SEC_IN_NANO= 1000000000;
	private ArrayList<Cart> carts;
	private Rail rail;
	private Player player;
	private long timeDeltaCounter;//Summe der Zeit zwischen den Ticks
	private int speed;
	private Direction direction;
	private Map map;
	private Square square;
	
	/**
	 * Konstruktor einer Lok
	 * @param square auf dem die Lok steht wird mitgegeben
	 */
	public Loco(String sessionName, Square square, Player player) {
		super(sessionName,square);
		this.setCarts(new ArrayList<Cart>());
		this.player = player;
		
		//TODO: Wenn Zug Richtung implementiert ist, muss der Wagon so initialisiert werden, dass er ein Feld hinter der Lok steht 
		this.addCart(new Cart(sessionName,square));
		
		this.rail = (Rail)square.getPlaceableOnSquare();
		this.map = square.getMap();
		this.direction = rail.getFirstSection().getNode1();
		SendCreatedLocoMessage();
	}

	/**
	 * Fuege Carts zur Liste der Loco hinzu
	 * @param cart
	 */
	public void addCart(Cart cart) {
		this.carts.add(cart);
		
	}
	
	
	/**
	 * steuert das spezifische verhalten der Lok 
	 * beim eintreten eines Ticks
	 */
	@Override
	public void specificUpdate() {
		this.timeDeltaCounter += timeDeltaInNanoSeconds;
		
		if(this.timeDeltaCounter >= SEC_IN_NANO/speed*timeDeltaInNanoSeconds) {
			timeDeltaCounter = 0;
			drive();
		}
	}

	/**
	 * Überführt die Lok in das nächste mögliche feld in Fahrtrichtung
	 */
	public void drive(){
		Rail nextRail = getNextRail();
		this.direction = nextRail.getExitDirection(getDirectionNegation());
		this.rail.setPlaceableOnRail(null);
		this.rail = nextRail;
		this.rail.setPlaceableOnRail(this);
	}

	/**
	 * Gibt die nächste Rail in Fahrtrichtung zurück
	 * @return nextRail
	 */
	public Rail getNextRail(){
		switch (this.direction) {
			case NORTH:
				return (Rail) map.getSquare(square.getXIndex(),square.getYIndex()-1).getPlaceableOnSquare();
			case EAST:
				return (Rail) map.getSquare(square.getXIndex()+1,square.getYIndex()).getPlaceableOnSquare();
			case SOUTH:
				return (Rail) map.getSquare(square.getXIndex(),square.getYIndex()+1).getPlaceableOnSquare();
			case WEST:
				return (Rail) map.getSquare(square.getXIndex()-1,square.getYIndex()).getPlaceableOnSquare();
		}
		return null;
	}

    /**
     * Negiert die aktuelle Fahrtrichtung
     * Ist benötigt um einen U-Turn zu verhindern.
     * @return negated Direction
     */
	public Direction getDirectionNegation(){
		switch (this.direction) {
			case NORTH:
				return Direction.SOUTH;
			case EAST:
				return Direction.WEST;
			case SOUTH:
				return Direction.NORTH;
			case WEST:
				return Direction.EAST;
		}
		return null;
	}
	private void SendCreatedLocoMessage() {
		MessageInformation messageInfo = new MessageInformation("CreateLoco");
		messageInfo.putValue("locoId", getId());
		messageInfo.putValue("squareId", getSquareId());
		//messageInfo.putValue("railId", rail.getId());
		messageInfo.putValue("xPos", getXPos());
		messageInfo.putValue("yPos", getYPos());
		
		System.out.println("LOCO WURDE ERSTELLT; NACHRICHT WIRD ZUR�CKGESCHICKT");
		notifyChange(messageInfo);
	}
	
	//Getter und Setter
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public ArrayList<Cart> getCarts() {
		return carts;
	}

	public void setCarts(ArrayList<Cart> carts) {
		this.carts = carts;
	}

	/**
	 * Finde die Rail, auf der die Loco steht
	 * @return rail
	 */
	public Rail getRail() {
		return this.rail;
	}
	public int getSpeed() {
		return this.speed;
	}


}
