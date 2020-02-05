package thread;

import java.util.List;
import java.util.Scanner;

public class GetInputThread extends Thread {
    private List<String> inputCommand;
    private List<Boolean> gameOver;

    public GetInputThread(List<String> inputCommand, List<Boolean> gameOver){
        this.inputCommand = inputCommand;
        this.gameOver = gameOver;
    }

    public void run(){
        getInput();
    }

    private synchronized void getInput() {
        Scanner scanner = new Scanner(System.in);
        String command;
        while(!this.gameOver.get(0)){
            command = scanner.nextLine();
            if(command.equals("W") || command.equals("w")
                    || command.equals("S") || command.equals("s")
                    || command.equals("A") || command.equals("a")
                    || command.equals("D") || command.equals("d")){
                this.inputCommand.set(0, command);
            }
        }
    }
}
