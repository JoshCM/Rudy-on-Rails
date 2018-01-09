package states;

public interface GameState {
    void handleInput(GameState gamestate);
    GameState getState();
}
