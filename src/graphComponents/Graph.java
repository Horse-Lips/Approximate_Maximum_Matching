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
        System.out.println("Beginning degree reduction");
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


                /** STEP 5: Add edge between v and v1 */
                v.addToAdj(v1.getID(), 1);
                v1.addToAdj(v.getID(), 1);
            }
        }

        System.out.println("Resulting graph size: " + this.getSize());
    }
}
