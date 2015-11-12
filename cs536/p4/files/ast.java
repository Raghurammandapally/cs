import java.io.*;
import java.util.*;

// **********************************************************************
// The ASTnode class defines the nodes of the abstract-syntax tree that
// represents a Mini program.
//
// Internal nodes of the tree contain pointers to children, organized
// either in a list (for nodes that may have a variable number of 
// children) or as a fixed set of fields.
//
// The nodes for literals and ids contain line and character number
// information; for string literals and identifiers, they also contain a
// string; for integer literals, they also contain an integer value.
//
// Here are all the different kinds of AST nodes and what kinds of children
// they have.  All of these kinds of AST nodes are subclasses of "ASTnode".
// Indentation indicates further subclassing:
//
//     Subclass            Kids
//     --------            ----
//     ProgramNode         DeclListNode
//     DeclListNode        linked list of DeclNode
//     DeclNode:
//       VarDeclNode       TypeNode, IdNode, int
//       FnDeclNode        TypeNode, IdNode, FormalsListNode, FnBodyNode
//       FormalDeclNode    TypeNode, IdNode
//       StructDeclNode    IdNode, DeclListNode
//
//     FormalsListNode     linked list of FormalDeclNode
//     FnBodyNode          DeclListNode, StmtListNode
//     StmtListNode        linked list of StmtNode
//     ExpListNode         linked list of ExpNode
//
//     TypeNode:
//       IntNode           -- none --
//       BoolNode          -- none --
//       VoidNode          -- none --
//       StructNode        IdNode
//
//     StmtNode:
//       AssignStmtNode      AssignNode
//       PostIncStmtNode     ExpNode
//       PostDecStmtNode     ExpNode
//       ReadStmtNode        ExpNode
//       WriteStmtNode       ExpNode
//       IfStmtNode          ExpNode, DeclListNode, StmtListNode
//       IfElseStmtNode      ExpNode, DeclListNode, StmtListNode,
//                                    DeclListNode, StmtListNode
//       WhileStmtNode       ExpNode, DeclListNode, StmtListNode
//       CallStmtNode        CallExpNode
//       ReturnStmtNode      ExpNode
//
//     ExpNode:
//       IntLitNode          -- none --
//       StrLitNode          -- none --
//       TrueNode            -- none --
//       FalseNode           -- none --
//       IdNode              -- none --
//       DotAccessNode       ExpNode, IdNode
//       AssignNode          ExpNode, ExpNode
//       CallExpNode         IdNode, ExpListNode
//       UnaryExpNode        ExpNode
//         UnaryMinusNode
//         NotNode
//       BinaryExpNode       ExpNode ExpNode
//         PlusNode     
//         MinusNode
//         TimesNode
//         DivideNode
//         AndNode
//         OrNode
//         EqualsNode
//         NotEqualsNode
//         LessNode
//         GreaterNode
//         LessEqNode
//         GreaterEqNode
//
// Here are the different kinds of AST nodes again, organized according to
// whether they are leaves, internal nodes with linked lists of kids, or
// internal nodes with a fixed number of kids:
//
// (1) Leaf nodes:
//        IntNode,   BoolNode,  VoidNode,  IntLitNode,  StrLitNode,
//        TrueNode,  FalseNode, IdNode
//
// (2) Internal nodes with (possibly empty) linked lists of children:
//        DeclListNode, FormalsListNode, StmtListNode, ExpListNode
//
// (3) Internal nodes with fixed numbers of kids:
//        ProgramNode,     VarDeclNode,     FnDeclNode,     FormalDeclNode,
//        StructDeclNode,  FnBodyNode,      StructNode,     AssignStmtNode,
//        PostIncStmtNode, PostDecStmtNode, ReadStmtNode,   WriteStmtNode   
//        IfStmtNode,      IfElseStmtNode,  WhileStmtNode,  CallStmtNode
//        ReturnStmtNode,  DotAccessNode,   AssignExpNode,  CallExpNode,
//        UnaryExpNode,    BinaryExpNode,   UnaryMinusNode, NotNode,
//        PlusNode,        MinusNode,       TimesNode,      DivideNode,
//        AndNode,         OrNode,          EqualsNode,     NotEqualsNode,
//        LessNode,        GreaterNode,     LessEqNode,     GreaterEqNode
//
// **********************************************************************

// **********************************************************************
// ASTnode class (base class for all other kinds of nodes)
// **********************************************************************

abstract class ASTnode { 
    // every subclass must provide an unparse operation
    abstract public void unparse(PrintWriter p, int indent);

    // this method can be used by the unparse methods to do indenting
    protected void doIndent(PrintWriter p, int indent) {
        for (int k=0; k<indent; k++) p.print(" ");
    }
}

