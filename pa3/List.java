/* Guangyang Chen
 * gchen34@ucsc.edu
 * pa3
 */
public class List {
	private class Node {
		Object data;
		Node next;
		Node previous;

		Node(Object data) {
			this.data = data;
			next = null;
			previous = null;
		}

		public String toString() {
			return String.valueOf(data);
		}

		public boolean equals(Object x) {
			boolean eq = true;
			Node that = (Node) x;
			if (this.data != that) {
				eq = false;
			}
			return eq;
		}
	}

	private Node front;
	private Node back;
	private Node cursor;
	private int length;
	private int index;

	// Constructor
	List() {
		front = null;
		back = null;
		cursor = null;
		length = 0;
		index = -1;
	}

	// Access functions
	int length() {
		return length;
	}

	int index() {
		if (cursor == null) {
			return -1;
		} else {
			return index;
		}
	}

	Object front() {
		if (this.length() == 0) {
			throw new RuntimeException("List Error: front() called on empty List");
		}
		return front.data;
	}

	Object back() {
		if (this.length() == 0) {
			throw new RuntimeException("List Error: back() called on empty List");
		}
		return back.data;
	}

	Object get() {
		if (this.length() == 0) {
			throw new RuntimeException("List Error: get() called on empty List");
		}
		if (this.index() < 0) {
			throw new RuntimeException("List Error: index < 0");
		}
		return cursor.data;
	}

	public boolean equals(Object O) {
		List L = (List) O;

		if (L.length() != this.length()) {
			return false;
		}

		Node temp1 = this.front;
		Node temp2 = (Node) L.front;

		while (temp1 != null) {
			if (!temp1.data.equals(temp2.data)) {
				return false;
			}
			temp1 = temp1.next;
			temp2 = temp2.next;
		}
		return true;
	}

	// Manipulation procedures
	void clear() {
		front = back = cursor = null;
		length = 0;
		index = -1;
	}

	void moveFront() {
		if (this.length() > 0) {
			cursor = front;
			index = 0;
		}
	}

	void moveBack() {
		if (this.length() > 0) {
			cursor = back;
			index = this.length() - 1;
		}
	}

	void movePrev() {
		if (cursor != null) {
			if (cursor != front) {
				cursor = cursor.previous;
				index--;
			} else {
				cursor = null;
				index = -1;
			}
		}
	}

	void moveNext() {
		if (cursor != null) {
			if (cursor != back) {
				cursor = cursor.next;
				index++;
			} else {
				cursor = null;
				index = -1;
			}
		}
	}

	void prepend(Object data) {
		Node N = new Node(data);
		if (this.length() == 0) {
			front = back = N;
		} else {
			N.next = front;
			front.previous = N;
			front = N;
		}
		length++;
		if (cursor != null) {
			index++;
		}
	}

	void append(Object data) {
		Node N = new Node(data);
		if (this.length() == 0) {
			front = back = N;
		} else {
			N.previous = back;
			back.next = N;
			back = N;
		}
		length++;
	}

	void insertBefore(Object data) {
		Node N = new Node(data);
		if (this.length() > 0 && this.index() >= 0) {
			if (this.index() != 0) {
				N.next = cursor;
				N.previous = cursor.previous;
				cursor.previous.next = N;
				cursor.previous = N;
			} else {
				N.next = cursor;
				cursor.previous = N;
				front = N;
			}
			length++;
			if (cursor != null) {
				index++;
			}
		}
	}

	void insertAfter(Object data) {
		Node N = new Node(data);
		if (this.length() > 0 && this.index() >= 0) {
			if (this.index() != this.length() - 1) {
				N.previous = cursor;
				N.next = cursor.next;
				cursor.next.previous = N;
				cursor.next = N;
			} else {
				N.previous = cursor;
				cursor.next = N;
				back = N;
			}
			length++;
		}
	}

	void deleteFront() {
		if (this.length() > 0) {
			if (this.length() > 1) {
				if (cursor != null) {
					if (cursor == front) {
						cursor = null;
					} else {
						index--;
					}
				}
				front = front.next;

			} else {
				front = back = null;

				if (cursor != null) {
					cursor = null;
				}
			}
			length--;
		}
	}

	void deleteBack() {
		if (this.length() > 0) {
			if (this.length() > 1) {
				if (cursor != null) {
					if (cursor == back) {
						cursor = null;
					} else {
						// do nothing
					}
				}
				back = back.previous;
			} else {
				back = front = null;

				if (cursor != null) {
					cursor = null;
				}
			}
			length--;
		}
	}

	void delete() {
		if (this.length() > 0 && this.index() >= 0) {
			if (this.length() == 1) {
				front = back = cursor = null;
			} else {
				if (cursor == front) {
					front = cursor.next;
					front.previous = null;
					cursor.next = null;
					cursor = null;
				} else if (cursor == back) {
					back = cursor.previous;
					back.next = null;
					cursor.previous = null;
					cursor = null;
				} else {
					cursor.previous.next = cursor.next;
					cursor.next.previous = cursor.previous;
					cursor.next = cursor.previous = null;
					cursor = null;
				}
			}
			length--;
		}
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		Node N = this.front;
		while (N != null) {
			sb.append(N.toString());
			if (N.next != null) {
				sb.append(" ");
			}
			N = N.next;
		}
		return new String(sb);
	}

	List copy() {
		List L = new List();
		Node N = this.front;
		while (N != null) {
			L.append(N.data);
			N = N.next;
		}
		return L;
	}
}
