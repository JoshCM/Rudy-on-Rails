package models;

public class MainModels {
	
	public static void main(String [] args) {
		
		Map map = Map.getInstance();
		map.fillMap();
		
		
		for(int i= 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				System.out.print(map.squares[i][j].id);
			}
		
		}
	}

}
