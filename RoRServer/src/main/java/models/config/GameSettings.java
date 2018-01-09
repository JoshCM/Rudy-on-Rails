package models.config;

public class GameSettings {

    //TODO: Gehört in config.properties

    // Ressourcen
    public static final int DEF_CONTAINER_GOLD = 5;
    public static final int DEF_CONTAINER_COAL = 100;

    // Mapsettings
    public static final String DEF_MAP_NAME = "DEFMAP";
    public static final int DEF_MAPSIZE = 125;

    // Playersettings
    public static final String DEF_PLAYERNAME = "HORST OHNE NAMEN!";

    // Spielsettings
    public final static int TICKRATE = 100;

    // DEBUG Settings
    // TODO: Debug Settings gehören in eigenes File!
    public final static boolean DISPATCHER_LOGGING = true;
    public final static boolean QUEUERECEIVER_LOGGING = true;

    public final static boolean DEBUGMODE = true;

}
