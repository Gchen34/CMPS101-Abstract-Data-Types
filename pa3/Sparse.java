/* Guangyang Chen
 * gchen34@ucsc.edu
 * pa3
 */
import java.io.*;
import java.util.Scanner;

class Sparse {
    public static void main (String args[]) throws IOException {
	Scanner input;
	String line = "";
	int lineNumber = 0;
	String token[] = null;

	if (args.length < 2) {
	    System.exit(1);
	}
	input = new Scanner(new File(args[0]));
	
	Matrix A = new Matrix(1); 
	Matrix B = new Matrix(1); 
	int n = 0, nnzA = 0, nnzB = 0;
	
	while(input.hasNextLine()) {
	    lineNumber++;
	    line = input.nextLine() + " ";
	    token = null;
	    token = line.split("\\s+");
	    
	    if (lineNumber == 1) {
		A = new Matrix((int)Integer.parseInt(token[0]));
		B = new Matrix((int)Integer.parseInt(token[0]));
		nnzA = (int)Integer.parseInt(token[1]);
		nnzB = (int)Integer.parseInt(token[2]);
		continue;
	    }
	    if (lineNumber > 2) {
		if (lineNumber <= nnzA + 2) {
		    A.changeEntry((int)Integer.parseInt(token[0]), (int)Integer.parseInt(token[1]), (Double)Double.parseDouble(token[2]));
		    continue;
		}
		if (lineNumber > nnzA + 3) {
		    B.changeEntry((int)Integer.parseInt(token[0]), (int)Integer.parseInt(token[1]), (Double)Double.parseDouble(token[2]));
		    continue;
		}
	    }
	}
	
	input.close();
	
	PrintWriter output;
	output = new PrintWriter(new FileWriter(args[1]));

	output.println("A has " + nnzA + " non-zero entries: ");
	output.println(A);
	output.println("B has " + nnzB + " non-zero entries: ");
	output.println(B);

	// 1.5*A
	output.println("(1.5)*A =");
	output.println(A.scalarMult(1.5));
	// A+B
	output.println("A+B =");
	output.println(A.add(B));
	// A+A
	output.println("A+A =");
	output.println(A.add(A));
	// B-A
	output.println("B-A =");
	output.println(B.sub(A));
	// A-A
	output.println("A-A =");
	output.println(A.sub(A));
	// Transpose(A)
	output.println("Transpose(A) =");
	output.println(A.transpose());
	// A*B
	output.println("A*B =");
	output.println(A.mult(B));
	// B*B
	output.println("B*B =");
	output.println(B.mult(B));

	output.close();
    }
}