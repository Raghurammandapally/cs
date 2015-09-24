import java.io.*;
import java_cup.runtime.Symbol;

public class Scanner {
  public static void main(String[] args) throws Exception {
    File f = new File("shouldpass.txt");
    FileInputStream fis = new FileInputStream(f);
    System.out.println("*************************");
    System.out.println("   THESE SHOULD PASS");
    System.out.println("*************************");
    Symbol token;

    Yylex scanner = new Yylex(fis);
    do{
      token = scanner.next_token();
    } while(token != null);

    File f2 = new File("shouldfail.txt");
    FileInputStream fis2 = new FileInputStream(f2);
    System.out.println("*************************");
    System.out.println("   THESE SHOULD FAIL");
    System.out.println("*************************");

    Yylex scanner2 = new Yylex(fis2);
    do{
      token = scanner2.next_token();
    } while(token != null);
  }
}
