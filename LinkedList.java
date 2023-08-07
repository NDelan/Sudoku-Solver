/*
Name: Delanyo Nutakor
Title: Sudoku Solver
File Name: LinkedList.java
Date: 03/17/2023

Purpose: Implement stacks for the main program
*/

import java.util.Iterator;    // defines the Iterator interface
import java.util.ArrayList;   
 

//Let linkedlist file implement methods in the Iterable interface
@SuppressWarnings("unchecked")
public class LinkedList<T> implements Iterable<T>, Queue<T>,Stack<T>{

    //Private because we don't want the user to get access to Node
    private static class Node<T> {
        T data;
        Node<T> next;
        Node<T> prev;

        public Node(T data) {
            this.data = data;
            this.next = null;
        } 

        public Node(T data, Node<T> next){
            this.data = data;
            this.next = next;
            this.prev = null;
        }

        public Node(T data, Node<T> next, Node<T> prev){
            this.data = data;
            this.next = next;
            this.prev = prev;
        }

        public T getData() {
            return data;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }

        public void setPrev(Node<T> prev) {
            this.prev = prev;
        }

        public Node<T> getNext() {
            return next;
        }

        public Node<T> getPrev() {
            return prev;
        }
    }

    private int size;
    private Node<T> head;
    private Node<T> tail;

    /*
     * Constructor for LinkedListp
     */
    public LinkedList() {
        size = 0;
        head = null;
        tail = null;
    }

    //ArrayList Method

    public ArrayList<T> toArrayList() {
        ArrayList<T> myArrayList = new ArrayList<T>();

        for (T item: this) {
            myArrayList.add(item);
        }

        return myArrayList;
    }


    // Return a new LLIterator pointing to the head of the list
    public Iterator iterator() {

        return new LLIterator(head);
    }

    // Iterator and Iterable class
    private class LLIterator implements Iterator<T> {

        Node<T> head;
        //private Node<T> head;

        //Constructor for LLIterator class given head node
        public LLIterator(Node<T> head) {
            this.head = head;
        }

        //Checks if there is a next value in the list
        public boolean hasNext() {
            return head != null;
        }

        // Returns the next item in the list
        public T next() {
            T recentData = head.getData();
            head = head.next;
            return recentData;
        }

        //Optional
        public void remove() {
            //Optional
            assert (true);
        }
    }

    //Add new head to list
    public void add(T item) {
        Node<T> newNode = new Node<T>(item, head);
        head = newNode;
        
        size++;
    }

    public int size() {
        return size;
    }

    //Get data at a particular index
    public T get(int index) {
        Node<T> walker = head;

        for (int i = 0; i < index; i++) {
            walker = walker.getNext();
        }

        return walker.getData();
    }

    //Add item at a particular index
    public void add(int index, T item) {
        if (index == 0) {
            add(item);
            return;
        }

        Node<T> walker = head;

        for (int i = 0; i < index-1; i++) {
            walker = walker.getNext();
        }

        Node<T> nextToWalker = new Node<T>(item, walker.getNext());
        size++;
        walker.next = nextToWalker;
        
    }

    public void clear() {
        head = null;
        size = 0;
    }

    //Checks if linked list contains object o
    public boolean contains(Object o) {

        for (T data : this) {
            if (data.equals(o)) {
                return true;
            }
        }

        return false;
    }

    //Check if two linkedlists are equal
    public boolean equals(Object o) {
        if (!(o instanceof LinkedList)) {
            return false;
        }

        LinkedList<T> oAsALinkedList = (LinkedList<T>) o;

        if (this.size != oAsALinkedList.size) {
            return false;
        }

        Node<T> originalWalker = this.head;
        Node<T> oWalker = oAsALinkedList.head;

        for (int i = 0; i < size; i++) {
            if (!originalWalker.equals(oWalker)) {
                if (!originalWalker.getData().equals(oWalker.getData())) {
                    return false;
                }
            }

           originalWalker.getNext();
           oWalker.getNext();
        }
        return true;

    }

    //Checks if linkedlist is empty
    public boolean isEmpty() {
        return head == null;
    }


    /**
	 * This method adds the given {@code data} to the start of the list.
	 * 
	 * @param data the data to be added into the list.
	 */
	public void addFirst(T data) {
        
        if (head == null) {
            Node<T> newNode = new Node<>(data);
            head = newNode;
            tail = newNode;
        } else {
            Node<T> newNode = new Node<>(data,head);
            head.prev = newNode;
            head = newNode; 
        }
        size++;
	}

	/**
	 * This method adds the given {@code data} to the end of the list.
	 * 
	 * @param data the data to be added into the list.
	 */
	public void addLast(T data) {
        if (tail != null) {
            Node<T> newTail = new Node<>(data,null,tail);
            tail.next = newTail;
            tail = newTail;

        } else {
            Node<T> newTail = new Node<>(data);
            head = newTail;
            tail = newTail;
            }
        size++;		
	}

	/**
	 * This method returns and removes the first entry of the list.
	 * 
	 * @return the last entry of the list.
	 */
	public T removeFirst(){

		if (head == null) {
            return null;
        } 
        
        T result = head.getData();
        if (head.getNext() == null) {
            head = tail = null;
        } else {
            head = head.getNext();
            head.prev = null;
        }
        size--;
		return result;
	}

	/**
	 * This method returns and removes the last entry of the list.
	 * 
	 * @return the last entry of the list.
	 */
	public T removeLast(){
		if (tail == null) {
            return null;
        } 

        T result = tail.getData();
        if (tail.prev == null) {
            head = tail = null;
        } else {
            tail = tail.prev;
            tail.next = null;
        }
        size--;
		return result;
	}

    public T remove() {
        Node<T> temp = head;
        head = head.getNext();
        size --;

        return temp.getData();
    }

    public T remove(int index) {
        Node<T> temp;
        Node<T> nextToRemoved;
        temp = head;

        if (index == 0) {
            this.remove();
            return temp.getData();
            // this.clear();
            // size --;
            // return temp.getData();
        }

        for (int i = 0; i < index-1; i++) {
            temp = temp.getNext();
        }

        nextToRemoved = temp.getNext().getNext();
        temp.setNext(nextToRemoved);
        size--; 
        
        return temp.getData();
    }


    public void push(T item) {
        addFirst(item);
    }

    public T pop(T item) {
        return removeFirst();
    }

    /**
     * Adds the given {@code item} to the end of this queue.
     * 
     * @param item the item to add to the queue.
     */
    public void offer(T item) {
         addLast(item);
    }

    /**
     * Returns the item at the front of the queue.
     * @return the item at the front of the queue.
     */
    public T peek() { 
        if (head == null) {
            return null;
        }
        return head.getData();
    }

    /**
     * Returns and removes the item at the front of the queue.
     * @return the item at the front of the queue.
     */
    public T poll() {
        return removeFirst(); 
    }

    public String toString(){
        Node<T> walker = this.head;

        if (this.isEmpty()) {
            return "[]";
        }

        String output = "[";

        for (int i = 0; i < size - 1; i++) {
            output += walker.getData();
            output += ", ";
            walker = walker.getNext();
        }

        return output + "]";
    }

    @Override
    public T pop() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'pop'");
    }

    


}
