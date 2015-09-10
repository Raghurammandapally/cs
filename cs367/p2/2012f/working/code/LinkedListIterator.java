import java.util.*;

public class LinkedListIterator<E> implements Iterator<E> {
	private ListNode<E> curr;
	
	LinkedListIterator(ListNode<E> start) {
		curr = start;
	}
	
	public boolean hasNext() {
		return curr!=null;
	}

	public E next() {
		if (!hasNext())
			throw new NoSuchElementException();
		
		E currData = curr.getData();
		curr = curr.getNext();
		return currData;
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}
	
}