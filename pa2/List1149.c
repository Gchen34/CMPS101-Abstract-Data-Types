/****************************************************************************************
* Guangyang Chen
* 1518931
* PA2
* List.c
* Implementation for List ADT
*****************************************************************************************/
#include<stdio.h>
#include<stdlib.h>
#include "List.h"

//private NodeObj type
typedef struct NodeObj {
   int data;
   struct NodeObj* next;
   struct NodeObj* prev;
} NodeObj;

//private Node type
typedef NodeObj* Node;

//private ListObj type
typedef struct ListObj {
   Node front;
   Node back;
   Node cursor;
   int length;
   int index;
} ListObj;

// Constructors-Destructors ---------------------------------------------------
Node newNode(int data) {
   Node N = malloc(sizeof(NodeObj));
   N->data = data;
   N->prev = NULL;
   N->next = NULL;
   return(N);
}
//free memory for the Node.
void freeNode(Node* pN) {
   if(pN != NULL && *pN != NULL) {
      free(*pN);
      *pN = NULL;
   }
}

//creates a new empty List.
List newList(void){
   List L;
   L = malloc(sizeof(ListObj));
   L->front = NULL;
   L->back = NULL;
   L->length = 0;
   L->index = -1;
   L->cursor = NULL;
   return(L);
}

//free memory for the List and the Nodes
void freeList(List* pL) {
   if(pL != NULL && *pL != NULL) { 
      Node temp = (*pL)->front; 
      while(temp != NULL) {
         Node cur = temp;
         temp = temp->next;
         free(cur);
      }
      free(*pL);
      *pL = NULL;
   }
}

// Access functions----------------------------------------------------------
//returns the number of elements in this List.
int length(List L) {
   if(L == NULL) {
      printf("List Error: calling length() on NULL List reference\n");
      exit(1);
   }
   return L->length;
}

// If cursor is defined, returns the index of the cursor
// element, otherwise returns -1.
int index(List L) {
   if(L == NULL) {
      printf("List Error: calling index() on NULL List reference\n");
      exit(1);
   }
   return L->index;
}

// Returns front element.
// Pre: length() > 0
int front(List L) {
   if(L == NULL) {
      printf("List Error: calling front() on NULL List reference\n");
      exit(1);
   }
   if(L->length < 1) {
      printf("List Error: calling front() on empty List reference\n");
      exit(1);
   }
   return L->front->data;
}

// Returns back element.
// Pre: length() > 0
int back(List L) {
   if(L == NULL) {
      printf("List Error: calling back() on NULL List reference\n");
      exit(1);
   }
   if(L->length < 1) {
      printf("List Error: calling back() on empty List reference\n");
      exit(1);
   }
   return L->back->data;
}

// Returns cursor element.
// Pre: length() > 0
int get(List L) {
   if(L == NULL) {
      printf("List Error: calling get() on NULL List reference\n");
      exit(1);
   }
   if(L->index < 0) {
      printf("List Error: calling get() with an undefined index on List\n");
      exit(1);
   }
   if(L->length < 1) {
      printf("List Error: calling get() on empty List reference\n");
      exit(1);
   }
   return L->cursor->data;
}

// Returns true if this List A is identical to List B
int equals(List A, List B) {
   if(A == NULL || B == NULL) {
      printf("List Error: calling equals() on NULL List reference\n");
      exit(1);
   }
   if(A->length != B->length) {
      return 0;
   }
   Node N = B->front;
   Node M = A->front;
   while(N->next != NULL && M->next != NULL) {
      if(N->data != M->data)
         return 0;
      N = N->next;
      M = M->next;
   }
   return 1;
}

// Manipulation procedures --------------------------------------------------
// Resets this List to empty state.
void clear(List L) {
   if(L == NULL) {
      printf("List Error: calling clear() on NULL List reference\n");
      exit(1);
   }
   Node temp = L->front; 
   while(temp != NULL) {
      Node cur = temp;
      temp = temp->next;
      free(cur);
   }
   L->front = L->back = L->cursor = NULL;
   L->length = 0;
   L->index = -1;
}

