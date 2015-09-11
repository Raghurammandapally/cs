import static java.lang.System.out;

public class P1 {
  public static void main (String[] args) throws EmptySymTableException,DuplicateSymException {
    //can we test this using a try/catch block?
    SymTable s = new SymTable();
    String err = "  ERROR!";
    String exc = "  THIS SHOULD HAVE FAILED!";
    boolean success = false;

    out.println("Checking throwing an EmptySymTableException...");
    try {
      throw new EmptySymTableException();
    } catch (Exception e) {
      out.println(err);
    }

    out.println("Checking adding values to empty table...");
    try {
      for(int i=0; i < 10; i++) {
        s.addDecl("key"+i, new Sym("val"+i));
      }
    } catch (Exception e) {
      out.println(err);
    }

    out.println("Checking adding a duplicate value...");
    success = false;
    try {
      s.addDecl("key0", new Sym("val0"));
      success = true;
    } catch (DuplicateSymException e) {
    } finally {
      if(success) {
        out.println(exc);
      }
    }
    
    out.println("Checking emptying a non-empty table...");
    try {
      s.removeScope();
    } catch (Exception e) {
      out.println(err);
    }

    out.println("Checking emptying an empty table...");
    success = false;
    try {
      s.removeScope();
      success = true;
    } catch (EmptySymTableException e) {
    } finally {
      if(success) {
        out.println(exc);
      }
    }

    out.println("Checking adding to an empty table...");
    success = false;
    try {
      s.addDecl("key", new Sym("val"));
      success = true;
    } catch (EmptySymTableException e) {
    } finally {
      if(success) {
        out.println(exc);
      }
    }


    //TEMPLATES
    /*
    out.println("Checking ...");
    success = false;
    try {
      // do something that should throw an exception
      success = true;
    } catch (Exception e) {
    } finally {
      if(success) {
        out.println(exc);
      }
    }

    out.println("Checking ...");
    try {
      // do something that shouldn't throw an exception
    } catch (Exception e) {
      out.println(err);
    }
    */

      
    
    
  }
}

