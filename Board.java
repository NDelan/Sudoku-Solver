/*
Name: Delanyo Nutakor
Title: Sudoku Solver
File Name: Board.java
Date: 03/17/2023

Purpose: This holds the array of Cells that make up the Sudoku board. 
         It test if a value is valid at a certain position on the board, 
         and test if the board is solved.

How to Run: Compile the file by typing "javac Board.java" in the command line
            Then, type "Board.java" in the command line.
*/
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.awt.*;

public class Board {

    private Cell[][] arrayOfCells;
    public static int SIZE;
    public boolean finished;
    public double root;


    public Board() {
      finished = false;
      SIZE = 9;
      arrayOfCells = new Cell[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                arrayOfCells[i][j] = new Cell(i, j, 0);
            }
        }
    }

    //Takes String filename
    public Board(String filename) {
        this();
        read(filename);
    }

    //Creates board with numOfLockedCells locked cells
    public Board(int numOfLockedCells) {
        this();
        
        Random random = new Random();

        int count = 0;
        
        //Generates numOfLockedCells of randomly placed locked cell
        while (count < numOfLockedCells) {

            //generate random row and column numbers
            int row = random.nextInt(SIZE);
            int col = random.nextInt(SIZE);
            
            //sets value and locks it at a valid spot
            if (!arrayOfCells[row][col].isLocked()) {
                //generate a random value from 1 to size
                int value = random.nextInt(SIZE) + 1;
                if (validValue(row, col, value)) {
                    arrayOfCells[row][col].setValue(value);
                    arrayOfCells[row][col].setLocked(true);
                    count++;
                }
            }
        }       
   
    }

    //Extension Constructor: Takes both size and number of locked cells
    public Board(int size, int numOfLockedCells) {
        //board not solved
        finished = false;
        root = Math.sqrt(size);
        SIZE = size;
        int count = 0;
        Random random = new Random();

        //ensures that size is a perfect square
        if (size/Math.sqrt(size) != Math.sqrt(size)) {
            System.out.println("Please input a perfect square");
            System.exit(1);
        }

        //Fill all cell position with 0
        arrayOfCells = new Cell[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                arrayOfCells[i][j] = new Cell(i, j, 0);
            }
        }

        //fill board with locked cells
        while (count < numOfLockedCells) {
            int row = random.nextInt(SIZE);
            int col = random.nextInt(SIZE);
            
            if (!arrayOfCells[row][col].isLocked()) {
                int value = random.nextInt(SIZE) + 1;
                if (validValue(row, col, value)) {
                    arrayOfCells[row][col].setValue(value);
                    arrayOfCells[row][col].setLocked(true);
                    count++;
                }
            }
        } 
    }

    //return number of columns
    public int getCols() {
        return SIZE;
    }

    //return number of columns
    public int getRows() {
        return SIZE;
    }

    //returns whether the Cell at r, c, is locked.
    public boolean isLocked(int r, int c) {
        return arrayOfCells[r][c].isLocked();
    }

    //sets the value and locked fields of the Cell at r, c.
    public void set(int r, int c, int value, boolean locked) {
        arrayOfCells[r][c].setValue(value);
        arrayOfCells[r][c].setLocked(locked);
    }

    //this returns the Cell at the given row and col
    public Cell get(int row, int col) {
        return arrayOfCells[row][col];
    }

    //this sets the Cell at the given row and col to the given value
    public void set(int row, int col, int value) {
        arrayOfCells[row][col].setValue(value);
    }

    //this sets whether the Cell at the given row and col is locked
    public void set(int row, int col, boolean locked) {
        arrayOfCells[row][col].setLocked(locked);
    }

    //returns the value at Cell r, c.
    public int value(int r, int c) {
        return get(r, c).getValue();
    }

    //returns the number of locked Cells on the board.
    public int numLocked() {
        int numLocked = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (isLocked(i, j) == true) {
                    numLocked += 1;
                }
            }
        }
        return numLocked;
    }

    //Returns true is the value at row, col is valid
    public boolean validValue(int row, int col, int value) {
        //eliminate edge cases
        if (value < 1 || value > SIZE) {
            return false;
        }

        //check for row and column values
        for (int i = 0; i < getRows(); i++) {
            if (value(row, i) == value && i != col) {
                return false;
            }

            if (value(i, col) == value && i != row) {
                return false;
            }
        }

        //checks the values within the square of the cell
        int startRow = (int) ((int)(row/root) * root);
        int startCol = (int) ((int)(col/root) * root);
       
        for (int i = startRow; i < startRow + root; i++) {
            for (int j = startCol; j < startCol + root; j++) {
               
                //returns false if there is a cell with the same value 
                //and shares either the same row
                if (value(i, j) == value && (i != row || j != col)) {
                    return false;
                }
            }
        }
        return true;
    }

    //returns true if the board is completely solved
    public boolean validSolution() {
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getCols(); j++) {
                int currVal = arrayOfCells[i][j].getValue();
                if (validValue(i, j, currVal) == false) {
                    return false;
                }
            }
        }
        return true;
    }


    //draws cells on the board
    public void draw(Graphics g, int scale){
        for(int i = 0; i<getRows(); i++){
            for(int j = 0; j<getCols(); j++){
                get(i, j).draw(g, j*scale+5, i*scale+10, scale);
            }
        //displays final state of board
        } if(finished){
            if(validSolution()){
                g.setColor(new Color(0, 127, 0));
                g.drawChars("Hurray!".toCharArray(), 0, "Hurray!".length(), scale*3+5, scale*10+10);
            } else {
                g.setColor(new Color(127, 0, 0));
                g.drawChars("No solution!".toCharArray(), 0, "No Solution!".length(), scale*3+5, scale*10+10);
            }
        }
    }

    //reads board from file
    public boolean read(String filename) {
        try {
          // assign to a variable of type FileReader a new FileReader object, passing filename to the constructor
          FileReader fr = new FileReader(filename);
          // assign to a variable of type BufferedReader a new BufferedReader, passing the FileReader variable to the constructor
          BufferedReader br = new BufferedReader(fr);
          
          // assign to a variable of type String line the result of calling the readLine method of your BufferedReader object.
          String line = br.readLine();
          // start a while loop that loops while line isn't null
          int row = 0;
          while(line != null){
              // print line
              // assign to an array of Strings the result of splitting the line up by spaces (line.split("[ ]+"))
              String[] arrayOfValues = line.split("[ ]+");
              // print the size of the String array (you can use .length)
              // use the line to set various Cells of this Board accordingly
              for (int i = 0; i < arrayOfValues.length; i++) {
                if (Integer.parseInt(arrayOfValues[i]) != 0) {
                    arrayOfCells[row][i] = new Cell(row, i, Integer.parseInt(arrayOfValues[i]), true);
                } else {
                    arrayOfCells[row][i] = new Cell(row, i, Integer.parseInt(arrayOfValues[i]));
                }
              }
              // assign to line the result of calling the readLine method of your BufferedReader object.
              line = br.readLine();
              row++;

          }
          // call the close method of the BufferedReader
          br.close();
          return true;
        }
        catch(FileNotFoundException ex) {
          System.out.println("Board.read():: unable to open file " + filename );
        }
        catch(IOException ex) {
          System.out.println("Board.read():: error reading file " + filename);
        }
    
        return false;
    }

    //returns string representation of board
    public String toString() {

        String toReturn = "";
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                toReturn += arrayOfCells[i][j].getValue() + " ";
                if ((j + 1) % root == 0) {
                    toReturn += " ";
                }
            }
            toReturn += "\n";
            if ((i + 1) % root == 0) {
                toReturn += "\n";
            }
        }

        return toReturn;

    }


    public static void main(String[] args) {
        Board board = new Board(9,2);
        System.out.println("String representation: " + board.toString());
        System.out.println("Number of locked cells: 2 == " + board.numLocked());
        System.out.println("It is a valid solutions: true: " + board.validSolution());
        // if (args.length > 0) {
        //     board.read(args[0]);
        //     System.out.println(board.toString());
        // } else {
        //     System.out.println("Usage: Please pass file name for board");
        // }
    }
}