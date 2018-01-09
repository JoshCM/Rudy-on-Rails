package models.base;

public abstract class PlaceableModel extends InterActiveGameModel {
    int x, y;

    public PlaceableModel(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * TODO: Wahrscheinlich verursacht dieser Standardkonstruktor sehr viele Fehler!
     */
    public PlaceableModel() {
        this.x = -1;
        this.y = -1;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
