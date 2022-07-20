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
		for (int i = 0; i < g.getSize(); i++) {
			Vertex currentVert = g.getVertex(i);

			currentVert.setStart(false);
			currentVert.setVisited(false);
		}


		for (int i = 0; i < g.getSize(); i++) {
			Vertex currentRoot = g.getVertex(i);

			if (currentRoot.isMatched() || currentRoot.getVisited()) { continue; }

			ArrayList<Vertex> forest = new ArrayList<Vertex>();
			forest.add(currentRoot);
			currentRoot.setStart(true);

			while (!forest.isEmpty()) {
				Vertex currentVert = forest.remove(0);
				currentVert.setVisited(true);

				for (Vertex currentNeighb: currentVert.getAdj()) {
					if (!currentNeighb.getVisited() && !currentNeighb.isMatched() && !currentVert.grouping.contains(currentNeighb)) {
						currentNeighb.setVisited(true);
						currentNeighb.setPred(currentVert);

						if (currentNeighb.getPartner() == null) {
							return currentNeighb;

						} else {
							forest.add(currentNeighb.getPartner());
						}
					}
				}

				
			}
		}

		return null;
	}

	
	/** Augment M along an augmenting path starting from the end vertex */
	public static void augment(Vertex endVert) {
		Vertex pred = endVert.getPred();
		Vertex temp;

		while (!pred.isStart()) {
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
