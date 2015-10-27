
//----------------------------------------------------
// The following code was generated by CUP v0.11b ${cupversion} (SVN rev 60)
//----------------------------------------------------

import java_cup.runtime.*;
import java.util.*;
import java_cup.runtime.XMLElement;

/** CUP v0.11b ${cupversion} (SVN rev 60) generated parser.
  */
@SuppressWarnings({"rawtypes"})
public class parser extends java_cup.runtime.lr_parser {

 public final Class getSymbolContainer() {
    return sym.class;
}

  /** Default constructor. */
  public parser() {super();}

  /** Constructor which sets the default scanner. */
  public parser(java_cup.runtime.Scanner s) {super(s);}

  /** Constructor which sets the default scanner. */
  public parser(java_cup.runtime.Scanner s, java_cup.runtime.SymbolFactory sf) {super(s,sf);}

  /** Production table. */
  protected static final short _production_table[][] = 
    unpackFromStrings(new String[] {
    "\000\046\000\002\002\004\000\002\002\003\000\002\003" +
    "\004\000\002\003\002\000\002\004\003\000\002\004\003" +
    "\000\002\004\003\000\002\005\005\000\002\010\010\000" +
    "\002\011\006\000\002\012\004\000\002\012\005\000\002" +
    "\015\003\000\002\015\005\000\002\016\004\000\002\013" +
    "\006\000\002\014\004\000\002\014\002\000\002\017\004" +
    "\000\002\017\004\000\002\023\003\000\002\023\005\000" +
    "\002\022\005\000\002\022\006\000\002\024\003\000\002" +
    "\025\003\000\002\025\003\000\002\025\003\000\002\025" +
    "\003\000\002\025\003\000\002\025\005\000\002\025\003" +
    "\000\002\006\003\000\002\006\003\000\002\006\003\000" +
    "\002\021\003\000\002\021\005\000\002\007\003" });

  /** Access to production table. */
  public short[][] production_table() {return _production_table;}

