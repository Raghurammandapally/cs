// TODO *** add comments as specified in the commenting guide ***

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
public class  Twitter{

    public static void main(String[] args) {

		ArrayList<String> users = new ArrayList<String>();
		ArrayList<ArrayList<Tweet>> userTweets = new ArrayList<ArrayList<Tweet>>();
		ArrayList<String> followers = new ArrayList<String>(); 
		Timeline tl = new Timeline();
        // TODO *** steps 1 - 3 of the main method ***

		if (args.length < 1) {
			System.out.println("Usage: java TWITTER USER_FILES...");
			System.exit(0);
		}

		for(int ndx = 0;ndx < args.length;ndx++){		
			File src = new File(args[ndx]);
			if (!src.exists() || !src.canRead()) {
				System.out.println("Error: Cannot access input file");
				System.exit(0);
			}
			String user = args[ndx].split("\\.")[0];
			users.add(user);
            followers.add(user);
			ArrayList<Tweet> tweets = new ArrayList<Tweet>();
			Scanner inFile;
			try{
				inFile = new Scanner(src);
			} catch (FileNotFoundException e){
				return;
			}
			while (inFile.hasNext()) {
				String line = inFile.nextLine();
				String[] tokens = line.split(":");
				int time  = Integer.parseInt(tokens[0]);
				String message = tokens[1];
				try{
					tweets.add(new Tweet(time,message,user));
				}
				catch(TweetTooLongException e){}
			}
			tl.add(tweets);
			userTweets.add(tweets);
		}
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
						//check if users or followers passed in
                        if(commands.length != 2){
                            System.out.println("Invalid Command");
                        }
                        else if(commands[1].equals("users")){
                            Iterator<String> iter = users.iterator();
							while(iter.hasNext()){
								System.out.println(iter.next());
							}
                        }
                        else if(commands[1].equals("following")){
                            Iterator<String> iter = followers.iterator();
                            while(iter.hasNext()){
                                System.out.println(iter.next());
                            }

                        } 
						else{
                            System.out.println("Invalid Command");
						}  
						break;
					case "follow":
						//add user to follower list
						if(commands.length != 2){
							System.out.println("Invalid Command");
						}
						else if(followers.contains(commands[1])){
                            System.out.println("Warning: User already followed");
						}
						else{
							if(users.contains(commands[1])){
								followers.add(commands[1]);
								int ndx = 0;
								for(ndx=0;ndx < users.size();ndx++){
									if(commands[1].equals(users.get(ndx))){
										tl.add(userTweets.get(ndx));
									}
								}
							}
							else{
								System.out.println("Invalid user");
							}
						}
						break;
					case "unfollow":
						//remove user from follower list
                        if(commands.length != 2){
                            System.out.println("Invalid Command");
                        }
						else if(!users.contains(commands[1])){
                        	System.out.println("Invalid user");
						}
                        else if(!followers.contains(commands[1])){
                            System.out.println("Warning: User not followed");
                        }
                        else{
                            followers.remove(commands[1]);
                       		tl.remove(commands[1]);
                        }   
						break;
					case "search":
						//search for keyword
						if(commands.length == 2){
							Timeline hold = tl.search(commands[1]);
							hold.print();
						}						
						else
							System.out.println("Invalid Command");
						break;
					case "print":
						//check if second command, print list
						if(commands.length == 1)
							tl.print();
						else if(commands.length == 2){
							try{
								int val = Integer.parseInt(commands[1]);
								if(val<0)
									System.out.println("Invalid Command");
								else
									tl.print(Integer.parseInt(commands[1]));
							} catch(  NumberFormatException e){
								System.out.println("Invalid Command");
							}								
						}
						else
							System.out.println("Invalid Command");
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
