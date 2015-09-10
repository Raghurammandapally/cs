/**
 * This is the solution class, please do not share with students.
 * @author segill
 *
 * @param <E> Students will use Strings.
 */

public class LinkedList<E> implements ListADT<E>{

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
		else {
			ListNode<E> curr = first;
			while (curr.getNext()!=null) {
				curr = curr.getNext();
			}
			curr.setNext(newNode);
		}
		numItems++;
	}

	public void add(int pos, E item) throws 
InvalidListPositionException {
		ListNode<E> newNode = new ListNode<E>(item);
		int currPos = 0;
		ListNode<E> curr = first;
		
		if (pos < 0 || (first==null && pos!=0)) {
			throw new InvalidListPositionException();
		}
		
		if (pos==0) {
			newNode.setNext(first);
			first = newNode;
			numItems++;
			return;
		}
		
		while (currPos < pos - 1) {
			if (curr.getNext()==null) {
				throw new 
InvalidListPositionException();
			}
			curr = curr.getNext();
			currPos++;
		}
		
		ListNode<E> oldNext = curr.getNext();
		curr.setNext(newNode);
		newNode.setNext(oldNext);
		numItems++;
	}

	public boolean isEmpty() {
		return numItems == 0;
	}
	
	public int size() {
		return numItems;
	}

	public E remove(int pos) throws InvalidListPositionException {
		int currPos = 0;
		ListNode<E> curr = first;
		ListNode<E> removed;
		
		if (pos < 0 || first==null) {
			throw new InvalidListPositionException();
		}
		
		if (pos==0) {
			removed = first;
			first = first.getNext();
			numItems--;
			return removed.getData();
		}
		
		while (currPos < pos - 1) {
			if (curr.getNext()==null) {
				throw new 
InvalidListPositionException();
			}
			curr = curr.getNext();
			currPos++;
		}
		
		removed = curr.getNext();
		if (removed==null) {
			throw new InvalidListPositionException();
		}
		ListNode<E> next = removed.getNext();
		curr.setNext(next);
		numItems--;
		
		return removed.getData();
	}


	public E get(int pos) throws InvalidListPositionException {
		int currPos = 0;
		ListNode<E> curr = first;
		
		if (pos < 0 || first==null) {
			throw new InvalidListPositionException();
		}
		
		while (currPos < pos) {
			if (curr.getNext()==null) {
				throw new 
InvalidListPositionException();
			}
			curr = curr.getNext();
			currPos++;
		}
		
		return curr.getData();
	}
	
	public String print(boolean lineNumbers) {
		String ret = "";
		
		if (isEmpty()) {
    		ret = "Empty";
    		return ret;
    	}
    	else {
    		int lineNum = 1;
    		ListNode<E> curr = first;
    		while (curr!=null) {
    			ret += (lineNumbers?lineNum + " ":"") + 
curr.getData() + "\n";
    			lineNum++;
    			curr = curr.getNext();
    		}
    	}
		
		return ret;
	}

}

