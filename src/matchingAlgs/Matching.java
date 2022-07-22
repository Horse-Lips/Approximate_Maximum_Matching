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

		/* Unmark all vertices in G and mark all vertices in M */
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
			
			/* While there is an unmarked vertex in G with dist(v, root(v)) even */
			if (getDist(currentRoot) % 2 != 0 && !currentRoot.getVisited()) { continue; }

			currentRoot.setOuter();

			for (Vertex currentNeighb: currentRoot.getAdj()) {
				/* While there exists an unmarked edge (v, w) */
				
				if (currentRoot.outer && currentNeighb.outer) {
					System.out.println("Blossom found on edge (" + currentRoot.getID() + "," + currentNeighb.getID() + ")");
					findCycle(currentRoot, currentNeighb);
					return contract(currentRoot, currentNeighb);
				}

				if (currentNeighb.isMatched()) {	//w is not in F (q) (its matched)
					currentRoot.setPred(currentNeighb);
					tracePath(currentRoot);

				} else {
					if (getDist(currentNeighb) % 2 == 0) { //If dist (w, root(w)) is even then do stuff
						if (currentRoot.outer && currentNeighb.outer) {
							System.out.println("Blossom detected");

						} else {
							Vertex temp;

							while ((temp = currentNeighb.getPred()) != null) {
								if (currentRoot.inner) { currentNeighb.setOuter(); }
								if (currentRoot.outer) { currentNeighb.setInner(); }

								currentNeighb.setPred(currentRoot);

								currentRoot   = currentNeighb;
								currentNeighb = temp;
							}

							if (currentNeighb != null) {
								currentNeighb.setPred(currentRoot); 

								if (currentNeighb.getPred().inner) { currentNeighb.setOuter(); }
								if (currentNeighb.getPred().outer) { currentNeighb.setInner(); }
							}

							return currentNeighb;

						}
					}


					if (currentNeighb.outer && currentRoot.outer) { 
						System.out.println("Blossom found at edge (" + 
										   currentRoot.getID() + "," + 
										   currentNeighb.getID() + ")");

						findCycle(currentRoot, currentNeighb);
						//contract(currentRoot, currentNeighb);
					}
				}

			currentRoot.setVisited(true);
			}
		}

		return null;
	}


	/** Traces the path from a given end vertex to the start, prints in order */
	public static void tracePath(Vertex endVert) {
		String path        = "";

		while (endVert != null) {
			path = "," + endVert.getID() + path;
			endVert = endVert.getPred();
		}

		System.out.println(path);
	}


	/** Returns the root vertex from a given end vertex */
	public static Vertex getRoot(Vertex endVert) {
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
		while (endVert != null && endVert.getPred() != null) {
			Vertex pred = endVert.getPred();

			endVert.setPartner(pred);
			pred.setPartner(endVert);

			endVert.setMatched(true);
			pred.setMatched(true);

			endVert = pred.getPred();
		}
	}


	/** Helper function for finding the start of a cycle and setting pred of a root */
	public static void findCycle(Vertex endVert, Vertex startVert) {
		for (Vertex v: endVert.getAdj()) {
			if (v == startVert) { continue; }
			Vertex currentVert = v;

			while (currentVert.getPred() != null) {
				if (currentVert == startVert) {
					endVert.setPred(v);
					return;
				}

				currentVert = currentVert.getPred();
			}
		}
	}


	/** Given the end vert of a path and its neighbour representing the final edge in a cycle, contract blossom */
	public static Vertex contract(Vertex endVert, Vertex startVert) {
		Vertex blossom = endVert;
		Vertex ret     = endVert;

		while (blossom.getPred() != startVert) { blossom = blossom.getPred(); }

		ArrayList<Vertex> blossomAdj = blossom.getAdj();

		/* Begin contracting edges around the cycle until we reach the start */
		while (endVert != blossom) {
			ArrayList<Vertex> toRemove = new ArrayList<Vertex>();

			for (Vertex v: endVert.getAdj()) {
				if (v == startVert) { v.removeFromAdj(startVert); startVert.removeFromAdj(v); }
				if (v == blossom)   { v.removeFromAdj(blossom);   blossom.removeFromAdj(v);    }

				if (v != endVert.getPred()) { 
					toRemove.add(v);
					v.removeFromAdj(endVert);

					if (!blossomAdj.contains(v)) { blossomAdj.add(v); }}
			}

			for (Vertex v: toRemove) { endVert.removeFromAdj(v); }

			if (endVert.getPred() == blossom) {
				endVert.setPred(null);
				endVert = blossom;

			} else {
				endVert = endVert.getPred();
			
			}
		}

		endVert.setVisited(true);
		return endVert;
	}
} 












































