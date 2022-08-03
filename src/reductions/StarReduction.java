package reductions;


import java.util.*;		//Hashmap, ArrayList
import graphUtils.*;	//Graph Reading, SimpleTuple
import graphComponents.*;


public class StarReduction {
	public static void reduce(Graph g) {
		System.out.println("=== k-Star Removal ===\nInitial graph size: " + g.size());

		ArrayList<Vertex> vertsToRemove = new ArrayList<Vertex>();	//Verts to remove and token hashmap
		HashMap<Vertex, HashMap<SimpleTuple, Integer>> tokens = new HashMap<Vertex,HashMap<SimpleTuple, Integer>>();
		for (Vertex v: g.vertList) { tokens.put(v, new HashMap<SimpleTuple, Integer>()); }

		for (Vertex v: g.vertList) {
			if (v.degree() > 2) { continue; }	//Only send tokens from verts of degree 1 or 2

			Vertex[] U = {g.get(v.adjList.get(0)), g.get(v.adjList.get(0))};	//Get first neighbour twice

			if ((v.degree() == 1 && U[0].degree() < 2) || (v.degree() == 2 && (U[0].degree() < 3 || (U[1] = g.get(v.adjList.get(1))).degree() < 3))) { continue; }	//Ignore non-2- and non-3-stars

			SimpleTuple<Vertex> token = new SimpleTuple<Vertex>(U[0], U[1]);	//Create token from neighbours

			/* Pass token to neighbours */
			for (Vertex u: U) {
				HashMap<SimpleTuple, Integer> currentEntry = tokens.get(u);

				if (currentEntry.get(token) != null) {
					currentEntry.put(token, currentEntry.get(token) + 1);

				} else {
					currentEntry.put(token, 1);

				}
			}

			/* This vertex creates a k- or k-double- star so remove its edges */
			if (tokens.get(U[0]).get(token) > 2 && tokens.get(U[1]).get(token) > 2) {
				U[0].adjList.remove(v); U[1].adjList.remove(v);
				vertsToRemove.add(v);	//Mark v as to-be-removed
			}
		}

		g.vertList.removeAll(vertsToRemove);
		for (int i = 0; i < g.size(); i++) { g.get(i).id = i; }	//Correct any missing IDs

		System.out.println("Resulting graph size: " + g.size() + "\n");
	}
}

