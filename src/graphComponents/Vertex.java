package graphComponents;


import java.util.ArrayList;
import java.util.HashMap;
import graphUtils.SimpleTuple;


public class Vertex {
	/* Misc fields */
    public int                id;		//ID of this vertex
    public ArrayList<Integer> adjList;	//Adjcency list of this vertex
	public boolean            visited;	//true if vertex has been visited

	/* Maximum matching fields */
	public boolean matched;	//true if this vertex is in M and false otherwise
	public Integer partner;	//Vertex that this Vertex is matched with

    public Vertex(int id) {
        this.id      = id;
        this.adjList = new ArrayList<Integer>();
		this.visited = false;
		this.matched = false;
		this.partner = null;
    }


    /** Return the degree of the vertex */
    public int degree() { return this.adjList.size(); }


    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        
        } else if (!(o instanceof Vertex)) {
            return false;

        }

        Vertex v = (Vertex) o;

        if (v.id == this.id) {
            return true;
        }

        return false;
    }


	@Override
	public String toString() { return Integer.toString(this.id); }

}
