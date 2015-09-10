// TODO *** add comments as specified in the commenting guide ***

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
public class  Twitter{

    public static void main(String[] args) {

        // TODO *** steps 1 - 3 of the main method ***

        Scanner stdin = new Scanner(System.in);  //for console input

        boolean done = false;
        while (!done) {
            System.out.print("Enter option : ");
            String input = stdin.nextLine();

            //only do something if the user enters at least one character
            if (input.length() > 0) {
                String[] commands = input.split(" ");//split on space
                switch(commands[0]){
                    case "list":
                        break;
                    case "follow":
                        break;
                    case "unfollow":
                        break;
                    case "search":
                        break;
                    case "print":
                        break;
                    case "quit":
                        done = true;
                        System.out.println("exit");
                        break;
                    default:  //a command with no argument
                        System.out.println("Invalid Command");
                        break;
                }
            } //end if
        } //end while
    } //end main
}
