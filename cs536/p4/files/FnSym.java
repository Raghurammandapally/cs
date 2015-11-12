import java.util.List;
import java.lang.StringBuilder;
import java.util.Iterator;

public class FnSym extends SemSym {
  private List<String> params;
  public FnSym(String retType, List<String> paramTypes) {
    super(retType);
    params = paramTypes;
  }

  public List<String> getParamTypes() {
    return params;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    Iterator<String> itr = params.iterator();
    while(itr.hasNext()) {
      sb.append(itr.next());
      if(itr.hasNext()) {
        sb.append(",");
      }
    }

    sb.append("->");
    sb.append(getType());
      
    String ret = sb.toString();  
    return ret;
  }
  
}

