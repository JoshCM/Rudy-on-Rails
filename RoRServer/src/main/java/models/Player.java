package models;

public class Player {
    private String clientid;     //Das ist ebenfalls die ClientID
    private String name;

    public Player (String id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
