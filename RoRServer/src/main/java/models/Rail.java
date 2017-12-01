package models;

/**
 * Klasse für Schienen, die einem Feld (Square) zugeordnet sind
 * und ein Schienenstück (= Gerade, Kurve) bzw. zwei Schienenstücke (= Kreuzung, Weiche) besitzen
 */

public class Rail extends InteractiveGameObject implements PlaceableOnSquare {
	
	protected PlaceableOnRail placeableOnRail = null;
	protected RailSection section1;
	protected RailSection section2;
    private final String className;
	
	/**
	 * Konstruktor für Geraden oder Kurven
	 */
	public Rail (Square square , RailSection section) {
		super(square);
		this.className = getClass().getName();
		this.section1 = section;
	}
	
	/**
	 * 
	 * Konstruktor für Kreuzungen oder Weichen
	 */
	public Rail (Square square, RailSection section1, RailSection section2) {
		super(square);
		this.className = getClass().getName();
		this.section1 = section1;
		this.section2 = section2;
	}
	
	public void setPlaceableOnRail (PlaceableOnRail placeableOnRail) {
		if(placeableOnRail == null){
			this.placeableOnRail = placeableOnRail;
		}
	}

	@Override
	public String getClassName() {
		// TODO Auto-generated method stub
		return className;
	}
	
	public RailSection getSection() {
		return section1;
	}
	
	
	
}
