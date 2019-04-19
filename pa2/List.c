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

// free memory for the List and the Nodes
void freeList(List* pL){
   if(pL!=NULL && *pL!=NULL) { 
      while( length(*pL) >= 1 ) { 
         deleteFront(*pL);
      }
      free(*pL);
      *pL = NULL;
   }
}

// Access functions----------------------------------------------------------
//returns the number of elements in this List.
int length(List L) 
{ 
  if(L == NULL)
  {
    printf("List Error: calling length() on NULL List reference\n");
    exit(1);
  }
  else
  {
    return L->length;
  }
}
// If cursor is defined, returns the index of the cursor
// element, otherwise returns -1.
int index(List L) {
  if(L->cursor != NULL)
  {
    return L->index;
  }
  else
  {
    return -1;
  }
}
// Returns front element.
// Pre: length() > 0
int front(List L) {
  if(L->length >= 1)
  {
    return L->front->data;
  }
  else
  {
    printf("List Error: calling front() on empty List reference\n");
    exit(1);
  }
}
// Returns back element.
// Pre: length() > 0
int back(List L) {
  if(length(L) >= 1)
  {
    return L->back->data;
  }
  else
  {
    printf("List Error: calling back() on empty List reference\n");
    exit(1);
  }
}
// Returns cursor element.
// Pre: length() > 0
int get(List L) {
  if(length(L) > 0 && L->index >= 0)
  {
    return L->cursor->data;
  }
  else
  { 
    printf("List Error: calling get() with an undefined index on List or an empty List reference\n");
    exit(1);
  }
}
// equals()
// Returns true if this List A is identical to List B
int equals(List A, List B){
   int eq = 0;
   Node N = NULL;
   Node M = NULL;
   if(A==NULL || B==NULL)
   {
      printf("List Error: calling equals() on NULL List reference\n");
      exit(1);
   }
   eq = ( A->length == B->length );
   N = A->front;
   M = B->front;
   while( eq && N!=NULL){
      eq = (N->data==M->data);
      N = N->next;
      M = M->next;
   }
   return eq;
}

