import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Scanner;


public class RobotLogMainSoln {

	public static void main(String args[]) {
		
		LinkedList<String> robotLog = new LinkedList<String>();
		
		Scanner stdin = new Scanner(System.in);  // for reading console input
        boolean done = false;
        while (!done) {
            System.out.println("Enter option - l, p, r, s, t, or u: ");
            String input = stdin.nextLine();
            
            if (input.length() > 0) {
                char choice = input.charAt(0);  // strip off option character
                String remainder = "";  // used to hold the remainder of input
                // trim off any leading or trailing spaces
                remainder = input.substring(1).trim();
                
                switch (choice) {
                
                case 'l' :
                	if (remainder.length() == 0) {
                		System.out.println("Command 'l': Takes one " +
                				"argument, the name of a file. Loads the " +
                				"RobotLog from that file.");
                		break;
                	}
                	File loadFile = new File(remainder);
                	Scanner inFile = null;
                	try {
                		inFile = new Scanner(loadFile);
                	}
                	catch(FileNotFoundException e) {
                		System.out.println("Cannot find the specified file.");
                		break;
                	}
                	
                	robotLog = new LinkedList<String>();
                	while (inFile.hasNextLine()) {
                		robotLog.add(inFile.nextLine());
                	}
                	printLog(robotLog);
                	break;
                	
                case 'p' :
                	if (remainder.length() > 0) {
                		System.out.println("Command 'p': Takes no " +
                				"arguments. Prints RobotLog.");
                		break;
                	}
                	printLog(robotLog);
                	break;
                	
                case 'r' :
                	if (remainder.length() == 0) {
                		System.out.println("Command 'r': Takes one " +
                				"argument, an int. Removes the line at " +
                				"that position in the log.");
                		break;
                	}
                	
                	try {
                		int lineNumber = Integer.parseInt(remainder);
						robotLog.remove(lineNumber - 1);
                	}
                	catch (NumberFormatException e) {
                		System.out.println("Please enter a valid integer.");
                		break;
                	}
                	catch (InvalidListPositionException e) {
                		System.out.println("Invalid line number.");
                		break;
                	}
                	System.out.println("Successfully removed line " + remainder
                			+ ".");
                	break;
                	
                case 's' :
                	if (remainder.length() == 0) {
                		System.out.println("Command 's': Takes one " +
                				"argument, the name of a file. Saves the " +
                				"current RobotLog to that file.");
                		break;
                	}
                	File saveFile = new File(remainder);
                	PrintWriter writeOut = null;
                	try {
						writeOut = new PrintWriter(saveFile);
					} catch (FileNotFoundException e) {
						System.out.println("Cannot write to specified file.");
						break;
					}
					Iterator<String> itr = robotLog.iterator();
					if (!itr.hasNext()) {
						System.out.println("Cannot write to file, log is empty.");
						break;
					}
					while (itr.hasNext()) {
						writeOut.println(itr.next());
					}
					writeOut.close();
					System.out.println("Successfully saved.");
                	break;
                	
                case 't' :
                	if (remainder.length() == 0) {
                		System.out.println("Command 't': Takes two " +
                				"arguments, both ints, i.e., 't 12 3'." +
                				"Takes the line at the first # and copies it " +
                				"to the position at the second #.");
                		break;
                	}
                	
                	try {
                		String[] nums = remainder.split(" ");
                		int lineNumber1 = Integer.parseInt(nums[0]) - 1;
                		int lineNumber2 = Integer.parseInt(nums[1]) - 1;
                		String line = robotLog.get(lineNumber1);
                		robotLog.add(lineNumber2, line);
                	}
                	catch (IndexOutOfBoundsException e) {
                		System.out.println("Please enter two valid integers.");
                		break;
                	}
                	catch (NumberFormatException e) {
                		System.out.println("Please enter two valid integers.");
                		break;
                	} catch (InvalidListPositionException e) {
						System.out.println("Invalid line number.");
						break;
					}
                	System.out.println("Successfully copied.");
                	break;
                	
                case 'u' :
                	if (remainder.length() == 0) {
                		System.out.println("Command 'u': Takes one " +
                				"argument, an int. Takes the line at " +
                				"that position in the log and copies it " +
                				"at the end of the log.");
                		break;
                	}

                	try {
                		int lineNumber = Integer.parseInt(remainder) - 1;
                		String line = robotLog.get(lineNumber);
                		robotLog.add(line);
                	}
                	catch (NumberFormatException e) {
                		System.out.println("Please enter a valid integer.");
                		break;
                	} catch (InvalidListPositionException e) {
						System.out.println("Invalid line number.");
						break;
					}
                	System.out.println("Successfully copied.");
                	break;
                	
                case 'x':
                	done = true;
                	break;
                	
                default :
                	System.out.println("Unknown Command");
                	break;
                }
            }
        }
	}
	
	public static void printLog(LinkedList<String> robotLog) {
		if (robotLog.isEmpty()) {
    		System.out.println("Empty");
    	}
    	else {
    		int lineNum = 1;
    		Iterator<String> itr = robotLog.iterator();
    		while (itr.hasNext()) {
    			System.out.println(lineNum + " " + itr.next());
    			lineNum++;
    		}
    	}
	}
}
