package models.game;

public class Coal extends Resource{
	
	private String name = "Coal";

	public Coal(String sessionName, Square square) {
		super(sessionName, square);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
