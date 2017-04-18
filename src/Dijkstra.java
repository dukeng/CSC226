/*
Following: http://www.geeksforgeeks.org/greedy-algorithms-set-6-dijkstras-shortest-path-algorithm/
Original: O(V^2). Can use PriorityQueue to make it run faster: O(|E| + |V|log|V|)
Proof of correctness: https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm

 */


public class Dijkstra {

	private static int V;

	public Dijkstra(int v){
		this.V = v;
	}
	private static void printSolution(int dist[], int n){
		System.out.println("Vertex Distance from Source");
		for (int i = 0; i < V; i++)
			System.out.println(i+" \t\t "+dist[i]);
	}
	//Find the next vertex to proceed(mininum distance value) from the set of vertices not yet included
	private static int minDistance(int[] distance, Boolean[] visited){
		int min = Integer.MAX_VALUE;
		int min_index = -1;
		for(int v = 0; v < V; v++){
			if(visited[v] == false && distance[v] <= min){
				min = distance[v];
				min_index = v;
			}
		}
		return min_index;
	}
	public static void dijkstra(int graph[][], int src){
		int distance[] = new int[V];
		Boolean visited[] = new Boolean[V];
		for(int k = 0; k < V; k ++){
			distance[k] = Integer.MAX_VALUE;
			visited[k] = false;
		}
		distance[src] = 0;
		for(int count = 0; count < V - 1; count++){
			int u = minDistance(distance, visited); // the next vertex to proceed
			visited[u] = true;
			for(int v = 0; v < V; v++){
				if( !visited[v] && // if the node is unvisited
					graph[u][v] != 0 && // if there is an edge between it and the node
					distance[u] != Integer.MAX_VALUE && // dunno what this means
					distance[u] + graph[u][v] < distance[v]){ // The path from src to v through u is smaller than current value of distance[v]
						distance[v] = distance[u] + graph[u][v];
				}
			}
		}
		printSolution(distance, V);
	}
	public static void main(String[] args){
		int graph[][] = new int[][]{{0, 4, 0, 0, 0, 0, 0, 8, 0},
				{4, 0, 8, 0, 0, 0, 0, 11, 0},
				{0, 8, 0, 7, 0, 4, 0, 0, 2},
				{0, 0, 7, 0, 9, 14, 0, 0, 0},
				{0, 0, 0, 9, 0, 10, 0, 0, 0},
				{0, 0, 4, 14, 10, 0, 2, 0, 0},
				{0, 0, 0, 0, 0, 2, 0, 1, 6},
				{8, 11, 0, 0, 0, 0, 1, 0, 7},
				{0, 0, 2, 0, 0, 0, 6, 7, 0}
		};
		Dijkstra dijkstra = new Dijkstra(graph.length);
		dijkstra.dijkstra(graph, 0);
	}
}
