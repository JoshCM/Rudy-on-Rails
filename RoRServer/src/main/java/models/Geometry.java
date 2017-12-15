package models;

public class Geometry {
	/**
	 * Rotiert eine Koordinate um einen definierten Punkt
	 * @param x Koordinate X
	 * @param y Koordinate Y
	 * @param alpha Winkel um den gedreht wird
	 * @param centerX Koordinate X des defnierten Mittelpunkts
	 * @param centerY Koordinate Y des defnierten Mittelpunkts
	 * @return Koordinate mit X, Y
	 */
	public static Coordinate rotate(double x, double y, double alpha, double centerX, double centerY) {
		alpha = Math.toRadians(alpha);		
		double rotatedX = centerX + (x - centerX) * Math.cos(alpha) - (y - centerY) * Math.sin(alpha);
		double rotatedY = centerY + (x - centerX) * Math.sin(alpha) + (y - centerY) * Math.cos(alpha);
		return new Coordinate((int)Math.round(rotatedX), (int)Math.round(rotatedY));
	}
	

	/**
	 * Hält die neuen Koordinaten, die beim Berechnen erzeugt werden
	 */
	public static class Coordinate{
		public int x;
		public int y;
		public Coordinate(int x, int y) {
			this.x = x;
			this.y = y;
		}
		@Override
		public String toString() {
			return "Coordinate [x=" + x + ", y=" + y + "]";
		}
	}
}
