package main;


import graphComponents.*;
import graphUtils.*;
import matchingAlgs.*;
import java.io.IOException;
import java.util.Objects;
import java.util.HashSet;


public class Main {
    
    public static void main(String[] args) throws IOException {
        Graph g = General.fromSNAPFile("Graphs/example.txt");

		g.starReduction();
		g.degreeReduction();

		Matching.maxMatch(g);

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

		System.out.println("Matching size: " + matchingSize);


		/* Testing to check aug path lengths */
		/*for (int i = 0; i < g.getSize(); i++) {
			Vertex currentVert = g.getVertex(i);
			String path = "";

			while (currentVert != null) {
				path = "," + currentVert.getID() + path;
				currentVert = currentVert.getPred(); 
			}

			System.out.println(path);
		}*/
    }

}
































