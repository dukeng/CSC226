/*
   The assignment is to implement the mwst() method below, using any of the algorithms
   studied in the course (Kruskal, Prim-Jarnik or Baruvka). The mwst() method computes
   a minimum weight spanning tree of the provided graph and returns the total weight
   of the tree. To receive full marks, the implementation must run in O(mlog(n)) time
   on a graph with n vertices and m edges.

   This template includes some testing code to help verify the implementation.
   Input graphs can be provided with standard input or read from a file.

   To provide test inputs with standard input, run the program with
	java Kruskal
   To terminate the input, use Ctrl-D (which signals EOF).

   To read test inputs from a file (e.g. graphs.txt), run the program with
    java Kruskal graphs.txt

   The input format for both methods is the same. Input consists
   of a series of graphs in the following format:

    <number of vertices>
	<adjacency matrix row 1>
	...
	<adjacency matrix row n>

   For example, a path on 3 vertices where one edge has weight 1 and the other
   edge has weight 2 would be represented by the following

    3
	0 1 0
	1 0 2
	0 2 0

*/

import com.sun.deploy.util.ArrayUtil;

import java.util.*;

import java.io.File;

public class Kruskal{


	/* mwst(G)
		Given an adjacency matrix for graph G, return the total weight
		of all edges in a minimum weight spanning tree.

		If G[i][j] == 0, there is no edge between vertex i and vertex j
		If G[i][j] > 0, there is an edge between vertices i and j, and the
		value of G[i][j] gives the weight of the edge.
		No entries of G will be negative.
	*/
	private static class EdgeObject{
		public int from;
		public int to;
		public int weight;
		public EdgeObject(int from, int to, int weight){
			this.from = from;
			this.to = to;
			this. weight = weight;
		}
	}

	private static EdgeObject peek(ArrayList<EdgeObject> edgeObject){
		EdgeObject minEdge = edgeObject.get(0);
		for (EdgeObject edge : edgeObject) {
			if (minEdge.weight > edge.weight){
				minEdge = edge;
			}
		}
		return minEdge;
	}

	public static boolean contains( int[] array, final int key) {
		return Arrays.asList(array).contains(key);
	}

	static int mwst(int[][] G){
		int numVerts = G.length;
		int totalWeight = 0;
		int[] firstVector = G[0];
		int initCapacity = 10;
		ArrayList<EdgeObject> pq = new ArrayList<EdgeObject>();
		ArrayList<Integer> result = new ArrayList<>();
		ArrayList<Integer> addedNode = new ArrayList<>();
		/*
		Structure
			array addedNode // contain final nodes
			array pq(EdgeObject) // a temporary arrayList
		Algorithm
			1. Since we start with 0, add 0 to addedNode
			2. Get all outcoming edges from 0 and add it to pq
			3.
				While pq is not empty:
					newEdge = pq.getSmallest() // gotta implement this if you dont use priorityQueu
					pq.remove(newEdge) // remove the edge from pq
					weight += newEdge.weight
					if( addedNode not contain newEdge.to): // if the destination is not in the final nodes
						addedNode.add(newEdge.to)
						for each connectingEdge that newEdge connects to:
							if addNode not contain connectingEdge: // tricky part
								loop through pq:
									if one of the element has element.destination = connectingEdge
										if element.weight > G[newEdge[connectingEdge]
											element.weight = G[newEdge[connectingEdge]
										if connectingEdge is not in list then just add it to pq
				return weight
		*/


		addedNode.add(0); // add the first index
		for(int k = 0; k < firstVector.length; k++){
			if(G[0][k] != 0){
				pq.add(new EdgeObject(0, k, G[0][k]));
			}
		}
		while(pq.size() != 0){
			EdgeObject edge = peek(pq);
			pq.remove(edge);
			if(!addedNode.contains(edge.to)){ // if node doesn't contain
				addedNode.add(edge.to); // add new node
				result.add(edge.weight); // add to result
				for(int k = 0; k < numVerts; k++){
					if(G[edge.to][k] != 0){ // go to the newly added edge and check its connection
						if(!addedNode.contains(k)) { //if the new connection is not in the graph
							boolean exist = false;
							for(int i = 0; i < pq.size(); i++){
								if(pq.get(i).to == k){
									exist = true;
									if (pq.get(i).weight > G[edge.to][k]){
										pq.get(i).weight = G[edge.to][k];
										pq.get(i).from = edge.to;
									}
									break;
								}
							}
							if(!exist){ // if new edge is discovered
								pq.add(new EdgeObject(edge.to, k, G[edge.to][k]));
							}
						}
					}
				}
			}
		}
		for (int k = 0; k < result.size(); k++) {
			totalWeight += result.get(k);
		}
		return totalWeight;

	}


	public static void main(String[] args){
		/* Code to test your implementation */
		/* You may modify this, but nothing in this function will be marked */

		int graphNum = 0;
		Scanner s;

		if (args.length > 0){
			//If a file argument was provided on the command line, read from the file
			try{
				s = new Scanner(new File(args[0]));
			} catch(java.io.FileNotFoundException e){
				System.out.printf("Unable to open %s\n",args[0]);
				return;
			}
			System.out.printf("Reading input values from %s.\n",args[0]);
		}else{
			//Otherwise, read from standard input
			s = new Scanner(System.in);
			System.out.printf("Reading input values from stdin.\n");
		}

		//Read graphs until EOF is encountered (or an error occurs)
		while(true){
			graphNum++;
			if(!s.hasNextInt())
				break;
			System.out.printf("Reading graph %d\n",graphNum);
			int n = s.nextInt();
			int[][] G = new int[n][n]; // initialize the size of the number of node
			int valuesRead = 0;
			for (int i = 0; i < n && s.hasNextInt(); i++){
				G[i] = new int[n]; // initialize the new node with n possible connection
				for (int j = 0; j < n && s.hasNextInt(); j++){
					G[i][j] = s.nextInt();
					valuesRead++;
				}
			}
			if (valuesRead < n*n){
				System.out.printf("Adjacency matrix for graph %d contains too few values.\n",graphNum);
				break;
			}
			if (!isConnected(G)){
				System.out.printf("Graph %d is not connected (no spanning trees exist...)\n",graphNum);
				continue;
			}
			int totalWeight = mwst(G);
			System.out.printf("Graph %d: Total weight is %d\n",graphNum,totalWeight);

		}
	}

	/* isConnectedDFS(G, covered, v)
	   Used by the isConnected function below.
	   You may modify this, but nothing in this function will be marked.
	*/
	static void isConnectedDFS(int[][] G, boolean[] covered, int v){
		covered[v] = true;
		for (int i = 0; i < G.length; i++)
			if (G[v][i] > 0 && !covered[i])
				isConnectedDFS(G,covered,i);
	}

	/* isConnected(G)
	   Test whether G is connected.
	   You may modify this, but nothing in this function will be marked.
	*/
	static boolean isConnected(int[][] G){
		boolean[] covered = new boolean[G.length];
		for (int i = 0; i < covered.length; i++)
			covered[i] = false;
		isConnectedDFS(G,covered,0);
		for (int i = 0; i < covered.length; i++)
			if (!covered[i])
				return false;
		return true;
	}

}