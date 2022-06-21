package graphComponents;


import java.util.ArrayList;


public class Graph {

    private ArrayList<Vertex> vertList;

    public Graph(int graphSize) {
        this.vertList = new ArrayList<Vertex>();

        for (int i = 0; i < graphSize; i++) {
            this.vertList.add(new Vertex(i));
        }
    }


    /** Return the number of vertices in the graph */
    public int getSize() {
        return this.vertList.size();
    }


    /** Return the vertex with the specified ID */
    public Vertex getVertex(int id) {
        return this.vertList.get(id);    
    }


    /** Reduce all vertices to degree 3*/
    public void degreeReduction() {
        System.out.println("=== Degree Reduction ===");
        System.out.println("Initial graph size: " + this.getSize());

        for (int i = 0; i < this.vertList.size(); i++) {
            Vertex v = this.vertList.get(i);

            while (v.getDegree() > 3) {
                /* STEP 1: Create vertices v1 and v2 */
                Vertex v1 = new Vertex(this.vertList.size());
                Vertex v2 = new Vertex(this.vertList.size() + 1);

                /* Create edge between v1 and v2 */
                v1.addToAdj(v2.getID(), 1);
                v2.addToAdj(v1.getID(), 1);

                /* Add v1 and v2 to graph */
                this.vertList.add(v1);
                this.vertList.add(v2);


                /* STEP 2: Get last two neighbours u1 and u2 of v and remove them from its adjacency list*/
                Vertex u1 = this.vertList.get(v.removeFromAdj(v.getDegree() - 1));
                Vertex u2 = this.vertList.get(v.removeFromAdj(v.getDegree() - 1));


                /* STEP 3: Remove edges between u1, u2 and v */
                Integer vIndex = v.getID();   //Convert to Integer to remove specific ID
                
                u1.removeFromAdj(vIndex);
                u2.removeFromAdj(vIndex);


                /* STEP 4: Add edges between u1, u2 and v2 */
                u1.addToAdj(v2.getID(), 1);
                u2.addToAdj(v2.getID(), 1);
                
                v2.addToAdj(u1.getID(), 1);
                v2.addToAdj(u2.getID(), 1);


                /** STEP 5: Add edge between v and v1 */
                v.addToAdj(v1.getID(), 1);
                v1.addToAdj(v.getID(), 1);
            }
        }

        System.out.println("Resulting graph size: " + this.getSize() + "\n");
    }

    
    /** Removes all 2-stars by reducing all k-stars to 1-stars */
    public void kStarReduction() {
        System.out.println("=== k-Star Removal ===");
        System.out.println("Initial graph size: " + this.vertList.size());

        ArrayList<Integer> toRemoveVert = new ArrayList<Integer>(); //List of vertices to remove from graph
        ArrayList<Integer> toRemoveAdj  = new ArrayList<Integer>(); //List of vertices to remove from v's adj

        for (int i = 0; i < this.vertList.size(); i++) {
            Vertex v      = this.vertList.get(i);   //Get current vertex
            int numDegOne = 0;                      //Number of degree 1 neighbours found

            ArrayList<Integer> vAdj        = v.getAdj();    //Current vertex adjacency list
            ArrayList<Integer> toRemoveAdj = new ArrayList<Integer>();  //List of vertex indices to remove from adj

            for (Integer j: vAdj) {
                if (this.vertList.get(j).getDegree() == 1) {
                    if (numDegOne > 0) {
                        numDegOne++;

                        toRemoveAdj.add(j);
                        toRemoveVert.add(j);

                    } else {
                        numDegOne++;

                    }
                }
            }

            for (Integer j: toRemoveAdj) {
                v.removeFromAdj(j);
            }
        }

        for (Integer j: toRemoveVert) {
            this.vertList.remove(this.vertList.get(j));
        }

        System.out.println("Resulting graph size: " + this.vertList.size() + "\n");
    }
}
