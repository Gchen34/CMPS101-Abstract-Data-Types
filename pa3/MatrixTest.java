/* Guangyang Chen
 * gchen34@ucsc.edu
 * pa3
 */
public class MatrixTest {
    public static void main(String[] args){
	int i, j, n=1000;
	Matrix A = new Matrix(n);
	Matrix B = new Matrix(n);

	A.changeEntry(1,1,1); B.changeEntry(1,1,1);
	A.changeEntry(1,2,2); B.changeEntry(1,2,0);
	A.changeEntry(1,3,3); B.changeEntry(1,3,1);
	A.changeEntry(2,2,5); B.changeEntry(2,1,1);
	A.changeEntry(2,1,4); B.changeEntry(2,2,1);
	A.changeEntry(2,3,6); B.changeEntry(2,3,0);
	A.changeEntry(3,1,7); B.changeEntry(3,1,1);
	A.changeEntry(3,2,8); B.changeEntry(3,2,1);
	A.changeEntry(3,3,9); B.changeEntry(3,3,1);

	System.out.println(A.getNNZ());
	System.out.println(A);

	Matrix C = new Matrix(n);
	C = A.transpose();
	System.out.println(C);
	
	System.out.println(B.getNNZ());
	System.out.println(B);
	
	Matrix D = new Matrix(n);
	D = B.scalarMult(3);
	System.out.println(D);
	
	Matrix E = A.add(B);
	System.out.println(E);
	
	Matrix F = A.sub(A);
	System.out.println(F);

	Matrix G = B.mult(C);
	System.out.println(G);
	
	Matrix H = C.mult(F);
	System.out.println(H);
	
    System.out.println("This is matrix A");
    System.out.println(A.getNNZ());
    System.out.println(A);

	A.makeZero();
	System.out.println(A.getNNZ());
	System.out.println(A);
    }
}
