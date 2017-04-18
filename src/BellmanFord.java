/**
 * Created by User on 17/04/17.
 * Proof of Correctness: https://en.wikipedia.org/wiki/Bellman%E2%80%93Ford_algorithm
 * Running time: O(|V|*|E|)
 */
public class BellmanFord {

	public static void bellman_ford(int[][] graph, int src){
		int V = graph.length;
		int[] distance = new int[V];
		int[] predecessor = new int[V];
		//initialize distances to infinity except for src
		for(int k = 0; k < V; k++){
			distance[k] = Integer.MAX_VALUE;
			predecessor[k] = Integer.MAX_VALUE;

		}
		distance[src] = 0;
		// Relax the graph V-1 times
		for (int i = 0; i < V - 1; i++){
			for(int v = 0; v < V; v++){
				if (distance[v] != Integer.MAX_VALUE){
					for(int u = 0; u < V; u++) {
						if (graph[v][u] != 0 &&
								graph[v][u] + distance[v] < distance[u]){
							distance[u] = graph[v][u] + distance[v];
							predecessor[u] = v;
						}
					}
				}
			}
		}
		// Check for negative cycles
		for(int u = 0; u < V; u++){
			for(int v = 0; v < V; v++){
				if(distance[u] + graph[u][v] < distance[v]){
					System.out.println("Graph contains negative-weighted cycles");
				}
			}
		}
		System.out.println(distance);
		System.out.println(predecessor);

	}

	public static void main(String[] args){

	}
}