  /** Parse-action table. */
  protected static final short[][] _action_table = 
    unpackFromStrings(new String[] {
    "\000\076\000\014\002\ufffe\004\ufffe\005\ufffe\006\ufffe\011" +
    "\ufffe\001\002\000\014\002\000\004\010\005\011\006\013" +
    "\011\012\001\002\000\004\002\006\001\002\000\004\002" +
    "\001\001\002\000\022\002\ufffd\004\ufffd\005\ufffd\006\ufffd" +
    "\011\ufffd\017\ufffd\020\ufffd\024\ufffd\001\002\000\004\020" +
    "\uffdf\001\002\000\004\020\uffe0\001\002\000\004\020\020" +
    "\001\002\000\004\020\uffe1\001\002\000\004\020\020\001" +
    "\002\000\022\002\uffff\004\uffff\005\uffff\006\uffff\011\uffff" +
    "\017\uffff\020\uffff\024\uffff\001\002\000\022\002\ufffb\004" +
    "\ufffb\005\ufffb\006\ufffb\011\ufffb\017\ufffb\020\ufffb\024\ufffb" +
    "\001\002\000\022\002\ufffc\004\ufffc\005\ufffc\006\ufffc\011" +
    "\ufffc\017\ufffc\020\ufffc\024\ufffc\001\002\000\016\023\uffdc" +
    "\025\uffdc\026\uffdc\027\uffdc\030\uffdc\031\uffdc\001\002\000" +
    "\006\025\024\027\022\001\002\000\022\002\ufffa\004\ufffa" +
    "\005\ufffa\006\ufffa\011\ufffa\017\ufffa\020\ufffa\024\ufffa\001" +
    "\002\000\004\023\035\001\002\000\012\004\010\005\011" +
    "\006\013\026\025\001\002\000\004\023\ufff7\001\002\000" +
    "\004\020\020\001\002\000\004\026\033\001\002\000\006" +
    "\026\ufff5\030\031\001\002\000\010\004\010\005\011\006" +
    "\013\001\002\000\004\026\ufff4\001\002\000\004\023\ufff6" +
    "\001\002\000\006\026\ufff3\030\ufff3\001\002\000\020\004" +
    "\ufffe\005\ufffe\006\ufffe\011\ufffe\017\ufffe\020\ufffe\024\ufffe" +
    "\001\002\000\022\002\ufff8\004\ufff8\005\ufff8\006\ufff8\011" +
    "\ufff8\017\ufff8\020\ufff8\024\ufff8\001\002\000\020\004\010" +
    "\005\011\006\013\011\012\017\ufff0\020\ufff0\024\ufff0\001" +
    "\002\000\010\017\041\020\020\024\045\001\002\000\004" +
    "\027\073\001\002\000\004\027\072\001\002\000\010\017" +
    "\ufff1\020\ufff1\024\ufff1\001\002\000\004\025\046\001\002" +
    "\000\022\002\ufff2\004\ufff2\005\ufff2\006\ufff2\011\ufff2\017" +
    "\ufff2\020\ufff2\024\ufff2\001\002\000\020\007\052\010\054" +
    "\020\020\021\061\022\056\025\062\026\060\001\002\000" +
    "\012\025\046\026\uffde\030\uffde\031\uffde\001\002\000\006" +
    "\026\uffe9\030\uffe9\001\002\000\006\026\uffed\030\uffed\001" +
    "\002\000\006\026\uffe5\030\uffe5\001\002\000\010\026\uffe8" +
    "\030\uffe8\031\070\001\002\000\006\026\uffe4\030\uffe4\001" +
    "\002\000\006\026\066\030\065\001\002\000\006\026\uffe6" +
    "\030\uffe6\001\002\000\006\026\uffe2\030\uffe2\001\002\000" +
    "\010\026\uffeb\027\uffeb\030\uffeb\001\002\000\006\026\uffe7" +
    "\030\uffe7\001\002\000\016\007\052\010\054\020\020\021" +
    "\061\022\056\025\062\001\002\000\004\026\064\001\002" +
    "\000\006\026\uffe3\030\uffe3\001\002\000\016\007\052\010" +
    "\054\020\020\021\061\022\056\025\062\001\002\000\010" +
    "\026\uffea\027\uffea\030\uffea\001\002\000\006\026\uffec\030" +
    "\uffec\001\002\000\004\020\020\001\002\000\010\026\uffdd" +
    "\030\uffdd\031\uffdd\001\002\000\010\017\uffee\020\uffee\024" +
    "\uffee\001\002\000\010\017\uffef\020\uffef\024\uffef\001\002" +
    "\000\004\023\075\001\002\000\014\004\ufffe\005\ufffe\006" +
    "\ufffe\011\ufffe\024\ufffe\001\002\000\014\004\010\005\011" +
    "\006\013\011\012\024\077\001\002\000\004\027\100\001" +
    "\002\000\022\002\ufff9\004\ufff9\005\ufff9\006\ufff9\011\ufff9" +
    "\017\ufff9\020\ufff9\024\ufff9\001\002" });

  /** Access to parse-action table. */
  public short[][] action_table() {return _action_table;}

  /** <code>reduce_goto</code> table. */
  protected static final short[][] _reduce_table = 
    unpackFromStrings(new String[] {
    "\000\076\000\006\002\004\003\003\001\001\000\014\004" +
    "\014\005\006\006\013\010\016\011\015\001\001\000\002" +
    "\001\001\000\002\001\001\000\002\001\001\000\002\001" +
    "\001\000\002\001\001\000\004\007\073\001\001\000\002" +
    "\001\001\000\004\007\020\001\001\000\002\001\001\000" +
    "\002\001\001\000\002\001\001\000\002\001\001\000\004" +
    "\012\022\001\001\000\002\001\001\000\004\013\035\001" +
    "\001\000\010\006\025\015\026\016\027\001\001\000\002" +
    "\001\001\000\004\007\033\001\001\000\002\001\001\000" +
    "\002\001\001\000\010\006\025\015\031\016\027\001\001" +
    "\000\002\001\001\000\002\001\001\000\002\001\001\000" +
    "\004\003\036\001\001\000\002\001\001\000\016\004\014" +
    "\005\006\006\013\010\016\011\015\014\037\001\001\000" +
    "\010\007\043\017\042\022\041\001\001\000\002\001\001" +
    "\000\002\001\001\000\002\001\001\000\002\001\001\000" +
    "\002\001\001\000\016\007\046\021\052\022\056\023\054" +
    "\024\050\025\047\001\001\000\002\001\001\000\002\001" +
    "\001\000\002\001\001\000\002\001\001\000\002\001\001" +
    "\000\002\001\001\000\002\001\001\000\002\001\001\000" +
    "\002\001\001\000\002\001\001\000\002\001\001\000\014" +
    "\007\046\021\052\022\056\024\062\025\047\001\001\000" +
    "\002\001\001\000\002\001\001\000\014\007\046\021\052" +
    "\022\056\024\066\025\047\001\001\000\002\001\001\000" +
    "\002\001\001\000\004\007\070\001\001\000\002\001\001" +
    "\000\002\001\001\000\002\001\001\000\002\001\001\000" +
    "\004\003\075\001\001\000\014\004\014\005\006\006\013" +
    "\010\016\011\015\001\001\000\002\001\001\000\002\001" +
    "\001" });

