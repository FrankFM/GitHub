package Java_Project;

/**
 * Created by f_mol on 01-12-2016.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Abstract Data Type for Sudoku_1 playing field
 */
public class Field {

    public static final int SIZE = 9;

    private int model[][];

    public Field() {
        // make new array of size SIZExSIZE
        this.model = new int[SIZE][SIZE];
        // initialize with empty cells
        init(SIZE - 1, SIZE - 1);
    }

    private void init(int i, int j) {
        if (i < 0) {
            // all rows done!
        } else if (j < 0) {
            // this row done - go to next!
            init(i - 1, SIZE - 1);
        } else {
            this.clear(i, j);
            init(i, j - 1);
        }
    }

    public void fromFile(String filename) {
        try {
            Scanner sc = new Scanner(new File(filename));
            fromScanner(sc, 0, 0);
        } catch (FileNotFoundException e) {
            // :-(
        }
    }

    private void fromScanner(Scanner sc, int i, int j) {
        if (i >= SIZE)
        {
            // all rows done!
        } else if (j >= SIZE)
        {
            // this row done - go to next!
            fromScanner(sc, i + 1, 0);
        } else {
            try {
                int val = Integer.parseInt(sc.next());
                this.model[i][j] = val;
            } catch (NumberFormatException e) {
                // skip this cell
            }
            fromScanner(sc, i, j + 1);
        }
    }

    public String toString() {
        StringBuffer res = new StringBuffer();
        for (int i = 0; i < SIZE; i++) {
            if (i % 3 == 0) {
                res.append("+-------+-------+-------+\n");
            }
            for (int j = 0; j < SIZE; j++) {
                if (j % 3 == 0) {
                    res.append("| ");
                }
                int val = this.model[i][j];
                res.append(val > 0 ? val + " " : "  ");
            }
            res.append("|\n");
        }
        res.append("+-------+-------+-------+");
        return res.toString();
    }

    /**
     * returns false if the value val cannot be placed at
     * row i and column j. returns true and sets the cell
     * to val otherwise.
     */
    public boolean tryValue(int val, int i, int j)
    {
        if (!checkRow(val, i))
        {
            return false;
        }
        if (!checkCol(val, j))
        {
            return false;
        }
        if (!checkBox(val, i, j))
        {
            return false;
        }
        this.model[i][j] = val;
        return true;
    }

    /**
     * checks if the cell at row i and column j is empty,
     * i.e., whether it contains 0
     */
    public boolean isEmpty(int i, int j)
    {
        return model[i][j] == 0;
    }

    /**
     * sets the cell at row i and column j to be empty, i.e.,
     * to be 0
     */
    public void clear(int i, int j)
    {
        model[i][j] = 0;
    }

    /**
     * checks if val is an acceptable value for the row i
     */
    private boolean checkRow(int val, int i)
    {
        for (int j = 0; j < 9; j++)
        {
            if (model[i][j] == val)
            {
                return false;
            }
        }
        return true;
    }

    /**
     * checks if val is an acceptable value for the column j
     */
    private boolean checkCol(int val, int j)
    {
        for (int i = 0; i < 9; i++)
        {
            if (model[i][j] == val)
            {
                return false;
            }
        }
        return true;
    }

    /**
     * checks if val is an acceptable value for the box around
     * the cell at row i and column j
     */
    private boolean checkBox(int val, int i, int j)
    {
        int start_i = (i / 3) * 3;
        int start_j = (j / 3) * 3;

        for (int row = start_i; row < start_i + 3; row++)
        {
            for (int col = start_j; col < start_j + 3; col++)
            {
                if (model[i][j] == val)
                    return false;
            }
        }
        return true;
    }

    public int getValue(int i, int j)
    {
        return model[i][j];
    }

}