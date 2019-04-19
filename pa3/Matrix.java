/* Guangyang Chen
 * gchen34@ucsc.edu
 * pa3
 */
public class Matrix {
    private class Entry {
	int column;
	double value;

	Entry (int column, double value) {
	    this.column = column;
	    this.value = value;
	}

	public String toString() {
	    return " (" + column + ", " + value + ")";
	}
	
	public boolean equals(Object x) {
	    Entry temp = (Entry) x;
	    return (this.column == temp.column && this.value == temp.value);
	}
    }

    // Matrix info ----------------
    int n;
    int nnz;
    List[] matrix;

    // Constructor --------------
    Matrix(int n){
	if (n >= 1) {
	    this.n = n;
	    nnz = 0;
	    matrix = new List[n];
	    for (int i = 0; i < n; i++) {
		matrix[i] = new List();
	    }
	}
    } // Makes a new n x n zero Matrix. pre: n>=1
   
    // Access functions -----------------
    int getSize() {
	return this.n;
    } // Returns n, the number of rows and columns of this Matrix
    
    int getNNZ() {
	return this.nnz;
    } // Returns the number of non-zero entries in this Matrix
    
    public boolean equals(Object x) {
	boolean tb = true;
	if (x instanceof Matrix) {
	    Matrix temp = (Matrix) x;
	    if (temp.getNNZ() !=  this.getNNZ()) tb = false;
	    if (temp.getSize() != this.getSize()) tb = false;
	    
	    for (int i = 0; i < n; i++) {
		if (!this.matrix[i].equals(temp.matrix[i])) {
		    tb = false;
		    break;
		}
	    }
	}
	return tb;
    } // overrides Object's equals() method
    
    // Manipulation procedures ---------------------
    void makeZero() {
	if (n != 0) {
	    for (int i = 0; i < n; i++) {
		matrix[i].clear();
	    }
	}
	nnz = 0;
	return;
    }// sets this Matrix to the zero state
    
    Matrix copy() {
	Matrix result = new Matrix(n);
	for(int i = 0; i < n; i++) {
	    if (matrix[i].length() != 0) {
		matrix[i].moveFront();
		while (matrix[i].index() != -1) {
		    result.matrix[i].append(new Entry(((Entry) matrix[i].get()).column, ((Entry) matrix[i].get()).value));
		    matrix[i].moveNext();
		}
	    }
	}
	result.nnz = this.nnz;
	return result;
    }// returns a new Matrix having the same entries as this Matrix
    
    void changeEntry(int i, int j, double x) {
	if ( !(i >= 1 && i <= getSize()) ) {
	    return;
	}
	if ( !(j >= 1 && j <= getSize()) ) {
	    return;
	}
	Entry e = new Entry(j, x);
	if (matrix[i - 1].length() == 0) {
	    if (x != 0){
		matrix[i - 1].append(e);
		nnz++;
	    }
	    return;
	}
	else {
	    matrix[i - 1].moveFront();
	    if (x == 0) {
		while (matrix[i - 1].index() != -1) {
		    if (((Entry) matrix[i - 1].get()).column == j) {
			matrix[i - 1].delete();
			nnz--;
			break;
		    }
		    else {
			matrix[i - 1].moveNext();
		    }
		}
		return;
	    }
	    else {
		if (j < ((Entry) matrix[i - 1].front()).column) {
		    matrix[i - 1].prepend(e);
		    nnz++;
		    return;
		}
		if (((Entry) matrix[i - 1].back()).column < j) {
		    matrix[i - 1].append(e);
		    nnz++;
		    return;
		}
		
		while (matrix[i - 1].index() != -1) {
		    if (((Entry) matrix[i - 1].get()).column == j) {
			((Entry) matrix[i - 1].get()).value = x;
			break;
		    }
		    if (j < ((Entry) matrix[i - 1].get()).column) {
			matrix[i - 1].insertBefore(e);
			nnz++;
			break;
		    }
		    matrix[i - 1].moveNext();
		}
	    }
	}
	return;
    }
    // changes ith row, jth column of this Matrix to x
    // pre: 1<=i<=getSize(), 1<=j<=getSize()

    Matrix scalarMult(double x) {
	Matrix result = new Matrix(n);
	if (x == 0) {
	    result.makeZero();
	}
	else {
	    for (int i = 0; i < this.n; i++) {
		if (matrix[i].length() != 0) {
		    matrix[i].moveFront();
		    while (matrix[i].index() != -1) {
			result.matrix[i].append(new Entry(((Entry) matrix[i].get()).column, ((Entry) matrix[i].get()).value * x));
			matrix[i].moveNext();
		    }
		}
	    }
	}
	result.nnz = this.nnz;
	return result;
    }
    // returns a new Matrix that is the scalar product of this Matrix with x

