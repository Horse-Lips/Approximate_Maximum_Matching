package main;


import graphComponents.*;
import graphUtils.*;
import matchingAlgs.*;
import reductions.*;
import java.io.IOException;
import java.util.*;


public class Main {
    
    public static void main(String[] args) throws IOException {
        Graph g = General.fromSNAPFile("Graphs/example.txt");

		StarReduction.reduce(g);
		HashMap<Integer, ArrayList<Integer>> groupings = DegreeReduction.reduce(g);

		Matching.maxMatch(g, groupings);

		int matchingSize = 0;
		
		for (Vertex v: g.vertList) { 
			if (!v.visited && v.partner != null) {
				System.out.println("(" + v.id + "," + v.partner + ")");
				
				v.visited = true;
				g.get(v.partner).visited = true;

				matchingSize++;
			}
		}

		System.out.println("Matching size: " + matchingSize);
    }

}
































