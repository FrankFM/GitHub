package Java_Project;

/**
 * Created by f_mol on 01-12-2016.
 */
public class Sudoku
{

    public static void main(String[] args)
    {
        Field field = new Field();
        field.fromFile("src/Java_Project/testHard2.txt");
        try
        {
            solve(field, 0, 0);
        }
        catch (SolvedException e)
        {
            System.out.println(field);
        }

    }

    public static void solve(Field f, int i, int j) throws SolvedException
    {

        if (i >= Field.SIZE)
        {
            // we are done!
            throw new SolvedException();
        }
        else if (j >= Field.SIZE)
        {
            solve(f, i + 1, 0);
        }
        else
        {
            if (f.isEmpty(i, j))
            {
                for (int val = 1; val < 10; val++)
                {
                    if (f.tryValue(val, i, j))
                    {
                        solve(f, i, j + 1);
                        f.clear(i, j);
                    }
                }
            } else
            {
                solve(f, i, j + 1);
            }
        }
    }

}