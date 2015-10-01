public class Listnode<E> {
  //*** fields ***
  private E data;
  private Listnode<E> next;

  //*** constructors ***
  // 2 constructors
  public Listnode(E d) {
    this(d, null);
    System.out.println("Calling the constructor with one parameter.");
  }

  public Listnode(E d, Listnode n) {
    System.out.println("Calling the constructor with two parameters.");
    data = d;
    next = n;
  }

  //*** methods ***
  // access to fields
  public E getData() {
    return data;
  }

  public Listnode<E> getNext() {
    return next;
  }

  // modify fields
  public void setData(E d) {
    data = d;
  }

  public void setNext(Listnode<E> n) {
    next = n;
  }
}
