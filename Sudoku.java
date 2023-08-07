/*
Name: Delanyo Nutakor
Title: Sudoku Solver
File Name: Sudoku.java
Date: 03/17/2023

Purpose: Generates and solves the Sudoku puzzle.

How to Run: To compile the file, enter "javac Sudoku.java" in the command line.
            type "Sudoku.java" in the command line 
            input the board size and the initially filled cells as per the follow-up instructions.
*/
import java.util.Random;
import java.util.Scanner;

public class Sudoku {
    Board board;
    LandscapeDisplay ld;
    public static int SIZE;
    
    public Sudoku(int numOfLockedCells) {
        board = new Board(numOfLockedCells);
        ld = new LandscapeDisplay(board);
        SIZE = 9;
    }

    public Sudoku(int size, int numOfLockedCells) {
        board = new Board(size, numOfLockedCells);
        ld = new LandscapeDisplay(board);
        SIZE = size;
    }

    public int findNextValue(Cell currentCell) {
        for (int currval = currentCell.getValue() + 1; currval < SIZE + 1; currval++) {
            if (board.validValue(currentCell.getRow(), currentCell.getCol(), currval)) {
                return currval;
            }
        }
        return 0;
    }

    //finds next cell with value 0
    public Cell findNextCell() {
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                if (board.get(r, c).getValue() == 0) {
                    return board.get(r, c);
                }
            }
        }
        return null;
    }

    //solves board using recursion
    public boolean solveRecursion(Cell next){
        int delay = 20;
        //base case
        if (next == null) {
            return true;
        }

        if (delay > 10) {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        } 
        if (ld != null) {
            ld.repaint();
        }

        int currRow = next.getRow();
        int currCol = next.getCol();

        //recursive case
        for (int i = 1; i <= SIZE; i++) {
            if (board.validValue(currRow, currCol, i)) {
                board.get(currRow, currCol).setValue(i);

                if (solveRecursion(findNextCell())) {
                    board.finished = true;
                    return true;
                }
            }
        }

        board.get(currRow, currCol).setValue(0);

        board.finished = false;
        return false;
    }

    //Solves board using backtracking
    public boolean solve() {

        Stack<Cell> stack = new LinkedList<>();
 
        while (stack.size() < (board.getCols() * board.getRows()) - board.numLocked()) {
            Cell next = findNextCell();
            System.out.println(next.getValue());

            while (next == null && stack.size() > 0) {
                System.out.println("d");
                Cell topOfStack = stack.pop();
                int topVal = findNextValue(topOfStack);
                topOfStack.setValue(topVal);

                if (topVal != 0) {
                    next = topOfStack;
                }
            }

            if (next == null) {
                return false;
            } else {
                 stack.push(next);
            }
        }

        board.finished = true;
        return true;
    }

    public static void main(String[] args) {

            Scanner sc = new Scanner(System.in);

            System.out.println("Enter the size of board: (A perfect square. Example: 4,9,16,25..)");
            int sizeOfBoard = sc.nextInt();

            System.out.println("Enter the number of initial cells filled: ");
            int initiallyLockedCells = sc.nextInt();

            Sudoku playGame = new Sudoku(sizeOfBoard,initiallyLockedCells);

            System.out.println("Initial Board:");

            //Display string representation of board in command line
            System.out.println(playGame.board.toString());

            //Solve the board
            playGame.solveRecursion(playGame.findNextCell());

            //Display Solved board
            System.out.println("Solved Board:");
            System.out.println(playGame.board.toString());

    }
}