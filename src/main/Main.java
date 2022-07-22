package main;


import graphComponents.*;
import graphUtils.*;
import matchingAlgs.*;
import java.io.IOException;
import java.util.Objects;
import java.util.HashSet;


public class Main {
    
    public static void main(String[] args) throws IOException {
        Graph g = General.fromSNAPFile("Graphs/exampleBlossom.txt");

		//g.starReduction();
		//g.degreeReduction();

		Matching.maxMatch(g);

		int matchingSize = 0;
		
		for (int i = 0; i < g.getSize(); i++) { 
			Vertex currentVert = g.getVertex(i);

			if (currentVert.getPartner() == null) {
				if (currentVert.isMatched()) {
					System.out.println("Vertex " + currentVert.getID() + " is unmatched BUT MARKED!");

				} else {
					System.out.println("Vertex " + currentVert.getID() + " is unmatched!"); 

				}

			} else {
				System.out.println("(" + currentVert.getID() + "," + currentVert.getPartner().getID() + ")");

				matchingSize++;
			}
		}

		System.out.println("Matching size: " + matchingSize / 2);


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
































