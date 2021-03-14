package field;

public class FieldImpl implements Field {
    private int dimensionX;
    private int dimensionY;
    private char[][] field;

    public FieldImpl(int dimensionX, int dimensionY) {
        this.dimensionX = dimensionX;
        this.dimensionY = dimensionY;
        this.field = new char[dimensionX][dimensionY];
        this.createField();
    }

    private void createField() {
        for (int r = 0; r < dimensionX; r++) {
            for (int c = 0; c < dimensionY; c++) {
                this.field[r][c] = '.';
            }
        }

        for (int c = 0; c < dimensionY; c++) {
            this.field[0][c] = '-';
        }

        for (int c = 0; c < dimensionY; c++) {
            this.field[dimensionX - 1][c] = '-';
        }

        for (int r = 0; r < dimensionX; r++) {
            this.field[r][0] = '|';
        }

        for (int r = 0; r < dimensionX; r++) {
            this.field[r][dimensionY - 1] = '|';
        }
    }

    @Override
    public char[][] getField() {
        return this.field;
    }

    @Override
    public int getDimensionX() {
        return this.dimensionX;
    }

    @Override
    public int getDimensionY() {
        return this.dimensionY;
    }

    @Override
    public boolean checkIfWon() {
        boolean wonGame = true;

        for (int r = 0; r < this.field.length; r++) {
            for (int c = 0; c < this.field[r].length; c++) {
                if(this.field[r][c] == '.'){
                    wonGame = false;
                }
            }
        }

        return wonGame;
    }


}
