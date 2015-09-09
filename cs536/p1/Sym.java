/*The Sym class must be in a file named Sym.java. You must implement the following Sym constructor and public methods (and no other public or protected methods):
*/

public class Sym {
  private String type;

  //Sym(String type)  This is the constructor; it should initialize the Sym to have the given type.
  public Sym(String type) {
    this.type = type;
  }

  //String getType()  Return this Sym's type.
  public String getType() {
    return type;
  }

  //String toString() Return this Sym's type. (This method will be changed later in a future project when more information is stored in a Sym.)
  public String toString() {
    return getType();
  }
}