// **********************************************************************
// ProgramNode,  DeclListNode, FormalsListNode, FnBodyNode,
// StmtListNode, ExpListNode
// **********************************************************************

class ProgramNode extends ASTnode {
    public ProgramNode(DeclListNode L) {
        myDeclList = L;
    }

    public void unparse(PrintWriter p, int indent) {
        myDeclList.unparse(p, indent);
    }

    public void analyze() {
      SymTable s = new SymTable();
      myDeclList.analyze(s);
      s.print();
    }

    // 1 kid
    private DeclListNode myDeclList;
}

class DeclListNode extends ASTnode {
    public DeclListNode(List<DeclNode> S) {
        myDecls = S;
    }

    public void unparse(PrintWriter p, int indent) {
        Iterator it = myDecls.iterator();
        try {
            while (it.hasNext()) {
                ((DeclNode)it.next()).unparse(p, indent);
            }
        } catch (NoSuchElementException ex) {
            System.err.println("unexpected NoSuchElementException in DeclListNode.print");
            System.exit(-1);
        }
    }

    public void analyze(SymTable s) {
        Iterator it = myDecls.iterator();
        try {
            while (it.hasNext()) {
                ((DeclNode)it.next()).analyze(s);
            }
        } catch (NoSuchElementException ex) {
            System.err.println("unexpected NoSuchElementException in DeclListNode.print");
            System.exit(-1);
        }
    }

    public void analyzeStruct(SymTable s, SymTable sLocal) {
      for(DeclNode d : myDecls) {
        if(!(d instanceof VarDeclNode)) {
          throw new RuntimeException();
        } else {
          ((VarDeclNode) d).analyzeStruct(s, sLocal);
          //((VarDeclNode) d).analyze(s);
          
        }
      }
    }

    // list of kids (DeclNodes)
    private List<DeclNode> myDecls;
}

class FormalsListNode extends ASTnode {
    public FormalsListNode(List<FormalDeclNode> S) {
        myFormals = S;
    }

    public void unparse(PrintWriter p, int indent) {
        Iterator<FormalDeclNode> it = myFormals.iterator();
        if (it.hasNext()) { // if there is at least one element
            it.next().unparse(p, indent);
            while (it.hasNext()) {  // print the rest of the list
                p.print(", ");
                it.next().unparse(p, indent);
            }
        } 
    }

    public List<String> getFormalList() {
      List<String> formals = new ArrayList<String>();
      for(FormalDeclNode f : myFormals) {
        formals.add(f.getType());
      }
      return formals;
    }

    public void analyze(SymTable s) {
      Iterator it = myFormals.iterator();
      try {
          while (it.hasNext()) {
              ((DeclNode)it.next()).analyze(s);
          }
      } catch (NoSuchElementException ex) {
          System.err.println("unexpected NoSuchElementException in DeclListNode.print");
          System.exit(-1);
      }
    }

    // list of kids (FormalDeclNodes)
    private List<FormalDeclNode> myFormals;
}

class FnBodyNode extends ASTnode {
    public FnBodyNode(DeclListNode declList, StmtListNode stmtList) {
        myDeclList = declList;
        myStmtList = stmtList;
    }

    public void unparse(PrintWriter p, int indent) {
        myDeclList.unparse(p, indent);
        myStmtList.unparse(p, indent);
    }

    public void analyze(SymTable s) {
      myDeclList.analyze(s);
      myStmtList.analyze(s);
    }

    // 2 kids
    private DeclListNode myDeclList;
    private StmtListNode myStmtList;
}

class StmtListNode extends ASTnode {
    public StmtListNode(List<StmtNode> S) {
        myStmts = S;
    }

    public void unparse(PrintWriter p, int indent) {
        Iterator<StmtNode> it = myStmts.iterator();
        while (it.hasNext()) {
            it.next().unparse(p, indent);
        }
    }

    public void analyze(SymTable s) {
        Iterator it = myStmts.iterator();
        try {
            while (it.hasNext()) {
                ((StmtNode)it.next()).analyze(s);
            }
        } catch (NoSuchElementException ex) {
            System.err.println("unexpected NoSuchElementException in DeclListNode.print");
            System.exit(-1);
        }
    }

    // list of kids (StmtNodes)
    private List<StmtNode> myStmts;
}

class ExpListNode extends ASTnode {
    public ExpListNode(List<ExpNode> S) {
        myExps = S;
    }

    public void unparse(PrintWriter p, int indent) {
        Iterator<ExpNode> it = myExps.iterator();
        ExpNode e;
        if (it.hasNext()) { // if there is at least one element
            e = it.next();
            e.unparse(p, indent);
            while (it.hasNext()) {  // print the rest of the list
              e = it.next();
              p.print(", ");
              e.unparse(p, indent);
            }
        } 
    }

