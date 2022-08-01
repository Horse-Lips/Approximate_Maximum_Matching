package matchingAlgs;


import graphComponents.*;
import java.util.ArrayList;
import java.util.HashMap;


public class Matching {
	/** Find the maximum matching in the graph */
	public static void maxMatch(Graph g) {
		while (findAug(g));
	}


	/** Class used to store vertex information */
	static class VertInfo {
		public Vertex  parent;		//Vertex' parent in augmenting path
		public Vertex  treeRoot;	//Root of the augmenting path
		public boolean outer;		//Whether or not the vertex is outer

		public VertInfo(Vertex parent, Vertex treeRoot, boolean outer) {
			this.parent   = parent;
			this.treeRoot = treeRoot;
			this.outer    = outer;
		}
	}


	/** Class used to represent an edge between two vertices */
	static class Edge {
		public Vertex v;	//Vertex at one end of edge
		public Vertex w;	//Vertex at other end of edge

		public Edge(Vertex v, Vertex w) {
			this.v = v;
			this.w = w;
		}
	}


	/** Find an augmenting path in G */
	public static boolean findAug(Graph g) {
		HashMap<Vertex, VertInfo> F = new HashMap<Vertex, VertInfo>();	//Augmenting path forest
		ArrayList<Edge>           q = new ArrayList<Edge>();			//Edges to expand

		// Add all unmatched vertices to forest with VertInfo and edges to the queue
		for (Vertex v: g.vertList) {
			if (v.matched) { continue; }

			for (Vertex w: v.adjList) {
				if (!v.grouping.contains(w)) {
					F.put(v, new VertInfo(null, v, true));
					q.add(new Edge(v, w));

				}
			}
		}


		while (!q.isEmpty()) {	//While there is an unmarked edge (v, w)

			Edge e = q.remove(0);
			VertInfo vInfo = F.get(e.v);
			VertInfo wInfo = F.get(e.w);

			if (wInfo != null) {	//We have VertInfo for the neighbour, so it is unmatched
				if (wInfo.outer && wInfo.treeRoot == vInfo.treeRoot) {	//v and w share a root, potential blossom
					System.out.println("Blossom found: (" + e.w.id + "," + e.v.id + ") Roots: " + wInfo.treeRoot + "," + vInfo.treeRoot);

					ArrayList<Vertex> path = new ArrayList<Vertex>();

					for (Vertex u1 = e.v; u1 != null; u1 = F.get(u1).parent) { path.add(0, u1); }
					for (Vertex u2 = e.w; u2 != null; u2 = F.get(u2).parent) { path.add(u2);    }

					return augment(path);

				} else if (wInfo.outer && wInfo.treeRoot != vInfo.treeRoot) {	//No shared root, augmenting path
					ArrayList<Vertex> path = new ArrayList<Vertex>();

					for (Vertex u1 = e.v; u1 != null; u1 = F.get(u1).parent) { path.add(0, u1); }
					for (Vertex u2 = e.w; u2 != null; u2 = F.get(u2).parent) { path.add(u2);    }

					return augment(path);

				}

			} else if (e.w.partner != null) {	//No VertInfo so w is matched
				F.put(e.w,         new VertInfo(e.v, vInfo.treeRoot, false));				
				F.put(e.w.partner, new VertInfo(e.w, vInfo.treeRoot, true ));

				for (Vertex u: e.w.partner.adjList) { q.add(new Edge(e.w.partner, u)); }

			}

			
		}

		return false;
	}


	/** Augment M along an augmenting path starting from the end vertex */
	public static boolean augment(ArrayList<Vertex> path) {
		int i = 0;

		while (i < path.size()) {
			path.get(i).partner     = path.get(i + 1);
			path.get(i + 1).partner = path.get(i);
			i += 2;
		}

		for (Vertex v: path) { v.setMatched(); }

		return true;
	}

} 












































