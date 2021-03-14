package engine;

import field.FieldImpl;
import snake.SnakeImpl;
import thread.GetInputThread;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class EngineImpl implements Engine {
    private static final int DIMENSION_X = 9;
    private static final int DIMENSION_Y = 16;
    private Scanner scanner;
    private FieldImpl field;
    private SnakeImpl snake;
    private boolean collectibleOnField;
    private char lastCommand;
    private boolean wonGame;
    private List<String> inputCommand;
    private List<Boolean> gameOver;
    private List<Boolean> gameWon;


    public EngineImpl() {
        this.field = new FieldImpl(DIMENSION_X, DIMENSION_Y);
        this.snake = new SnakeImpl();
        this.wonGame = false;
        this.scanner = new Scanner(System.in);

        this.inputCommand = new ArrayList<>();
        this.gameOver = new ArrayList<>();
        this.gameWon = new ArrayList<>();

        this.inputCommand.add("a");
        this.gameOver.add(false);
        this.gameWon.add(false);
    }

    @Override
    public void run() {
        GetInputThread getInputThread = new GetInputThread(inputCommand, gameOver, gameWon);
        getInputThread.start();

        spawnSnake();
        spawnCollectible();

        this.collectibleOnField = true;
        this.lastCommand = 'a';

        while (true) {
            printField();
            System.out.println();

            this.wonGame = this.field.checkIfWon();
            if(this.wonGame){
                System.out.println("You win");
                this.gameWon.set(0, true);
                break;
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                moveSnake(this.inputCommand.get(0));
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                break;
            }

            if (!this.collectibleOnField) {
                this.spawnCollectible();
                this.collectibleOnField = true;
            }

        }
    }

    private void moveSnake(String command) {
        int headPositionR;
        int headPositionC;
        boolean isCollectible;
        switch (command) {
            case "w":
            case "W":
                if (this.lastCommand == 's' || this.lastCommand == 'S') {
                    break;
                } else {
                    isCollectible = false;
                    headPositionR = this.snake.getHeadR() - 1;
                    headPositionC = this.snake.getHeadC();
                    this.field.getField()[this.snake.getHeadR()][this.snake.getHeadC()] = '@';
                    int oldSnakeHeadR = this.snake.getHeadR();
                    int oldSnakeHeadC = this.snake.getHeadC();


                    if (headPositionR == 0) {
                        this.gameOver.set(0, true);
                        throw new IllegalArgumentException("Game Over");
                    }

                    if (this.field.getField()[headPositionR][headPositionC] == '@') {
                        this.gameOver.set(0, true);
                        throw new IllegalArgumentException("Game Over");
                    }

                    this.snake.setHeadR(headPositionR);


                    if (this.field.getField()[headPositionR][headPositionC] == '$') {
                        isCollectible = true;
                    }

                    this.field.getField()[headPositionR][headPositionC] = '^';

                    this.snake.getBodyCoordinates().get(0)[0]++;
                    moveSnakeBody(oldSnakeHeadR, oldSnakeHeadC, isCollectible);

                    if (isCollectible) {
                        this.collectibleOnField = false;
                        this.snake.setSize(this.snake.getSize() + 1);
                    }

                    this.lastCommand = command.charAt(0);
                }
                break;
            case "s":
            case "S":
                if (this.lastCommand == 'w' || this.lastCommand == 'W') {
                    break;
                } else {
                    isCollectible = false;
                    headPositionR = this.snake.getHeadR() + 1;
                    headPositionC = this.snake.getHeadC();
                    this.field.getField()[this.snake.getHeadR()][this.snake.getHeadC()] = '@';
                    int oldSnakeHeadR = this.snake.getHeadR();
                    int oldSnakeHeadC = this.snake.getHeadC();

                    if (headPositionR == this.field.getDimensionX() - 1) {
                        this.gameOver.set(0, true);
                        throw new IllegalArgumentException("Game Over");
                    }

                    if (this.field.getField()[headPositionR][headPositionC] == '@') {
                        this.gameOver.set(0, true);
                        throw new IllegalArgumentException("Game Over");
                    }

                    this.snake.setHeadR(headPositionR);

                    if (this.field.getField()[headPositionR][headPositionC] == '$') {
                        isCollectible = true;
                    }

                    this.field.getField()[headPositionR][headPositionC] = 'v';

                    this.snake.getBodyCoordinates().get(0)[0]--;
                    moveSnakeBody(oldSnakeHeadR, oldSnakeHeadC, isCollectible);

                    if (isCollectible) {
                        this.collectibleOnField = false;
                        this.snake.setSize(this.snake.getSize() + 1);
                    }

                    this.lastCommand = command.charAt(0);
                }
                break;
            case "a":
            case "A":
                if (this.lastCommand == 'd' || this.lastCommand == 'D') {
                    break;
                } else {
                    isCollectible = false;
                    headPositionR = this.snake.getHeadR();
                    headPositionC = this.snake.getHeadC() - 1;
                    this.field.getField()[this.snake.getHeadR()][this.snake.getHeadC()] = '@';
                    int oldSnakeHeadR = this.snake.getHeadR();
                    int oldSnakeHeadC = this.snake.getHeadC();

                    if (headPositionC == 0) {
                        this.gameOver.set(0, true);
                        throw new IllegalArgumentException("Game Over");
                    }

                    if (this.field.getField()[headPositionR][headPositionC] == '@') {
                        this.gameOver.set(0, true);
                        throw new IllegalArgumentException("Game Over");
                    }

                    this.snake.setHeadC(headPositionC);

                    if (this.field.getField()[headPositionR][headPositionC] == '$') {
                        isCollectible = true;
                    }

                    this.field.getField()[headPositionR][headPositionC] = '<';

                    this.snake.getBodyCoordinates().get(0)[1]--;
                    moveSnakeBody(oldSnakeHeadR, oldSnakeHeadC, isCollectible);

                    if (isCollectible) {
                        this.collectibleOnField = false;
                        this.snake.setSize(this.snake.getSize() + 1);
                    }

                    this.lastCommand = command.charAt(0);
                }
                break;
            case "d":
            case "D":
                if (this.lastCommand == 'a' || this.lastCommand == 'A') {
                    break;
                } else {
                    isCollectible = false;
                    headPositionR = this.snake.getHeadR();
                    headPositionC = this.snake.getHeadC() + 1;
                    this.field.getField()[this.snake.getHeadR()][this.snake.getHeadC()] = '@';
                    int oldSnakeHeadR = this.snake.getHeadR();
                    int oldSnakeHeadC = this.snake.getHeadC();

                    if (headPositionC == this.field.getDimensionY() - 1) {
                        this.gameOver.set(0, true);
                        throw new IllegalArgumentException("Game Over");
                    }

                    if (this.field.getField()[headPositionR][headPositionC] == '@') {
                        this.gameOver.set(0, true);
                        throw new IllegalArgumentException("Game Over");
                    }

                    this.snake.setHeadC(headPositionC);

                    if (this.field.getField()[headPositionR][headPositionC] == '$') {
                        isCollectible = true;
                    }

                    this.field.getField()[headPositionR][headPositionC] = '>';

                    this.snake.getBodyCoordinates().get(0)[1]++;
                    moveSnakeBody(oldSnakeHeadR, oldSnakeHeadC, isCollectible);

                    if (isCollectible) {
                        this.collectibleOnField = false;
                        this.snake.setSize(this.snake.getSize() + 1);
                    }

                    this.lastCommand = command.charAt(0);
                }
                break;
            default:
        }
    }

    private void moveSnakeBody(int oldSnakeHeadR, int oldSnakeHeadC, boolean isCollectible) {
        List<int[]> bodyCoordinates = this.snake.getBodyCoordinates();
        int previousCoordinateR = oldSnakeHeadR;
        int previousCoordinateC = oldSnakeHeadC;

        if (!isCollectible) {
            for (int i = 1; i < bodyCoordinates.size(); i++) {
                int currentCoordinatesR = bodyCoordinates.get(i)[0];
                int currentCoordinatesC = bodyCoordinates.get(i)[1];

                bodyCoordinates.get(i)[0] = previousCoordinateR;
                bodyCoordinates.get(i)[1] = previousCoordinateC;

                previousCoordinateR = currentCoordinatesR;
                previousCoordinateC = currentCoordinatesC;

                if (i == bodyCoordinates.size() - 1) {
                    this.snake.setTailR(bodyCoordinates.get(i)[0]);
                    this.snake.setTailC(bodyCoordinates.get(i)[1]);
                }
            }

            this.field.getField()[previousCoordinateR][previousCoordinateC] = '.';
        } else {
            for (int i = 1; i < bodyCoordinates.size(); i++) {
                int currentCoordinatesR = bodyCoordinates.get(i)[0];
                int currentCoordinatesC = bodyCoordinates.get(i)[1];

                bodyCoordinates.get(i)[0] = previousCoordinateR;
                bodyCoordinates.get(i)[1] = previousCoordinateC;

                previousCoordinateR = currentCoordinatesR;
                previousCoordinateC = currentCoordinatesC;
            }

            this.snake.addBody(previousCoordinateR, previousCoordinateC);
            this.snake.setTailR(previousCoordinateR);
            this.snake.setTailC(previousCoordinateC);
        }
    }

    private String getCommand() {
        String input = this.scanner.nextLine();
        if (input.trim().isEmpty()) {
            input = getCommand();
        }
        String command = "";
        command += input.charAt(0);
        return command;
    }

    private void spawnSnake() {
        this.snake.setHeadR(3);
        this.snake.setHeadC(6);
        this.snake.setTailR(3);
        this.snake.setTailC(8);

        this.snake.addBody(3, 6);
        this.snake.addBody(3, 7);
        this.snake.addBody(3, 8);

        this.field.getField()[3][6] = '<';
        this.field.getField()[3][7] = '@';
        this.field.getField()[3][8] = '@';
    }

    private void spawnCollectible() {
        Random randomR = new Random();
        Random randomC = new Random();
        int collectibleR = 0;
        while (collectibleR == 0) {
            collectibleR = randomR.nextInt(DIMENSION_X - 2);
        }

        int collectibleC = 0;
        while (collectibleC == 0) {
            collectibleC = randomC.nextInt(DIMENSION_Y - 2);
        }

        if (field.getField()[collectibleR][collectibleC] == '.') {
            this.field.getField()[collectibleR][collectibleC] = '$';
        } else {
            spawnCollectible();
        }
    }

    private void printField() {
        for (int r = 0; r < this.field.getDimensionX(); r++) {
            for (int c = 0; c < this.field.getDimensionY(); c++) {
                System.out.print(this.field.getField()[r][c]);
                System.out.print(' ');
            }
            System.out.println();
        }
    }
}
