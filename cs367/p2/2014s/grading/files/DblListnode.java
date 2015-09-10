// from on-line reading on linked lists
public class DblListnode<E> {
	  //*** fields ***
	    private DblListnode<E> prev;
	    private E data;
	    private DblListnode<E> next;

	  //*** methods ***
	    // 3 constructors
	    public DblListnode() {
			this(null, null, null);
	    }

	    public DblListnode(E d) {
			this(null, d, null);
	    }

	    public DblListnode(DblListnode<E> p, E d, DblListnode<E> n) {
	        prev = p;
			data = d;
			next = n;
	    }

	    // access to fields
	    public E getData() {
	        return data;
	    }

	    public DblListnode<E> getNext() {
	        return next;
	    }

	    public DblListnode<E> getPrev() {
	        return prev;
	    }

	    // modify fields
	    public void setData(E ob) {
	        data = ob;
	    }

	    public void setNext(DblListnode<E> n) {
	        next = n;
	    }

	    public void setPrev(DblListnode<E> p) {
	        prev = p;
	    }
	}
