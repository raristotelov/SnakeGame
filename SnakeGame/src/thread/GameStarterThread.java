package thread;

import engine.Engine;
import engine.EngineImpl;
import java.util.List;

public class GameStarterThread extends Thread {
    private List<String> inputCommand;
    private List<Boolean> gameOver;

    public GameStarterThread(List<String> inputCommand, List<Boolean> gameOver){
        this.inputCommand = inputCommand;
        this.gameOver = gameOver;
    }

    public void run(){
        runGame();
    }

    private synchronized void runGame() {
        Engine engine = new EngineImpl(this.inputCommand, this.gameOver);

        engine.run();
    }
}
