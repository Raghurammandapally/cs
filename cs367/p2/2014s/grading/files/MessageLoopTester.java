import java.util.*;
import java.io.*;

/**
 * Class to test MessageLoop class
 * This class uses the many of the same commands as the DisplayEditor 
 * and does some additional tests
 * 
 * The tester does not use the commands: ? s l j f r
 * 
 * @author CS 367 TAs
 *
 */
public class MessageLoopTester {
	private static MessageLoop<List<String>> loop;
	private static boolean checkCurrent = false, badCurrent = false;
	private static boolean checkIterNext = false, badIterNext = false;
	private static boolean checkHasNext = false;
	private static int beforeSize = -1;
	
	public static void main(String[] args) {
		Scanner in = null;
		if (!(args.length == 0 || args.length == 2)){
			System.out.println(args.length);
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
		
		/*
		 * Do a bunch of tests on empty loop
		 */
		if (!(loop instanceof LoopADT)) {
			error("MessageLoop doesn't implement LoopADT interface");
		}
	
		try {	
			if (loop.size() != 0)
				error("size incorrect for empty loop");
		} catch(Exception e) {
			error("size on empty loop threw an exception");
		}
		
		try {
			loop.getCurrent();
			error("getCurrent on empty loop did not throw exception");
		} catch (EmptyLoopException e) {
			// correct result
		} catch (Exception e) {
			error("getCurrent threw " + e.getClass().getName());
		}
		
		try {
			loop.removeCurrent();
			error("removeCurrent on empty loop did not throw exception");
		} catch (EmptyLoopException e) {
			// correct result
		} catch (Exception e) {
			error("removeCurrent threw " + e.getClass().getName());
		}
		
		try {
			loop.forward();
		} catch (Exception e) {
			error("forward on empty loop threw an exception");
		}
		
		try {
			loop.back();
		} catch (Exception e) {
			error("back on empty loop threw an exception");
		}
		
		/*
		 * Run the main loop
		 */
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
			if (useFile)
				System.out.println(input);

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
					try {
						if (loop.size() == 0)
							System.out.println("no messages");
						else {
							loop.forward();
							currentContext();					
						}
					} catch(Exception e) {
						error("size or forward on loop threw an exception " + e.getClass().getName());
					}
					break;
					
				case 'p':
					try {
						if (loop.size() == 0)
							System.out.println("no messages");
						else {
							loop.back();
							currentContext();
						}	
                    } catch(Exception e) {
                        error("size or back on loop threw an exception " + e.getClass().getName());
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
						try {
							if (loop.size() == 0) 
								System.out.println("no messages");
							else {
								int beforeSize = loop.size();
								loop.removeCurrent();
								if (loop.size() != beforeSize - 1)
									error("removeCurrent doesn't correctly update size");
								if (loop.size() == 0)
									System.out.println("no messages");
								else
									currentContext();	
							}
						} catch(Exception e) {
							error("size or removeCurrent on loop threw an exception " + e.getClass().getName());
						}
					}
					break;
					
				case 'a':
					msg = extract(input);
					if (msg != null) {
						try {	
							beforeSize = loop.size();
						} catch(Exception e) {
							error("size on loop threw an exception " + e.getClass().getName());
						}
            	        int len = msg.length();
            	        for (int i = 0; i < len;  i++) {
            	            if(!dm.isValidCharacter(Character.toString(msg.charAt(i)))) {
            	                throw new UnrecognizedCharacterException();
            	            }
            	        }
            	        for (int i = 0; i < len; i++) {
            	            str = Character.toString(msg.charAt(i));
            	            myList = dm.getDotMatrix(str);
			
							try {
    	        	            loop.addAfter(myList);
	                        } catch(Exception e) {
    	                        error("addAfter on loop threw an exception " + e.getClass().getName());
        	                }

            	        }

						try {
	            	        if (loop.size() != beforeSize + len) {
    	        	            error("addAfter doesn't correctly update size");
							}
                        } catch(Exception e) {
                            error("size on loop threw an exception " + e.getClass().getName());
                        }

            	        currentContext();
	
					}
					else
						System.out.println("invalid command");
					break;
					
				case 'i':
            	    msg = extract(input);
            	    if (msg != null) {

                        try {
                            beforeSize = loop.size();
                        } catch(Exception e) {
                            error("size on loop threw an exception " + e.getClass().getName());
                        }
            	    
				        int len = msg.length();
            	        for (int i = 0; i < len;  i++) {
            	            if(!dm.isValidCharacter(Character.toString(msg.charAt(i)))) {
            	                throw new UnrecognizedCharacterException();
            	            }
            	        }
            	        for(int i = 0; i < len; i++) {
            	            str = Character.toString(msg.charAt(i));
            	            myList = dm.getDotMatrix(str);
                            try {
                                loop.addBefore(myList);
                            } catch(Exception e) {
                                error("addBefore on loop threw an exception " + e.getClass().getName());
                            }
            	        }
                        try {
                            if (loop.size() != beforeSize + len) {
                                error("addBefore doesn't correctly update size");
                            }
                        } catch(Exception e) {
                            error("size on loop threw an exception " + e.getClass().getName());
                        }

            	        currentContext();
            	    }
            	    else
            	        System.out.println("invalid command");
            	    break;

                case 'r':
					try {
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
					} catch(Exception e) {
						error("remove command on loop threw an exception " + e.getClass().getName());	
					}
	
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
			} catch (UnrecognizedCharacterException e) {
				System.out.println("An unrecognized character has been entered.");
			}
		}
		if (useFile)
			in.close();
	}

	private static void error(String s) {
		System.out.println("Error: " + s);
	}

	@SuppressWarnings("unchecked")
	private static List<String> currListOfStrings(Object ob) {
		List<String> mylist = new ArrayList<String>();
		if (badCurrent)
			mylist = (List<String>) ((DblListnode)ob).getData();
		else
			mylist = (List<String>) ob;
	    return mylist;
	}

	private static String extract(String s) {
        String msg = null;
          if ( s.length() > 2 && s.charAt(1) == ' ')
            msg = s.substring(2, s.length());
        return msg;
	}

	private static void currentContext() {
		try {
			if (loop.size() < 1) {
				System.out.println("no messages");
				return;
			}
			
        	List<String> current = new ArrayList<String>();
        	current = loop.getCurrent();

			if (!checkCurrent && current instanceof DblListnode) {
				error("getCurrent returns a list node");
				badCurrent = true;
			}
			checkCurrent = true;		
			
			if (loop.size() > 2) {
        	    loop.back();
        	    List<String> myList1 = new ArrayList<String>();
        	    myList1 = currListOfStrings(loop.getCurrent());
        	    Iterator<String> myListIter1 = myList1.iterator();
        	    while(myListIter1.hasNext()) {
        	        System.out.println(myListIter1.next());
        	    }
        	    loop.forward();
			}
			
        	System.out.println("**********");
        	List<String> myList2 = new ArrayList<String>();
        	myList2 = currListOfStrings(loop.getCurrent());
        	Iterator<String> myListIter2 = myList2.iterator();
        	while(myListIter2.hasNext()) {
        	    System.out.println(myListIter2.next());
        	}
        	System.out.println("**********");

			
			if (loop.size() > 1) {
        	    loop.forward();
        	    List<String> myList3 = new ArrayList<String>();
        	    myList3 = currListOfStrings(loop.getCurrent());
        	    Iterator<String> myListIter3 = myList3.iterator();
        	    while(myListIter3.hasNext()) {
        	        System.out.println(myListIter3.next());
        	    }
        	    loop.back();
			}
		} catch(Exception e) {
			error("currentContext threw an exception " + e.getClass().getName());
		}
	}

	@SuppressWarnings("unchecked")
	private static void display() {
		try {
			if (loop.size() == 0)
				System.out.println("no messages");
			else {
				Iterator<List<String>> iter = loop.iterator();
				System.out.println("");
				while (iter.hasNext()) {
					Object ob = iter.next();
					if (!checkIterNext && ob instanceof DblListnode) {
						error("Iterator next returns list node");
						badIterNext = true;
					}
					checkIterNext = true;

					if (badIterNext) {
						List<String> myList = new ArrayList<String>();
						myList = (ArrayList<String>)((DblListnode)ob).getData();
	    	            Iterator<String> myListIter = myList.iterator();
    		            while(myListIter.hasNext()) {
        		            System.out.println(myListIter.next());
						}
						System.out.println("");
					}
					else {
						List<String> myList = new ArrayList<String>();
						myList = (ArrayList<String>)ob;
        	            Iterator<String> myListIter = myList.iterator();
        	            while(myListIter.hasNext()) {
        	                System.out.println(myListIter.next());
        	            }
						System.out.println("");
					}
				}
				if (!checkHasNext) {
					try {
						iter.next();  // expect an exception
						error("Iterator next did not throw exception - check if called hasNext");
					} catch (NoSuchElementException e) {
						// expected result
					} catch (Exception e) {
						error("hasNext threw " + e.getClass().getName());
					}
					checkHasNext = true;
				}
			}
		} catch(Exception e) {
			error("display threw an exception " + e.getClass().getName());
		}
	}

    private static void save(String filename) {
		try {
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
		} catch(Exception e) { 
			error("save threw an exception " + e.getClass().getName());
		}
    }

    private static void load(String filename) {
		try {
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
		} catch(Exception e) {
			error("load threw an exception " + e.getClass().getName());
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
        } catch (Exception e) {
			error("jump threw an exception " + e.getClass().getName());
		}
    }

}
