///////////////////////////////////////////////////////////////////////////////
// Main Class File:  P1.java
// File:             Sym.java
// Semester:         CS536 Fall 2015
//
// Author:           Keith Funkhouser
// CS Login:         wfunkhouser
// Lecturer's Name:  Aws Albarghouthi
// Lab Section:      1
//
//////////////////////////// 80 columns wide //////////////////////////////////

/**
 * The Sym class is a simplified representation of the information to be stored
 * in our symbol table. Currently, the symbol is represented using a String.
 *
 * <p>Bugs: none known
 *
 * @author Keith Funkhouser
 */
public class Sym {

/** This Sym's type. */
  private String type;

/**
 * Constructor initializes the Sym by storing the given type.
 *
 * @param preconditions none
 * @param postconditions none
 * @return a new Sym object with the given type
 */
  public Sym(String type) {
    this.type = type;
  }

/**
 * The returned value is the type (currently a String) of this Sym.
 *
 * @param preconditions none
 * @param postconditions none
 * @return a String, the type of this Sym
 */
  public String getType() {
    return type;
  }

/**
 * Currently, toString simply returns this Sym's type. This will be modified
 * in the future as more information is stored in Sym.
 *
 * @param preconditions none
 * @param postconditions none
 * @return a String, the type of this Sym
 */
  public String toString() {
    return getType();
  }
}