// Manipulation procedures --------------------------------------------------
// Resets this List to empty state.
void clear(List L) {
  if(L == NULL) {
      printf("List Error: calling clear() on NULL List reference\n");
      exit(1);
   }
  while(L->back != NULL)
  {
    deleteFront(L);
  }
  L->front = NULL;
  L->back = NULL;
  L->cursor = NULL;
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
  if(length(L) >= 1)    
  {
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
  if(length(L) >= 1)  
  {
    L->cursor = L->back;
    L->index = length(L) - 1;
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
  if(L->cursor != NULL && L->cursor != L->front)
  {
    L->cursor = L->cursor->prev;
    (L->index)--;
  }
  else if(L->cursor != NULL && L->cursor == L->front )
  {
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
  if(L->cursor != NULL && L->cursor != L->back)
  {
    L->cursor = L->cursor->next;
    (L->index)++;
  }
  else if(L->cursor != NULL && L->cursor == L->back)
  {
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
  if(length(L) == 0)
  {
    L->front = L->back = N;
    (L->length)++;
    L->cursor = L->front = L->back;
  }
  else
  { 
    L->front->prev = N;
    N->next = L->front;
    L->front = N;
    (L->length)++;
    if(L->cursor != NULL)
    {
      (L->index)++;
    }
  }
}
// Insert new element into this List. If List is non-empty,
// insertion takes place after back element.
void append(List L, int data) {
  if(L == NULL) {
      printf("List Error: calling append() on NULL List reference\n");
      exit(1);
   }
  Node N = newNode(data);
  if(length(L) == 0)
  {
    L->front = L->back = N;
    (L->length)++;
  }
  else
  {
    L->back->next = N;
    N->prev = L->back;
    L->back = N;      
    (L->length)++;
  }
}
// Insert new element before cursor.
// Pre: length() > 0, index() >= 0
void insertBefore(List L, int data) {
  if(L == NULL) {
      printf("List Error: calling insertBefore() on NULL List reference\n");
      exit(1);
   }
  Node N = newNode(data);
  if(length(L) <= 0 || L->index < 0)
  {
    printf("List Error: insertBefore() called with an undefined index on List or on an empty List");
    exit(1);
  }
  else if(L->cursor == L->front)
  {
    N->prev = NULL;
    N->next = L->cursor;
    L->cursor->prev = N;
    L->front = N;
    (L->length)++;
    (L->index)++;
  }
  else
  {
    N->next = L->cursor;
    N->prev = L->cursor->prev;
    L->cursor->prev->next = N;
    L->cursor->prev = N;
    (L->length)++;
    (L->index)++;
  }
}
// Insert new element after cursor.
// Pre: length() > 0, index() >= 0
void insertAfter(List L, int data) {
  Node N = newNode(data);
  if(length(L) <= 0 || index(L) < 0)
  {
    printf("List Error: insertAfter() called with an undefined index on List or on an empty List");
    exit(1);
  }
  else if(L->cursor == L->back)
  {
    N->next = NULL;
    N->prev = L->cursor;
    L->cursor->next = N;
    L->back = N;
    (L->length)++;
  }
  else
  {
    N->prev = L->cursor;
    N->next = L->cursor->next;
    L->cursor->next->prev = N;
    L->cursor->next = N;
    (L->length)++;
  }
}
// Deletes the front element.
// Pre: length() > 0
void deleteFront(List L) {
  if(L == NULL) {
      printf("List Error: deleteFront() called on NULL List reference\n");
      exit(1);
   }
  if(length(L) >= 2)
  {
    L->front = L->front->next;
    if(L->cursor == L->front->prev)
    {
      L->cursor = NULL;
    }
    L->index--;
    L->length--;
    freeNode(&L->front->prev);
  }
  else if (length(L) == 1) 
  { 
    (L->length)--;
    L->index = -1;
    L->cursor = NULL;
    L->back = NULL;
    freeNode(&L->front);
  }
  else
  {
    printf("List Error: deleteFront() called on an empty List\n");
    exit(1);
  }
}
// Deletes the back element.
// Pre: length() > 0
void deleteBack(List L) {
  if(L == NULL) {
      printf("List Error: deleteBack() called on NULL List reference\n");
      exit(1);
   }
  if(length(L) >= 2)
  { 
    if(L->cursor == L->back)
    {
      L->index = -1;
      L->cursor = NULL; 
      L->back = L->back->prev;
      (L->length)--;
      freeNode(&L->back->next);
    }
    else
    { 
      L->back = L->back->prev;
      (L->length)--;
      freeNode(&L->back->next);
    }
  }
  else if (length(L) == 1)      
  {
    freeNode(&L->back);
    L->front = L->back = NULL;
    (L->length)--;
    L->index = -1;
  }
  else
  {
    printf("List Error: deleteBack() called on an empty List\n");
    exit(1);
  }
}
// Deletes cursor element, making cursor undefined.
// Pre: length() > 0, index() >= 0
void delete(List L) {
  if(L == NULL) {
      printf("List Error: delete() called on NULL List reference\n");
      exit(1);
   }
  if(L->length <= 0 || L->index < 0)
  {
    printf("List Error: delete() called with an undefined index on List or on an empty List\n");
    exit(1);
  }
  else if(L->cursor == L->front)
  {
    deleteFront(L);
  }
  else if(L->cursor == L->back)
  {
    deleteBack(L);
  }
  else
  {
    L->cursor->next->prev = L->cursor->prev;
    L->cursor->prev->next = L->cursor->next;
    freeNode(&L->cursor);
    (L->length)--;
    L->index = -1;
  } 
}

// Overrides Object's toString method. Returns a String
// representation of this List consisting of a space
// separated sequence of integers, with front on left.
void printList(FILE* out, List L)
{
   Node N = NULL;
   if( L == NULL )
   {
      printf("List Error: calling printList() on NULL List reference\n");
      exit(1);
   }
   for(N = L->front; N != NULL; N = N->next)
   {
      printf("%d ", N->data);
   }
}

// Returns a new List representing the same integer sequence as this
// List. The curor in the new list is undefined, regardless of the
// state of the cursor in this List. This List is unchanged.
List copyList(List L) { 
    List temp = newList();
        Node N = L->front;
        while(N != NULL)
        {
          append(temp, N->data);
          N = N->next;
        }
        return temp;
  }