// If List is non-empty, places the cursor under the front
// element, otherwise does nothing.
void moveFront(List L) {
   if(L == NULL) {
      printf("List Error: calling moveFront() on NULL List reference\n");
      exit(1);
   }
   if (L->length == 0){
		printf("List Error: calling moveFront() on empty List reference\n");
   }
   if(L->length > 0) {
      L->cursor = L->front;
      L->index = 0;
   }
}

// If List is non-empty, places the cursor under the back
// element, otherwise does nothing.
void moveBack(List L) {
   if(L == NULL) {
      printf("List Error: calling moveBack() on NULL List reference\n");
      exit(1);
   }
   if(L->length > 0) {
      L->cursor = L->back;
      L->index = L->length - 1;
   }
}

// If cursor is defined and not at front, moves cursor one step
// toward the front of this List, if cursor is defined and at front,
// cursor becomes undefined, if cursor is undefined does nothing.
void movePrev(List L) {
   if(L == NULL) {
      printf("List Error: calling movePrev() on NULL List reference\n");
      exit(1);
   }
   if(  L->length < 1 ){
      printf("List Error: calling moveTo() on an empty List\n");
      exit(1);
   }
   if(L->cursor != NULL && L->index != 0) {
      L->cursor = L->cursor->prev;
      --L->index;
   }
   else if(L->cursor != NULL && L->index == 0) {
      L->cursor = NULL;
      L->index = -1;
   }
}

// If cursor is defined and not at back, moves cursor one step
// toward the back of this List, if cursor is defined and at back,
// cursor becomes undefined, if cursor is undefined does nothing.
void moveNext(List L) {
   if(L == NULL) {
      printf("List Error: calling moveNext() on NULL List reference\n");
      exit(1);
   }
   if(  L->length < 1 ){
      printf("List Error: calling moveTo() on an empty List\n");
      exit(1);
   }
   if(L->cursor != NULL && L->index != L->length - 1) {
      L->cursor = L->cursor->next;
      ++L->index;
   }
   else if(L->cursor != NULL && L->index == L->length - 1) {
      L->cursor = NULL;
      L->index = -1;
   }
}

// Insert new element into this List. If List is non-empty,
// insertion takes place before the front element.
void prepend(List L, int data) {
   if(L == NULL) {
      printf("List Error: calling prepend() on NULL List reference\n");
      exit(1);
   }
   Node N = newNode(data);
   if(L->front == NULL){
      L->front = N;
      L->back = N;}
   else{
      L->front->prev = N;
      N->next = L->front;
      L->front = N;
   }
   ++L->length;
   if(L->cursor!=NULL)++L->index;
}

// Insert new element into this List. If List is non-empty,
// insertion takes place after back element.
void append(List L, int data) {
   if(L == NULL) {
      printf("List Error: calling append() on NULL List reference\n");
      exit(1);
   }
   Node N = newNode(data);
   if(L->front == NULL){
      L->front = L->back=N;}
   else{
      L->back->next = N;
      N->prev=L->back;
      L->back=N;
   }
   ++L->length;
}

// Insert new element before cursor.
// Pre: length() > 0, index() >= 0
void insertBefore(List L, int data) {
   if(L == NULL) {
      printf("List Error: calling insertBefore() on NULL List reference\n");
      exit(1);
   }
   if(L->index < 0) {
      printf("List Error: insertBefore() called with an undefined index on List");
      exit(1);
   }
   if(L->length < 1) {
      printf("List Error: insertBefore() called on an empty List");
      exit(1);
   }
   if (L->cursor == L->front) {
      prepend(L, data);
      return;
   }
   Node N = newNode(data);
   N->next = L->cursor;
   N->prev = L->cursor->prev;
   if(L->cursor->prev != NULL)
      L->cursor->prev->next = N;
   else
      L->front = N;
   L->cursor->prev = N;
   ++L->length;
   ++L->index;
}