  /** Access to <code>reduce_goto</code> table. */
  public short[][] reduce_table() {return _reduce_table;}

  /** Instance of action encapsulation class. */
  protected CUP$parser$actions action_obj;

  /** Action encapsulation object initializer. */
  protected void init_actions()
    {
      action_obj = new CUP$parser$actions(this);
    }

  /** Invoke a user supplied parse action. */
  public java_cup.runtime.Symbol do_action(
    int                        act_num,
    java_cup.runtime.lr_parser parser,
    java.util.Stack            stack,
    int                        top)
    throws java.lang.Exception
  {
    /* call code in generated class */
    return action_obj.CUP$parser$do_action(act_num, parser, stack, top);
  }

  /** Indicates start state. */
  public int start_state() {return 0;}
  /** Indicates start production. */
  public int start_production() {return 0;}

  /** <code>EOF</code> Symbol index. */
  public int EOF_sym() {return 0;}

  /** <code>error</code> Symbol index. */
  public int error_sym() {return 1;}




public void syntax_error(Symbol currToken) {
    if (currToken.value == null) {
        ErrMsg.fatal(0,0, "Syntax error at end of file");
    }
    else {
        ErrMsg.fatal(((TokenVal)currToken.value).linenum,
                     ((TokenVal)currToken.value).charnum,
                     "Syntax error");
    }
    System.exit(-1);
}


/** Cup generated class to encapsulate user supplied action code.*/
@SuppressWarnings({"rawtypes", "unchecked", "unused"})
class CUP$parser$actions {
  private final parser parser;

  /** Constructor */
  CUP$parser$actions(parser parser) {
    this.parser = parser;
  }

  /** Method 0 with the actual generated action code for actions 0 to 300. */
  public final java_cup.runtime.Symbol CUP$parser$do_action_part00000000(
    int                        CUP$parser$act_num,
    java_cup.runtime.lr_parser CUP$parser$parser,
    java.util.Stack            CUP$parser$stack,
    int                        CUP$parser$top)
    throws java.lang.Exception
    {
      /* Symbol object for return from actions */
      java_cup.runtime.Symbol CUP$parser$result;

      /* select the action based on the action number */
      switch (CUP$parser$act_num)
        {
          /*. . . . . . . . . . . . . . . . . . . .*/
          case 0: // $START ::= program EOF 
            {
              Object RESULT =null;
		int start_valleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).left;
		int start_valright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).right;
		ProgramNode start_val = (ProgramNode)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-1)).value;
		RESULT = start_val;
              CUP$parser$result = parser.getSymbolFactory().newSymbol("$START",0, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          /* ACCEPT */
          CUP$parser$parser.done_parsing();
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 1: // program ::= declList 
            {
              ProgramNode RESULT =null;
		int dleft = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int dright = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		LinkedList d = (LinkedList)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = new ProgramNode(new DeclListNode(d));
                
              CUP$parser$result = parser.getSymbolFactory().newSymbol("program",0, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 2: // declList ::= declList decl 
            {
              LinkedList RESULT =null;
		int dlleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).left;
		int dlright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).right;
		LinkedList dl = (LinkedList)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-1)).value;
		int dleft = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int dright = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		DeclNode d = (DeclNode)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 dl.addLast(d);
                   RESULT = dl;
                
