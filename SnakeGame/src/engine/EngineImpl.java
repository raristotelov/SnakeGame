package engine;

import field.FieldImpl;
import snake.SnakeImpl;

import java.util.Random;
import java.util.Scanner;

public class EngineImpl implements Engine {
    private Scanner scanner;
    private FieldImpl field;
    private SnakeImpl snake;
    private boolean collectibleOnField;
    private char lastCommand;


    public EngineImpl() {
        this.field = new FieldImpl(9, 30);
        this.snake = new SnakeImpl();
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void run() {
        spawnSnake();
        String command;
        spawnCollectible();
        this.collectibleOnField = true;
        this.lastCommand = 'a';

        while (true) {
            printField();
            System.out.println();
            command = getCommand();
            try {
                moveSnake(command);
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


                    if (headPositionR == 0) {
                        throw new IllegalArgumentException("Game Over");
                    }

                    if (this.field.getField()[headPositionR][headPositionC] == '@') {
                        throw new IllegalArgumentException("Game Over");
                    }

                    this.snake.setHeadR(headPositionR);

                    this.snake.getBodyCoordinates().get(0)[0] =  headPositionR;

                    if (this.field.getField()[headPositionR][headPositionC] == '$') {
                        isCollectible = true;
                    }

                    this.field.getField()[headPositionR][headPositionC] = '^';

                    if (!isCollectible) {
                        setTail();
                    } else {
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

                    if (headPositionR == this.field.getDimensionX() - 1) {
                        throw new IllegalArgumentException("Game Over");
                    }

                    if (this.field.getField()[headPositionR][headPositionC] == '@') {
                        throw new IllegalArgumentException("Game Over");
                    }

                    this.snake.setHeadR(headPositionR);

                    if (this.field.getField()[headPositionR][headPositionC] == '$') {
                        isCollectible = true;
                    }

                    this.field.getField()[headPositionR][headPositionC] = '+';

                    if (!isCollectible) {
                        setTail();
                    } else {
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

                    if (headPositionC == 0) {
                        throw new IllegalArgumentException("Game Over");
                    }

                    this.snake.setHeadC(headPositionC);

                    if (this.field.getField()[headPositionR][headPositionC] == '$') {
                        isCollectible = true;
                    }

                    if (this.field.getField()[headPositionR][headPositionC] == '@') {
                        throw new IllegalArgumentException("Game Over");
                    }

                    this.field.getField()[headPositionR][headPositionC] = '<';

                    if (!isCollectible) {
                        setTail();
                    } else {
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

                    if (headPositionC == this.field.getDimensionY() - 1) {
                        throw new IllegalArgumentException("Game Over");
                    }

                    if (this.field.getField()[headPositionR][headPositionC] == '@') {
                        throw new IllegalArgumentException("Game Over");
                    }

                    this.snake.setHeadC(headPositionC);

                    if (this.field.getField()[headPositionR][headPositionC] == '$') {
                        isCollectible = true;
                    }

                    this.field.getField()[headPositionR][headPositionC] = '>';

                    if (!isCollectible) {
                        setTail();
                    } else {
                        this.collectibleOnField = false;
                        this.snake.setSize(this.snake.getSize() + 1);
                    }
                    this.lastCommand = command.charAt(0);
                }
                break;
            default:
        }
    }

    private void setTail() {
        int tailPositionR = this.snake.getTailR();
        int tailPositionC = this.snake.getTailC();
        int snakeSize = this.snake.getSize();

        if (this.field.getField()[tailPositionR][tailPositionC - 1] == '@') {
            int newTailPositionC = tailPositionC - 1;

            this.snake.setTailC(newTailPositionC);
            this.field.getField()[tailPositionR][tailPositionC] = '.';

        } else if (this.field.getField()[tailPositionR][tailPositionC + 1] == '@') {
            int newTailPositionC = tailPositionC + 1;

            this.snake.setTailC(newTailPositionC);
            this.field.getField()[tailPositionR][tailPositionC] = '.';

        } else if (this.field.getField()[tailPositionR + 1][tailPositionC] == '@') {
            int newTailPositionR = tailPositionR + 1;

            this.snake.setTailR(newTailPositionR);
            this.field.getField()[tailPositionR][tailPositionC] = '.';

        } else if (this.field.getField()[tailPositionR - 1][tailPositionC] == '@') {
            int newTailPositionR = tailPositionR - 1;

            this.snake.setTailR(newTailPositionR);
            this.field.getField()[tailPositionR][tailPositionC] = '.';
        }
    }

    private String getCommand() {
        String input = this.scanner.nextLine();
        if(input.trim().isEmpty()){
            input = getCommand();
        }
        String command = "";
        command += input.charAt(0);
        return command;
    }

    private void spawnSnake() {
        this.snake.setHeadR(4);
        this.snake.setHeadC(13);
        this.snake.setTailR(4);
        this.snake.setTailC(15);

        this.snake.addBody(4, 13);
        this.snake.addBody(4, 14);
        this.snake.addBody(4, 15);

        this.field.getField()[4][13] = '<';
        this.field.getField()[4][14] = '@';
        this.field.getField()[4][15] = '@';
    }

    private void spawnCollectible() {
        Random randomR = new Random();
        Random randomC = new Random();
        int collectibleR = 0;
        while (collectibleR == 0) {
            collectibleR = randomR.nextInt(7);
        }

        int collectibleC = 0;
        while (collectibleC == 0) {
            collectibleC = randomC.nextInt(28);
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
            }
            System.out.println();
        }
    }
}
