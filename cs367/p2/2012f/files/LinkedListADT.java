import java.util.Iterator;

/**
 * A LinkedList ADT is a doubly-linked sequence of items.  A LinkedList 
has a 
 * current item and the ability to move forward or backwards.  A LinkedList 
 * can be modified by removing the current item or by adding an item before 
 * the current item.
 */
public interface LinkedListADT<E> {
    
    /**
     * Adds the given item to the very end of the list.
     * 
     * @param item the item to add
     */
    void add(E item);
    
    /**
     * Adds the given item to the given position in the list. The ListNode
     * currently in this position should be shifted to be after this node.
     * If the position is larger than the size of the list, or less than zero,
     * throws an <tt>InvalidListPositionException</tt>.
     * 
     * @param item the item to add
     * @param pos the position at which to add the item
     * @throws an InvalidListPositionException if the position is invalid
     */
    void add(int pos, E item) throws InvalidListPositionException;
    
    /**
     * Removes the item at the given position. If the position is larger than 
     * the size of the list, or less than zero, throws an 
     * <tt>InvalidListPositionException</tt>.\
     * 
     * @param pos the position at which to remove a node
     * @return The data from the node which has been removed
     * @throws InvalidListPositionException if the position is invalid
     */
    E remove(int pos) throws InvalidListPositionException;
    
    /**
     * Returns the item at the given position in the list. If the position is
     * invalid, throws an <tt>InvalidListPositionException</tt>.
     * @param pos The position of the item to return
     * @return The data from the node at the given position
     * @throws InvalidListPositionException if the position is invalid.
     */
    E get(int pos) throws InvalidListPositionException;
    
    /**
     * Determines if this LinkedList is empty, i.e., contains no items.
     * @return true if the LinkedList is empty; false otherwise
     */
    boolean isEmpty();
    
    /**
     * Returns the number of items in this LinkedList.
     * @return the number of items in this LinkedList
     */
    int size();
    
    /**
     * Returns an iterator for this LinkedList.
     * @return an iterator for this LinkedList
     */
    Iterator<E> iterator();
}
