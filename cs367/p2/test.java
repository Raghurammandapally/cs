import java.util.zip.*;
import java.util.*;
import java.io.*;
public class test {
  public static void main(String[] args) throws IOException {
    ZipFile zf = new ZipFile("./test1.zip");
    try {
      for (Enumeration<? extends ZipEntry> e = zf.entries(); e.hasMoreElements();) {
        ZipEntry ze = e.nextElement();
        String name = ze.getName();
        System.out.println(name);
        if (name.endsWith(".txt")) {
          InputStream in = zf.getInputStream(ze);
          Scanner sc = new Scanner(in);
          while(sc.hasNextLine()) {
            System.out.println("  " + sc.nextLine());
          }

          //// read from 'in'
        }
      }
    } finally {
      zf.close();
    }

  }
}
