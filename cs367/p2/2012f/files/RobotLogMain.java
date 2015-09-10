import java.util.Scanner;


public class RobotLogMain {

    public static void main(String args[]) {
        
        LinkedList<String> robotLog = new LinkedList<String>();
        
        Scanner stdin = new Scanner(System.in);  // for reading console input
        boolean done = false;
        while (!done) {
            System.out.print("Enter option - l, p, r, s, t, or u: ");
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
                    
                    //** Add your code here for option l **//
                    
                    break;
                    
                case 'p' :
                    if (remainder.length() > 0) {
                        System.out.println("Command 'p': Takes no " +
                                "arguments. Prints RobotLog.");
                        break;
                    }
                    
                    //** Add your code here for option p *//
                    
                    break;
                    
                case 'r' :
                    if (remainder.length() == 0) {
                        System.out.println("Command 'r': Takes one " +
                                "argument, an int. Removes the line at " +
                                "that position in the log.");
                        break;
                    }
                    
                    //** Add your code here for option r **//
                    
                    break;
                    
                case 's' :
                    if (remainder.length() == 0) {
                        System.out.println("Command 's': Takes one " +
                                "argument, the name of a file. Saves the " +
                                "current RobotLog to that file.");
                        break;
                    }
                    
                    //** Add your code here for option s **//
                    
                    break;
                    
                case 't' :
                    if (remainder.length() == 0) {
                        System.out.println("Command 't': Takes two " +
                                "arguments, both ints, i.e., 't 12 3'." +
                                "Takes the line at the first # and copies it " +
                                "to the position at the second #.");
                        break;
                    }
                    
                    //** Add your code here for option t **//
                    
                    break;
                    
                case 'u' :
                    if (remainder.length() == 0) {
                        System.out.println("Command 'u': Takes one " +
                                "argument, an int. Takes the line at " +
                                "that position in the log and copies it " +
                                "at the end of the log.");
                        break;
                    }

                    //** Add your code here for option u **//
                    
                    break;
                    
                case 'x' :
                    System.out.println("exit");
                    done = true;
                    break;
                    
                default :
                    System.out.println("Unknown Command");
                    break;
                }
            }
        }
    }
}
