public class main {
  static boolean var1 = true;
  static boolean var2 = true;
  static boolean var3 = false;
  public static void main(String[] args ) throws Exc2,Exc3 {
    System.out.println("main -");
    try {
        a();
        b();
    } catch (Exc1 ex) {
        System.out.println("main caught Exc1");
    } finally {
        System.out.println("finally main");
    }
    System.out.println("- main");
  }

  private static void a( ) throws Exc1 {
      System.out.println("a -");
      try {
          if (var1){
              b();
          }
          else{
              throw new Exc1();
          }
      } catch (Exc2 ex) {
          System.out.println("a caught Exc2");
      } catch (Exc3 ex) {
          System.out.println("a caught Exc3");
      }
      System.out.println("- a");
  }

  private static void b( ) throws Exc2,Exc3{
      System.out.println("b -");
      try {
          if (var2) throw new Exc2();
          if (var3) throw new Exc3();
      } catch (Exc3 ex) {
          System.out.println("b caught Exc3");
      } finally {
          System.out.println("finally b");
      }
      System.out.println("- b");
  }
}
