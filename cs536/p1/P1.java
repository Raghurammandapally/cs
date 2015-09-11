import static java.lang.System.out;

public class P1 {
  public static void main (String[] args) throws EmptySymTableException,DuplicateSymException {
    //can we test this using a try/catch block?
    String err = "  ERROR!";
    String exc = "  THIS SHOULD HAVE FAILED!";
    boolean success = false;

    out.println("*****************************************");
    out.println("************Beginning tests**************");
    out.println("*****************************************");

    /* uncomment these to test throwing our exceptions 
     *  note: you will also have to comment out all remaining code in order
     *        not to receive "unreachable statement" errors on compilation 
     *
     * (leave lines commented in order to run rest of tests)
    */
    //throw new EmptySymTableException();
    //throw new DuplicateSymException();

    //initialize our SymTable
    SymTable s = new SymTable();

    //initialize an array of test Sym's
    //  syms[0] = new Sym("val0");
    //  syms[1] = new Sym("val1");
    //      ...
    //  syms[9] = new Sym("val9");
    Sym[] syms = new Sym[10];
    for (int i=0; i < 10; i++) {
      syms[i] = new Sym("val" + i);
    }

    out.println("Checking adding multiple values to empty table...");
    try {
      for(int i=0; i < 10; i++) {
        s.addDecl("key"+i, syms[i]);
      }
    } catch (Exception e) {
      out.println(err);
    }

    out.println("Checking retrieving those values locally...");
    try {
      for(int i=0; i < 10; i++) {
        if(s.lookupLocal("key"+i) != syms[i]) {
          out.println(err + "idx " + i);
        }
      }
    } catch (Exception e) {
      out.println(err);
    }

    out.println("Checking retrieving those values globally...");
    for(int i=0; i < 10; i++) {
      if(s.lookupGlobal("key"+i) != syms[i]) {
        out.println(err + "idx " + i);
      }
    }

    out.println("Checking adding a duplicate value...");
    success = false;
    try {
      s.addDecl("key0", new Sym("diffval"));
      success = true;
    } catch (DuplicateSymException e) {
      //expected behavior
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
      //expected behavior
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
      //expected behavior
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
      //expected behavior
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