    public void analyze(SymTable s) {
        Iterator<ExpNode> it = myExps.iterator();
        try {
            while (it.hasNext()) {
              it.next().analyze(s);
            }
        } catch (NoSuchElementException ex) {
            System.err.println("unexpected NoSuchElementException in DeclListNode.print");
            System.exit(-1);
        }
    }

    // list of kids (ExpNodes)
    private List<ExpNode> myExps;
}

// **********************************************************************
// DeclNode and its subclasses
// **********************************************************************

abstract class DeclNode extends ASTnode {
    abstract public void analyze(SymTable s);
}

class VarDeclNode extends DeclNode {
    public VarDeclNode(TypeNode type, IdNode id, int size) {
        myType = type;
        myId = id;
        mySize = size;
    }

    public void unparse(PrintWriter p, int indent) {
        doIndent(p, indent);
        myType.unparse(p, 0);
        p.print(" ");
        myId.unparse(p, 0);
        p.println(";");
    }

    public void analyze(SymTable s) {
      SemSym sym;
      if(myType instanceof VoidNode) {
        ErrMsg.fatal(myId.getLineNum(), myId.getCharNum(), "Non-function declared void");
      }
      if(myType instanceof StructNode) {
        IdNode id = ((StructNode) myType).getId();
        sym = s.lookupGlobal(id.getStrVal());
        if(sym == null) {
          ErrMsg.fatal(id.getLineNum(), id.getCharNum(), "Invalid name of struct type");
          return;
        } else {
          sym = new StructUsageSym("struct", id.getStrVal());
        }
      } else {
        sym = new SemSym(myType.getType());
      }
      try {
        s.addDecl(myId.getStrVal(), sym);
      } catch (EmptySymTableException e1) {
        throw new RuntimeException();
      } catch (DuplicateSymException e2) {
        ErrMsg.fatal(myId.getLineNum(), myId.getCharNum(), "Multiply declared identifier");
      }
    }

    public void analyzeStruct(SymTable s, SymTable sLocal) {
      SemSym sym;
      //void
      if(myType instanceof VoidNode) {
        ErrMsg.fatal(myId.getLineNum(), myId.getCharNum(), "Non-function declared void");
      }
      //struct
      if(myType instanceof StructNode) {
        IdNode id = ((StructNode) myType).getId();
        sym = s.lookupGlobal(id.getStrVal());
        if(sym == null) {
          ErrMsg.fatal(id.getLineNum(), id.getCharNum(), "Invalid name of struct type");
          return;
        } else {
          if(!sym.getType().equals("struct")) {
            ErrMsg.fatal(id.getLineNum(), id.getCharNum(), "Invalid name of struct type");
            return;
          }
        }
        sym = new StructUsageSym("struct", id.getStrVal());
      } else {
        sym = new SemSym(myType.getType());
      }

      try {
        sLocal.addDecl(myId.getStrVal(), sym);
      } catch (EmptySymTableException e1) {
        throw new RuntimeException();
      } catch (DuplicateSymException e2) {
        ErrMsg.fatal(myId.getLineNum(), myId.getCharNum(), "Multiply declared identifier");
      }

      
    }

    // 3 kids
    private TypeNode myType;
    private IdNode myId;
    private int mySize;  // use value NOT_STRUCT if this is not a struct type

    public static int NOT_STRUCT = -1;
}

class FnDeclNode extends DeclNode {
    public FnDeclNode(TypeNode type,
                      IdNode id,
                      FormalsListNode formalList,
                      FnBodyNode body) {
        myType = type;
        myId = id;
        myFormalsList = formalList;
        myBody = body;
    }

    public void unparse(PrintWriter p, int indent) {
        doIndent(p, indent);
        myType.unparse(p, 0);
        p.print(" ");
        myId.unparse(p, 0);
        p.print("(");
        myFormalsList.unparse(p, 0);
        p.println(") {");
        myBody.unparse(p, indent+4);
        p.println("}\n");
    }

    public void analyze(SymTable s) {
      FnSym sym = new FnSym(myType.getType(), myFormalsList.getFormalList());
      try {
        s.addDecl(myId.getStrVal(), sym);
      } catch (EmptySymTableException e1) {
        throw new RuntimeException();
      } catch (DuplicateSymException e2) {
        ErrMsg.fatal(myId.getLineNum(), myId.getCharNum(), "Multiply declared identifier");
      }

      //myId.addSym(sym);
      s.addScope();
      myFormalsList.analyze(s);
      myBody.analyze(s);
      try {
        s.removeScope();
      } catch (EmptySymTableException e1) {
        throw new RuntimeException();
      }
    }

