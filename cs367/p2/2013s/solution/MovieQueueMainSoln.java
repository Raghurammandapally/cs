import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;


public class MovieQueueMainSoln {

	public static void main(String args[]) {
		
		LinkedList<String> movieQueue = new LinkedList<String>();
		ArrayList<String> movieTitles = new ArrayList<String>();
		String lastGenre = "";
		int line;
		
		Scanner stdin = new Scanner(System.in);  // for reading console input
        boolean done = false;
        while (!done) {
            System.out.print("Enter option - a, c, l, m, p, r, s, w or x: ");
            String input = stdin.nextLine();
            
            if (input.length() > 0) {
                char choice = input.charAt(0);  // strip off option character
                String remainder = "";  // used to hold the remainder of input
                // trim off any leading or trailing spaces
                remainder = input.substring(1).trim();
                
                switch (choice) {
                
                case 'a' :
                	
                	if (!remainder.equals(lastGenre)) {
                		movieTitles = new ArrayList<String>();
	                	String fileName = remainder + ".txt";
	                	File genre = new File(fileName);
	                	Scanner movies = null;
	                	try {
							movies = new Scanner(genre);
						} catch (FileNotFoundException e) {
							System.out.println("Cannot find the specified file.");
							break;
						}
						
						while(movies.hasNextLine())
							movieTitles.add(movies.nextLine());
                	}
						
					int lineNum = 1;
					for (String title : movieTitles) {
						System.out.println(lineNum++ + " " + title);
					}
					
					int entry = -1;
					while (entry < 0 || entry > lineNum-1) {
						System.out.println("Please enter a number between 1 and " +
								(lineNum - 1));
						try {
							entry = Integer.parseInt(stdin.nextLine());
						} catch (NumberFormatException e) {
							entry = -1;
						}
					}
					
					String title = movieTitles.get(entry - 1);
					
					movieQueue.add(title);
					
					System.out.println("Added " + title + " to queue.");
                	
                	break;
                	
                case 'c' :
                	
                	line = -1;
                	try {
                		line = Integer.parseInt(remainder)-1;
                		title = movieQueue.get(line);
						movieQueue.add(title);
						System.out.println("Copied " + title + " to end of queue.");
                	} catch (NumberFormatException e) {
                		System.out.println("Invalid line number.");
                		break;
                	} catch (InvalidListPositionException e) {
                		System.out.println("Invalid line number.");
                		break;
					}
                	
                	break;
                
                case 'l' :
                	
                	movieQueue = new LinkedList<String>();
                	
                	File newQueue = new File(remainder);
                	Scanner newScan = null;
                	try {
                		newScan = new Scanner(newQueue);
                	} catch (FileNotFoundException e) {
                		System.out.println("Cannot find the specified file.");
                		break;
                	}
                	
                	while (newScan.hasNextLine()) {
                		movieQueue.add(newScan.nextLine());
                	}
                	
                	System.out.print(movieQueue.print(true));
                	
                	break;
                	
                case 'm' :
                	
                	line = -1;
                	try {
                		line = Integer.parseInt(remainder)-1;
                		String movie = movieQueue.get(line);
                		movieQueue.remove(line);
                		movieQueue.add(0, movie);
                		System.out.println("Moved " + movie + " to front of queue.");
                	} catch (NumberFormatException e) {
                		System.out.println("Invalid line number.");
                		break;
                	} catch (InvalidListPositionException e) {
                		System.out.println("Invalid line number.");
                		break;
					}
                	
                	break;
                	
                case 'p' :
                	
                	if (!movieQueue.isEmpty())
                		System.out.print(movieQueue.print(true));
                	else
                		System.out.println("Empty.");
                	
                	break;
                	
                case 'r' :
                	
                	line = -1;
                	try {
                		line = Integer.parseInt(remainder)-1;
                		title = movieQueue.get(line);
                		movieQueue.remove(line);
                		System.out.println("Removed " + title + " from queue.");
                	} catch (NumberFormatException e) {
                		System.out.println("Invalid line number.");
                		break;
                	} catch (InvalidListPositionException e) {
                		System.out.println("Invalid line number.");
                		break;
					}
                	
                	break;
                	
                case 's' :
                	
                	if (movieQueue.isEmpty()) {
                		System.out.println("Cannot write to file, movie queue " +
                				"is empty.");
                		break;
                	}
                	
                	File save = new File(remainder);
                	PrintWriter pw = null;
                	try {
                		pw = new PrintWriter(save);
                	} catch (FileNotFoundException e) {
                		System.out.println("Cannot write to the specified file.");
                		break;
                	}
                	
                	pw.print(movieQueue.print(false));
                	pw.close();
                	System.out.println("Saved.");
                	
                	break;
                	
                case 'w' :
                	
                	try {
                		int numLines = Integer.parseInt(remainder);
                		if (numLines < 0) {
                			System.out.println("Invalid number of movies.");
                			break;
                		}
                		int soFar = 0;
                		while (soFar < numLines && !movieQueue.isEmpty()) {
                			movieQueue.remove(0);
                			soFar++;
                		}
                		System.out.println("Removed first " + soFar + " movies " +
                				"from queue.");
                	} catch (NumberFormatException e) {
                		System.out.println("Invalid number of movies.");
                	} catch (InvalidListPositionException e) {
						//Won't get here
					}
                	
                	break;
                	
                case 'x' :
                	//Exit the program. This command is already implemented.
                	done = true;
                    System.out.println("exit");
                    break;
                	
                default :
                	System.out.println("Unknown Command");
                	break;
                }
            }
        }
	}
}
