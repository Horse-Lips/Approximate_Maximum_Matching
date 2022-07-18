package graphComponents;


import java.util.ArrayList;
import java.util.HashSet;
import graphUtils.SimpleTuple;
import graphUtils.SimpleQueuePrio;


public class Graph {

    private ArrayList<Vertex> vertList;         //List of all Vertex objects in the graph
    private int               currentVertID;	//ID of next Vertex to be added to the graph

    public Graph(int graphSize) {
        this.vertList = new ArrayList<Vertex>();
        this.currentVertID = graphSize;

        for (int i = 0; i < graphSize; i++) {
            this.vertList.add(new Vertex(i));
        }
    }


    /** Return the number of vertices in the graph */
    public int getSize() {
        return this.vertList.size();
    }


    /** Return the vertex with the specified vertList index */
    public Vertex getVertex(int index) {
        return this.vertList.get(index);    
    }


	/** Create an edge between the two given vertices */
	public void createEdge(Vertex v1, Vertex v2) {
		v1.addToAdj(v2, 1);
		v2.addToAdj(v1, 1);
	}


	/** Helper function for grouping vertices*/
	public void groupVertices(Vertex... verts) {
		for (Vertex v: verts) {
			for (Vertex u: verts) {
				if (v != u) { v.addToGrouping(u); }
			}
		}
	}


    /** Reduce all vertices to degree 3*/
    public void degreeReduction() {
        System.out.println("=== Degree Reduction ===\nInitial graph size: " + this.getSize());

		ArrayList<Vertex> toAddVert = new ArrayList<Vertex>();

        for (Vertex v: this.vertList) {
            while (v.getDegree() > 3) {
                /* STEP 1: Create vertices v1 and v2 */
                Vertex v1 = new Vertex(this.currentVertID++);
                Vertex v2 = new Vertex(this.currentVertID++);

				this.groupVertices(v, v1, v2);	//Group vertices so max matchings are the same

				this.createEdge(v1, v2);		//Create edge between v1 and v2

				toAddVert.add(v1);				//Add v1 and v2 to graph
				toAddVert.add(v2);

                /* STEP 2: Get last two neighbours u1 and u2 of v and remove them from its adjacency list*/
                Vertex u1 = v.removeFromAdj(v.getDegree() - 1);
                Vertex u2 = v.removeFromAdj(v.getDegree() - 1);

                /* STEP 3: Remove edges between u1, u2 and v */
                u1.removeFromAdj(v);
                u2.removeFromAdj(v);

                /* STEP 4: Add edges between u1, u2 and v2 */
				this.createEdge(u1, v2);
				this.createEdge(u2, v2);

                /** STEP 5: Add edge between v and v1 */
				this.createEdge(v, v1);
            }
        }
		
		this.vertList.addAll(toAddVert);

        System.out.println("Resulting graph size: " + this.getSize() + "\n");
    }

    
    /** Removes all 2-stars by reducing all k-stars to 1-stars */
    public void starReduction() {
        System.out.println("=== k-Star Removal ===\nInitial graph size: " + this.vertList.size());

        ArrayList<Vertex> toRemoveVert = new ArrayList<Vertex>();	//List of vertices to remove from graph
        for (Vertex v: this.vertList) { v.clearTokens(); }  		//Clear all tokens

        for (Vertex v: this.vertList) {
			Vertex[] neighbs = {v.getAdj().get(0), v.getAdj().get(0)};	//Get first neighbour twice

			/* Ignore all non-2-stars and non-3-double-stars */
			if (v.getDegree() == 1 && neighbs[0].getDegree() < 2) { continue; }
			if (v.getDegree() == 2 && (neighbs[0].getDegree() < 3 || (neighbs[1] = v.getAdj().get(1)).getDegree() < 3)) { continue; }
			if (v.getDegree() > 2) { continue; }

			SimpleTuple<Vertex> token = new SimpleTuple<Vertex>(neighbs[0], neighbs[1]);  //Create token on neighbs

			for (Vertex neighb: neighbs) { neighb.setToken(token); }	//Pass token to neighbours

			if (neighbs[0].getToken(token) > 2 && neighbs[1].getToken(token) > 2) {	//2-star/3-double-star exists
				neighbs[0].removeFromAdj(v);	//Remove neighbour connections with v
				neighbs[1].removeFromAdj(v);

				toRemoveVert.add(v);			//Set v as to-be-removed
			}
        }

        this.vertList.removeAll(toRemoveVert);	//Remove all vertices contributing to stars

        System.out.println("Resulting graph size: " + this.vertList.size() + "\n");
    }


	/** Check if degree reduction was successful (Max degree is 3) */
	public void checkDegree() {
		for (Vertex v: this.vertList) {
			if (v.getDegree() > 3) {
				System.out.println("Degree reduction unsuccessful!");
			}
		}
	}



