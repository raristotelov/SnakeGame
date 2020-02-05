import engine.Engine;
import engine.EngineImpl;
import starter.GameStarter;

public class Main {
    public static void main(String[] args) {
        //Engine engine = new EngineImpl();

        //engine.run();

        GameStarter gameStarter = new GameStarter();

        gameStarter.run();
    }
}
