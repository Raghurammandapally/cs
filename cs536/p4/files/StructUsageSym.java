public class StructUsageSym extends SemSym {
  private String myStructName;
  public StructUsageSym(String type, String structName) {
    super("struct");
    myStructName = structName;
  }
  public String getName() {
    return myStructName;
  }
}

