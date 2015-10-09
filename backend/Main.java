package backend;

/**
 * Created by nikitin_ns on 01.10.2015.
 */
public class Main {
	public static void main(String[] args) {
		Graph theGraph = new Graph();

		theGraph.addVertex(1); // 0 root
		theGraph.addVertex(2); // 1
		theGraph.addVertex(3); // 2
		theGraph.addVertex(4); // 3
		theGraph.addVertex(5); // 4
		theGraph.addVertex(6); // 5
		theGraph.addVertex(7); // 6

		theGraph.addEdge(3, 1); // DB
		theGraph.addEdge(3, 2); // DC
		theGraph.addEdge(2, 0); // CA
		theGraph.addEdge(0, 3); // AD
		theGraph.addEdge(3, 4); // DE
		theGraph.addEdge(5, 6); // NH
		theGraph.addEdge(6, 2); // HC

		System.out.println("Visits (DFS): ");
		theGraph.dfs();
		System.out.println("\nVisits (BFS): " );
		theGraph.bfs();

//        GraphW theGraphW = new GraphW();

//        theGraphW.addVertex('A'); // 0 root
//        theGraphW.addVertex('B'); // 1
//        theGraphW.addVertex('C'); // 2
//        theGraphW.addVertex('D'); // 3
//        theGraphW.addVertex('E'); // 4
//        theGraphW.addVertex('N'); // 5
//        theGraphW.addVertex('H'); // 6
//
//        theGraphW.addEdge(0, 1, 10);
//        theGraphW.addEdge(0, 3, 23);
//        theGraphW.addEdge(1, 2, 52);
//        theGraphW.addEdge(1, 3, 34);
//        theGraphW.addEdge(2, 4, 50);
//        theGraphW.addEdge(3, 2, 34);
//        theGraphW.addEdge(3, 4, 65);
//        theGraphW.addEdge(4, 1, 65);
//
//        System.out.println("Diykstra: ");
//        theGraphW.path();
//        System.out.println();


		System.out.println();
	}
}