    // 4 kids
    private TypeNode myType;
    private IdNode myId;
    private FormalsListNode myFormalsList;
    private FnBodyNode myBody;
}

class FormalDeclNode extends DeclNode {
    public FormalDeclNode(TypeNode type, IdNode id) {
        myType = type;
        myId = id;
    }

    public void unparse(PrintWriter p, int indent) {
        myType.unparse(p, 0);
        p.print(" ");
        myId.unparse(p, 0);
    }

    public void analyze(SymTable s) {
      if(myType instanceof VoidNode) {
        ErrMsg.fatal(myId.getLineNum(), myId.getCharNum(), "Non-function declared void");
      }
      SemSym sym = new SemSym(myType.getType());
      try {
        s.addDecl(myId.getStrVal(), sym);
      } catch (EmptySymTableException e1) {
        throw new RuntimeException();
      } catch (DuplicateSymException e2) {
        ErrMsg.fatal(myId.getLineNum(), myId.getCharNum(), "Multiply declared identifier");
      }
    }

    public String getType() {
      return myType.getType();
    }

    // 2 kids
    private TypeNode myType;
    private IdNode myId;
}

class StructDeclNode extends DeclNode {
    public StructDeclNode(IdNode id, DeclListNode declList) {
        myId = id;
        myDeclList = declList;
    }

    public void unparse(PrintWriter p, int indent) {
        doIndent(p, indent);
        p.print("struct ");
        myId.unparse(p, 0);
        p.println("{");
        myDeclList.unparse(p, indent+4);
        doIndent(p, indent);
        p.println("};\n");

    }

    public void analyze(SymTable s) {
      StructSym sym = new StructSym("struct");
      try {
        s.addDecl(myId.getStrVal(), sym);
      } catch (EmptySymTableException e1) {
        throw new RuntimeException();
      } catch (DuplicateSymException e2) {
        ErrMsg.fatal(myId.getLineNum(), myId.getCharNum(), "Multiply declared identifier");
      }

      SymTable sLocal = new SymTable();
      myDeclList.analyzeStruct(s, sLocal);
      sym.setSymTable(sLocal);
    }

    // 2 kids
    private IdNode myId;
	private DeclListNode myDeclList;
}

// **********************************************************************
// TypeNode and its Subclasses
// **********************************************************************

abstract class TypeNode extends ASTnode {
    abstract public String getType();
}

class IntNode extends TypeNode {
    public IntNode() {
    }

    public String getType() {
      return "int";
    }

    public void unparse(PrintWriter p, int indent) {
        p.print("int");
    }
}

class BoolNode extends TypeNode {
    public BoolNode() {
    }

    public String getType() {
      return "bool";
    }

    public void unparse(PrintWriter p, int indent) {
        p.print("bool");
    }
}

class VoidNode extends TypeNode {
    public VoidNode() {
    }

    public String getType() {
      return "void";
    }

    public void unparse(PrintWriter p, int indent) {
        p.print("void");
    }
}

class StructNode extends TypeNode {
    public StructNode(IdNode id) {
      myId = id;
    }

    public String getType() {
      return "struct";
    }

    public void unparse(PrintWriter p, int indent) {
        p.print("struct ");
        myId.unparse(p, 0);
    }

    public IdNode getId() {
      return myId;
    }
	
	// 1 kid
    private IdNode myId;
}

// **********************************************************************
// StmtNode and its subclasses
// **********************************************************************

abstract class StmtNode extends ASTnode {
  abstract public void analyze(SymTable s);
  abstract public void unparseWithType(PrintWriter p, int indent);
}

class AssignStmtNode extends StmtNode {
    public AssignStmtNode(AssignNode assign) {
        myAssign = assign;
    }

    public void unparse(PrintWriter p, int indent) {
        doIndent(p, indent);
        myAssign.unparse(p, -1); // no parentheses
        p.println(";");
    }

    public void unparseWithType(PrintWriter p, int indent) {
    }

    public void analyze(SymTable s) {
      myAssign.analyze(s);
    }

    // 1 kid
    private AssignNode myAssign;
}

class PostIncStmtNode extends StmtNode {
    public PostIncStmtNode(ExpNode exp) {
        myExp = exp;
    }

    public void unparse(PrintWriter p, int indent) {
        doIndent(p, indent);
        myExp.unparse(p, 0);
        p.println("++;");
    }

    public void unparseWithType(PrintWriter p, int indent) {
    }

    public void analyze(SymTable s) {
      myExp.analyze(s);
    }

    // 1 kid
    private ExpNode myExp;
}

class PostDecStmtNode extends StmtNode {
    public PostDecStmtNode(ExpNode exp) {
        myExp = exp;
    }

