import java.util.*;
import java.io.*;

public class DisplayEditor {
	private static LoopADT<List<String>> loop;
	public static void main(String[] args) {
		Scanner in = null;
		if (args.length != 0  && args.length != 2){
			System.err.println("invalid command-line arguments");
			System.exit(1);
		}
		boolean useFile = args.length == 2;
		if (useFile) {
			File inFile = new File(args[0]);
			if (!inFile.exists() || !inFile.canRead()) {
			    System.err.println("Problem with input file!");
			    System.exit(1);
			 }
			try {
			 in = new Scanner(inFile);
			} catch (FileNotFoundException e) {
			    System.err.println("Problem with input file!");
			    System.exit(1);
			}
		}
		else
			in = new Scanner(System.in);
		
		loop = new MessageLoop<List<String>>();
		boolean again = true;
		String msg;
		String str;
		String fileName;
		DotMatrix dm = new DotMatrix();
		if(!useFile) {
			System.out.print("Enter the dot-matrix alphabets file:");
			Scanner fileScan = new Scanner(System.in);
			fileName = fileScan.nextLine();
		} else {
			fileName = args[1];
		}
		
		dm.loadAlphabets(fileName);
        List<String> myList = new ArrayList<String>();
	
		while (again) {
			System.out.print("enter command (? for help)> ");
			String input = in.nextLine();
			if (useFile) {
				System.out.println(input);
			}
			char choice;
			if(input != null && !input.isEmpty())
				choice = input.charAt(0);
			else
				choice = 'z';
			try {	
				switch (choice) {
				case '?':
					System.out.println("s (save)    l (load)       d (display)");
					System.out.println("n (next)    p (previous)   j (jump)");
					System.out.println("x (delete)  a (add after)  i (insert before)");
					System.out.println("c (context) r (replace)    q (quit)");
					break;
					
				case 's':
					msg = extract(input);
					if (msg != null)
						save(msg);
					else
						System.out.println("invalid command");
					break;
					
				case 'l':
					msg = extract(input);
					if (msg != null)
						load(msg);
					else
						System.out.println("invalid command");
					break;
					
				case 'd': 
					display();
					break;
					
				case 'n':
					if (loop.size() == 0)
						System.out.println("no messages");
					else {
						loop.forward();
						currentContext();					
					}
					break;
					
				case 'p':
					if (loop.size() == 0)
						System.out.println("no messages");
					else {
						loop.back();
						currentContext();	
					}
					break;
					
				case 'j':
					msg = extract(input);
					if (msg != null) 
						jump(msg);
					else
						System.out.println("invalid command");
					break;
					
				case 'x':
					msg = extract(input);
					if(msg != null)
						System.out.println("invalid command");
					else {
						if (loop.size() == 0) 
							System.out.println("no messages");
						else {
							loop.removeCurrent();
							if (loop.size() == 0)
								System.out.println("no messages");
							else
								currentContext();	
						}
					}
					break;
				
				case 'a':
					msg = extract(input);
					if (msg != null) {
						int len = msg.length();
						for (int i = 0; i < len;  i++) {
							if(!dm.isValidCharacter(Character.toString(msg.charAt(i)))) {
								throw new UnrecognizedCharacterException();
							}
						}
						for (int i = 0; i < len; i++) {			
							str = Character.toString(msg.charAt(i));
							myList = dm.getDotMatrix(str);
							loop.addAfter(myList);
						}
						currentContext();	
					}
					else
						System.out.println("invalid command");
					break;

				case 'i':
					msg = extract(input);
					if (msg != null) {
            	        int len = msg.length();
                        for (int i = 0; i < len;  i++) {
                            if(!dm.isValidCharacter(Character.toString(msg.charAt(i)))) {
                                throw new UnrecognizedCharacterException();
                            }
                        }
            	        for(int i = 0; i < len; i++) {
            	            str = Character.toString(msg.charAt(i));
							myList = dm.getDotMatrix(str);
							loop.addBefore(myList);
            	        }
						currentContext();	
					}
					else
						System.out.println("invalid command");
					break;
					
				case 'r':
					msg = extract(input);
					if (msg != null && msg.length() == 1) {
						if (loop.size() == 0) 
							System.out.println("no messages");
						else {
							if(dm.isValidCharacter(msg)) 
								myList = dm.getDotMatrix(msg);
							else
								throw new UnrecognizedCharacterException();
							loop.removeCurrent();
							loop.addBefore(myList);
							currentContext();
						}
					}
					else
						System.out.println("invalid command");
					break;
			
				case 'c':
					msg = extract(input);
					if(msg != null)
						System.out.println("invalid command");
					else
						currentContext();
					break;
					
				case 'q': 
                   	msg = extract(input);
                    if(msg != null)
                        System.out.println("invalid command");
                    else {
						System.out.println("quit");
						again = false;
					}
					break;

				case 'z':
					break;

				default:
					System.out.println("invalid command");
				}
		    }        
		    catch (UnrecognizedCharacterException e) {
              System.out.println("An unrecognized character has been entered.");
            }
		}
		if (useFile)
			in.close();
	}
	private static String extract(String s) {
		String msg = null;
		  if ( s.length() > 2 && s.charAt(1) == ' ')
			msg = s.substring(2, s.length());
		return msg;
	}

