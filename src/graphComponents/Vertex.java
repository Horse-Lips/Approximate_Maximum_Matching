package graphComponents;


import java.util.ArrayList;


public class Vertex {

    private int id;

    ArrayList<Integer> adjListVert;
    
    public Vertex(int vertID) {
        this.id = vertID;
        this.adjListVert = new ArrayList<Integer>();
    }


    /** Return the ID of the vertex */
    public int getID() {
        return this.id;
    }


    /** Return the entire adjacency list hashmap */
	public ArrayList<Integer> getAdj() {
		return this.adjListVert;
	}
	

	/** Add an edge between this vertex and another specified vertex */
	public void addToAdj(int vertIndex, double weight) {
		this.adjListVert.add(vertIndex);
	}
	
	
	/** Removes an edge from this vertex to the specified vertex */
	public Integer removeFromAdj(int vertIndex) {
		return this.adjListVert.remove(vertIndex);
	}

    
    /** Removes an edge from this vertex to the specified vertex based on Vertex Object (Integer object) */
    public void removeFromAdj(Integer vertIndex) {
        this.adjListVert.remove(vertIndex);
    }


    /** Return the degree of the vertex */
    public int getDegree() {
        return this.adjListVert.size();
    }
}
