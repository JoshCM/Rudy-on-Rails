package models.session;

import java.util.ArrayList;
import java.util.UUID;

import communication.MessageInformation;
import communication.dispatcher.GameSessionDispatcher;
import communication.queue.receiver.ReceiverQueue;
import communication.topic.SenderTopic;
import models.base.InterActiveGameModel;
import models.game.Loco;
import models.game.Map;
import models.game.Player;
import models.game.TickableGameObject;
import states.RoRState;

import static models.config.GameSettings.TICKRATE;

/**
 * Oberklasse vom Game-Modus.
 * Haelt die Map und die Liste von verbundenen Playern
 * Erhaelt ueber einen ReceiverQueue Anfragen von Clients, die mit der GameSession verbunden sind
 */
public class GameSession extends RoRSession {
    private Thread tickingThread;
    private long lastTimeUpdatedInNanoSeconds;
    private Ticker ticker;
    private ArrayList<Loco> locomotives = new ArrayList<>();

    public GameSession(String sessionName, Map map, Player hostPlayer) {
        super(sessionName, map, hostPlayer);
        sessionDispatcher = new GameSessionDispatcher();
        receiverQueue = new ReceiverQueue(sessionName, sessionDispatcher);
        senderTopic = new SenderTopic(sessionName);
        ticker = new Ticker();
        setState(RoRState.READYTOSTART);
    }

    public GameSession(String sessionName, Map map) {
        super(sessionName, map, null);
        setState(RoRState.NOTENOUGHPLAYERS);
    }


    /**
     * startet den Thread der für das Ticking verantwortlich ist
     * und ruft die tick()-Methode der Ticker-Klasse auf
     */
    private void startTickingThread() {
        tickingThread = new Thread() {
            @Override
            public void run() {
                while (GameSession.this.getState(RoRState.RUNNING)) {
                    if (lastTimeUpdatedInNanoSeconds != 0)
                        ticker.tick(System.nanoTime() - lastTimeUpdatedInNanoSeconds);
                    lastTimeUpdatedInNanoSeconds = System.nanoTime();
                    try {
                        Thread.sleep(TICKRATE);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        tickingThread.start();
    }

    /**
     * stoppt den TickingThread
     */
    public void stopTickingThread() {
        setState(RoRState.STOPPED);
    }


    /**
     * Fügt dem Ticker eine Collection von TickableGameObjects hinzu
     *
     * @param tgos
     */
    public void addAll(TickableGameObject... tgos) {
        for (TickableGameObject tgo : tgos) {
            ticker.registerObserver(tgo);
        }
    }

    /**
     * Entfernt ein TickableGameObject  aus der Liste der Ticker-Observer
     *
     * @param tgo
     */
    public void remove(TickableGameObject tgo) {
        ticker.unregisterObserver(tgo);
    }

    /**
     * Sucht Locomotive über die playerId heraus, null falls playerId nicht vorhanden
     *
     * @param playerId
     * @return
     */
    public Loco getLocomotiveByPlayerId(UUID playerId) {
        for (Loco loc : locomotives) {
            if (loc.getPlayerId().toString().equals(playerId.toString())) {
                return loc;
            }
        }
        return null;
    }

    /**
     * Fügt Locotmotive der ArrayList hinzu
     *
     * @param locomotive
     */
    public void addLocomotive(Loco locomotive) {

        if (locomotive != null) {
            this.locomotives.add(locomotive);
            ticker.registerObserver(locomotive);
        }
    }

    public void startGame() {
        setState(RoRState.RUNNING);
        MessageInformation messageInfo = new MessageInformation("StartGame");
        this.startTickingThread();
        notifyObservers(this);
    }


    @Override
    public void update(InterActiveGameModel o, Object arg) {

        update(o, arg);
        // Was macht GameSession, wenn Sie geupdatet wurde.
        // Nachricht erstellen und an Clients schicken
    }

}









