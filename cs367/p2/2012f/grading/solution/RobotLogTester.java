import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class RobotLogTester {
    public static void main(String[] args) throws Exception {
	ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(new Test());
	System.setErr(System.out);
        try {
            System.out.println("Started tests running...");
	    System.out.println(future.get(40, TimeUnit.SECONDS));
            System.out.println("Finished tests!");
        } catch (TimeoutException e) {
            System.out.println("Tests were automatically timed out after 3 minutes. " +
            		"Student's code may have infinite-looped.");
        }

	executor.shutdownNow();	
    }
}


class Test implements Callable<String>{
	
	public String call() throws Exception {
		try {		
			try {
				testEmpty();
				testOne();
				testAddRemove();
				System.out.println("Testing Completed Successfully");
			} catch (IOException e) {
				System.out.println("Was unable to create file");
			}
			
			try {
				System.out.println("\n\n================= Student's Output ==================\n\n");
				System.setIn(new FileInputStream("MainInput.txt"));
				
				String[] mainArgs = {};
				
				RobotLogMain.main(mainArgs);
			} catch (FileNotFoundException e) {
				System.out.println("Could not find input file");
			}
			return "Success!";
		}
		 catch (Exception e) {
	        	System.out.println("Student's code threw exception: \n\n");
			System.out.println(e.getMessage());
			return "Not success :(";
	        }
	}
	private void testEmpty() throws IOException {
		LinkedList<String> robotlog = new LinkedList<String>();
		
		if (!(robotlog instanceof LinkedListADT))
			System.out.println("LinkedList does not implement LinkedListADT");
		
		if (robotlog.size() != 0)			
			System.out.println("size incorrect for empty lsit");
		try {
			robotlog.remove(0);
			System.out.println("remove did not throw exception " +
					"for empty list");
		}
		catch(InvalidListPositionException e) { /*correct*/ }
		catch (Exception e) {
			System.out.println("remove method threw wrong exception for empty" +
					" list");
		}
		
		try {
			robotlog.get(0);
			System.out.println("get did not throw exception " +
					"for empty list");
		} catch(InvalidListPositionException e) { /*correct*/ }
		catch (Exception e) {
			System.out.println("get method threw wrong exception for empty" +
			" list");
		}
		
		if (!robotlog.isEmpty()) System.out.println("isEmpty incorrect for " +
				"empty list");
		
		try {		
			Iterator<String> itr = robotlog.iterator();
			try {
				if (itr.hasNext()) System.out.println("Iterator's hasNext method" +
					" incorrect for empty list");
			} catch (Exception e) {
				System.out.println("Iterator's hasNext method threw exception for" +
					" empty list");
			}
		
			try {
				itr.next();
				System.out.println("Iterator's next method did not throw exception for " +
					"empty list");
			} catch(Exception e) {}
		} catch (Exception e) {
			System.out.println("iterator method threw exception for empty list");
		}
		
	}

	
	private void testOne() throws IOException {
		LinkedList<String> robotlog = new LinkedList<String>();
		
		try {
			robotlog.add("ABC");
		} catch (Exception e) {
			System.out.println("add(item) method incorrect when list empty");
			return;
		}
		
		Iterator<String> itr = robotlog.iterator();
		boolean nextWorks = true;
		if(!itr.hasNext()) System.out.println("Iterator hasNext method " +
				"incorrect for list with one item");
		try {
			if(itr.next()==null) {
				System.out.println("Iterator next method incorrect for list" +
						" with one item");
				nextWorks = false;
			}
		} catch (Exception e) {
			System.out.println("Iterator next method threw exception for list " +
					"with one item");
		}
		
		try {
			robotlog.get(0);
		}
		catch (InvalidListPositionException e) {
			try { 
				robotlog.get(1);
			} catch(InvalidListPositionException e2) {
				System.out.println("get method threw InvalidListPositionException" +
						" for list with one item");
			}
		}
		
		if (robotlog.isEmpty()) System.out.println("isEmpty method incorrect " +
				"for nonempty list");
		
		if (robotlog.size()!=1)
			System.out.println("size method incorrect for list with 1 item");
		
		try {
			robotlog.add(0,"DEF");
			itr = robotlog.iterator();
			String next = itr.next();
			if (nextWorks && next.equals("ABC")) System.out.println("add(pos, item) " +
					"method adds in incorrect order for list with one item");
		}catch(InvalidListPositionException e) {
			try {
				robotlog.add(1,"DEF");
				itr = robotlog.iterator();
				if (nextWorks && itr.next().equals("ABC")) System.out.println("add(pos, item) " +
						"method adds in incorrect order for list with one item");
			}catch(InvalidListPositionException e2) {
				System.out.println("add(pos, item) method threw " +
						"InvalidListPositionException for list with one item");
			}
			catch (Exception e2) {
				System.out.println("add(pos, item) method threw exception " + 
					"for list with one item");
			}
		}
		catch (Exception e2) {
			System.out.println("add(pos, item) method threw exception " + 
				"for list with one item");
		}
		
		try {
			robotlog.remove(0);
		}catch(InvalidListPositionException e) {
			try {
				robotlog.remove(1);
			}
			catch (InvalidListPositionException e2) {
				System.out.println("remove method threw " +
						"InvalidListPositionException for list with 1 item");
			}
			catch (Exception e2) {
				System.out.println("remove method threw exception " + 
					"for list with one item");
			}
		}
		catch (Exception e2) {
			System.out.println("remove method threw exception " + 
				"for list with one item");
		}
		
		
	}
	
