import java.util.*;
import java.io.*;
import java_cup.runtime.*;  // defines Symbol

/**
 * This program is to be used to test the Scanner.
 * This version is set up to test all tokens, but more code is needed to test 
 * other aspects of the scanner (e.g., input that causes errors, character 
 * numbers, values associated with tokens)
 */
public class P2 {
    public static void main(String[] args) throws IOException {
                                           // exception may be thrown by yylex
        // test all tokens
        testAllTokens();
        CharNum.num = 1;
        testComments();
        CharNum.num = 1;
        testWhitespace();
        CharNum.num = 1;
        //testInvalidChars();
        CharNum.num = 1;
        testStringErrors();
        CharNum.num = 1;
    }

    /**
     * symToString
     *
     * Almost identical to the functionality given in testAllTokens. This 
     * private method takes an integer, symbol, and converts it to the 
     * corresponding String in sym.java. This aids in printing out helpful
     * information to file or stdout.
     */
    private static String symToString(int symbol) {
      switch (symbol) {
        case sym.BOOL:
          return("bool"); 
        case sym.INT:
          return("int");
        case sym.VOID:
          return("void");
        case sym.TRUE:
          return("true"); 
        case sym.FALSE:
          return("false"); 
        case sym.STRUCT:
          return("struct"); 
        case sym.CIN:
          return("cin"); 
        case sym.COUT:
          return("cout");
        case sym.IF:
          return("if");
        case sym.ELSE:
          return("else");
        case sym.WHILE:
          return("while");
        case sym.RETURN:
          return("return");
        case sym.ID:
          return("ID");
        case sym.INTLITERAL:  
          return("INTLITERAL");
        case sym.STRINGLITERAL: 
          return("STRINGLITERAL");
        case sym.LCURLY:
          return("{");
        case sym.RCURLY:
          return("}");
        case sym.LPAREN:
          return("(");
        case sym.RPAREN:
          return(")");
        case sym.SEMICOLON:
          return(";");
        case sym.COMMA:
          return(",");
        case sym.DOT:
          return(".");
        case sym.WRITE:
          return("<<");
        case sym.READ:
          return(">>");
        case sym.PLUSPLUS:
          return("++");
        case sym.MINUSMINUS:
          return("--");
        case sym.PLUS:
          return("+");
        case sym.MINUS:
          return("-");
        case sym.TIMES:
          return("*");
        case sym.DIVIDE:
          return("/");
        case sym.NOT:
          return("!");
        case sym.AND:
          return("&&");
        case sym.OR:
          return("||");
        case sym.EQUALS:
          return("==");
        case sym.NOTEQUALS:
          return("!=");
        case sym.LESS:
          return("<");
        case sym.GREATER:
          return(">");
        case sym.LESSEQ:
          return("<=");
        case sym.GREATEREQ:
          return(">=");
        case sym.ASSIGN:
          return("=");
        default:
          return("UNKNOWN TOKEN");
      } // end switch
    }

    /**
     * testAllTokens
     *
     * Open and read from file allTokens.txt
     * For each token read, write the corresponding string to allTokens.out
     * If the input file contains all tokens, one per line, we can verify
     * correctness of the scanner by comparing the input and output files
     * (e.g., using a 'diff' command).
     */
    private static void testAllTokens() throws IOException {
        // open input and output files
        FileReader inFile = null;
        PrintWriter outFile = null;
        try {
            inFile = new FileReader("allTokens.in");
            outFile = new PrintWriter(new FileWriter("allTokens.out"));
        } catch (FileNotFoundException ex) {
            System.err.println("File allTokens.in not found.");
            System.exit(-1);
        } catch (IOException ex) {
            System.err.println("allTokens.out cannot be opened.");
            System.exit(-1);
        }

        // create and call the scanner
        Yylex scanner = new Yylex(inFile);
        Symbol token = scanner.next_token();
        while (token.sym != sym.EOF) {
            switch (token.sym) {
            case sym.BOOL:
                outFile.println("bool"); 
                break;
            case sym.INT:
                outFile.println("int");
                break;
            case sym.VOID:
                outFile.println("void");
                break;
            case sym.TRUE:
                outFile.println("true"); 
                break;
            case sym.FALSE:
                outFile.println("false"); 
                break;
            case sym.STRUCT:
                outFile.println("struct"); 
                break;
            case sym.CIN:
                outFile.println("cin"); 
                break;
            case sym.COUT:
                outFile.println("cout");
                break;				
            case sym.IF:
                outFile.println("if");
                break;
            case sym.ELSE:
                outFile.println("else");
                break;
            case sym.WHILE:
                outFile.println("while");
                break;
            case sym.RETURN:
                outFile.println("return");
                break;
            case sym.ID:
                outFile.println(((IdTokenVal)token.value).idVal);
                break;
            case sym.INTLITERAL:  
                outFile.println(((IntLitTokenVal)token.value).intVal);
                break;
            case sym.STRINGLITERAL: 
                outFile.println(((StrLitTokenVal)token.value).strVal);
                break;    
            case sym.LCURLY:
                outFile.println("{");
                break;
            case sym.RCURLY:
                outFile.println("}");
                break;
            case sym.LPAREN:
                outFile.println("(");
                break;
            case sym.RPAREN:
                outFile.println(")");
                break;
            case sym.SEMICOLON:
                outFile.println(";");
                break;
            case sym.COMMA:
                outFile.println(",");
                break;
            case sym.DOT:
                outFile.println(".");
                break;
            case sym.WRITE:
                outFile.println("<<");
                break;
            case sym.READ:
                outFile.println(">>");
                break;				
            case sym.PLUSPLUS:
                outFile.println("++");
                break;
            case sym.MINUSMINUS:
                outFile.println("--");
                break;	
            case sym.PLUS:
                outFile.println("+");
                break;
            case sym.MINUS:
                outFile.println("-");
                break;
            case sym.TIMES:
                outFile.println("*");
                break;
            case sym.DIVIDE:
                outFile.println("/");
                break;
            case sym.NOT:
                outFile.println("!");
                break;
            case sym.AND:
                outFile.println("&&");
                break;
            case sym.OR:
                outFile.println("||");
                break;
            case sym.EQUALS:
                outFile.println("==");
                break;
            case sym.NOTEQUALS:
                outFile.println("!=");
                break;
            case sym.LESS:
                outFile.println("<");
                break;
            case sym.GREATER:
                outFile.println(">");
                break;
            case sym.LESSEQ:
                outFile.println("<=");
                break;
            case sym.GREATEREQ:
                outFile.println(">=");
                break;
            case sym.ASSIGN:
                outFile.println("=");
                break;
            default:
              outFile.println("UNKNOWN TOKEN");
            } // end switch

            token = scanner.next_token();
        } // end while
        outFile.close();
    }


