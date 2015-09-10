import java.util.Iterator;

/**
 * A Loop ADT is a circular sequence of items.  A Loop has a current item 
 * and the ability to move forward or backwards.  A Loop can be modified by 
 * removing the current item or by adding an item either before or after the 
 * current item.
 * 
 * @author Rebecca Hasti (hasti@cs.wisc.edu) for CS 367, Fall 2009
 */
public interface LoopADT<E> {
    
    /**
     * Adds the given <tt>E</tt> immediately <em>before</em> the current 
     * item.  After the new item has been added, the new item becomes the 
     * current item.
     * 
     * @param ob the item to add
     */
    void addBefore(E ob);
    
    /**
     * Adds the given <tt>E</tt> immediately <em>after</em> the current 
     * item.  After the new item has been added, the new item becomes the 
     * current item.
     * 
     * @param ob the item to add
     */
    void addAfter(E ob);
    
    /**
     * Returns the current item.  If the Loop is empty, an 
     * <tt>EmptyLoopException</tt> is thrown.
     * 
     * @return the current item
     * @throws EmptyLoopException if the Loop is empty
     */
    E getCurrent();
    
    /**
     * Removes and returns the current item.  The item immediately 
     * <em>after</em> the removed item then becomes the  current item.  
     * If the Loop is empty initially, an <tt>EmptyLoopException</tt> 
     * is thrown.
     * 
     * @return the removed item
     * @throws EmptyLoopException if the Loop is empty
     */
    E removeCurrent();
    
    /**
     * Advances the current item forward one item resulting in the item 
     * that is immediately <em>after</em> the current item becoming the  
     * current item.
     */
    void forward();
    
    /**
     * Moves the current item backwards one item resulting in the item 
     * that is immediately <em>before</em> the current item becoming the  
     * current item.
     */
 																																																																																																					   void back();
    
    /**
     * Returns the number of items in this Loop.
     * @return the number of items in this Loop
     */
    int size();
    
    /**
     * Returns an iterator for this Loop.
     * @return an iterator for this Loop
     */
    Iterator<E> iterator();
}
