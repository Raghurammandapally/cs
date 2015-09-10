public class SimpleArrayList implements ListADT<Object> {
 
  // *** fields ***
    private Object[] items; // the items in the List
    private int numItems;   // the number of items in the List
 
  // *** constructor ***
    public SimpleArrayList() { }      
 
  //*** required ListADT methods ***
 
    // add items
    public void add(Object item) { }  
    public void add(int pos, Object item) { }   
 
    // remove items
    public Object remove(int pos) { return null; }  
 
    // get items
    public Object get (int pos) { return null; }  
 
    // other methods
    public boolean contains (Object item) { return true; }
    public int size() { return 0; }      
    public boolean isEmpty() { return true; }  
}  
