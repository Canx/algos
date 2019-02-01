/*
 * Problem Statement: Implementation of Dijkstra Algorithm.
 * Time Complexity: O(|V|^2)
 * Space Complexity: O(|V|) for priority Queue.
 * Matrix representation is used here. Linked List representation would reduce time complexity to O(Elog(V)),
 * if implemented using binary heaps.
 */
package es.schooleando.algos;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class Dijkstra {

	// matriz cuadrada para almacenar los el grafo inicial
    public int[][] graph;
    
    // Vector de objetos nodos, que utilizará el algoritmo
    public Node[] nodes;

    // Todo nodo tendrá un padre, que apuntará al camino más corto hacia el origen desde el nodo actual
    // una vez se ejecute el algoritmo (al principio será Null)
    // El coste se calculará durante el algoritmo y representará el coste minimo agregado de llegar hasta el 
    // origen.
    // Id será un identificador.
    public static class Node {
        public Node parent;
        public int cost;
        public int id;

        public Node(Node parent, int cost, int id) {
            this.parent = parent;
            this.cost = cost;
            this.id = id;
        }
    }

    public Dijkstra() {
    	// Representación del grafo.
    	// Cada fila representa un nodo.
    	// Cada columna representa el coste de llegar desde un nodo a otro
    	// Cuando hay un 0 es que no hay camino
        graph = new int[][]{{0, 4, 0, 0, 0, 0, 0, 8, 0},
                {4, 0, 8, 0, 0, 0, 0, 11, 0},
                {0, 8, 0, 7, 0, 4, 0, 0, 2},
                {0, 0, 7, 0, 9, 14, 0, 0, 0},
                {0, 0, 0, 9, 0, 10, 0, 0, 0},
                {0, 0, 4, 14, 10, 0, 2, 0, 0},
                {0, 0, 0, 0, 0, 2, 0, 1, 6},
                {8, 11, 0, 0, 0, 0, 1, 0, 7},
                {0, 0, 2, 0, 0, 0, 6, 7, 0}
        };
        
        // calculamos el número de nodos del grafo.
        nodes = new Node[graph.length];
    }

    public static void main(String[] args) {

        Dijkstra dijkstra = new Dijkstra();
        
        // Inicializamos los nodos con padre a null, valor máximo y identificador.
        for(int i = 0; i < dijkstra.nodes.length; i++)
            dijkstra.nodes[i] = new Node(null, Integer.MAX_VALUE, i);
        
        // Indicamos el nodo de origen y de destino
        int source = 0;
        int destination = 4;
        
        // Aquí esta el meollo que calcula el coste en la matriz de nodos,
        // donde coste es el coste mínimo para llegar a cada nodo desde origen
        dijkstra.shortestPath(source, destination);
        
        System.out.println("Shortest Distance from " + source + " to " + destination + "  is " + dijkstra.nodes[destination].cost);
        Node temp = dijkstra.nodes[destination];
        System.out.println("Path is ");
        
        // A partir del nodo de destino seguimos los punteros a padre creados por el algoritmo
        // hasta que llegamos al nodo origen, que no tendrá padre.
        while(temp.parent != null) {
            System.out.print(temp.id + " <--- ");
            temp = temp.parent;
        }
        System.out.println(temp.id);
    }

    // Algoritmo de Dijsktra
    public void shortestPath(int source, int destination) {
    	// Creamos una lista para guardar los nodos visitados
        Set<Node> visited = new HashSet<>();
        
        // Creamos una cola que tendrá los nodos siempre ordenados por el 
        // coste menor de llegada desde el origen
        PriorityQueue<Node> pQueue = new PriorityQueue<>(new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return o1.cost - o2.cost;
            }
        });
        
        // El coste de llegar desde origen hasta origen es siempre 0
        nodes[source].cost = 0;
        
        // Añadimos el nodo origen a la cola.
        pQueue.add(nodes[source]);
        
        // Mientra la cosa no esté vacía...
        while(!pQueue.isEmpty()) {
        	
        	// Sacamos el nodo de la cola
            Node currVertex = pQueue.poll();
        	
        	// Para cada uno de los potenciales nodos... 
            for(int i = 0; i < graph.length; i++) {
            	// Si hay camino hacia el nodo (no es 0 en la matriz) Y
            	// el nodo no lo hemos visitado (no está en el conjunto de nodos comprobados)
                if(graph[currVertex.id][i]!=0 && !visited.contains(nodes[i]) ) {
                	// si la cola no contiene el nodo que queremos explorar es que 
                	// es la primera vez que lo exploramos.
                    if(!pQueue.contains(nodes[i])) {
                    	// obtenemos el coste hacia el nodo y se lo asignamos al nodo explorado
                    	//      sería la suma del coste del nodo actual + el coste del nodo a explorar
                        nodes[i].cost = currVertex.cost + graph[currVertex.id][i];
                        
                        // Asignamos como padre del nodo explorado al nodo actual (al principio es null) 
                        nodes[i].parent = currVertex;
                       // y lo añadimos a la cola de prioridad, ordenándose automáticamente.
                        pQueue.add(nodes[i]);
                    }
                    // si la cola contiene el nodo que queremos explorar
                    // es que ya lo hemos explorado y ya tendrá un coste agregado calculado
                    else {
                    	// Vemos cual de los costes es menor, si el agregado ya calculado del nodo explorado
                    	// O 
                    	// el del camino del nodo actual (currVertex)
                    	// y le asignamos como nuevo coste el menor de los dos.
                        nodes[i].cost = Math.min(nodes[i].cost, currVertex.cost + graph[currVertex.id][i]);
                        
                        // Además le cambiamos el padre, si le hemos tenido que cambiar el coste, al nodo actual
                        if(nodes[i].cost == currVertex.cost + graph[currVertex.id][i])
                            nodes[i].parent = currVertex;
                    }
                }
            }
            // añadimos como visitado el nodo actual y vamos al siguiente en el bucle.
            visited.add(currVertex);
        }
    }
}
