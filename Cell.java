/*
Name: Delanyo Nutakor
Title: Sudoku Solver
File Name: Cell.java
Date: 03/17/2023

Purpose: This creates cells which represent unit locations
         on the 2D grid. It initializes the row, column, 
         and value fields to the given parameter values.

How to Run: Compile the file by typing "javac Cell.java" in the command line
            Then, type "Cell.java" in the command line.
*/

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class Cell { 

    private int row;
    private int col;
    private int value;
    private boolean locked;

    //Class constructors
    public Cell() {
        row = 0;
        col = 0;
        value = 0;
        locked = false;
    }

    public Cell(int row, int col, int value) {
        this.row = row;
        this.col = col;
        this.value = value;
        locked = false;
    }

    public Cell(int row, int col, int value, boolean locked) {
        this.row = row;
        this.col = col;
        this.value = value;
        this.locked = locked;
    }

    //return the Cell's row index.
    public int getRow() {
        return row;
    }

    //return the Cell's column index
    public int getCol() {
        return col;
    }

    //return the Cell's value.
    public int getValue() {
        return value;
    }

    //set the Cell's value.
    public void setValue(int newval) {
        value = newval;
    }

    //return the value of the locked field.
    public boolean isLocked() {
        return locked;
    }

    //set the Cell's locked field to the new value.
    public void setLocked(boolean lock) {
        locked = lock;
    }

    //returns cells in string format
    public String toString(Cell cell){
        String toReturn = "" + cell.value;

        return toReturn;
    }

    //draws the cell's number
    public void draw(Graphics g, int x, int y, int scale){
        char toDraw = (char) ((int) '0' + getValue());
        g.setColor(isLocked()? Color.BLUE : Color.RED);
        g.drawChars(new char[] {toDraw}, 0, 1, x, y);
    }

    public int getNumChoices(Board board) {
        /*
         * Get the total number of choices of the current cell
         */
        Set<Integer> choices = new HashSet<>();
        for (int i = 1; i <= 9; i++) {
            choices.add(i);
        }
        // Check the row
        for (int c = 0; c < 9; c++) {
            int value = board.get(row, c).getValue();
            if (value != 0) {
                choices.remove(value);
            }
        }
        // Check the column
        for (int r = 0; r < 9; r++) {
            int value = board.get(r, col).getValue();
            if (value != 0) {
                choices.remove(value);
            }
        }
        // Check the box
        int boxRow = (row / 3) * 3;
        int boxCol = (col / 3) * 3;
        for (int r = boxRow; r < boxRow + 3; r++) {
            for (int c = boxCol; c < boxCol + 3; c++) {
                int value = board.get(r, c).getValue();
                if (value != 0) {
                    choices.remove(value);
                }
            }
        }
        return choices.size();
    }
    

    public static void main(String[] args) {
        //Tests cases to check validity of the methods

        Cell myTestCell1 = new Cell();

        System.out.println("0 ==" + myTestCell1.getCol() );
        System.out.println("0 ==" + myTestCell1.getRow() );
        System.out.println("0 ==" + myTestCell1.getValue() );
        System.out.println("false ==" + myTestCell1.isLocked());

        Cell myTestCell = new Cell(5,10,15);

        System.out.println("10 ==" + myTestCell.getCol() );
        System.out.println("5 ==" + myTestCell.getRow() );
        System.out.println("15 ==" + myTestCell.getValue() );
        System.out.println("false ==" + myTestCell.isLocked());

        myTestCell.setLocked(true);
        myTestCell.setValue(16);
        
        System.out.println("5 ==" + myTestCell.getRow());
        System.out.println("10 ==" + myTestCell.getCol());
        System.out.println("16 ==" + myTestCell.getValue());
        System.out.println("true ==" + myTestCell.isLocked());

    }
    
}
