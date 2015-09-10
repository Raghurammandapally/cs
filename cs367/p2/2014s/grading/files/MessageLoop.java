import java.util.*;
public class MessageLoop<E> implements LoopADT<E> {
	private DblListnode<E> curr;
	private int size;

	public MessageLoop() {
		curr = null;
		size = 0;
	}

	public void addAfter(E ob) {
		if (curr == null) {
			curr = new DblListnode<E>(ob);
			curr.setNext(curr);
			curr.setPrev(curr);
		}
		else {
			DblListnode<E> newNode = new DblListnode<E>(curr, ob, curr.getNext());
			curr.getNext().setPrev(newNode);
			curr.setNext(newNode);
			curr = newNode;
		}
		size++;

	}

	public void addBefore(E ob) {
		if (curr == null) {
			curr = new DblListnode<E>(ob);
			curr.setNext(curr);
			curr.setPrev(curr);
		}
		else {
			DblListnode<E> newNode = new DblListnode<E>(curr.getPrev(), ob, curr);
			curr.getPrev().setNext(newNode);
			curr.setPrev(newNode);
			curr = newNode;
		}
		size++;
	}

	public void back() {
		if (curr == null)
			return;
		else
			curr = curr.getPrev();

	}

	public void forward() {
		if (curr == null)
			return;
		else
			curr = curr.getNext();
	}

	public E getCurrent() {
		if (curr == null)
			throw new EmptyLoopException();
		return curr.getData();
	}

	public Iterator<E> iterator() {
		return new MessageLoopIterator<E>(curr);
	}

	public E removeCurrent() {
		if (curr == null)
			throw new EmptyLoopException();
		E item = curr.getData();
		size--;
		if (size == 0)
			curr = null;
		else {
			curr.getNext().setPrev(curr.getPrev());
			curr.getPrev().setNext(curr.getNext());
			curr = curr.getNext();
		}
		return item;
	}

	public int size() {
		return size;
	}

}