    public void unparse(PrintWriter p, int indent) {
        doIndent(p, indent);
        myExp.unparse(p, 0);
        p.println("--;");
    }

    public void unparseWithType(PrintWriter p, int indent) {
    }

    public void analyze(SymTable s) {
      myExp.analyze(s);
    }

    // 1 kid
    private ExpNode myExp;
}

class ReadStmtNode extends StmtNode {
    public ReadStmtNode(ExpNode e) {
        myExp = e;
    }

    public void unparse(PrintWriter p, int indent) {
        doIndent(p, indent);
        p.print("cin >> ");
        myExp.unparse(p, 0);
        p.println(";");
    }

    public void unparseWithType(PrintWriter p, int indent) {
    }

    public void analyze(SymTable s) {
      myExp.analyze(s);
    }

    // 1 kid (actually can only be an IdNode or an ArrayExpNode)
    private ExpNode myExp;
}

class WriteStmtNode extends StmtNode {
    public WriteStmtNode(ExpNode exp) {
        myExp = exp;
    }

    public void unparse(PrintWriter p, int indent) {
        doIndent(p, indent);
        p.print("cout << ");
        myExp.unparse(p, 0);
        p.println(";");
    }

    public void unparseWithType(PrintWriter p, int indent) {
    }

    public void analyze(SymTable s) {
      myExp.analyze(s);
    }

    // 1 kid
    private ExpNode myExp;
}

class IfStmtNode extends StmtNode {
    public IfStmtNode(ExpNode exp, DeclListNode dlist, StmtListNode slist) {
        myDeclList = dlist;
        myExp = exp;
        myStmtList = slist;
    }

    public void unparse(PrintWriter p, int indent) {
        doIndent(p, indent);
        p.print("if (");
        myExp.unparse(p, 0);
        p.println(") {");
        myDeclList.unparse(p, indent+4);
        myStmtList.unparse(p, indent+4);
        doIndent(p, indent);
        p.println("}");
    }

    public void unparseWithType(PrintWriter p, int indent) {
    }

    public void analyze(SymTable s) {
      myExp.analyze(s);
      s.addScope();
      myDeclList.analyze(s);
      myStmtList.analyze(s);
      try {
        s.removeScope();
      } catch (EmptySymTableException e1) {
        throw new RuntimeException();
      }
    }

    // e kids
    private ExpNode myExp;
    private DeclListNode myDeclList;
    private StmtListNode myStmtList;
}

class IfElseStmtNode extends StmtNode {
    public IfElseStmtNode(ExpNode exp, DeclListNode dlist1,
                          StmtListNode slist1, DeclListNode dlist2,
                          StmtListNode slist2) {
        myExp = exp;
        myThenDeclList = dlist1;
        myThenStmtList = slist1;
        myElseDeclList = dlist2;
        myElseStmtList = slist2;
    }

    public void unparse(PrintWriter p, int indent) {
        doIndent(p, indent);
        p.print("if (");
        myExp.unparse(p, 0);
        p.println(") {");
        myThenDeclList.unparse(p, indent+4);
        myThenStmtList.unparse(p, indent+4);
        doIndent(p, indent);
        p.println("}");
        doIndent(p, indent);
        p.println("else {");
        myElseDeclList.unparse(p, indent+4);
        myElseStmtList.unparse(p, indent+4);
        doIndent(p, indent);
        p.println("}");        
    }

    public void unparseWithType(PrintWriter p, int indent) {
    }

    public void analyze(SymTable s) {
      myExp.analyze(s);
      s.addScope();
      myThenDeclList.analyze(s);
      myThenStmtList.analyze(s);
      try {
        s.removeScope();
      } catch (EmptySymTableException e1) {
        throw new RuntimeException();
      }
      s.addScope();
      myElseDeclList.analyze(s);
      myElseStmtList.analyze(s);
      try {
        s.removeScope();
      } catch (EmptySymTableException e1) {
        throw new RuntimeException();
      }
    }

    // 5 kids
    private ExpNode myExp;
    private DeclListNode myThenDeclList;
    private StmtListNode myThenStmtList;
    private StmtListNode myElseStmtList;
    private DeclListNode myElseDeclList;
}

class WhileStmtNode extends StmtNode {
    public WhileStmtNode(ExpNode exp, DeclListNode dlist, StmtListNode slist) {
        myExp = exp;
        myDeclList = dlist;
        myStmtList = slist;
    }
	
    public void unparse(PrintWriter p, int indent) {
        doIndent(p, indent);
        p.print("while (");
        myExp.unparse(p, 0);
        p.println(") {");
        myDeclList.unparse(p, indent+4);
        myStmtList.unparse(p, indent+4);
        doIndent(p, indent);
        p.println("}");
    }

