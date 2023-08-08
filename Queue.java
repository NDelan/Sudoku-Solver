/*
Name: Delanyo Nutakor
Title: Sudoku Solver
File Name: Queue.java
Date: 03/17/2023

Purpose: Specifies the abstracts methods to create a queue
*/
public interface Queue<T> extends Iterable<T>{

    /**
     * Adds the given {@code item} to the end of this queue.
     * 
     * @param item the item to add to the queue.
     */
    public void offer(T item);

    /**
     * Returns the number of items in the queue.
     * @return the number of items in the queue.
     */
    public int size();

    /**
     * Returns the item at the front of the queue.
     * @return the item at the front of the queue.
     */
    public T peek();

    /**
     * Returns and removes the item at the front of the queue.
     * @return the item at the front of the queue.
     */
    public T poll();
}