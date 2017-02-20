package Java_Project;

/**
 * Created by f_mol on 15-12-2016.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class SudokuGUI extends JPanel
{
    public static void main (String[] args)
    {
        JFrame window = new JFrame("Sudoku Solver");
        Java_Project.SudokuGUI content = new Java_Project.SudokuGUI();
        window.setSize(1000, 1000);
        window.setContentPane(content);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }

    //We start out with defining the Buttons to be used and shown in the Sudoku GUI
    private JButton Save; //A button which will give the opportunity to solve the Sudoku result
    private JButton Clear; //A button which will clear the Sudoku Field
    private JButton Load; //A button which will load any given Sudoku input text file for solving
    private JButton Solve; //A button which will solve the Sudoku when pressed
    private JLabel[][] all_fields;
    private Font FontNumber = new Font("Monospaced", Font.BOLD, 20);
    private int box = Field.SIZE / 3;
    Field field = new Field();
    Sudoku sudoku = new Sudoku();

    public SudokuGUI()
    {
        //We start out with defining variables which will be used in creating the Sudoku board
        all_fields = new JLabel[Field.SIZE][Field.SIZE];

        JPanel grid = new JPanel(new GridLayout(box, box)); //A panel that will hold the Sudoku field
        grid.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        grid.setBackground(Color.BLACK);
        grid.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        grid.setBackground(Color.WHITE);
        grid.setVisible(true);
        JPanel[][] panel = new JPanel[box][box];
        for (int i = 0; i < panel.length; i++){         //We define a nested for-loop for creating 3x3 panels within the main panel
            for (int j = 0; j < panel[i].length; j++){
                panel[i][j] = new JPanel(new GridLayout(box, box, 2, 2));
                panel[i][j].setBackground(Color.WHITE);
                panel[i][j].setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
                grid.add(panel[i][j]);
            }
        }
        //We define a nested for-loop to create the Sudoku field with JLabels inside
        for (int i = 0; i < all_fields.length; i++) {
            for (int j = 0; j < all_fields[i].length; j++) {
                all_fields[i][j] = new JLabel("");
                all_fields[i][j].setHorizontalAlignment((int) CENTER_ALIGNMENT);
                all_fields[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                all_fields[i][j].setFont(FontNumber);
                int r = i / 3;
                int c = j / 3;
                panel[r][c].add(all_fields[i][j]);
                }
                }

        JPanel buttonPanel = new JPanel(); //A panel that will hold the buttons
        buttonPanel.setLayout(new GridLayout(1, 4, 1, 1));

        //We create so called anonymous classes for each button to handle ActionListeners

        JButton save = new JButton("Save");
        save.setToolTipText("Save the Sudoku to a text file");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFileChooser saveFile = new JFileChooser();
                int returnVal = saveFile.showSaveDialog(save);
                BufferedWriter out = null;
                if (e.getSource() == save) {
                    if (returnVal == JFileChooser.APPROVE_OPTION) {

                        try {
                            File file = saveFile.getSelectedFile();
                            out = new BufferedWriter(new FileWriter(file));
                            for (int i = 0; i < Field.SIZE; i++) {
                                for (int j = 0; j < Field.SIZE; j++) {
                                    out.write(all_fields[i][j].getText());
                                    if (i < Field.SIZE) {
                                        out.write(" ");
                                    }
                                }
                                out.newLine();
                            }
                            out.close();

                        } catch (Exception err) {
                        }
                    }
                }
            }
        });

        JButton clear = new JButton("Clear");
        clear.setToolTipText("Reset the Sudoku board");
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //We use a nested for-loop and the function "clear" from Field class to reset the Sudoku field
                for (int i = 0; i < Field.SIZE; i++) {
                    for (int j = 0; j < Field.SIZE; j++) {
                        field.clear(i,j);
                    }
                }
                update();
            }
        });

        JButton load = new JButton("Load");
        load.setToolTipText("Load a new Sudoku text file");
        load.addActionListener(new ActionListener() {
            @Override
            //This function will handle the Load button,
            public void actionPerformed(ActionEvent e) {
                JFileChooser loadFile = new JFileChooser();
                int returnVal = loadFile.showOpenDialog(load);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    try {
                        File file = loadFile.getSelectedFile();
                        String filename = file.getPath();
                        field.fromFile(filename);
                        update();
                    } catch (Exception err) {
                    }
                }
            }
        });

        JButton solve = new JButton("Solve!");
        solve.setToolTipText("Solves The Sudoku");
        solve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    sudoku.solve(field, 0, 0);
                }
                catch (SolvedException err) { }
                update();
            }
        });

        JPanel mainpanel = new JPanel(new BorderLayout()); //A panel that will hold the buttonpanel
        mainpanel.setVisible(true);
        mainpanel.add(buttonPanel, BorderLayout.SOUTH);


        setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
        setLayout(new GridLayout(2,2,0,0));
        buttonPanel.add(save);
        buttonPanel.add(clear);
        buttonPanel.add(load);
        buttonPanel.add(solve);
        add(grid);
        add(mainpanel);
    }

    private void update()
    {
        for (int i = 0; i < Field.SIZE; i++) {
            for (int j = 0; j < Field.SIZE; j++) {
                all_fields[i][j].setText(Integer.toString(field.getValue(i, j)));
            }
        }
    }
}