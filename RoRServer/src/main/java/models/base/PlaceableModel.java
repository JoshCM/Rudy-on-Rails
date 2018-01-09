package models.base;

public abstract class PlaceableModel extends ObservableModel {
    int x, y;

    public PlaceableModel(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
