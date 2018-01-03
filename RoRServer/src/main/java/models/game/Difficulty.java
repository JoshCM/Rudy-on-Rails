package models.game;

/**
 * Enum f�r Schwierigkeit des Spiels
 * Hier k�nnen weitere Einstellungen f�r das Spiel vorgenommen werden (Start-Ressourcen, Punkte f�r Sieg etc...)
 * Muss noch in die GameSession integriert werden
 * @author Andreas P�hler
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
