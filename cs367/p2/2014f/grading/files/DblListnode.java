/**
 * A node for a doubly linked list. 
 * Represents one node of a SimpleLinked List.
 */
class DblListnode<E>{

    private E data;
    private DblListnode<E> prev;
    private DblListnode<E> next;

    /**
     * Constructs a DblListnode containing data, with null next and prev.
     * @param data the data to store in the DblListnode
     */
    public DblListnode(E data){
        this.data = data;
        prev = null;
        next = null;
    }

    /**
     * Constructs a DblListnode containing data, next and prev references.
     * @param data the data to store in the DblListnode
     * @param data reference to previous DblListnode in list
     * @param next reference to next DblListnode in list 
     */
    public DblListnode(E data,DblListnode<E> prev, DblListnode<E> next){
        this.data = data;
        this.prev = prev;
        this.next = next;
    }
    
    /**
     * Get the data stored in the node
     * 
     * @return the data stored in the node
     */
    public E getData(){
        return data;
    }   

    /**
     * Returns the DblListnode<E> that comes after this DblListnode<E>. 
     * Returns null if this node does not have a next node.
     * 
     * @return the next Node
     */
    public DblListnode<E> getNext(){
        return next;
    }
    
    /**
     * Returns the DblListnode<E> that comes before this DblListnode<E>. 
     * Returns null if this node does not have a previous node.
     * @return the item at position pos
     */
    public DblListnode<E> getPrev(){
        return prev;
    }   

    /**
     * Set the data stored in this node
     * 
     * @param data the data to store
     */
    public void setData(E data){
        this.data = data;
    }   

    /**
     * Set the previous node to reference prev
     * @param prev the node to set to previous
     */
    public void setPrev(DblListnode<E> prev){
        this.prev = prev;
    }
    
    /**
     * Set the next node to reference next
     * @param prev the node to set to next
     */
    public void setNext(DblListnode<E> next){
        this.next=next;
    }
}
