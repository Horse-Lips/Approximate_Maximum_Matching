package graphComponents;


import java.util.ArrayList;
import java.util.HashMap;
import graphUtils.SimpleTuple;


public class Vertex {

    private int id; //ID of the vertex

    private ArrayList<Vertex>             adjListVert;  //List of all vertices adjacent to this one
    private HashMap<SimpleTuple, Integer> tokens;       //Tokens received from other vertices (in star removal)
	public ArrayList<Vertex>			  grouping;		//Vertices that this vertex is grouped with after degree reduction

	private boolean matched;	//true if this vertex is in M and false otherwise
	private boolean startVert;	//true if this vertex is at the start of the augmenting path
	private boolean visited;	//true if this vertex has been visited
	private Vertex  partner;	//Vertex that this Vertex is matched with
	private Vertex  pred;		//Previous vertex in augmenting path

    public Vertex(int vertID) {
        this.id          = vertID;
        this.adjListVert = new ArrayList<Vertex>();
        this.tokens      = new HashMap<SimpleTuple, Integer>();
		this.grouping	 = new ArrayList<Vertex>();

		this.matched = false;
		this.partner = null;
    }


    /** Return the ID of the vertex */
    public int getID() { return this.id;}


    /** Return the entire adjacency list hashmap */
	public ArrayList<Vertex> getAdj() { return this.adjListVert; }
	

	/** Add an edge between this vertex and another specified vertex */
	public void addToAdj(Vertex v, double weight) { this.adjListVert.add(v); }
	
	
	/** Removes an edge from this vertex to the specified vertex */
	public boolean removeFromAdj(Vertex v) { return this.adjListVert.remove(v); }


	/** Removes an edge from this vertex based on index in the adjacency list */
	public Vertex removeFromAdj(int i) { return this.adjListVert.remove(i); }


    /** Return the degree of the vertex */
    public int getDegree() { return this.adjListVert.size(); }


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


	/** Returns the value of matched */
	public boolean isMatched() { return this.matched; }


	/** Sets matched to the boolean value given */
	public void setMatched(boolean b) { 
		this.matched = b;
		
		if (!b) { this.partner = null; }

		for (Vertex v: this.grouping) {
			if (!v.isMatched()) { v.setMatched(b); }
		}
	}


	/** Returns the value of starVert */
	public boolean isStart() {
		return this.startVert;
	}


	/** Sets the value of starVert to the given boolean value */
	public void setStart(boolean b) {
		this.startVert = b;
	}


	/** Returns the partner of this vertex */
	public Vertex getPartner() { return this.partner; }


	/** Updates the partner of this vertex */
	public void setPartner(Vertex v) { this.partner = v; }


	/** Returns the value of visited */
	public boolean getVisited() {
		return this.visited;
	}


	/** Update the value of visited to the given boolean value */
	public void setVisited(boolean b) {
		this.visited = b;
	}

	
	/** Returns the predecessor in the path */
	public Vertex getPred() {
		return this.pred;
	}


	/** Updates the predecessor in the path */
	public void setPred(Vertex v) {
		this.pred = v;
	}


    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        
        } else if (!(o instanceof Vertex)) {
            return false;

        }

        Vertex v = (Vertex) o;

        if (v.getID() == this.getID()) {
            return true;
        }

        return false;
    }


	@Override
	public String toString() { return Integer.toString(this.getID()); }

}
