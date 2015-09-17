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
 * Adds the key,value pair with key name and value sym to the symbol table.
 *
 * @param preconditions Name/sym are non-null
 * @param preconditions The first HashMap in the list does not contain the 
 *  given key
 * @param postconditions The first HashMap in the list contains the key,value
 *  pair
 * @return void
 */
  public void addScope() {
    tableList.add(0, new HashMap<String,Sym>());
  }

  public Sym lookupLocal(String name) throws EmptySymTableException {
    if(tableList.isEmpty()) {
      throw new EmptySymTableException();
    } else if (getFirst(tableList).containsKey(name)) {
      return getFirst(tableList).get(name);
    } else {
      return null;
    }
  }

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

  void removeScope() throws EmptySymTableException {
    if(tableList.isEmpty()) {
      throw new EmptySymTableException();
    } else {
      tableList.remove(0);
    }
  }

  public void print() {
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