    Matrix add(Matrix M) {
	Matrix result = new Matrix(n);
	if (M.getSize() != this.getSize()) {
	    throw new RuntimeException("Different size: can't add");
	}
	if (this.equals(M) == true) {
	    result = this.scalarMult(2.0);
	    return result;
	}

	for (int i = 0; i < n; i++) {
	    // both row contain all zeros
	    if (this.matrix[i].length() == 0 && M.matrix[i].length() == 0) {
		continue;
	    }
	    // one row contains all zeros
	    if (this.matrix[i].length() == 0 && M.matrix[i].length() > 0) {
		M.matrix[i].moveFront();
		while (M.matrix[i].index() != -1) {
		    result.matrix[i].append(new Entry(((Entry) M.matrix[i].get()).column, ((Entry) M.matrix[i].get()).value));
		    M.matrix[i].moveNext();
		    result.nnz++;
		}
		continue;
	    }
	    if (this.matrix[i].length() > 0 && M.matrix[i].length() == 0) {
		this.matrix[i].moveFront();
		while (this.matrix[i].index() != -1) {
		    result.matrix[i].append(new Entry(((Entry) this.matrix[i].get()).column, ((Entry) this.matrix[i].get()).value));
		    this.matrix[i].moveNext();
		    result.nnz++;
		}
		continue;
	    }
	    // neither row contains all zeros
	    if (this.matrix[i].length() > 0 && M.matrix[i].length() > 0) {
		this.matrix[i].moveFront();
		M.matrix[i].moveFront();
		while (this.matrix[i].index() != -1 && M.matrix[i].index() != -1) {
		    if (((Entry) this.matrix[i].get()).column == ((Entry) M.matrix[i].get()).column) {
			if (((Entry) this.matrix[i].get()).value + ((Entry) M.matrix[i].get()).value != 0){
			    result.matrix[i].append(new Entry(((Entry) this.matrix[i].get()).column,((Entry) this.matrix[i].get()).value + ((Entry) M.matrix[i].get()).value));
			    result.nnz++;
			}
			this.matrix[i].moveNext();
			M.matrix[i].moveNext();
			continue;
		    }
		    if (((Entry) this.matrix[i].get()).column > ((Entry) M.matrix[i].get()).column) {
			result.matrix[i].append(new Entry(((Entry) M.matrix[i].get()).column, ((Entry) M.matrix[i].get()).value));
			result.nnz++;
			M.matrix[i].moveNext();
			continue;
		    }
		    if (((Entry) this.matrix[i].get()).column < ((Entry) M.matrix[i].get()).column) {
			result.matrix[i].append(new Entry(((Entry) this.matrix[i].get()).column, ((Entry) this.matrix[i].get()).value));
			result.nnz++;
			this.matrix[i].moveNext();
			continue;
		    }
		}
		while (this.matrix[i].index() != -1) {
		    result.matrix[i].append(new Entry(((Entry) this.matrix[i].get()).column, ((Entry) this.matrix[i].get()).value));
		    result.nnz++;
		    this.matrix[i].moveNext();
		}
		while (M.matrix[i].index() != -1) {
		    result.matrix[i].append(new Entry(((Entry) M.matrix[i].get()).column, ((Entry) M.matrix[i].get()).value));
		    result.nnz++;
		    M.matrix[i].moveNext();
		}
	    }
	}
	return result;
    }
    // returns a new Matrix that is the sum of this Matrix with M
    // pre: getSize()==M.getSize()
    
