import java.util.Scanner;


public class MovieQueueMain {
	
	/* You may create additional private static 
	   variables or methods as needed */

	public static void main(String args[]) {
		
		LinkedList<String> movieQueue = new LinkedList<String>();
		//** You may also add additional variables as needed **//
		
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
                	
                	//** Add your code here for option a **//
                	
                	break;
                	
                case 'c' :
                	
                	//** Add your code here for option c **//
                	
                	break;
                
                case 'l' :
                	
                	//** Add your code here for option l **//
                	
                	break;
                	
                case 'm' :
                	
                	//** Add your code here for option m **//
                	
                	break;
                	
                case 'p' :
                	
                	//** Add your code here for option p *//
                	
                	break;
                	
                case 'r' :
                	
                	//** Add your code here for option r **//
                	
                	break;
                	
                case 's' :
                	
                	//** Add your code here for option s **//
                	
                	break;
                	
                case 'w' :
                	
                	//** Add your code here for option w **//
                	
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
