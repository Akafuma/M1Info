//nombre de variables = nombre de sommets du graphe x k
//nombre de clauses = k + 

#include "graph.h"

void toSAT(Graph * g, int k, char * output)
{
	int nbsommets = g->nb_sommets;
	
	int nbvar = nbsommets * k;// -1 ?
	int nbclauses = 0;//getit
	int var = 1;
	int mat[k][nbsommets]; //k : place, nbsommets : sommets
	
	printf("p cnf %d %d", nbvar, nbclauses);
	//
	for(int i = 0; i < k; i++)
	{
		for(int j = 0; j < nbsommets; j++)
		{
			mat[i][j] = var;
			printf("%d ", var);
			var++;
		}
		printf("0");//fin de clause
	}
	
	//Chaque sommet doit être vrai au plus pour une place
	
	for(int i = 0; i < k; i++) // Pour chaque place
	{
		for(int j = 0; j < nbsommets; j++) // / ! \ à x1 == x1 seems good
		{
			for(int t = j + 1; t < nbsommets; t++)
			{
				printf("%d %d 0", -mat[k][j], -mat[k][t]);
			}
		}
	}
	
	//Chaque place est occupé par un seul sommet
	
	for(int i = 0; i < nbsommets; i++) // Pour chaque sommet
	{
		for(int j = 0; j < k; j++)
		{
			for(int t = j + 1; t < k; t++)
			{
				printf("%d %d 0", -mat[j][i], -mat[t][i]);
			}
		}
	}
	
	//Représentation des arêtes
	
	//Parcours du graphe
	//Pour chaque sommet,
		//Pour chaque voisin d'un sommet
			//Pour chaque place possible du sommet
			//Alors pour toute les autres places, le sommet voisin est faux
			
			
	//FIN
	
}
