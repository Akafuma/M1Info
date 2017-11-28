#ifndef GRAPH__H
#define GRAPH__H

/******************************************/
/* structures de donnees pour les graphes */
/******************************************/

struct Maillon
{
  int st;
  struct Maillon * suiv;
};

typedef struct Maillon maillon, * chain_list;
typedef struct Maillon * ens_sommets;

struct Graph
{
  int nb_sommets;               /* nombre de sommets du graphe                                          */
  chain_list * list_voisins;    /* ensembles des voisins de chaque sommet (liste d'adjacence)   */
  int ** mat_voisins;           /* ensembles des sommets voisins de chaque sommet (matrice d'adjacence) */
};

typedef struct Graph graph;

/*
    ****************************************************************************
*/

// Charge un graphe depuis un fichier

void load_graph(graph * g, char namefile[64]);

// Désalloue l'espace alloué pour un graphe

void free_graph(graph * g);

// Test d'existence d'un arc en temps constant

int arc(graph g, int x, int y);

// Affichage la matrice contenant le graphe

void print_matrice(graph g);

// Affichage la liste d'adjacence contenant le graphe

void print_list(graph g);

// Construit une liste/ajoute un élément en tête de la liste déjà créée

void ajoute(chain_list * m, int x); // ajoute en tete

void enleve_tete(chain_list * m); //Supprime l'element en tête

// Désalloue l'espace alloué pour la liste

void free_list(chain_list l);

#endif
