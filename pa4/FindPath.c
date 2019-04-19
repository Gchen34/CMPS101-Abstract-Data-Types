/****************************************************************************************
* Guangyang Chen
* 1518931
* PA4
* FindPath.c
* Use this Graph ADT to find shortest paths
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
	int b;//other end of the same edge
	int s;//source
	int d;//destination


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

   fscanf(in, " %d ", &n);//scans the number of vertices
   Graph G = newGraph(n);//creates the graph

   while(fscanf(in, "%d %d ", &a, &b))//builds the graph
   {
   		if(a != 0 && b != 0)
   		{
   			addEdge(G,a,b);
   	   	}
   	   	else
   	   	{
   	   		break;
   	   	}
   }

   printGraph(out, G);
   fprintf(out, "\n\n");

   while(fscanf(in, "%d %d ", &s, &d))
   {
   		if(s != 0 && d != 0)
   		{
   			List shortestPath = newList();
   			BFS(G,s);
   			getPath(shortestPath,G,d);
   			if(getDist(G,d) != INF)
   			{
   				fprintf(out, "The distance from %d to %d is %d", s, d, getDist(G,d));
   				fprintf(out, "\n");
   				fprintf(out, "A shortest %d-%d path is: ", s, d);
   				printList(out, shortestPath);
   				fprintf(out, "\n\n");
   			}
   			else
   			{
   				fprintf(out, "The distance from %d to %d is infinity", s, d);
   				fprintf(out, "\n");
   				fprintf(out, "No %d-%d path exists", s,d);
   				fprintf(out, "\n\n");
   			}

   			freeList(&shortestPath);//frees memory
   		}
   		else
   		{
   			break;
   		}	
   }

   freeGraph(&G);//frees graph

   fclose(in);
   fclose(out);

   return 0;
}