import java.util.List;

public class StructSym extends SemSym {
  private SymTable mySymTable;
  public StructSym(String type) {//, SymTable symTable) {
    super(type);
  }

  public void setSymTable(SymTable symTable) {
    mySymTable = symTable;
  }

  public SymTable getSymTable() {
    return mySymTable;
  }

}