	private void testAddRemove() throws IOException {
		LinkedList<String> robotLog = new LinkedList<String>();
		
		try {
			for (int i=1; i<=20; i++) {
				robotLog.add("Item" + i);
			}
		}
		catch (Exception e) {
			System.out.println("add method threw exception for list with " +
				"many items");
		}
		
		Iterator<String> itr = robotLog.iterator();
		
		boolean addMethodWorks = true;
		int num = 1;
		while (itr.hasNext()) {
			String item = "Item" + num;
			num++;
			try {
				if (!itr.next().equals(item) && addMethodWorks) {
					addMethodWorks = false;
					System.out.println("add method adds items in wrong order");
				}
			} catch (Exception e) {
				System.out.println("Iterator's next method threw exception for " +
					"list with many items");
			}
		}
		
		if (robotLog.size() != 20)
			System.out.println("size method does not correctly update after" +
					" add method is called");
		
		if (robotLog.isEmpty()) System.out.println("isEmpty incorrect for " +
				"list with many items");
		
		robotLog = new LinkedList<String>();
		boolean addWorked = true;
		for (int i=1; i<=20; i++) {
			try {
				robotLog.add(0, "Item" + i);
			} catch (InvalidListPositionException e) {
				try {
					robotLog.add(1, "Item" + i);
				}
				catch (InvalidListPositionException e2) {
					if(addWorked)
					System.out.println("add(pos, item) method threw " +
					"InvalidListPositionException for adding at first position");
					addWorked = false;
					break;
				}
			} catch (Exception e) {
				if(addWorked)
				System.out.println("add(pos, item) threw exception for " +
					"adding at first position");
				addWorked = false;
				break;
			}
		}
		
		if (addWorked && robotLog.size() != 20)
			System.out.println("size method does not correctly update after" +
					" add(pos, item) method is called");
		
		itr = robotLog.iterator();
		
		num = 20;
		while (itr.hasNext()) {
			String item = "Item" + num;
			num--;
			if (!itr.next().equals(item)) {
				System.out.println("add(pos, item) method adds items in wrong order");
			}
		}
		
		try {
			robotLog.remove(19);
			robotLog.remove(12);
			robotLog.remove(4);
			if (robotLog.size() != 17) System.out.println("size method does " +
					"not correctly update after remove method is called");
		} catch (InvalidListPositionException e) {
			System.out.println("remove method threw exception for list with" +
					" many items");
		} catch (Exception e) {
			System.out.println("remove method threw exception for list with" +
					" many items");		
		}
		
		try {
			robotLog.remove(21);
			System.out.println("remove method did not throw exception for" +
					" too large position");
		} catch (InvalidListPositionException e) { /*correct*/ }
		catch (Exception e) {
			System.out.println("remove method threw wrong exception for " +
					"too large position");
		}
		
		try {
			robotLog.remove(-1);
			System.out.println("remove method did not throw exception for" +
					" negative position");
		} catch (InvalidListPositionException e) { /*correct*/ }
		catch (Exception e) {
			System.out.println("remove method threw wrong exception for " +
					"negative position");
		}
		
		try {
			robotLog.add(21, "new");
			System.out.println("add(pos, item) method did not throw exception for" +
					" too large position");
		} catch (InvalidListPositionException e) { /*correct*/ }
		catch (Exception e) {
			System.out.println("add(pos, item) method threw wrong exception for " +
					"too large position");
		}
		
		try {
			robotLog.add(-1, "item");
			System.out.println("add(pos, item) method did not throw exception for" +
					" negative position");
		} catch (InvalidListPositionException e) { /*correct*/ }
		catch (Exception e) {
			System.out.println("add(pos, item) method threw wrong exception for " +
					"negative position");
		}
		
		try {
			robotLog.get(21);
			System.out.println("get method did not throw exception for" +
					" too large position");
		} catch (InvalidListPositionException e) { /*correct*/ }
		catch (Exception e) {
			System.out.println("get method threw wrong exception for " +
					"too large position");
		}
		
		try {
			robotLog.get(-1);
			System.out.println("get method did not throw exception for" +
					" negative position");
		} catch (InvalidListPositionException e) { /*correct*/ }
		catch (Exception e) {
			System.out.println("get method threw wrong exception for " +
					"negative position");
		}
	}

}
