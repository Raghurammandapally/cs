import java.util.*;
public class MessageLoopIterator<E> implements Iterator<E> {
	private DblListnode<E> curr, first;
	
	MessageLoopIterator(DblListnode<E> init) {
		curr = null;
		first = init;
	}
	public boolean hasNext() {
		if (first == null)
			return false;
		else
			return curr != first;
	}

	public E next() {
		if (!hasNext())
			throw new NoSuchElementException();
		if (curr == null)
			curr = first;
		E item = curr.getData();
		curr = curr.getNext();
		return item;
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}
	
}