              CUP$parser$result = parser.getSymbolFactory().newSymbol("declList",1, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 3: // declList ::= 
            {
              LinkedList RESULT =null;
		 RESULT = new LinkedList<DeclNode>();
                
              CUP$parser$result = parser.getSymbolFactory().newSymbol("declList",1, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 4: // decl ::= varDecl 
            {
              DeclNode RESULT =null;
		int vleft = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int vright = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		VarDeclNode v = (VarDeclNode)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = v;
                
              CUP$parser$result = parser.getSymbolFactory().newSymbol("decl",2, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 5: // decl ::= structDecl 
            {
              DeclNode RESULT =null;
		int sleft = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int sright = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		StructDeclNode s = (StructDeclNode)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = s;
                
              CUP$parser$result = parser.getSymbolFactory().newSymbol("decl",2, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 6: // decl ::= fnDecl 
            {
              DeclNode RESULT =null;
		int fleft = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int fright = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		FnDeclNode f = (FnDeclNode)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = f;
                
              CUP$parser$result = parser.getSymbolFactory().newSymbol("decl",2, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 7: // varDecl ::= type id SEMICOLON 
            {
              VarDeclNode RESULT =null;
		int tleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)).left;
		int tright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)).right;
		TypeNode t = (TypeNode)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-2)).value;
		int ileft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).left;
		int iright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).right;
		IdNode i = (IdNode)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-1)).value;
		 RESULT = new VarDeclNode(t, i, VarDeclNode.NOT_STRUCT);
                
