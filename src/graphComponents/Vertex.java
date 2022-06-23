package graphComponents;


import java.util.ArrayList;
import java.util.HashMap;
import graphUtils.SimpleTuple;


public class Vertex {

    private int id; //ID of the vertex

    ArrayList<Vertex>             adjListVert;  //List of all vertices adjacent to this one
    HashMap<SimpleTuple, Integer> tokens;       //Tokens received from other vertices (in star removal)

    public Vertex(int vertID) {
        this.id          = vertID;
        this.adjListVert = new ArrayList<Vertex>();
        this.tokens      = new HashMap<SimpleTuple, Integer>();
    }


    /** Return the ID of the vertex */
    public int getID() {
        return this.id;
    }


    /** Return the entire adjacency list hashmap */
	public ArrayList<Vertex> getAdj() {
		return this.adjListVert;
	}
	

	/** Add an edge between this vertex and another specified vertex */
	public void addToAdj(Vertex v, double weight) {
		this.adjListVert.add(v);
	}
	
	
	/** Removes an edge from this vertex to the specified vertex */
	public boolean removeFromAdj(Vertex v) {
		return this.adjListVert.remove(v);
	}


	/** Removes an edge from this vertex based on index in the adjacency list */
	public Vertex removeFromAdj(int i) {
		return this.adjListVert.remove(i);
	}


    /** Return the degree of the vertex */
    public int getDegree() {
        return this.adjListVert.size();
    }


    /** Get the value of a specific token from the hashmap */
    public int getToken(SimpleTuple t) {
        return this.tokens.get(t);
    }


    /** Insert a token into the hashmap */
    public void setToken(SimpleTuple t) {
        if (this.tokens.containsKey(t)) {
            this.tokens.put(t, this.tokens.get(t) + 1);

        } else {
            this.tokens.put(t, 1);
        
        }
    }


    /** Clear all tokens */
    public void clearTokens() {
        this.tokens = new HashMap<SimpleTuple, Integer>();
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
}
