import java.util.*;
///////////////////////////////////////////////////////////////////////////////
//                   
// Main Class File:  P1.java (turned in for part 1)
// File:             SymTable.java
// Semester:         CS536 Fall 2015
//
// Author:           Keith Funkhouser
// CS Login:         wfunkhouser
// Lecturer's Name:  Aws Albarghouthi
// Lab Section:      1
//
//////////////////////////// 80 columns wide //////////////////////////////////

/**
 * SymTable is a class for representing a symbol table (i.e. a list of HashMaps
 * which map String -> Sym). It has the following public methods:
 *   SymTable()
 *   void addDecl(String name, Sym sym) throws DuplicateSymException, 
 *     EmptySymTableException 
 *   void addScope() 
 *   Sym lookupLocal(String name)
 *   Sym lookupGlobal(String name)
 *   void removeScope() throws EmptySymTableException
 *   void print()
 *
 * <p>Bugs: none known
 *
 * @author Keith Funkhouser
 */
public class SymTable {
  private List<HashMap<String,Sym>> tableList;

  public SymTable() {
    //should initialize the SymTable's List field to contain a single, empty 
    // HashMap.
    tableList = new ArrayList<HashMap<String,Sym>>();
    addScope();
  }

  public void addDecl(String name, Sym sym) throws EmptySymTableException,DuplicateSymException {
    if(tableList.isEmpty()) {
      // If this SymTable's list is empty, throw an EmptySymTableException. 
      throw new EmptySymTableException();
    } else if(name==null || sym==null) {
      // If either name or sym (or both) is null, throw a NullPointerException. 
      throw new NullPointerException();
    } else if(getFirst(tableList).containsKey(name)) {
      // If the first HashMap in the list already contains the given name as a key, throw a DuplicateSymException. 
      throw new DuplicateSymException();
    } else {
      // Otherwise, add the given name and sym to the first HashMap in the list.
      getFirst(tableList).put(name, sym);
    }
  }

  public void addScope() {
    // Add a new, empty HashMap to the front of the list.
    tableList.add(0, new HashMap<String,Sym>());
  }

  public Sym lookupLocal(String name) throws EmptySymTableException {
    if(tableList.isEmpty()) {
      //If this SymTable's list is empty, throw an EmptySymTableException. 
      throw new EmptySymTableException();
    } else if (getFirst(tableList).containsKey(name)) {
      //Otherwise, if the first HashMap in the list contains name as a key, return the associated Sym; 
      return getFirst(tableList).get(name);
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
      tableList.remove(0);
    }
    //To clarify, throw an exception only if before attempting to remove, the list is empty (i.e. there are no HashMaps to remove).
  }

  public void print() {
    //This method is for debugging. First, print “\nSym Table\n”. Then, for each HashMap M in the list, print M.toString() followed by a newline. Finally, print one more newline. All output should go to System.out.
    System.out.print("\nSym Table\n");
    for(HashMap M : tableList) {
      System.out.print(M.toString());
      System.out.print("\n");
    }
    System.out.print("\n");
  }

  private HashMap<String,Sym> getFirst(List<HashMap<String,Sym>> symbolTable) {
    return symbolTable.get(0);
  }
}
