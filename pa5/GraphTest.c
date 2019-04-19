/****************************************************************************************
* Guangyang Chen
* gchen34
* 1518931
* PA5
* GraphTest.c
* A test client for Graph.c
*****************************************************************************************/
#include "Graph.h"
#include <stdlib.h>
#include <stdio.h>
#include "List.h"

int main(int argc, char* argv[])
{
	//Testing out all the graph operations and DFS sorting
	List L = newList();
	Graph G = newGraph(8);

	addEdge(G, 1, 3);
	addEdge(G, 1, 4);
	addEdge(G, 1, 7);
	addEdge(G, 2, 8);
	addEdge(G, 3, 6);
	addEdge(G, 4, 5);
	addEdge(G, 5, 8);
	addEdge(G, 6, 7);
	addEdge(G, 7, 8);	

	addArc(G,9,9);

	printf("%d", getOrder(G));
	printf("\n");

	printf("%d", getSize(G));
	printf("\n");	

	printf("%d", getSource(G));
	printf("\n");

	printf("%d", getParent(G, 1));
	printf("\n");

	printf("%d", getDist(G, 1));
	printf("\n");

	DFS(G,L);
	getPath(L,G,8);

	printGraph(stdout, G);
	freeGraph(G);
}