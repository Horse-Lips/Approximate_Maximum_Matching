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
		DegreeReduction.reduce(g);
		

		/*Matching.maxMatch(g);

		int matchingSize = 0;
		
		for (int i = 0; i < g.getSize(); i++) { 
			Vertex currentVert = g.getVertex(i);

			if (currentVert.partner == null) {
				if (currentVert.matched) {
					System.out.println("Vertex " + currentVert.id + " is unmatched BUT MARKED!");

				} else {
					System.out.println("Vertex " + currentVert.id + " is unmatched!"); 

				}

			} else if (!currentVert.visited) {
				System.out.println("(" + currentVert.id + "," + currentVert.partner.id + ")");
				
				currentVert.visited = true;
				currentVert.partner.visited = true;

				matchingSize++;
			}
		}

		System.out.println("Matching size: " + matchingSize);*/
    }

}
































