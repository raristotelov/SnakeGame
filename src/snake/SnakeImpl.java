package snake;

import java.util.ArrayList;
import java.util.List;

public class SnakeImpl {
    private static final int INITIAL_SIZE = 3;
    private int size;
    private List<int[]> bodyCoordinates;
    private int headR;
    private int headC;
    private int tailR;
    private int tailC;

    public SnakeImpl() {
        size = INITIAL_SIZE;
        bodyCoordinates = new ArrayList<>();
    }

    public void addBody(int r, int c) {
        this.bodyCoordinates.add(new int[]{r, c});
    }

    public List<int[]> getBodyCoordinates() {
        return this.bodyCoordinates;
    }

    public void setHeadR(int headR) {
        this.headR = headR;
    }

    public void setHeadC(int headC) {
        this.headC = headC;
    }

    public void setTailR(int tailR) {
        this.tailR = tailR;
    }

    public void setTailC(int tailC) {
        this.tailC = tailC;
    }

    public int getHeadR() {
        return this.headR;
    }

    public int getHeadC() {
        return this.headC;
    }

    public int getTailR() {
        return this.tailR;
    }

    public int getTailC() {
        return this.tailC;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return this.size;
    }
}
