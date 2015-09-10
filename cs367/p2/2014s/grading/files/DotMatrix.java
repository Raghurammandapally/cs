import java.io.*;
import java.util.*;

public class DotMatrix {

     private Map <String, List<String>> dm;
     static String characters = null;

     public DotMatrix() {
       	dm = new TreeMap<String, List<String>>();
     }
     
     public void loadAlphabets(String filename) {
     	try {
        	//String characters = null;
      		File f = new File(filename);
      		Scanner s = new Scanner(f); 
        	String line = null; 
      		List<String> dot_mat_alpha = new ArrayList<String>();			      
      		if(s.hasNext())
      			characters = s.nextLine();
      		int i = 0;
        	while (s.hasNext() && (line = s.nextLine()) != null && i < characters.length()) {	    	  
      			char c = line.charAt(0);
      			if (c == '#') {	    		  
      				String alpha = Character.toString(characters.charAt(i++));					  
      				this.dm.put(alpha, dot_mat_alpha);
      				dot_mat_alpha = new ArrayList<String>();
      			}
      			else 
      				dot_mat_alpha.add(line);						  					  	    	  
      		}					
      		//Dot Matrix representation for the blank character
      		dot_mat_alpha = new ArrayList<String>(); 
      		for(int j = 0; j < 7; j++) {
      			dot_mat_alpha.add("     ");
      		}
      		this.dm.put(" ", dot_mat_alpha);
      	}

      	catch (FileNotFoundException e) {
      		System.out.println(" Dot Matrix Alphabets file not found");
      		System.exit(1);
      	}
    }// End of method
    
    public List<String> getDotMatrix (String s) {
        List<String> dot_mat_rep = this.dm.get(s.toUpperCase());
        return dot_mat_rep;
        
    }

	public boolean isValidCharacter(String s) {
		return (characters.toUpperCase().contains(s.toUpperCase()) || s.equals(" "));
	}
   
} // End of class DotMatrix
