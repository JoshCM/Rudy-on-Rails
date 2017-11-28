package models;

public class Player {
    private String id;     //Das ist ebenfalls die ClientID
    private String name;

    public Player (String id, String name) {
        this.id = id;
        this.name = name;
        System.out.println("player wurde erstellt: Name " + this.name + " id: " + this.id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
