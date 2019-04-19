import java.io.*;
import java.util.Scanner;
public class Lex {

	public static void main(String[] args) throws IOException {
		Scanner in = null;
		PrintWriter out = null;
		String[] words = null;
		int i = 0;
		int n = 0;
		int lineNumber = 0;

		// check two command line arguments
		if (args.length < 2) {
			System.err.println("Usage: <infile> <outfile>");
			System.exit(1);
		}

		// open files
		try {
			// initialize input and output
			in = new Scanner(new File(args[0]));
			out = new PrintWriter(new FileWriter(args[1]));
		} catch (FileNotFoundException e) {
			System.err.println("Error: file target <" + args[0] + "> not found");
			System.exit(1);
		}
		
		// count lines
		while (in.hasNextLine()) {
			n++;
			in.nextLine();
		}
		// close file
		in.close();

		// Place words into Array
		words = new String[n];
		in = new Scanner(new File(args[0]));
		while( in.hasNextLine()){
			words[lineNumber++] = in.nextLine();
		}
		
		List L = new List();// Declare list for indexes
		int j,k; 
		String temp;
		L.append(0);//create list with zero_th element
		L.moveFront(); //set and define cursor
		
		for(j=1; j<words.length; j++){
			k = L.get(); //get pointer index
			i = j-1; //length
			temp = words[j];
			while(i>=0 && temp.compareTo(words[k])<0){
				i--; 
				L.movePrev(); 
				try{
					k = L.get();
				}catch(RuntimeException e){
					//get() has -1 index exception error
					break;
				}
			}
			if(L.index() >= 0){
				L.insertAfter(j); //insert after pointer cursor
			}else if(L.index() < 0){
				L.prepend(j); //put at the front of list  
			} 
			L.moveBack(); //reset cursor to the back of sorted list
		}
		
		//print
		L.moveFront();
		while(L.index()>=0){
			i = L.get();
			out.println(words[i]);
			L.moveNext();
		}
		//close file reading and writing
		in.close();
		out.close();
	}

}
