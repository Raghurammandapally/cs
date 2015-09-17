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

/** The instance's list for keeping track of symbol tables (or scopes). */
  private List<HashMap<String,Sym>> tableList;

/**
 * Constructs a new SymTable.
 *
 * @param preconditions none
 * @param postconditions none 
 * @return Returned SymTable has a single local scope, with an 
 *  empty HashMap<String,Sym> in it.
 */
  public SymTable() {
    tableList = new ArrayList<HashMap<String,Sym>>();
    addScope();
  }

/**
 * Adds the key,value pair with key name and value sym to the symbol table.
 *
 * @param preconditions Name/sym are non-null
 * @param preconditions The first HashMap in the list does not contain the 
 *  given key
 * @param postconditions The first HashMap in the list contains the key,value
 *  pair
 * @return void
 */
  public void addDecl(String name, Sym sym) throws EmptySymTableException,
                                                   DuplicateSymException {
    if(tableList.isEmpty()) {
      throw new EmptySymTableException();
    } else if(name==null || sym==null) {
      throw new NullPointerException();
    } else if(getFirst(tableList).containsKey(name)) {
      throw new DuplicateSymException();
    } else {
      getFirst(tableList).put(name, sym);
    }
  }

/**
 * Adds an empty HashMap to the front of the list (i.e. new local scope).
 *
 * @param preconditions None
 * @param postconditions this.isEmpty() is false
 * @return void
 */
  public void addScope() {
    tableList.add(0, new HashMap<String,Sym>());
  }

/**
 * If the first HashMap in the list contains name as a key, returns the
 * associated Sym. Otherwise, returns null. May throw an EmptySymTableException
 * if the list is empty.
 *
 * @param preconditions this.isEmpty() is false
 * @param postconditions None
 * @return returned Sym is either the associated value in local scope, or null
 */
  public Sym lookupLocal(String name) throws EmptySymTableException {
    if(tableList.isEmpty()) {
      throw new EmptySymTableException();
    } else if (getFirst(tableList).containsKey(name)) {
      return getFirst(tableList).get(name);
    } else {
      return null;
    }
  }

/**
 * If any HashMap in the list contains name as a key, returns the first
 * associated Sym. Otherwise, returns null. May throw an EmptySymTableException
 * if the list is empty.
 *
 * @param preconditions this.isEmpty() is false
 * @param postconditions None
 * @return either the associated value in the HashMap closest to the front of 
 * the list, or null
 */
  public Sym lookupGlobal(String name) throws EmptySymTableException {
    if(tableList.isEmpty()) {
      throw new EmptySymTableException();
    } else {
      for(HashMap<String,Sym> M : tableList) {
        if(M.containsKey(name)) {
          return (M.get(name));
        }
      }
      return null;
    }
  }

/**
 * Removes the HashMap in the front of the list. May throw an exception if
 * the list is already empty.
 *
 * @param preconditions this.isEmpty() is false
 * @param postconditions the list has one less HashMap, and the HashMap
 * previously in the second position is now in the first
 * @return void
 */
  void removeScope() throws EmptySymTableException {
    if(tableList.isEmpty()) {
      throw new EmptySymTableException();
    } else {
      tableList.remove(0);
    }
  }

/**
 * (FOR DEBUGGING) Calls toString() on every HashMap in the list and prints
 * to System.out.
 *
 * @param preconditions None
 * @param postconditions System.out receives output from printing
 * @return void
 */
  public void print() {
    System.out.print("\nSym Table\n");
    for(HashMap M : tableList) {
      System.out.print(M.toString());
      System.out.print("\n");
    }
    System.out.print("\n");
  }


/**
 * (PRIVATE) shorthand method for pulling first HashMap from list
 *
 * @param preconditions this.isEmpty() is false
 * @param postconditions None
 * @return the first HashMap in the list
 */
  private HashMap<String,Sym> getFirst(List<HashMap<String,Sym>> symbolTable) {
    return symbolTable.get(0);
  }
}
