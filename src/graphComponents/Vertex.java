package graphComponents;


import java.util.ArrayList;
import java.util.HashMap;
import graphUtils.SimpleTuple;


public class Vertex {

    public int id; //ID of the vertex

    public ArrayList<Vertex>             adjList; //List of all vertices adjacent to this one
    public HashMap<SimpleTuple, Integer> tokens;      //Tokens received from other vertices (in star removal)
	public ArrayList<Vertex>			 grouping;	  //Vertices this vertex is grouped with after degree reduction

	public boolean matched;	//true if this vertex is in M and false otherwise
	public Vertex  partner;	//Vertex that this Vertex is matched with

	public boolean visited;	//true if vertex has been visited

    public Vertex(int vertID) {
        this.id          = vertID;
        this.adjList = new ArrayList<Vertex>();
        this.tokens      = new HashMap<SimpleTuple, Integer>();
		this.grouping	 = new ArrayList<Vertex>();

		this.matched = false;
		this.partner = null;
    }
	

	/** Add an edge between this vertex and another specified vertex */
	public void addToAdj(Vertex v, double weight) { this.adjList.add(v); }
	
	
	/** Removes an edge from this vertex to the specified vertex */
	public boolean removeFromAdj(Vertex v) { return this.adjList.remove(v); }


	/** Removes an edge from this vertex based on index in the adjacency list */
	public Vertex removeFromAdj(int i) { return this.adjList.remove(i); }


    /** Return the degree of the vertex */
    public int getDegree() { return this.adjList.size(); }


    /** Get the value of a specific token from the hashmap */
    public int getToken(SimpleTuple t) { return this.tokens.get(t); }


    /** Insert a token into the hashmap */
    public void setToken(SimpleTuple t) {
        if (this.tokens.containsKey(t)) {
            this.tokens.put(t, this.tokens.get(t) + 1);

        } else {
            this.tokens.put(t, 1);
        
        }
    }


    /** Clear all tokens */
    public void clearTokens() { this.tokens = new HashMap<SimpleTuple, Integer>(); }


	/** Add a vertex to this vertex' gropuping */
	public void addToGrouping(Vertex v) { this.grouping.add(v); }


	/** Sets matched to the boolean value given */
	public void setMatched() { 
		this.matched = true;
		for (Vertex v: this.grouping) { if (!v.matched) { v.matched = true; } }
	}


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
