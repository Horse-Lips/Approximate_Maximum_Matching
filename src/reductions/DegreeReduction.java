package reductions;


import java.io.IOException;
import graphComponents.*;	//Graph, Vertex
import graphUtils.*;		//Graph Reading, SimpleTuple
import java.util.*;			//ArrayList, HashMap


public class DegreeReduction {
	/**
	 * Reduce the degree of all vertices such that no vertex has degree higher than n.
	 * Returns a hashmap indicating groupings between vertices after reduction:
	 *	- The key is the reduced vertex
	 *	- The entry is the list of vertices in the group
	*/
	public static HashMap<Integer, ArrayList<Integer>> reduce(Graph g) {
		System.out.println("=== Degree Reduction ===\nInitial graph size: " + g.size());

		int currentVertID = g.size();
		HashMap<Integer, ArrayList<Integer>> grouping = new HashMap<Integer, ArrayList<Integer>>();	//Vertex groups

		int size = g.size();

		for (int vInd = 0; vInd < size; vInd++) {
			Vertex v = g.get(vInd);

			if (v.degree() <= 3) { continue; }	//Only reduce vertices with higher degree than 3

			ArrayList<Integer> newGroup = new ArrayList<Integer>();

			while (v.degree() > 3) {
				/* STEP 1: Create vertices v1 and v2 */
				Vertex v1 = new Vertex(currentVertID++); g.vertList.add(v1); newGroup.add(v1.id);
		        Vertex v2 = new Vertex(currentVertID++); g.vertList.add(v2); newGroup.add(v2.id);				 
				
				/* Create a single edge between v and v1, v1 and v2 */
				v.adjList.add(v1.id); v1.adjList.add(v.id); v1.adjList.add(v2.id); v2.adjList.add(v1.id);


				/* STEP 2: Get first two neighbours of v, u1 and u2, and remove edges between them */
				//System.out.println(v.adjList);
				Vertex u1 = g.get(v.adjList.remove(0));
				Vertex u2 = g.get(v.adjList.remove(0));

				u1.adjList.remove((Integer) v.id); u2.adjList.remove((Integer) v.id);

				/* STEP 3: Add edges between u1 and u2, and v2 */
				u1.adjList.add(v2.id); u2.adjList.add(v2.id); v2.adjList.add(u1.id); v2.adjList.add(u2.id);
			}

			newGroup.add(v.id);

			for (Integer i: newGroup) { grouping.put(i, newGroup); }
			
		}

		System.out.println("Resulting graph size: " + g.size() + "\n");

		return grouping;
	}
}
