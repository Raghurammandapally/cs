public class test {
  public static void main(String[] args) throws DuplicateExceptionXYZ {
    Listnode<String> a = new Listnode<String>("hello");
    System.out.println(a.getData());
    System.out.println(a.getNext());

    Listnode<String> b = new Listnode<String>("world",a);
    System.out.println(b.getData());
    System.out.println(b.getNext());

    System.out.println("\n******************\nPrinting all values in LL\n********************");
    printLinkedList(b);

    throw new DuplicateExceptionXYZ("ohnoes");

  }
  static private void printLinkedList(Listnode<String> head) {
    while(head != null) {
      System.out.println(head.getData());
      head = head.getNext();
    }
  }
}
