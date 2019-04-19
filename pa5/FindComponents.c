/****************************************************************************************
* Guangyang Chen
* gchen34
* 1518931
* PA5
* FindCompoents.c
* Pass a List to the function DFS(), and a client of the List module
*****************************************************************************************/
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "Graph.h"
#include "List.h"

int main(int argc, char* argv[])
{

	FILE *in = NULL;
	FILE *out = NULL;
	int n;//number of vertices passed
	int a;//one end of an edge
	int b;//aother end of the  edge
	int scc = 0;//strongly connected components
	List S = newList();
	List temp = newList();

	if( argc != 3 )
	{
   printf("Usage: %s <input file> <output file>\n", argv[0]);
   exit(1);
 }

 in = fopen(argv[1], "r"); 
 out = fopen(argv[2], "w");

 if( in == NULL )
 {
  printf("Unable to open file %s for reading\n", argv[1]);
  exit(1);
}
if( out == NULL )
{
  printf("Unable to open file %s for writing\n", argv[2]);
  exit(1);
}

fscanf(in, " %d ", &n);
Graph G = newGraph(n);

while(fscanf(in, "%d %d ", &a, &b))
{
 if(a != 0 && b != 0)
 {
  addArc(G,a,b);
}
else
{
 break;
}
}

fprintf(out, "Adjacency list representation of G:\n");
printGraph(out, G);
fprintf(out, "\n\n");

for(int i = 1; i <= n; i++)
{
 append(S,i);
}


DFS(G,S);

Graph trans = transpose(G);
DFS(trans,S); 

   //counts the number of scc
for(moveFront(S); index(S) != -1; moveNext(S))
{
 int e = get(S);
 if(getParent(trans,e) == NIL)
 {
  scc++;
}
}

fprintf(out, "G contains %d strongly connected components: ", scc);
fprintf(out, "\n");

   scc = 1;//resets the number of scc

   for(moveBack(S); index(S) != -1; movePrev(S))
   {
    int e = get(S);
    prepend(temp, e);
    if(getParent(trans,e) == NIL)
    {
     fprintf(out, "Component %d: ", scc);
     printList(out,temp);
     fprintf(out,"\n");
     scc++;
     clear(temp);
   }
 }

 //frees up all memory allocation
 freeList(&S);
 freeList(&temp);
 freeGraph(&G);
 freeGraph(&trans);

 fclose(in);
 fclose(out);
}