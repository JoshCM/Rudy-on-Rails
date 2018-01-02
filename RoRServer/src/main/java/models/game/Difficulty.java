package models.game;

/**
 * Enum für Schwierigkeit des Spiels
 * Hier können weitere Einstellungen für das Spiel vorgenommen werden (Start-Ressourcen, Punkte für Sieg etc...)
 * Muss noch in die GameSession integriert werden
 * @author Andreas Pöhler
 *
 */
public enum Difficulty {
	
	EASY(15.0), 
	MEDIUM(10.0), 
	HARD(5.0);
	
	private Double chanceToSpawnResource;
	
	Difficulty(Double chanceToSpawnResource){
		this.chanceToSpawnResource = chanceToSpawnResource;
	}
	
	public Double getChanceToSpawnResource() {
		return chanceToSpawnResource;
	}

}
