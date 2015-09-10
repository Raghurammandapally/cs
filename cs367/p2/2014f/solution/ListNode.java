

class ListNode<E>{

	private E data;
	private ListNode<E> prev;
	private ListNode<E> next;

	public ListNode(E data_){
		data = data_;
		prev = null;
		next = null;
	}

	public ListNode(E data_,ListNode<E> prev_, ListNode<E> next_){
		data = data_;
		prev = prev_;
		next = next_;
	}
	
	public E getData(){
		return data;
	}	

	public ListNode<E> getNext(){
		return next;
	}
	
	public ListNode<E> getPrevious(){
		return prev;
	}	

	public void setData(E data_){
		data = data_;
	}	

	public void setPrevious(ListNode<E> prev_){
		prev = prev_;
	}
	
	public void setNext(ListNode<E> next_){
		next=next_;
	}

}
