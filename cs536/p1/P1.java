import static java.lang.System.out;

public class P1 {
  public static void main (String[] args) throws EmptySymTableException,
                                                 DuplicateSymException {
    // setting up default strings for printing errors
    String err = "  ERROR!";
    String exc = "  THIS SHOULD HAVE FAILED!";
     
    // this is used for testing exception throws
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
    Sym sym3 = new Sym("test2");

    out.println("Checking that Sym getters return correct values...");
    // getType should just return the strings they were instantiated with
    if (!sym1.getType().equals("test1")) out.println(err + "[0]");
    if (!sym2.getType().equals("test2")) out.println(err + "[1]");
    // toString should return the same as getType
    if (!sym1.toString().equals(sym1.getType())) out.println(err + "[2]");
    if (!sym2.toString().equals(sym2.getType())) out.println(err + "[3]");
    // their types should be the same ("test2")
    if (!sym2.getType().equals(sym3.getType())) out.println(err + "[4]");
    // these should be distinct instantiations
    if (sym2 == sym3) out.println(err + "[5]");

    //initialize an array of test Sym's:
    //  syms[0] = new Sym("val0");
    //  syms[1] = new Sym("val1");
    //      ...
    //  syms[9] = new Sym("val9");
    Sym[] syms = new Sym[10];
    for (int i = 0; i < 10; i++) {
      syms[i] = new Sym("val" + i);
    }

    // after this, the local scope of the table will have 10 key/value pairs
    out.println("Checking adding multiple values to empty table...");
    try {
      for (int i = 0; i < 10; i++) {
        s.addDecl("key"+i, syms[i]);
      }
    } catch (Exception e) {
      out.println(err);
    }

    // we should be able to retrieve those same values locally with no issue
    out.println("Checking retrieving those values locally...");
    try {
      for (int i = 0; i < 10; i++) {
        if(s.lookupLocal("key"+i) != syms[i]) {
          out.println(err + "idx " + i);
        }
      }
    } catch (Exception e) {
      out.println(err);
    }

    // ...globally should be the same, since we only have one scope at this 
    // point
    out.println("Checking retrieving those values globally...");
    try {
      for (int i = 0; i < 10; i++) {
        if(s.lookupGlobal("key"+i) != syms[i]) {
          out.println(err + "idx " + i);
        }
      }
    } catch (Exception e) {
      out.println(err);
    }

    // we should be throwing a DuplicateSymException for trying to add a 
    // duplicate key within the local scope
    out.println("Checking adding a duplicate key...");
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

    out.println("Checking that failed addition of duplicate key did not " +
        "modify the previous value...");
    if(!s.lookupLocal("key0").getType().equals("val0")) out.println(err);
    if(!s.lookupGlobal("key0").getType().equals("val0")) out.println(err);


    // if either name or sym (or both) is null, we should throw a 
    // NullPointerException
    out.println("Checking adding a symbol with null references...");
    success = false;
    try {
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
    
    // this should succeed with no error
    out.println("Checking emptying a non-empty table...");
    try {
      s.removeScope();
    } catch (Exception e) {
      out.println(err);
    }

    // after removing the (last) local scope, however, we should not be able 
    // to remove another
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

    out.println("Checking adding to a table with no scopes...");
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

    //adding many more scopes should not change global calls, but should make 
    //all local calls null
    for (int i = 0; i < 10; i++) {
      s.addScope();
    }

    out.println("Checking retrieving same values locally after adding a " + 
        "scope...");
    if(s.lookupLocal("key0") != null) out.println(err + "idx0");
    if(s.lookupLocal("key1") != null) out.println(err + "idx1");
    if(s.lookupLocal("key2") != null) out.println(err + "idx2");
    if(s.lookupLocal("key3") != null) out.println(err + "idx3");

    out.println("Checking retrieving those values globally...");
    if(s.lookupGlobal("key0") != syms[0]) out.println(err + "idx0");
    if(s.lookupGlobal("key1") != syms[1]) out.println(err + "idx1");
    if(s.lookupGlobal("key2") != syms[2]) out.println(err + "idx2");
    if(s.lookupGlobal("key3") != null) out.println(err + "idx3");

    //"key4", a new key, should return a value when searched locally or
    //globally
    out.println("Checking adding new local key to table with 2 scopes...");
    try {
      s.addDecl("key4", syms[4]);
    } catch (Exception e) {
      out.println(err);
    }

    //"key0" should now return syms[6] if searched locally or globally, rather
    //than syms[0] as previously
    out.println("Checking adding local key that overlaps with global scope " + 
        "to table with 2 scopes...");
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
    //leaving this commented out to minimize printed clutter for test
    /*
    s.print();
    s.removeScope();
    s.print();
    s.removeScope();
    s.print();
    */

  }
}