    Matrix sub(Matrix M) {
	Matrix result = new Matrix(n);
	if (M.getSize() != this.getSize()) {
	    throw new RuntimeException("Different size: can't subtract");
	}
	if (this.equals(M)) {
	    result.makeZero();
	    return result;
	}
	for (int i = 0; i < n; i++) {
	    if (this.matrix[i].length() == 0 && M.matrix[i].length() == 0) {
		continue;
	    }
	    if (this.matrix[i].length() == 0 && M.matrix[i].length() > 0) {
		M.matrix[i].moveFront();
		while (M.matrix[i].index() != -1) {
		    result.matrix[i].append(new Entry(((Entry) M.matrix[i].get()).column, -((Entry) M.matrix[i].get()).value));
		    result.nnz++;
		    M.matrix[i].moveNext();
		}
		continue;
	    }
	    if (this.matrix[i].length() > 0 && M.matrix[i].length() == 0) {
		this.matrix[i].moveFront();
		while (this.matrix[i].index() != -1) {
		    result.matrix[i].append(new Entry(((Entry) this.matrix[i].get()).column, ((Entry) this.matrix[i].get()).value));
		    result.nnz++;
		    this.matrix[i].moveNext();
		}
		continue;
	    }
	    if (this.matrix[i].length() > 0 && M.matrix[i].length() > 0) {
		this.matrix[i].moveFront();
		M.matrix[i].moveFront();
		while(this.matrix[i].index() != -1 && M.matrix[i].index() != -1) {
		    if (((Entry) this.matrix[i].get()).column == ((Entry) M.matrix[i].get()).column) {
			if (((Entry) this.matrix[i].get()).value - ((Entry) M.matrix[i].get()).value != 0) {
			    result.matrix[i].append(new Entry(((Entry) this.matrix[i].get()).column, ((Entry) this.matrix[i].get()).value - ((Entry) M.matrix[i].get()).value));
			    result.nnz++;
			}
			this.matrix[i].moveNext();
			M.matrix[i].moveNext();
			continue;
		    }
		    if (((Entry) this.matrix[i].get()).column < ((Entry) M.matrix[i].get()).column) {
			result.matrix[i].append(new Entry(((Entry) this.matrix[i].get()).column, ((Entry) this.matrix[i].get()).value));
			result.nnz++;
			this.matrix[i].moveNext();
			continue;
		    }
		    if (((Entry) this.matrix[i].get()).column > ((Entry) M.matrix[i].get()).column) {
			result.matrix[i].append(new Entry(((Entry) M.matrix[i].get()).column, -((Entry) M.matrix[i].get()).value));
			result.nnz++;
			M.matrix[i].moveNext();
			continue;
		    }
		}
		while (this.matrix[i].index() != -1) {
		    result.matrix[i].append(new Entry(((Entry) this.matrix[i].get()).column, ((Entry) this.matrix[i].get()).value));
		    result.nnz++;
		    this.matrix[i].moveNext();
		}
		while (M.matrix[i].index() != -1) {
		    result.matrix[i].append(new Entry(((Entry) M.matrix[i].get()).column, -((Entry) M.matrix[i].get()).value));
		    result.nnz++;
		    M.matrix[i].moveNext();
		}
	    }
	}
	return result;
    }
    // returns a new Matrix that is the difference of this Matrix with M
    // pre: getSize()==M.getSize()

    Matrix transpose() {
	Matrix result = new Matrix(this.n);
	for (int i = 0; i < this.n; i++) {
	    if (this.matrix[i].length() != 0) {
		this.matrix[i].moveFront();
		while (this.matrix[i].index() != -1) {
		    result.changeEntry(((Entry) this.matrix[i].get()).column, i + 1, ((Entry) this.matrix[i].get()).value);
		    this.matrix[i].moveNext();
		}
	    }
	}
	result.nnz = this.nnz;
	return result;
    }
    // returns a new Matrix that is the transpose of this Matrix
    
    Matrix mult(Matrix M){
	Matrix result = new Matrix(this.n);
	Matrix T = M.transpose();
	for (int i = 0; i < this.n; i++) {
	    for (int j = 0; j < this.n; j++) {
		if (dot(this.matrix[i], T.matrix[j]) != 0) {
		    result.matrix[i].append(new Entry(j+1,dot(this.matrix[i], T.matrix[j])));
		    result.nnz++;
		}
	    }
	}
	return result;
    }
    // returns a new Matrix that is the product of this Matrix with M
    // pre: getSize()==M.getSize()

    // Other functions ------------------------
    public String toString() {
	String str = "";
	for (int i = 0; i < this.n; i++) {
	    if (this.matrix[i].length() == 0) {
		continue;
	    }
	    if (this.matrix[i].length() > 0) {
		str += (i + 1) + ":";
		this.matrix[i].moveFront();
		while (this.matrix[i].index() != -1) {
		    str += ((Entry) this.matrix[i].get()).toString();
		    this.matrix[i].moveNext();
		}
	    }
	    str += "\n";
	}
	return str;
    } // overrides Object's toString() method

    public static double dot(List P, List Q) {
	double result = 0;
	if (P.equals(Q)) {
	    P.moveFront();
	    while (P.index() != -1) {
		result += ((Entry) P.get()).value * ((Entry) P.get()).value;
		P.moveNext();
	    }
	    return result;
	}
       	P.moveFront();
	Q.moveFront();
	while (P.index() != -1 && Q.index() != -1) {
	    if (((Entry) P.get()).column == ((Entry) Q.get()).column) {
		result = result + ((Entry) P.get()).value * ((Entry) Q.get()).value;
		P.moveNext();
		Q.moveNext();
	    }
	    else if (((Entry) P.get()).column < ((Entry) Q.get()).column) {
		P.moveNext();
	    }
	    else {
		Q.moveNext();
	    }
	}
	return result;
    } // calculate dot product 
}