    /**
     * testStringErrors
     *
     * Open and read from file stringErrors.in
     * Simply runs through the file until EOF, (hopefully) generating error
     * messages for strings with errors. The expected value of these messages 
     * are detailed in the Makefile comments.
     */
    private static void testStringErrors() throws IOException {
        // open input and output files
        FileReader inFile = null;
        PrintWriter outFile = null;
        try {
            inFile = new FileReader("stringErrors.in");
            outFile = new PrintWriter(new FileWriter("stringErrors.out"));
        } catch (FileNotFoundException ex) {
            System.err.println("File stringErrors.in not found.");
            System.exit(-1);
        } catch (IOException ex) {
            System.err.println("stringErrors.out cannot be opened.");
            System.exit(-1);
        }

        // create and call the scanner
        Yylex scanner = new Yylex(inFile);
        Symbol token = scanner.next_token();
        while (token.sym != sym.EOF) {
          int linenum = ((TokenVal)token.value).linenum;
          int charnum = ((TokenVal)token.value).charnum;
          outFile.println(symToString(token.sym) + " found at [L" + linenum + ", C" + 
              charnum + "]");
          token = scanner.next_token();
        } // end while

        outFile.close();
    }

    /**
     * testInvalidChars
     *
     * Open and read from file invalid.in
     * Simply runs through the file until EOF, (hopefully) generating error
     * messages for invalid chars. The expected value of these messages are
     * detailed in the Makefile.
     */
    private static void testInvalidChars() throws IOException  {
        // open input and output files
        FileReader inFile = null;
        try {
            inFile = new FileReader("invalid.in");
        } catch (FileNotFoundException ex) {
            System.err.println("File invalid.in not found.");
            System.exit(-1);
        } 

        // create and call the scanner
        Yylex scanner = new Yylex(inFile);
        Symbol token = scanner.next_token();
        while (token.sym != sym.EOF) {
          token = scanner.next_token();
        } // end while
    }
     
    /**
     * testWhitespace
     *
     * Open and read from file whitespace.in
     * For each token read, print the token along with the character and line
     * number.
     * whitespace.in contains a simple function header and body identical to
     * what was seen in HW4. This contains spaces, tabs, and newlines, which
     * the parser is expected to skip while keeping track of the character
     * and line count.
     * At the end, we will diff the output with the expected output, removing
     * whitespace from the expected output.
     */
    private static void testWhitespace() throws IOException {
        // open input and output files
        FileReader inFile = null;
        PrintWriter outFile = null;
        try {
            inFile = new FileReader("whitespace.in");
            outFile = new PrintWriter(new FileWriter("whitespace.out"));
        } catch (FileNotFoundException ex) {
            System.err.println("File whitespace.in not found.");
            System.exit(-1);
        } catch (IOException ex) {
            System.err.println("whitespace.out cannot be opened.");
            System.exit(-1);
        }

        // create and call the scanner
        Yylex scanner = new Yylex(inFile);
        Symbol token = scanner.next_token();
        while (token.sym != sym.EOF) {
          int linenum = ((TokenVal)token.value).linenum;
          int charnum = ((TokenVal)token.value).charnum;
          outFile.println(symToString(token.sym) + " found at [L" + linenum + ", C" + 
              charnum + "]");
          token = scanner.next_token();
        } // end while

        outFile.close();
    }

    /**
     * testComments
     *
     * Open and read from file comments.in
     * For each token read, print an error message. Valid comments should not
     * be passed as tokens, and should thus be skipped over. At the end, we
     * will diff the output with the expected output, including error messages
     * for each of the invalid inputs.
     */
    private static void testComments() throws IOException {
        // open input and output files
        FileReader inFile = null;
        PrintWriter outFile = null;
        try {
            inFile = new FileReader("comments.in");
            outFile = new PrintWriter(new FileWriter("comments.out"));
        } catch (FileNotFoundException ex) {
            System.err.println("File comments.in not found.");
            System.exit(-1);
        } catch (IOException ex) {
            System.err.println("comments.out cannot be opened.");
            System.exit(-1);
        }

        // create and call the scanner
        Yylex scanner = new Yylex(inFile);
        Symbol token = scanner.next_token();
        while (token.sym != sym.EOF) {
          int linenum = ((TokenVal)token.value).linenum;
          int charnum = ((TokenVal)token.value).charnum;
          outFile.println("token found at [L" + linenum + ", C" + 
              charnum + "]");
          token = scanner.next_token();
        } // end while
        outFile.close();
    }
}