    public void unparseWithType(PrintWriter p, int indent) {
    }


    public void analyze(SymTable s) {
      myExp.analyze(s);
      s.addScope();
      myDeclList.analyze(s);
      myStmtList.analyze(s);
      try {
        s.removeScope();
      } catch (EmptySymTableException e1) {
        throw new RuntimeException();
      }
    }

    // 3 kids
    private ExpNode myExp;
    private DeclListNode myDeclList;
    private StmtListNode myStmtList;
}

class CallStmtNode extends StmtNode {
    public CallStmtNode(CallExpNode call) {
        myCall = call;
    }

    public void unparse(PrintWriter p, int indent) {
        doIndent(p, indent);
        myCall.unparse(p, indent);
        p.println(";");
    }

    public void unparseWithType(PrintWriter p, int indent) {
    }

    public void analyze(SymTable s) {
      myCall.analyze(s);
    }

    // 1 kid
    private CallExpNode myCall;
}

class ReturnStmtNode extends StmtNode {
    public ReturnStmtNode(ExpNode exp) {
        myExp = exp;
    }

    public void unparse(PrintWriter p, int indent) {
        doIndent(p, indent);
        p.print("return");
        if (myExp != null) {
            p.print(" ");
            myExp.unparse(p, 0);
        }
        p.println(";");
    }

    public void unparseWithType(PrintWriter p, int indent) {
    }

    public void analyze(SymTable s) {
      myExp.analyze(s);
    }

    // 1 kid
    private ExpNode myExp; // possibly null
}

// **********************************************************************
// ExpNode and its subclasses
// **********************************************************************

abstract class ExpNode extends ASTnode {
  abstract public void analyze(SymTable s);
}

class IntLitNode extends ExpNode {
    public IntLitNode(int lineNum, int charNum, int intVal) {
        myLineNum = lineNum;
        myCharNum = charNum;
        myIntVal = intVal;
    }

    public void unparse(PrintWriter p, int indent) {
        p.print(myIntVal);
    }

    public void analyze(SymTable s) {
    }

    private int myLineNum;
    private int myCharNum;
    private int myIntVal;
}

class StringLitNode extends ExpNode {
    public StringLitNode(int lineNum, int charNum, String strVal) {
        myLineNum = lineNum;
        myCharNum = charNum;
        myStrVal = strVal;
    }

    public void unparse(PrintWriter p, int indent) {
        p.print(myStrVal);
    }

    public void analyze(SymTable s) {
    }

    private int myLineNum;
    private int myCharNum;
    private String myStrVal;
}

class TrueNode extends ExpNode {
    public TrueNode(int lineNum, int charNum) {
        myLineNum = lineNum;
        myCharNum = charNum;
    }

    public void unparse(PrintWriter p, int indent) {
        p.print("true");
    }

    public void analyze(SymTable s) {
    }

    private int myLineNum;
    private int myCharNum;
}

class FalseNode extends ExpNode {
    public FalseNode(int lineNum, int charNum) {
        myLineNum = lineNum;
        myCharNum = charNum;
    }

    public void unparse(PrintWriter p, int indent) {
        p.print("false");
    }

    public void analyze(SymTable s) {
    }

    private int myLineNum;
    private int myCharNum;
}

class IdNode extends ExpNode {
    public IdNode(int lineNum, int charNum, String strVal) {
        myLineNum = lineNum;
        myCharNum = charNum;
        myStrVal = strVal;
    }

    public void unparse(PrintWriter p, int indent) {
        p.print(myStrVal);
        if(mySym != null) {
          p.print("(" + mySym + ")");
        }
    }

    public void addSym(SemSym s) {
      mySym = s;
    }
    
    public SemSym getSym() {
      return mySym;
    }

    public int getLineNum() {
      return myLineNum;
    }

    public int getCharNum() {
      return myCharNum;
    }

    public String getStrVal() {
      return myStrVal;
    }

    public void analyze(SymTable s) {
      SemSym sym = s.lookupGlobal(myStrVal);
      if(sym == null) {
        ErrMsg.fatal(myLineNum, myCharNum, "Undeclared identifier");
      } else {
        mySym = sym;
      }
    }

    private int myLineNum;
    private int myCharNum;
    private String myStrVal;
    private SemSym mySym;
}

class DotAccessExpNode extends ExpNode {
    public DotAccessExpNode(ExpNode loc, IdNode id) {
        myLoc = loc;	
        myId = id;
    }

    public void unparse(PrintWriter p, int indent) {
	    p.print("(");
      myLoc.unparse(p, 0);
      p.print(").");
      myId.unparse(p, 0);
    }

