package starter;

import thread.GameStarterThread;
import thread.GetInputThread;

import java.util.ArrayList;
import java.util.List;

public class GameStarter {
    private List<String> inputCommand;
    private List<Boolean> gameOver;

    public GameStarter(){
        this.inputCommand = new ArrayList<>();
        this.gameOver = new ArrayList<>();

        this.inputCommand.add("a");
        this.gameOver.add(false);
    }

    public void run(){
        GameStarterThread gameStarterThread = new GameStarterThread(inputCommand, gameOver);
        GetInputThread getInputThread = new GetInputThread(inputCommand, gameOver);
        gameStarterThread.start();
        getInputThread.start();
    }
}
