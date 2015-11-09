import java.util.List;

public class FnSym extends SemSym {
  private List<String> params;
  public FnSym(String retType, List<String> paramTypes) {
    super(retType);
    params = paramTypes;
  }

  public List<String> getParamTypes() {
    return params;
  }
  
}

