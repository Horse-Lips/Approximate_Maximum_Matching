package graphComponents;


import java.util.ArrayList;


public class Graph {
	/* Misc fields */
    public ArrayList<Vertex> vertList;	// List of all vertices in the graph

    public Graph() {
        this.vertList = new ArrayList<Vertex>();
    }


    /** Return the number of vertices in the graph */
    public int size() { return this.vertList.size(); }

}
