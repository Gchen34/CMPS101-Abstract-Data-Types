/****************************************************************************************
* Guangyang Chen
* 1518931
* PA4
* GraphTest.c
* Test Client for Graph.c
*****************************************************************************************/
#include "Graph.h"
#include "List.h"
#include <stdlib.h>
#include <stdio.h>

int main(void)
{
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

	BFS(G,1);
	List L = newList();
	getPath(L,G,8);

	printGraph(stdout, G);
	freeGraph(G);
}