              CUP$parser$result = parser.getSymbolFactory().newSymbol("varDecl",3, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 8: // structDecl ::= STRUCT id LCURLY declList RCURLY SEMICOLON 
            {
              StructDeclNode RESULT =null;
		int ileft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-4)).left;
		int iright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-4)).right;
		IdNode i = (IdNode)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-4)).value;
		int dleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)).left;
		int dright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)).right;
		LinkedList d = (LinkedList)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-2)).value;
		 RESULT = new StructDeclNode(i, new DeclListNode(d));
                
              CUP$parser$result = parser.getSymbolFactory().newSymbol("structDecl",6, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-5)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 9: // fnDecl ::= type id formals fnBody 
            {
              FnDeclNode RESULT =null;
		int tleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-3)).left;
		int tright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-3)).right;
		TypeNode t = (TypeNode)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-3)).value;
		int ileft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)).left;
		int iright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)).right;
		IdNode i = (IdNode)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-2)).value;
		int fleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).left;
		int fright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).right;
		LinkedList f = (LinkedList)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-1)).value;
		int fbleft = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int fbright = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		FnBodyNode fb = (FnBodyNode)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = new FnDeclNode(t, i, new FormalsListNode(f), fb);
                
              CUP$parser$result = parser.getSymbolFactory().newSymbol("fnDecl",7, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-3)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 10: // formals ::= LPAREN RPAREN 
            {
              LinkedList RESULT =null;
		 RESULT = new LinkedList<FormalDeclNode>();
                
              CUP$parser$result = parser.getSymbolFactory().newSymbol("formals",8, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 11: // formals ::= LPAREN formalsList RPAREN 
            {
              LinkedList RESULT =null;
		int fleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).left;
		int fright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).right;
		LinkedList f = (LinkedList)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-1)).value;
		 RESULT = f;
                
              CUP$parser$result = parser.getSymbolFactory().newSymbol("formals",8, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 12: // formalsList ::= formalDecl 
            {
              LinkedList RESULT =null;
		int fleft = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int fright = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		FormalDeclNode f = (FormalDeclNode)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = new LinkedList<FormalDeclNode>();
                   RESULT.addLast(f);
                
              CUP$parser$result = parser.getSymbolFactory().newSymbol("formalsList",11, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 13: // formalsList ::= formalDecl COMMA formalsList 
            {
              LinkedList RESULT =null;
		int fleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)).left;
		int fright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)).right;
		FormalDeclNode f = (FormalDeclNode)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-2)).value;
		int flleft = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int flright = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		LinkedList fl = (LinkedList)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 fl.addLast(f);
                   RESULT = fl;
                
              CUP$parser$result = parser.getSymbolFactory().newSymbol("formalsList",11, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 14: // formalDecl ::= type id 
            {
              FormalDeclNode RESULT =null;
		int tleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).left;
		int tright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).right;
		TypeNode t = (TypeNode)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-1)).value;
		int ileft = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int iright = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		IdNode i = (IdNode)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = new FormalDeclNode(t, i);
                
              CUP$parser$result = parser.getSymbolFactory().newSymbol("formalDecl",12, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 15: // fnBody ::= LCURLY declList stmtList RCURLY 
            {
              FnBodyNode RESULT =null;
		int dleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)).left;
		int dright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)).right;
		LinkedList d = (LinkedList)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-2)).value;
		int sleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).left;
		int sright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).right;
		LinkedList s = (LinkedList)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-1)).value;
		 RESULT = new FnBodyNode(new DeclListNode(d), new StmtListNode(s));
                
              CUP$parser$result = parser.getSymbolFactory().newSymbol("fnBody",9, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-3)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 16: // stmtList ::= stmtList stmt 
            {
              LinkedList RESULT =null;
		int slleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).left;
		int slright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).right;
		LinkedList sl = (LinkedList)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-1)).value;
		int sleft = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int sright = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		StmtNode s = (StmtNode)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 sl.addLast(s);
                   RESULT = sl;
                
              CUP$parser$result = parser.getSymbolFactory().newSymbol("stmtList",10, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 17: // stmtList ::= 
            {
              LinkedList RESULT =null;
		 RESULT = new LinkedList<StmtNode>();
                
              CUP$parser$result = parser.getSymbolFactory().newSymbol("stmtList",10, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 18: // stmt ::= RETURN SEMICOLON 
            {
              StmtNode RESULT =null;
		 RESULT = new ReturnStmtNode(null);
                
              CUP$parser$result = parser.getSymbolFactory().newSymbol("stmt",13, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 19: // stmt ::= fncall SEMICOLON 
            {
              StmtNode RESULT =null;
		int fleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).left;
		int fright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).right;
		CallExpNode f = (CallExpNode)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-1)).value;
		 RESULT = new CallStmtNode(f);
                
              CUP$parser$result = parser.getSymbolFactory().newSymbol("stmt",13, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 20: // actualList ::= exp 
            {
              LinkedList RESULT =null;
		int eleft = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int eright = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		ExpNode e = (ExpNode)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = new LinkedList<ExpNode>();
                   RESULT.add(e);
                
              CUP$parser$result = parser.getSymbolFactory().newSymbol("actualList",17, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 21: // actualList ::= actualList COMMA exp 
            {
              LinkedList RESULT =null;
		int lleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)).left;
		int lright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)).right;
		LinkedList l = (LinkedList)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-2)).value;
		int eleft = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int eright = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		ExpNode e = (ExpNode)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		
                   l.addLast(e);
                   RESULT = l;
                
              CUP$parser$result = parser.getSymbolFactory().newSymbol("actualList",17, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 22: // fncall ::= id LPAREN RPAREN 
            {
              CallExpNode RESULT =null;
		int ileft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)).left;
		int iright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)).right;
		IdNode i = (IdNode)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-2)).value;
		 RESULT = new CallExpNode(i, null);
                
              CUP$parser$result = parser.getSymbolFactory().newSymbol("fncall",16, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 23: // fncall ::= id LPAREN actualList RPAREN 
            {
              CallExpNode RESULT =null;
		int ileft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-3)).left;
		int iright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-3)).right;
		IdNode i = (IdNode)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-3)).value;
		int lleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).left;
		int lright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).right;
		LinkedList l = (LinkedList)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-1)).value;
		 RESULT = new CallExpNode(i, new ExpListNode(l));
                
              CUP$parser$result = parser.getSymbolFactory().newSymbol("fncall",16, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-3)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 24: // exp ::= term 
            {
              ExpNode RESULT =null;
		int tleft = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int tright = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		ExpNode t = (ExpNode)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = t; 
                
              CUP$parser$result = parser.getSymbolFactory().newSymbol("exp",18, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 25: // term ::= loc 
            {
              ExpNode RESULT =null;
		int lleft = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int lright = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		ExpNode l = (ExpNode)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = l;
                
              CUP$parser$result = parser.getSymbolFactory().newSymbol("term",19, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 26: // term ::= INTLITERAL 
            {
              ExpNode RESULT =null;
		int ileft = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int iright = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		IntLitTokenVal i = (IntLitTokenVal)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = new IntLitNode(i.linenum, i.charnum, i.intVal);
                
              CUP$parser$result = parser.getSymbolFactory().newSymbol("term",19, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 27: // term ::= STRINGLITERAL 
            {
              ExpNode RESULT =null;
		int sleft = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int sright = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		StrLitTokenVal s = (StrLitTokenVal)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = new StringLitNode(s.linenum, s.charnum, s.strVal);
                
              CUP$parser$result = parser.getSymbolFactory().newSymbol("term",19, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 28: // term ::= TRUE 
            {
              ExpNode RESULT =null;
		int tleft = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int tright = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		TokenVal t = (TokenVal)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = new TrueNode(t.linenum, t.charnum);
                
              CUP$parser$result = parser.getSymbolFactory().newSymbol("term",19, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 29: // term ::= FALSE 
            {
              ExpNode RESULT =null;
		int fleft = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int fright = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		TokenVal f = (TokenVal)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = new FalseNode(f.linenum, f.charnum);
                
              CUP$parser$result = parser.getSymbolFactory().newSymbol("term",19, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 30: // term ::= LPAREN exp RPAREN 
            {
              ExpNode RESULT =null;
		int eleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).left;
		int eright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).right;
		ExpNode e = (ExpNode)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-1)).value;
		 RESULT = e;
                
              CUP$parser$result = parser.getSymbolFactory().newSymbol("term",19, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 31: // term ::= fncall 
            {
              ExpNode RESULT =null;
		int fleft = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int fright = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		CallExpNode f = (CallExpNode)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = f;
                
              CUP$parser$result = parser.getSymbolFactory().newSymbol("term",19, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 32: // type ::= VOID 
            {
              TypeNode RESULT =null;
		 RESULT = new VoidNode();
                
              CUP$parser$result = parser.getSymbolFactory().newSymbol("type",4, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 33: // type ::= BOOL 
            {
              TypeNode RESULT =null;
		 RESULT = new BoolNode();
                
              CUP$parser$result = parser.getSymbolFactory().newSymbol("type",4, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 34: // type ::= INT 
            {
              TypeNode RESULT =null;
		 RESULT = new IntNode();
                
              CUP$parser$result = parser.getSymbolFactory().newSymbol("type",4, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 35: // loc ::= id 
            {
              ExpNode RESULT =null;
		int ileft = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int iright = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		IdNode i = (IdNode)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = new DotAccessExpNode(null, i);
                
              CUP$parser$result = parser.getSymbolFactory().newSymbol("loc",15, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 36: // loc ::= loc DOT id 
            {
              ExpNode RESULT =null;
		int lleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)).left;
		int lright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)).right;
		ExpNode l = (ExpNode)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-2)).value;
		int ileft = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int iright = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		IdNode i = (IdNode)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = new DotAccessExpNode(l, i);
                
              CUP$parser$result = parser.getSymbolFactory().newSymbol("loc",15, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 37: // id ::= ID 
            {
              IdNode RESULT =null;
		int ileft = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int iright = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		IdTokenVal i = (IdTokenVal)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = new IdNode(i.linenum, i.charnum, i.idVal);
                
              CUP$parser$result = parser.getSymbolFactory().newSymbol("id",5, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /* . . . . . .*/
          default:
            throw new Exception(
               "Invalid action number "+CUP$parser$act_num+"found in internal parse table");

        }
    } /* end of method */

  /** Method splitting the generated action code into several parts. */
  public final java_cup.runtime.Symbol CUP$parser$do_action(
    int                        CUP$parser$act_num,
    java_cup.runtime.lr_parser CUP$parser$parser,
    java.util.Stack            CUP$parser$stack,
    int                        CUP$parser$top)
    throws java.lang.Exception
    {
              return CUP$parser$do_action_part00000000(
                               CUP$parser$act_num,
                               CUP$parser$parser,
                               CUP$parser$stack,
                               CUP$parser$top);
    }
}

}
