package matchingAlgs;


import graphComponents.*;
import java.util.ArrayList;


public class Matching {
	/** Find the maximum matching in the graph */
	public static void maxMatch(Graph g) {
		Vertex endAug;
		
		while ((endAug = findAug(g)) != null) {
			augment(endAug);
		}
	}


	/** Find an augmenting path in G */
	public static Vertex findAug(Graph g) {
		ArrayList<Vertex> q = new ArrayList<Vertex>();

		for (int i = 0; i < g.getSize(); i++) {
			Vertex currentVert = g.getVertex(i);

			if (!currentVert.isMatched()) {
				currentVert.setVisited(false);
				q.add(currentVert);

			} else {
				currentVert.setVisited(true);

			}
		}

		while (!q.isEmpty()) {
			Vertex currentRoot = q.remove(0);

			if (getDist(currentRoot) % 2 != 0) { continue; }

			for (Vertex currentNeighb: currentRoot.getAdj()) {
				if (currentNeighb.isMatched()) {
					currentNeighb.getPartner().setPred(currentNeighb);
					currentNeighb.setPred(currentRoot);

					q.add(currentNeighb);

				} else {
					if (getDist(currentNeighb) % 2 != 0) { continue; }

					Vertex temp;

					while ((temp = currentNeighb.getPred()) != null) {
						currentNeighb.setPred(currentRoot);

						currentRoot   = currentNeighb;
						currentNeighb = temp;
					}

					if (currentNeighb != null) { currentNeighb.setPred(currentRoot); }

					return currentNeighb;
				}
			}
		}

		return null;
	}


	/** Returns the root vertex from a given end vertex */
	public static Vertex getRoot(Vertex endVert) {
		System.out.println(endVert.getPred());
		while (endVert.getPred() != null) { endVert = endVert.getPred(); }

		return endVert;
	}


	/** Returns the distance from a given end vertex to its root in the path */
	public static int getDist(Vertex endVert) {
		int i;
		for (i = 0; endVert.getPred() != null; i++, endVert = endVert.getPred());

		return i;
	}


	/** Augment M along an augmenting path starting from the end vertex */
	public static void augment(Vertex endVert) {
		Vertex pred = endVert.getPred();
		Vertex temp;

		while (pred.getPred() != null) {
			temp = pred.getPartner();

			endVert.setPartner(pred);
			pred.setPartner(endVert);

			endVert.setMatched(true);
			pred.setMatched(true);

			endVert = temp;
			pred = endVert.getPred();
		}

		endVert.setPartner(pred);
		pred.setPartner(endVert);

		endVert.setMatched(true);
		pred.setMatched(true);
	}

} 












































