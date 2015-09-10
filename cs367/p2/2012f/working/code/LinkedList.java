import java.util.Iterator;


public class LinkedList<E> implements LinkedListADT<E>{

	private ListNode<E> first;
	private int numItems;

	public LinkedList() {
		first = null;
		numItems = 0;
	}
	
	public void add(E item) {
		ListNode<E> newNode = new ListNode<E>(item);
		
		if (first == null) {
			first = newNode;
		}
		else if (first.getNext() == null) {
			first.setNext(newNode);
			newNode.setPrev(first);
		}
		else {
			ListNode<E> curr = first.getNext();
			while (curr.getNext()!=null) {
				curr = curr.getNext();
			}
			curr.setNext(newNode);
			newNode.setPrev(curr);
		}
		numItems++;
	}

	public void add(int pos, E item) throws InvalidListPositionException {
		ListNode<E> newNode = new ListNode<E>(item);
		int currPos = 0;
		ListNode<E> curr = first;
		
		if (pos < 0) {
			throw new IndexOutOfBoundsException();
		}
		
		if (pos==0) {
			first.setPrev(newNode);
			newNode.setNext(first);
			first = newNode;
			numItems++;
			return;
		}
		
		while (currPos < pos) {
			if (curr.getNext()==null) {
				throw new IndexOutOfBoundsException();
			}
			curr = curr.getNext();
			currPos++;
		}
		
		ListNode<E> prev = curr.getPrev();
		prev.setNext(newNode);
		newNode.setPrev(prev);
		curr.setPrev(newNode);
		newNode.setNext(curr);
		numItems++;
	}

	public boolean isEmpty() {
		return numItems == 0;
	}

	public Iterator<E> iterator() {
		return new LinkedListIterator<E>(first);
	}
	
	public int size() {
		return numItems;
	}

	public E remove(int pos) throws InvalidListPositionException {
		int currPos = 0;
		ListNode<E> curr = first;
		
		if (pos < 0) {
			throw new IndexOutOfBoundsException();
		}
		
		if (pos==0) {
			if (first==null) {
				throw new IndexOutOfBoundsException();
			}
			else  {
				ListNode<E> removed = first;
				if (first.getNext()==null) {
					first = null;
					return removed.getData();
				}
				first = first.getNext();
				return removed.getData();
			}
		}
		
		while (currPos < pos) {
			if (curr.getNext()==null) {
				throw new IndexOutOfBoundsException();
			}
			curr = curr.getNext();
			currPos++;
		}
		
		ListNode<E> prev = curr.getPrev();
		ListNode<E> next = curr.getNext();
		prev.setNext(next);
		if (next!=null)
			next.setPrev(prev);
		numItems--;
		
		return curr.getData();
	}


	public E get(int pos) throws InvalidListPositionException {
		int currPos = 0;
		ListNode<E> curr = first;
		
		if (pos < 0) {
			throw new IndexOutOfBoundsException();
		}
		
		if (pos==0) {
			if (first==null) {
				throw new IndexOutOfBoundsException();
			}
			else  {
				return first.getData();
			}
		}
		
		while (currPos < pos) {
			if (curr.getNext()==null) {
				throw new IndexOutOfBoundsException();
			}
			curr = curr.getNext();
			currPos++;
		}
		
		return curr.getData();
	}

}