	private static void currentContext() {
		if (loop.size() < 1) {
			System.out.println("no messages");
			return;
		}
		
		if (loop.size() > 2) {
			loop.back();
			List<String> myList1 = new ArrayList<String>();
			myList1 = loop.getCurrent();
			Iterator<String> myListIter1 = myList1.iterator();
			while(myListIter1.hasNext()) {
				System.out.println(myListIter1.next());
			}
			loop.forward();
		}
		
		System.out.println("**********");
		List<String> myList2 = new ArrayList<String>();
        myList2 = loop.getCurrent();
        Iterator<String> myListIter2 = myList2.iterator();
        while(myListIter2.hasNext()) {
            System.out.println(myListIter2.next());
        }
		System.out.println("**********");
	
		if (loop.size() > 1) {
			loop.forward();
			List<String> myList3 = new ArrayList<String>();
			myList3 = loop.getCurrent();
            Iterator<String> myListIter3 = myList3.iterator();
            while(myListIter3.hasNext()) {
                System.out.println(myListIter3.next());
            }
			loop.back();
		}
	}
	
	private static void save(String filename) {
		if (loop.size() == 0) {
			System.out.println("no messages to save");
			return;
		}

		File file = new File(filename);
		if (file.exists()) 
			System.out.println("warning: file already exists, will be overwritten");
		if (file.exists() && !file.canWrite()) {
			System.out.println("unable to save");
			return;
		}
		try {
			PrintStream outFile = new PrintStream(file);
			Iterator<List<String>> iter = loop.iterator();
			while (iter.hasNext()) {
				List<String> tempList = iter.next();
				Iterator<String> iter_str = tempList.iterator();
				while (iter_str.hasNext()) {
					outFile.println(iter_str.next());
				}	
				outFile.println("##########");
			}
			outFile.close();
		} catch (FileNotFoundException e) {
			System.out.println("unable to save");
		}
	}
	
	private static void load(String filename) {
		File file = new File(filename);
		if (!file.exists() || !file.canRead()) {
			System.out.println("unable to load");
			return;
		}
		try {
			Scanner inFile = new Scanner(file);
			int count = 0; // count # of msgs added
			while (inFile.hasNext()) {
				String str_line = inFile.nextLine();
				List<String> node = new ArrayList<String>();
				while (str_line.charAt(0) != '#') {
					node.add(str_line);
					str_line = inFile.nextLine();
				}
				loop.addAfter(node);
				count++;
			}
			for (int i = count-1; i > 0; i--)
				loop.back();
			inFile.close();
		} catch (FileNotFoundException e) {
			
		}
	}

	private static void display() {
		Scanner s = new Scanner(System.in);
		List<String> myList = new ArrayList<String>();
		if (loop.size() == 0)
			System.out.println("no messages");
		else {
			int size = loop.size();
		    Iterator<List<String>> iter = loop.iterator();
			System.out.println("");
			while (iter.hasNext()) {
				myList = iter.next();
				Iterator<String> myListIter = myList.iterator();
        		while(myListIter.hasNext()) {
		           	System.out.println(myListIter.next());
        		}
				System.out.println("");
			}
		}
	}
	
	private static void jump(String msg) {
		try {
			int val = Integer.parseInt(msg);

			if (loop.size() == 0) {
				System.out.println("no messages");
				return;
			}
			if (val > 0)
				for (int i = 0; i < val; i++)
					loop.forward();
			else {
				val = -1 * val;
				for (int i = 0; i < val; i++)
					loop.back();
			}
			currentContext();
		} catch (NumberFormatException e) {
			System.out.println("invalid command");
		}
	}
}
