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

    Sym sym1 = new Sym("test1");
    Sym sym2 = new Sym("test2");

    out.println("Checking that Sym getters return correct values...");
    if (sym1.getType() != "test1") out.println(err + "[0]");
    if (sym2.getType() != "test2") out.println(err + "[1]");
    if (sym1.toString() != sym1.getType()) out.println(err + "[2]");
    if (sym2.toString() != sym2.getType()) out.println(err + "[3]");

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
    try {
      for(int i=0; i < 10; i++) {
        if(s.lookupGlobal("key"+i) != syms[i]) {
          out.println(err + "idx " + i);
        }
      }
    } catch (Exception e) {
      out.println(err);
    }

    out.println("Checking adding a duplicate key...");
    success = false;
    try {
      //If the first HashMap in the list already contains the given name as a key, throw a DuplicateSymException.
      s.addDecl("key0", new Sym("diffval"));
      success = true;
    } catch (DuplicateSymException e) {
      //expected behavior
    } finally {
      if(success) {
        out.println(exc);
      }
    }

    out.println("Checking adding a symbol with null references...");
    success = false;
    try {
      //If either name or sym (or both) is null, throw a NullPointerException.
      s.addDecl(null, syms[0]);
      s.addDecl("key0", null);
      s.addDecl(null, null);
      success = true;
    } catch (NullPointerException e) {
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
      s.addDecl("key0", syms[0]);
      success = true;
    } catch (EmptySymTableException e) {
      //expected behavior
    } finally {
      if(success) {
        out.println(exc);
      }
    }

    out.println("Checking retrieving local value from empty table...");
    success = false;
    try {
      s.lookupLocal("key0");
      success = true;
    } catch (EmptySymTableException e) {
      //expected behavior
    } finally {
      if(success) {
        out.println(exc);
      }
    }

    out.println("Checking retrieving global value from empty table...");
    success = false;
    try {
      s.lookupGlobal("key0");
      success = true;
    } catch (EmptySymTableException e) {
      //expected behavior
    } finally {
      if(success) {
        out.println(exc);
      }
    }

    s.addScope();

    out.println("Checking adding multiple values to table with 1 scope...");
    try {
      s.addDecl("key0", syms[0]);
      s.addDecl("key1", syms[1]);
      s.addDecl("key2", syms[2]);
    } catch (Exception e) {
      out.println(err);
    }

    out.println("Checking retrieving those values locally...");
    if(s.lookupLocal("key0") != syms[0]) out.println(err + "idx0");
    if(s.lookupLocal("key1") != syms[1]) out.println(err + "idx1");
    if(s.lookupLocal("key2") != syms[2]) out.println(err + "idx2");
    if(s.lookupLocal("key3") != null) out.println(err + "idx3");

    out.println("Checking retrieving those values globally...");
    if(s.lookupGlobal("key0") != syms[0]) out.println(err + "idx0");
    if(s.lookupGlobal("key1") != syms[1]) out.println(err + "idx1");
    if(s.lookupGlobal("key2") != syms[2]) out.println(err + "idx2");
    if(s.lookupGlobal("key3") != null) out.println(err + "idx3");

    //adding another scope should not change global calls, but should make all local calls null
    s.addScope();

    out.println("Checking retrieving same values locally after adding a scope...");
    if(s.lookupLocal("key0") != null) out.println(err + "idx0");
    if(s.lookupLocal("key1") != null) out.println(err + "idx1");
    if(s.lookupLocal("key2") != null) out.println(err + "idx2");
    if(s.lookupLocal("key3") != null) out.println(err + "idx3");

    out.println("Checking retrieving those values globally...");
    if(s.lookupGlobal("key0") != syms[0]) out.println(err + "idx0");
    if(s.lookupGlobal("key1") != syms[1]) out.println(err + "idx1");
    if(s.lookupGlobal("key2") != syms[2]) out.println(err + "idx2");
    if(s.lookupGlobal("key3") != null) out.println(err + "idx3");

    out.println("Checking adding new local key to table with 2 scopes...");
    try {
      s.addDecl("key4", syms[4]);
    } catch (Exception e) {
      out.println(err);
    }

    out.println("Checking adding local key that overlaps with global scope to table with 2 scopes...");
    try {
      s.addDecl("key0", syms[6]);
    } catch (Exception e) {
      out.println(err);
    }

    out.println("Checking retrieving values locally...");
    if(s.lookupLocal("key0") != syms[6]) out.println(err + "idx0");
    if(s.lookupLocal("key1") != null) out.println(err + "idx1");
    if(s.lookupLocal("key2") != null) out.println(err + "idx2");
    if(s.lookupLocal("key3") != null) out.println(err + "idx3");
    if(s.lookupLocal("key4") != syms[4]) out.println(err + "idx4");

    out.println("Checking retrieving values globally...");
    if(s.lookupGlobal("key0") != syms[6]) out.println(err + "idx0");
    if(s.lookupGlobal("key1") != syms[1]) out.println(err + "idx1");
    if(s.lookupGlobal("key2") != syms[2]) out.println(err + "idx2");
    if(s.lookupGlobal("key3") != null) out.println(err + "idx3");
    if(s.lookupGlobal("key4") != syms[4]) out.println(err + "idx4");

    //uncomment these lines to print out structure of symbol table
    //leaving this commented out so minimize printed clutter for test
    /*
    s.print();
    s.removeScope();
    s.print();
    s.removeScope();
    s.print();
    */

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