    public void analyze(SymTable s) {
      analyzeDot(s);
    }

    public SemSym analyzeDot(SymTable s) {
      SemSym lhs;
      if(myLoc instanceof IdNode) {
        IdNode locId = (IdNode) myLoc;
        lhs = s.lookupGlobal(locId.getStrVal());
        if(lhs == null) {
          ErrMsg.fatal(locId.getLineNum(), locId.getCharNum(), "Undeclared identifier");
          return null;
        } else {
          locId.addSym(lhs);
        }
      // myLoc instanceof DotAccessExpNode
      } else {
        DotAccessExpNode locDot = (DotAccessExpNode) myLoc;
        lhs = locDot.analyzeDot(s);
        if(lhs == null) {
          // pass failure up chain if error found further left
          return null;
        }
      }

      // now we have found a Sym recursively for the lhs
      if(!(lhs instanceof StructUsageSym)) {
        ErrMsg.fatal(getLineNum(), getCharNum(), "Dot-access of non-struct type");
        return null;
      } else {
        StructUsageSym struct = (StructUsageSym) lhs;
        SemSym sym = s.lookupGlobal(struct.getName());
        StructSym structSym = (StructSym) sym;
        if(structSym == null) {
          throw new RuntimeException();
        } else {
          SymTable sLocal = structSym.getSymTable();
          SemSym rhs = sLocal.lookupGlobal(myId.getStrVal());
          if(rhs == null) {
            ErrMsg.fatal(myId.getLineNum(), myId.getCharNum(), "Invalid struct field name");
            return null;
          } else {
            // add sym to IdNode
            myId.addSym(rhs);
            return rhs;
          }
        }
      }
    }

    public int getCharNum() {
      if(myLoc instanceof IdNode) {
        return ((IdNode) myLoc).getCharNum();
      } else {
        return ((DotAccessExpNode) myLoc).getCharNum();
      }
    }

    public int getLineNum() {
      if(myLoc instanceof IdNode) {
        return ((IdNode) myLoc).getLineNum();
      } else {
        return ((DotAccessExpNode)myLoc).getLineNum();
      }
    }

    // 2 kids
    private ExpNode myLoc;	
    private IdNode myId;
    private SemSym mySym;
}

class AssignNode extends ExpNode {
    public AssignNode(ExpNode lhs, ExpNode exp) {
        myLhs = lhs;
        myExp = exp;
    }

    public void unparse(PrintWriter p, int indent) {
		if (indent != -1)  p.print("(");
	    myLhs.unparse(p, 0);
		p.print(" = ");
		myExp.unparse(p, 0);
		if (indent != -1)  p.print(")");
    }

    public void analyze(SymTable s) {
      myLhs.analyze(s);
      myExp.analyze(s);
    }

    // 2 kids
    private ExpNode myLhs;
    private ExpNode myExp;
}

class CallExpNode extends ExpNode {
    public CallExpNode(IdNode name, ExpListNode elist) {
        myId = name;
        myExpList = elist;
    }

    public CallExpNode(IdNode name) {
        myId = name;
        myExpList = new ExpListNode(new LinkedList<ExpNode>());
    }

    // ** unparse **
    public void unparse(PrintWriter p, int indent) {
      myId.unparse(p, 0);
      p.print("(");
      if (myExpList != null) {
        myExpList.unparse(p, 0);
      }
      p.print(")");
    }

    public void analyze(SymTable s) {
      myId.analyze(s);
      myExpList.analyze(s);
    }

    // 2 kids
    private IdNode myId;
    private ExpListNode myExpList;  // possibly null
}

abstract class UnaryExpNode extends ExpNode {
    public UnaryExpNode(ExpNode exp) {
        myExp = exp;
    }

    public void analyze(SymTable s) {
      myExp.analyze(s);
    }

    // one child
    protected ExpNode myExp;
}

abstract class BinaryExpNode extends ExpNode {
    public BinaryExpNode(ExpNode exp1, ExpNode exp2) {
        myExp1 = exp1;
        myExp2 = exp2;
    }

    public void analyze(SymTable s) {
      myExp1.analyze(s);
      myExp2.analyze(s);
    }

    // two kids
    protected ExpNode myExp1;
    protected ExpNode myExp2;
}

// **********************************************************************
// Subclasses of UnaryExpNode
// **********************************************************************

class UnaryMinusNode extends UnaryExpNode {
    public UnaryMinusNode(ExpNode exp) {
        super(exp);
    }

    public void unparse(PrintWriter p, int indent) {
	    p.print("(-");
		myExp.unparse(p, 0);
		p.print(")");
    }
}

class NotNode extends UnaryExpNode {
    public NotNode(ExpNode exp) {
        super(exp);
    }

