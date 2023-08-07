/*
Name: Delanyo Nutakor
Title: Sudoku Solver
File Name: Exploration.java
Date: 03/17/2023

Purpose: This class investigates the relationship between the number of
         randomly selected values and the likelihood of finding a solution for the board.

How to Run: Compile the file by typing "javac Exploration.java" in the command line
            Then, type "Exploration.java" in the command line.
*/

//Explores how various parameters affect the number 
//and time it takes to solve a board
public class Exploration {
    
    public static void main (String[] args) {
        int[] numOfStarterValues = {30};
        int numTrials = 50;
        // int delay = 0;

        for (int num : numOfStarterValues) {
            int numSolutions = 0;
            long totalTime = 0;

            for (int i = 0; i < numTrials; i++) {
                Sudoku sudoku = new Sudoku(9,num);

                long startTime = System.nanoTime();
                sudoku.solveRecursion(sudoku.findNextCell());
                long endTime = System.nanoTime();
                long duration = endTime - startTime;

     
            }
;

            double avgTime = numSolutions > 0 ? totalTime / numSolutions : 0;
            System.out.printf("Number of initial values: %d \nnumber of solutions found: %d, \naverage time: %dms\n", num, numSolutions, (int) avgTime);
            System.out.println("---------------------------------");
        }
    }
}
