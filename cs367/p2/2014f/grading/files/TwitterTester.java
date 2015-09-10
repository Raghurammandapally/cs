import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class TwitterTester {
    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(new TWTest(args));

        try {
            //System.out.println("MainExecutionTester Started");
            System.out.print(future.get(180, TimeUnit.SECONDS));
            //System.out.println("MainExecutionTester Completed");
        } catch (TimeoutException e) {
            System.err.println("\n\nPROBLEM: Tests automatically timed out after 3 minutes. " +
                    "Student's code may have infinite-looped.");
            System.exit(-1);
        } catch (Exception e) {
            System.err.println("PROBLEM: Student's code threw an exception: \n");
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            System.out.println(sw.toString());
        }

        executor.shutdownNow();
    }
}


class TWTest implements Callable<String> {

    private String test[];

    public TWTest(String[] testName) {
        super();
	test = testName;
    }

    public String call() throws Exception {

        //System.out.println("\n=========== Exexute " + test + " ===========");
        //System.out.println("COMPARE this to " + test+ "Output.txt\n");
        
        try {
            System.setIn(new FileInputStream("MainInput.txt"));
            // line below needs to change for different mains
            Twitter.main(test);
        } catch (FileNotFoundException e) {
            System.err.println("PROBLEM: Unable to write to file ");
        }

        return "";
    }
}
