import java.util.*;

/*
 * It must be implemented using a List of HashMaps. (Think about the operations that will be done on a SymTable to decide whether to use an ArrayList or a LinkedList.) The HashMaps must map a String to a Sym. This means that the SymTable class will have a (private) field of type List<HashMap<String,Sym>>.
 * */

public class SymTable {
  //is this allowed? see ^
  private LinkedList<HashMap<String,Sym>> tableList;

  public SymTable() {
    //should initialize the SymTable's List field to contain a single, empty HashMap.
    tableList = new LinkedList<HashMap<String,Sym>>();
    addScope();
  }

  public void addDecl(String name, Sym sym) throws DuplicateSymException, EmptySymTableException {
    if(tableList.isEmpty()) {
      // If this SymTable's list is empty, throw an EmptySymTableException. 
      throw new EmptySymTableException();
    } else if(name==null || sym==null) {
      // If either name or sym (or both) is null, throw a NullPointerException. 
      throw new NullPointerException();
    } else if(tableList.getFirst().containsKey(name)) {
      // If the first HashMap in the list already contains the given name as a key, throw a DuplicateSymException. 
      throw new DuplicateSymException();
    } else {
      // Otherwise, add the given name and sym to the first HashMap in the list.
      tableList.getFirst().put(name, sym);
    }
  }

  public void addScope() {
    // Add a new, empty HashMap to the front of the list.
    tableList.addFirst(new HashMap<String,Sym>());
  }

  public Sym lookupLocal(String name) throws EmptySymTableException {
    if(tableList.isEmpty()) {
      //If this SymTable's list is empty, throw an EmptySymTableException. 
      throw new EmptySymTableException();
    } else if (tableList.getFirst().containsKey(name)) {
      //Otherwise, if the first HashMap in the list contains name as a key, return the associated Sym; 
      return tableList.getFirst().get(name);
    } else {
      //otherwise, return null.
      return null;
    }
  }

  public Sym lookupGlobal(String name) throws EmptySymTableException {
    if(tableList.isEmpty()) {
      // If this SymTable's list is empty, throw an EmptySymTableException. 
      throw new EmptySymTableException();
    } else {
      // If any HashMap in the list contains name as a key, return the first associated Sym (i.e., the one from the HashMap that is closest to the front of the list); 
      for(HashMap<String,Sym> M : tableList) {
        if(M.containsKey(name)) {
          return (M.get(name));
        }
      }
      //otherwise, return null.
      return null;
    }
  }

  void removeScope() throws EmptySymTableException {
    if(tableList.isEmpty()) {
      //If this SymTable's list is empty, throw an EmptySymTableException; 
      throw new EmptySymTableException();
    } else {
      //otherwise, remove the HashMap from the front of the list. 
      tableList.removeFirst();
    }
    //To clarify, throw an exception only if before attempting to remove, the list is empty (i.e. there are no HashMaps to remove).
  }

  public void print() {
    //This method is for debugging. First, print “\nSym Table\n”. Then, for each HashMap M in the list, print M.toString() followed by a newline. Finally, print one more newline. All output should go to System.out.
    System.out.println("\nSym Table\n");
    for(HashMap M : tableList) {
      System.out.println(M.toString());
    }
  }
}
