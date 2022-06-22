package graphComponents;


import java.util.ArrayList;
import graphUtils.SimpleTuple;


public class Graph {

    private ArrayList<Vertex> vertList;         //List of all Vertex objects in the graph
    private int               currentVertID;   //Graph "size" used as vertex ID when creating new vertices (Required due to vertex removal)

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


    /** Reduce all vertices to degree 3*/
    public void degreeReduction() {
        System.out.println("=== Degree Reduction ===");
        System.out.println("Initial graph size: " + this.getSize());

		ArrayList<Vertex> toAddVert = new ArrayList<Vertex>();

        for (Vertex v: this.vertList) {
            while (v.getDegree() > 3) {
                /* STEP 1: Create vertices v1 and v2 */
                Vertex v1 = new Vertex(this.currentVertID++);
                Vertex v2 = new Vertex(this.currentVertID++);

                /* Create edge between v1 and v2 */
                v1.addToAdj(v2, 1);
                v2.addToAdj(v1, 1);

                /* Add v1 and v2 to graph */
				toAddVert.add(v1);
				toAddVert.add(v2);


                /* STEP 2: Get last two neighbours u1 and u2 of v and remove them from its adjacency list*/
                Vertex u1 = v.removeFromAdj(v.getDegree() - 1);
                Vertex u2 = v.removeFromAdj(v.getDegree() - 1);


                /* STEP 3: Remove edges between u1, u2 and v */
                u1.removeFromAdj(v);
                u2.removeFromAdj(v);


                /* STEP 4: Add edges between u1, u2 and v2 */
                u1.addToAdj(v2, 1);
                u2.addToAdj(v2, 1);
                
                v2.addToAdj(u1, 1);
                v2.addToAdj(u2, 1);


                /** STEP 5: Add edge between v and v1 */
                v.addToAdj(v1, 1);
                v1.addToAdj(v, 1);
            }
        }
		
		this.vertList.addAll(toAddVert);

        System.out.println("Resulting graph size: " + this.getSize() + "\n");
    }

    
    /** Removes all 2-stars by reducing all k-stars to 1-stars */
    public void starReduction() {
        System.out.println("=== k-Star Removal ===");
        System.out.println("Initial graph size: " + this.vertList.size());

        ArrayList<Vertex> toRemoveVert = new ArrayList<Vertex>(); //List of vertices to remove from graph


        for (Vertex v: this.vertList) { v.clearTokens(); }  //Clear all tokens


        /* Collect all vertices of degree 1 contributing to k-stars with k > 1 */
        for (Vertex v: this.vertList) {
            if (v.getDegree() != 1) { continue; }
            
            Vertex neighb = v.getAdj().get(0);

			if (neighb.getDegree() < 2) { continue; }

			SimpleTuple<Integer, Integer> token = new SimpleTuple<Integer, Integer>(neighb.getID(), neighb.getID());

			neighb.setToken(token);

            if (neighb.getToken(token) > 1) {
                neighb.removeFromAdj(v);
                toRemoveVert.add(v);
            }
        }

        for (Vertex v: this.vertList) { v.clearTokens(); }  //Clear all tokens


		/* Collect all vertices of degree 2 contributing to k-double-stars with k > 2 */
        for (Vertex v: this.vertList) {
            if (v.getDegree() != 2) { continue; }

            Vertex neighb1 = v.getAdj().get(0);
            Vertex neighb2 = v.getAdj().get(1);

            if (neighb1.getDegree() < 3 || neighb2.getDegree() < 3) { continue; }
			
            SimpleTuple<Integer, Integer> token = new SimpleTuple<Integer, Integer>(neighb1.getID(), neighb2.getDegree());

            neighb1.setToken(token);
            neighb2.setToken(token);

            if (neighb1.getToken(token) > 2) {
                neighb1.removeFromAdj(v);
                neighb2.removeFromAdj(v);

                toRemoveVert.add(v);
            }
        }

        this.vertList.removeAll(toRemoveVert);

        System.out.println("Resulting graph size: " + this.vertList.size() + "\n");
    }

}














