// Insert new element after cursor.
// Pre: length() > 0, index() >= 0
void insertAfter(List L, int data) {
   if(L == NULL) {
      printf("List Error: calling insertAfter() on NULL List reference\n");
      exit(1);
   }
   if(L->index < 0) {
      printf("List Error: insertAfter() called with an undefined index on List");
      exit(1);
   }
   if(L->length < 1) {
      printf("List Error: insertAfter() called on an empty List");
      exit(1);
   }
   if (L->cursor == L->back) {
      append(L, data);
      return;
   }
   Node N = newNode(data);
   N->prev = L->cursor;
   N->next = L->cursor->next;
   if(L->cursor->next != NULL)
      L->cursor->next->prev = N;
   else
      L->back = N;
   L->cursor->next = N;
   ++L->length;
   //++L->index;
}

// Deletes the front element.
// Pre: length() > 0
void deleteFront(List L) {
   if(L == NULL) {
      printf("List Error: deleteFront() called on NULL List reference\n");
      exit(1);
   }
   if(L->length < 1) {
      printf("List Error: deleteFront() called on an empty List\n"); 
      exit(1);
   }
   if(L->cursor == L->front) {
      L->cursor = NULL;
      L->index = -1;
      L->front=L->front->next;
      freeNode(&L->front->prev);
      L->front->prev = NULL;
   } else {
   L->front = L->front->next;
   freeNode(&L->front->prev);
   L->front->prev = NULL;
   }
   --L->length;
   if(L->cursor!=NULL)--L->index;
}

// Deletes the back element.
// Pre: length() > 0
void deleteBack(List L) {
   if(L == NULL) {
      printf("List Error: deleteBack() called on NULL List reference\n");
      exit(1);
   }
   if(L->length < 1) {
      printf("List Error: deleteBack() called on an empty List\n");
      exit(1);
   }
   if(L->cursor == L->back) {
      L->cursor = NULL;
      L->index=-1;
      L->back=L->back->prev;
      freeNode(&L->back->next);
    L->back->prev->next=NULL;
   } else {
    L->back=L->back->prev;
      freeNode(&L->back->next);
    L->back->prev->next=NULL;
      } 
   --L->length;
   //--L->index;
}

// Deletes cursor element, making cursor undefined.
// Pre: length() > 0, index() >= 0
void delete(List L) {
   if(L == NULL) {
      printf("List Error: delete() called on NULL List reference\n");
      exit(1);
   }
   if(L->length < 1) {
      printf("List Error: delete() called with an undefined index on List\n");
      exit(1);
   }
   if(L->index < 0) {
      printf("List Error: delete() called on empty List");
      exit(1);
   }
   if(L->cursor == L->back) {
      deleteBack(L);
   } else if(L->cursor == L->front) {
      deleteFront(L);
   } else {
      Node N = L->cursor;
      L->cursor->prev->next = L->cursor->next;
      L->cursor->next->prev = L->cursor->prev;
      freeNode(&N);
      L->cursor = NULL;
      L->index = -1;
      --L->length;
      --L->index;
   }
}

// Overrides Object's toString method. Returns a String
// representation of this List consisting of a space
// separated sequence of integers, with front on left.
void printList(FILE* out, List L) {
   Node N = L->front;
   while(N != NULL) {
      printf("%d ", N->data);
      N = N->next;
   }
}

// Returns a new List representing the same integer sequence as this
// List. The curor in the new list is undefined, regardless of the
// state of the cursor in this List. This List is unchanged.
List copyList(List L) {
   List c = newList();
   Node N = L->front;
   while(N != NULL) {
      append(c, N->data);
      N = N->next;
   }
   return c;
}

// Returns a new List which is the concatenation of
// this list followed by L. The cursor in the new List
// is undefined, regardless of the states of the cursors
// in this List and L. The states of this List and L are
// unchanged.
List concat(List L) {
   List cc = newList();
   Node N = L->front;
   while(N != NULL) {
      append(cc, N->data);
      N = N->next;
   }
   return cc;
}
