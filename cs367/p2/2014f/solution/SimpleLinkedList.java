

public class SimpleLinkedList<E> implements ListADT<E> {
	
	/**
	 * Add item to the end of the List.
	 * 
	 * @param item the item to add
	 * @throws IllegalArgumentException if item is null 
	 */
	
	private ListNode<E> header;

	public SimpleLinkedList(){
		header = new ListNode<E>(null,null,null);//create a dummy header
	}


   /**
    * Add item to the end of the List.
    * 
    * @param item the item to add
    * @throws IllegalArgumentException if item is null 
    */

	public void add(E item){
		if(item == null){
			throw new IllegalArgumentException();
		}
		ListNode<E> n = header;
		while(n.getNext() != null){
			n = n.getNext();
		}
		ListNode<E> newNode = new ListNode<E>(item);
		newNode.setPrevious(n);
		n.setNext(newNode);
		return;
	}	

	/**
	 * Add item at position pos in the List, moving the items originally in 
	 * positions pos through size() - 1 one place to the right to make room.
	 * 
	 * @param pos the position at which to add the item
	 * @param item the item to add
	 * @throws IllegalArgumentException if item is null 
	 * @throws IndexOutOfBoundsException if pos is less than 0 or greater 
	 * than size()
	 */
	public void add(int pos, E item){
		if(item == null){
			throw new IllegalArgumentException();
		}
		if(pos < 0 || pos > this.size()){
			throw new IndexOutOfBoundsException();
		}
		int ndx = 0;
		ListNode<E> n = header;

		for(ndx = 0;ndx < pos;ndx++){
			n = n.getNext();
		}
		ListNode<E> newNode = new ListNode<E>(item);
		newNode.setNext(n.getNext());
		newNode.setPrevious(n);
		n.setNext(newNode);
		if(newNode.getNext() != null){
			newNode.getNext().setPrevious(newNode);
		}
		return;
	}
	
	/**
	 * Return true iff item is in the List (i.e., there is an item x in the List 
	 * such that x.equals(item))
	 * 
	 * @param item the item to check
	 * @return true if item is in the List, false otherwise
	 */
	public boolean contains(E item){
		ListNode<E> n = header;
		while(n != null){
			if(item.equals(n.getData())){
				return true;
			}	
		}
		return false;
	}
	
	/**
	 * Return the number of items in the List.
	 * 
	 * @return the number of items in the List
	 */
	public int size(){
		int s = 0;
		ListNode<E> n = header.getNext();
		while(n != null){
			n = n.getNext();
			s++;
		}
		return s;
	}
	
	/**
	 * Return true iff the List is empty.
	 * 
	 * @return true if the List is empty, false otherwise
	 */
	public boolean isEmpty(){
		return header == null;
	}
	
	/**
	 * Return the item at position pos in the List.
	 * 
	 * @param pos the position of the item to return
	 * @return the item at position pos
	 * @throws IndexOutOfBoundsException if pos is less than 0 or greater than
	 * or equal to size()
	 */
	public E get(int pos){
		if(pos < 0 || pos > this.size()){
			throw new IndexOutOfBoundsException();
		}
		int ndx = 0;
		ListNode<E> n = header.getNext();

		for(ndx = 0;ndx < pos;ndx++){
			n = n.getNext();
		}
		return n.getData();
	}
	
	/**
	 * Remove and return the item at position pos in the List, moving the items 
	 * originally in positions pos+1 through size() - 1 one place to the left to 
	 * fill in the gap.
	 * 
	 * @param pos the position at which to remove the item
	 * @return the item at position pos
	 * @throws IndexOutOfBoundsException if pos is less than 0 or greater than
	 * or equal to size()
	 */
	public E remove(int pos){
		if(pos < 0 || pos > this.size()){
			throw new IndexOutOfBoundsException();
		}
		int ndx = 0;
		ListNode<E> n = header.getNext();

		for(ndx = 0;ndx < pos;ndx++){
			n = n.getNext();
		}
		E ret = n.getData();

		n.getPrevious().setNext(n.getNext());
		if(n.getNext() != null){
			n.getNext().setPrevious(n.getPrevious());
		}

		return ret;

	}
}
