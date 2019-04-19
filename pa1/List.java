
public class List {
	// private inner Node class
	private class Node {
		int data;
		Node next;
		Node prev;

		// Constructor
		Node(int data) {
			this.data = data;
			this.next = null;
			this.prev = null;
		}

		// toString(): overrides Object's toString() method
		public String toString() {
			return String.valueOf(data);
		}

		// equals(): overrides Object's equals() method
		public boolean equals(Object x) {
			boolean eq = false;
			Node that;
			if (x instanceof Node) {
				that = (Node) x;
				eq = (this.data == that.data);

			}
			return eq;
		}
	}

	// List fields
	private Node head;
	private Node tail;
	private int length;
	private int index;
	private Node cursor;

	// Constructor
	// Creates a new empty list.
	List() {
		head = null;
		tail = null;
		length = 0;
		index = -1;
		cursor = null;
	}

	// Access functions
	int length() {
		return length;
	} // Returns the number of elements in this List.

	int index() {
		if (cursor != null) {
			return index;
		}
		return -1;
	}
	// If cursor is defined, returns the index of the cursor element,
	// otherwise returns -1.

	int front() throws RuntimeException {
		if (length() <= 0) {
			throw new RuntimeException("List Error: front() called on empty list");
		}
		return head.data;
	} // Returns front element. Pre: length()>0

	int back() throws RuntimeException {
		if (length() <= 0) {
			throw new RuntimeException("List Error: length() <= 0");
		}
		return tail.data; // return value
	}// Returns back element. Pre: length()>0

	int get() throws RuntimeException {
		if (length() <= 0) {
			throw new RuntimeException("List Error: get() called on empty list");
		}
		if (index() < 0) {
			throw new RuntimeException("List Error: get() called with undefined index on List");
		}
		return cursor.data;
	} // Returns cursor element. Pre: length()>0, index()>=0

	boolean equals(List L) {
		boolean eq = false;
		Node N;

		if (L != null) {
			N = this.head;
			L.moveFront();
			eq = (this.length() == L.length());
			while (eq && N != null) {
				eq = (N.data == L.get());
				N = N.next;
				L.moveNext();
			}
		}
		return eq;
	} // Returns true if this List and L are the same integer
		// sequence. The cursor is ignored in both lists.
		// Manipulation procedures

	void clear() {
		cursor = null;
		head = null;
		tail = null;
		length = 0;
		index = -1;
	} // Resets this List to its original empty state.

	void moveFront() {
		if (length() > 0) {
			cursor = head;
			index = 0;
		} // index == 0 cursor is at head
	} // If List is non-empty, places the cursor under the front element,
		// otherwise does nothing.

	void moveBack() {
		if (length() > 0) {
			cursor = tail;
			index = length() - 1;
		}
	} // If List is non-empty, places the cursor under the back element,
		// otherwise does nothing.

	void movePrev() {
		if (cursor != null) {
			cursor = cursor.prev;
			index--;
		}
	}       // If cursor is defined and not at front, moves cursor one step toward
		// front of this List, if cursor is defined and at front, cursor becomes
		// undefined, if cursor is undefined does nothing.

	void moveNext() {
		if (cursor != null) {
			cursor = cursor.next;
			index++;
		}
	}       // If cursor is defined and not at back, moves cursor one step toward
		// back of this List, if cursor is defined and at back, cursor becomes
		// undefined, if cursor is undefined does nothing.

	void prepend(int data) {
		cursor = head;
		if (length() > 0) {
			head.prev = N;
			N.next = head;
			head = N;
		} else {// length == 0 case
			head = N;
			tail = N;
		}
		length++;

	} // Insert new element into this List. If List is non-empty,
		// insertion takes place before front element.

	void append(int data) {
		Node N = new Node(data);
		if (length() > 0) {
			if (length() == 1) {
				head.next = N;
				N.prev = head;
			} else {
				tail.next = N;
				N.prev = tail;
			}
		} else {// zero element case
			head = N;
		}
		tail = N;
		length++;
	} // Insert new element into this List. If List is non-empty,
	  // insertion takes place after back element.

	void insertBefore(int data) {
		if (length() > 0 && index() >= 0) {
			Node P = cursor.prev; // previous Node
			Node N = new Node(data);
			cursor.prev = N; // Cursor points to N
			N.next = cursor;
			N.prev = P; 
			if (cursor.equals(head)) {
				head = N;
			}
			if (P != null) {
				P.next = N; // Prev point to N
			}
			length++;
		}
	}       // Insert new element before cursor.
		// Pre: length()>0, index()>=0

	void insertAfter(int data) {
		if (length() > 0 && index() >= 0) {
			Node A = cursor.next; // next Node
			Node N = new Node(data);
			cursor.next = N; // Cursor point to N
			N.prev = cursor;
			N.next = A; 
			if (cursor.equals(tail)) {
				tail = N;
			}
			if (A != null) {
				A.prev = N; // After point to N
			}
			length++;
		}
	}       // Inserts new element after cursor.
		// Pre: length()>0, index()>=0

	/************************************************************************************/
	void deleteFront() {
		if (length() > 0) {
			if (length() == 1) {// one element case
				head = null;
				tail = null;
			} else {
				head = head.next;
				head.prev = null;
				if (length() == 2) {
					tail = head;
				}
			}
			length--;
		}
	} // Deletes the front element. Pre: length()>0

	void deleteBack() {
		if (length() > 0) {
			if (length() == 1) {// one element case
				head = null;
				tail = null;
			} else {
				tail = tail.prev;
				tail.next = null;
				if (length() == 2) {
					head = tail;
				}
			}
			length--;
		}
	} // Deletes the back element. Pre: length()>0

	void delete() {
		if (index() >= 0) {
			if (length() == 1) {// one element case
				head = null;
				tail = null;
				length--;
			} else if (length() > 1) {// multiple element case
				if (cursor.equals(head)) { // cursor points to head
					deleteFront();
				} else if (cursor.equals(tail)) {// cursor points to tail
					deleteBack();
				} else {// multiple nodes case
					Node P = cursor.prev; 
					Node N = cursor.next; 
					P.next = N; 
					N.prev = P; 
					length--;
				}

			}
			cursor = null;
			index = -1;
		}
	}
	// Deletes cursor element, making cursor undefined.
	// Pre: length()>0, index()>=0
	// 3
	
	// Other methods
	private String myString(Node H) {
		if (H != null) {
			return H + " " + myString(H.next);
		}
		return "";
	}

	List concat(List L) {
		List N = new List();
		Node H = head;
		while (H != null) {
			N.append(H.data);
			H = H.next;
		}
		int indexL = L.index(); // temp index holder
		int v; // temp value holder
		L.moveFront();
		while (L.index() >= 0) {
			v = L.get();
			N.append(v);
			L.moveNext();
		}
		// return cursor to original state in L
		L.moveFront();
		while (L.index() != indexL) {
			L.moveNext();
		}
		return N;
	}

	public String toString() {
		return myString(head);
	}
	// Overrides Object's toString method. Returns a String
	// representation of this List consisting of a space
	// separated sequence of integers, with front on left.

	List copy() {
		List L = new List();
		Node N = head;
		while (N != null) {
			L.append(N.data);
			N = N.next;
		}
		return L;
	}       // Returns a new List representing the same integer sequence as this
		// List. The cursor in the new list is undefined, regardless of the
		// state of the cursor in this List. This List is unchanged.

}
