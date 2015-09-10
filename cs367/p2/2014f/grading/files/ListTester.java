import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ListTester {
    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(new SLLTest());

        try {
            //System.out.println("SimpleLinkedList<String>Tester Started");
            System.out.print(future.get(180, TimeUnit.SECONDS));
            //System.out.println("SimpleLinkedList<String>Tester Completed");
        } catch (TimeoutException e) {
            System.out.println("\n\nPROBLEM: Tests automatically timed out after 3 minutes. " +
                    "Student's code may have infinite-looped.");
            System.exit(-1);
        } catch (Exception e) {
            System.out.println("PROBLEM: Student's code threw an exception:\n");
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            System.out.println(sw.toString());
        }

        executor.shutdownNow();
    }
}


class SLLTest implements Callable<String> {

    public String call() throws Exception {

        try {
            testEmpty();
            testOne();
            testSmallAddRemove();
            testLarge();
        } catch (IOException e) {
            System.out.println("PROBLEM: Unable to create file during SimpleLinkedList test");
        }
        
        return "";
    }


    private static void testEmpty() throws IOException {
        SimpleLinkedList<String> list = new SimpleLinkedList<String>();
        System.out.println("-> testing empty List");
        if (list.size() != 0)           
            System.out.println("INCORRECT: size for empty list");
        
        try {
            if (list.contains("test")) 
                System.out.println("INCORRECT: contains for empty list");
        } catch (Exception e) {
            System.out.println("INCORRECT contains threw exception on empty list");
        }
        
    }

    
    private static void testOne() throws IOException {
        SimpleLinkedList<String> list = new SimpleLinkedList<String>();
        System.out.println("-> testing 1 item list");
        try {
	    String s = "C1";
            list.add(s);
            if (list.size() != 1) System.out.println("INCORRECT: size incorrect when 1");
            
            if (!list.contains(s))
                System.out.println("INCORRECT: contain incorrect when item is in list with 1 element");
            if (list.contains("C2"))
                System.out.println("INCORRECT: contains incorrect when item isn't in list with 1 item");
            
            try {
                list.remove(0);
                if (list.size() != 0) System.out.println("INCORRECT: size incorrect when only one removed");
                
                if (list.contains(s))
                    System.out.println("INCORRECT: only item not removed OR contains incorrect when only item removed");
            } catch (Exception e) {
                System.out.println("INCORRECT: remove threw unexpected exception");
            }
            
        } catch (Exception e) {
            System.out.println("INCORRECT: add threw unexpected exception");
        }
    }
    

    private static void testSmallAddRemove() throws IOException {
        SimpleLinkedList<String> list = new SimpleLinkedList<String>();
        System.out.println("-> testing add/remove operations on small list");
        for (int i = 0; i < 10; i++)
            list.add("S" + i);
        
        if (list.size() != 10) System.out.println("INCORRECT: size wrong on small-sized list");
        
        try {
            int count = 0;
            String curr;
            for(int ndx=0;ndx < 10;ndx++) {
                 curr = list.get(ndx);
                if (!curr.equalsIgnoreCase("S" + count))
                    System.out.println("INCORRECT: items not get'd in same order as added");
                count++;
            }
        } catch (Exception e) {
            System.out.println("INCORRECT: unexpected exception while -> testing add/get");
        }
        
        for (int i = 0; i < 10; i++) {
            if (!list.contains("S"+i))
                System.out.println("INCORRECT: contains wrong if item is in  small-sized list");
        }
        if (list.contains("T0"))
            System.out.println("INCORRECT: contains wrong if item is not in small-sized list");       

        int size, sizeNew;
        try {
        size = list.size();
        list.remove(4); // remove middle item
        sizeNew = list.size();
        if (size != sizeNew+1)
            System.out.println("INCORRECT: size incorrect after removing item");
        } catch (Exception e) {
            System.out.println("INCORRECT: remove unexpected exception while -> testing iterator");
        }


        try {
        list.remove(8); // remove first item
        try {
            String curr;
            for (int i = 0; i < 8; i++) { 
                 curr = list.get(i);
                if (curr.equals("S9"))
                    System.out.println("INCORRECT: remove did not remove correct item");
            }
        } catch (Exception e) {
            System.out.println("INCORRECT: remove unexpected exception while -> testing iterator");
        }
        } catch (ConcurrentModificationException e) {
            System.out.println("INCORRECT: remove concurrent modification exception while -> testing remove");
        }

        try {
        list.remove(0); // remove first item
        try {
   	    String curr;
            for (int i = 0; i < 7; i++) { 
                 curr = list.get(i);
                if (curr.equals("S0"))
                    System.out.println("INCORRECT: remove did not remove correct item");
            }
        } catch (Exception e) {
            System.out.println("INCORRECT: remove unexpected exception");
        }
        } catch (ConcurrentModificationException e) {
            System.out.println("INCORRECT: remove concurrent modification exception while -> testing remove");
        }
    }
    

    private static void testLarge() throws IOException {
        SimpleLinkedList<Integer> list = new SimpleLinkedList<Integer>();
        System.out.println("-> testing large list");
        int nums = 1000;
        int i = 0;
        // add 10,000 items
        try {
            for (int j = 0; j < nums; j++) {
                    list.add(0,j);   
                }
            if (list.size() != nums) 
                System.out.println("INCORRECT: size for large list");
        } catch (Exception e) {
            System.out.println("INCORRECT: add threw exception when adding item" + i);
        }
        // lookup s
        int flag = 1;
        try {
            for (i = 0; i < nums; i++)
                if (!list.contains(i)){
				 if(flag==1){  
                    System.out.println("INCORRECT: add(int,E) or contains incorrect for item in large list");
					flag=0;
				}}
        } catch (Exception e) {
            System.out.println("INCORRECT: contains threw exception when checking item" + i);
        }

        // remove 10,000 items
        try {
            for (i = 0; i < nums; i++)
                list.remove(0);    
            if (list.size() != 0) 
                System.out.println("INCORRECT: size for large list");
        } catch (ConcurrentModificationException e) {
            System.out.println("INCORRECT: remove threw concurrent modification exception");
        } catch (Exception e) {
            System.out.println("INCORRECT: remove threw unexpected exception when removing item" + i);
        }

    }

}