    public void unparse(PrintWriter p, int indent) {
	    p.print("(!");
		myExp.unparse(p, 0);
		p.print(")");
    }
}

// **********************************************************************
// Subclasses of BinaryExpNode
// **********************************************************************

class PlusNode extends BinaryExpNode {
    public PlusNode(ExpNode exp1, ExpNode exp2) {
        super(exp1, exp2);
    }

    public void unparse(PrintWriter p, int indent) {
	    p.print("(");
		myExp1.unparse(p, 0);
		p.print(" + ");
		myExp2.unparse(p, 0);
		p.print(")");
    }
}

class MinusNode extends BinaryExpNode {
    public MinusNode(ExpNode exp1, ExpNode exp2) {
        super(exp1, exp2);
    }

    public void unparse(PrintWriter p, int indent) {
	    p.print("(");
		myExp1.unparse(p, 0);
		p.print(" - ");
		myExp2.unparse(p, 0);
		p.print(")");
    }
}

class TimesNode extends BinaryExpNode {
    public TimesNode(ExpNode exp1, ExpNode exp2) {
        super(exp1, exp2);
    }

    public void unparse(PrintWriter p, int indent) {
	    p.print("(");
		myExp1.unparse(p, 0);
		p.print(" * ");
		myExp2.unparse(p, 0);
		p.print(")");
    }
}

class DivideNode extends BinaryExpNode {
    public DivideNode(ExpNode exp1, ExpNode exp2) {
        super(exp1, exp2);
    }

    public void unparse(PrintWriter p, int indent) {
	    p.print("(");
		myExp1.unparse(p, 0);
		p.print(" / ");
		myExp2.unparse(p, 0);
		p.print(")");
    }
}

class AndNode extends BinaryExpNode {
    public AndNode(ExpNode exp1, ExpNode exp2) {
        super(exp1, exp2);
    }

    public void unparse(PrintWriter p, int indent) {
	    p.print("(");
		myExp1.unparse(p, 0);
		p.print(" && ");
		myExp2.unparse(p, 0);
		p.print(")");
    }
}

class OrNode extends BinaryExpNode {
    public OrNode(ExpNode exp1, ExpNode exp2) {
        super(exp1, exp2);
    }

    public void unparse(PrintWriter p, int indent) {
	    p.print("(");
		myExp1.unparse(p, 0);
		p.print(" || ");
		myExp2.unparse(p, 0);
		p.print(")");
    }
}

class EqualsNode extends BinaryExpNode {
    public EqualsNode(ExpNode exp1, ExpNode exp2) {
        super(exp1, exp2);
    }

    public void unparse(PrintWriter p, int indent) {
	    p.print("(");
		myExp1.unparse(p, 0);
		p.print(" == ");
		myExp2.unparse(p, 0);
		p.print(")");
    }
}

class NotEqualsNode extends BinaryExpNode {
    public NotEqualsNode(ExpNode exp1, ExpNode exp2) {
        super(exp1, exp2);
    }

    public void unparse(PrintWriter p, int indent) {
	    p.print("(");
		myExp1.unparse(p, 0);
		p.print(" != ");
		myExp2.unparse(p, 0);
		p.print(")");
    }
}

class LessNode extends BinaryExpNode {
    public LessNode(ExpNode exp1, ExpNode exp2) {
        super(exp1, exp2);
    }

    public void unparse(PrintWriter p, int indent) {
	    p.print("(");
		myExp1.unparse(p, 0);
		p.print(" < ");
		myExp2.unparse(p, 0);
		p.print(")");
    }
}

class GreaterNode extends BinaryExpNode {
    public GreaterNode(ExpNode exp1, ExpNode exp2) {
        super(exp1, exp2);
    }

    public void unparse(PrintWriter p, int indent) {
	    p.print("(");
		myExp1.unparse(p, 0);
		p.print(" > ");
		myExp2.unparse(p, 0);
		p.print(")");
    }
}

class LessEqNode extends BinaryExpNode {
    public LessEqNode(ExpNode exp1, ExpNode exp2) {
        super(exp1, exp2);
    }

    public void unparse(PrintWriter p, int indent) {
	    p.print("(");
		myExp1.unparse(p, 0);
		p.print(" <= ");
		myExp2.unparse(p, 0);
		p.print(")");
    }
}

class GreaterEqNode extends BinaryExpNode {
    public GreaterEqNode(ExpNode exp1, ExpNode exp2) {
        super(exp1, exp2);
    }

    public void unparse(PrintWriter p, int indent) {
	    p.print("(");
		myExp1.unparse(p, 0);
		p.print(" >= ");
		myExp2.unparse(p, 0);
		p.print(")");
    }
}
