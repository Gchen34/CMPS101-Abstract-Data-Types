/* Guangyang Chen
 * gchen34@ucsc.edu
 * pa3
 */
public class ListTest
{
	 public static void main(String[] args)
	 {
	 	List A = new List();
	 	List B = new List();

	 	String film = "Avengers Three";


	 	A.append("The");
	 	A.moveFront();
	 	A.insertAfter(film);
	 	A.insertBefore("Is");
	 	A.append("Thanos");
	 	A.prepend("This");

	 	B.append("Pool");
	 	B.moveFront();
	 	B.insertAfter(film);
	 	B.insertBefore("Dead");
	 	B.append(3);
	 	B.prepend("Hello");

	 	System.out.println("List A:");
	 	A.moveFront();
	 	for(int index = 0; index < A.length(); index++)
	 	{
	 		System.out.println(A.get());
	 		A.moveNext();
	 	}

	 	System.out.println();

	 	System.out.println("List B:");
	 	B.moveFront();
	 	for(int index = 0; index < B.length(); index++)
	 	{
	 		System.out.println(B.get());
	 		B.moveNext();
	 	}

	 	System.out.println();

	 	A.clear();
	 	B.clear();

	 	System.out.println("A length: " + A.length());
	 	System.out.println("B length: " + B.length());


   }
}