	/** Separate the graph based on the planar separator theorem */
	public void separate() {
		for (Vertex v: this.vertList) { v.setUnvisited(); }	//Mark all vertices as unvisted

		Vertex start = this.vertList.get(0);	//Get start vertex
		start.setVisited();						//Mark start as visited
		ArrayList<Vertex> startArray = new ArrayList<Vertex>();
		startArray.add(start);

		ArrayList<ArrayList<Vertex>> distSets = new ArrayList<ArrayList<Vertex>>(); //Store all levels
		distSets.add(startArray);

		ArrayList<Vertex> currentNeighbours = start.getAdj();	//Get level 1 (level 0 is the original vertex)

		int numVisited = 1;	//Number of visited vertices

		while (numVisited < this.getSize()) {
			distSets.add(currentNeighbours);	//Store current level

			for (Vertex v: currentNeighbours) { v.setVisited(); }	//Set current level as visited
			numVisited += currentNeighbours.size();
			
			HashSet<Vertex> nextNeighbours = new HashSet<Vertex>();	//Get all vertices on next level

			for (Vertex v: currentNeighbours) {
				for (Vertex n: v.getAdj()) {
					if (!n.getVisited()) { nextNeighbours.add(n); }
				}
			}

			currentNeighbours = new ArrayList<Vertex>();
			currentNeighbours.addAll(nextNeighbours);
		}

		for (ArrayList<Vertex> a: distSets) { System.out.println(a); }
		System.out.println("\n");

		int   k          = 0;	//Number of vertices in levels 0 through l1
		int   levelIndex = 0;	//Used to index position in distSet

		int L0Index = 0;
		int L1Index = 0;
		int L2Index = 0;

		for (float totalCost = 0; totalCost < 0.5; ) {	//Find L1Index
			ArrayList<Vertex> currentSet = distSets.get(levelIndex++);
			totalCost += ((float) currentSet.size()) / (float) this.vertList.size();
			k         += currentSet.size();
		}

		L1Index = levelIndex - 1;

		for (L0Index = L1Index - 1; L0Index >= 0; L0Index--) {
			ArrayList<Vertex> currentSet = distSets.get(L0Index);

			float L1Cost = (float) distSets.get(L1Index).size() / (float) this.vertList.size();
			float L0Cost = (float) currentSet.size()            / (float) this.vertList.size();

			if (currentSet.size() + (2 * (L1Cost - L0Cost)) <= 2 * Math.sqrt(k)) { break; }
		}

		for (L2Index = L1Index + 1; L1Index < distSets.size(); L2Index++) {
			ArrayList<Vertex> currentSet = distSets.get(L2Index);

			float L1Cost = (float) distSets.get(L1Index).size() / (float) this.vertList.size();
			float L2Cost = (float) currentSet.size()            / (float) this.vertList.size();

			if (currentSet.size() + (2 * (L2Cost - L1Cost - 1)) <= 2 * Math.sqrt(this.vertList.size() - k)) { break; }
		}

		ArrayList<Vertex> A = new ArrayList<Vertex>();
		ArrayList<Vertex> B = new ArrayList<Vertex>();
		ArrayList<Vertex> C = new ArrayList<Vertex>();

		if (distSets.get(L1Index).size() >= distSets.get(L2Index).size()) {
			for (int i = 0; i < L1Index; i++)                   { A.addAll(distSets.get(i)); }
			for (int i = L1Index + 1; i < distSets.size(); i++) { B.addAll(distSets.get(i)); }
			C = distSets.get(L1Index);

		} else {
			ArrayList<Vertex> firstPart  = new ArrayList<Vertex>();
			ArrayList<Vertex> middlePart = new ArrayList<Vertex>();
			ArrayList<Vertex> endPart    = new ArrayList<Vertex>();

			for (int i = 0; i < L1Index; i++)                   { firstPart.addAll(distSets.get(i));  }
			for (int i = L1Index + 1; i < L2Index; i++)         { middlePart.addAll(distSets.get(i)); }
			for (int i = L2Index + 1; i < distSets.size(); i++) { endPart.addAll(distSets.get(i));    }

			if ((float) middlePart.size() / (float) this.vertList.size() <= 2.0 / 3.0) {
				A = firstPart;
				B.addAll(middlePart);
				B.addAll(endPart);

				if (middlePart.size() > A.size()) {
					A = middlePart;
					B = firstPart;
					B.addAll(endPart);

				} else if (endPart.size() > A.size()) {
					A = endPart;
					B = firstPart;
					B.addAll(middlePart);

				}

				C.addAll(distSets.get(L1Index));
				C.addAll(distSets.get(L2Index));

			} else {
				System.out.println("TBC");
			}

		}

		System.out.println("A: " + A);
		System.out.println("B: " + B);
		System.out.println("C: " + C);

		System.out.println("Graph size: " + this.vertList.size());
		System.out.println("A size: " + ((float) A.size() / (float) this.getSize()));
		System.out.println("B size: " + ((float) B.size() / (float) this.getSize()));
		System.out.println("C size: " + ((float) C.size() / (float) this.getSize()));
	}

}







































