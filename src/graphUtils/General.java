package graphUtils;


import graphComponents.*;
import java.util.*;
import java.io.*;


public class General {
	public static Graph fromSNAPFile(String filename) throws IOException {
		Scanner lineScanner = new Scanner(new BufferedReader(new FileReader(filename)));
		lineScanner.nextLine(); lineScanner.nextLine(); lineScanner.nextLine(); lineScanner.nextLine();

		Graph g = new Graph();	//Create Graph

		HashMap<Integer, ArrayList<Integer>> converter = new HashMap<Integer, ArrayList<Integer>>();
		
		int gIndex = -1;
		int prev   = -1;
		
		while (lineScanner.hasNextLine()) {
			String[] line = lineScanner.nextLine().split("\t");
			int curr = Integer.parseInt(line[0]);
			
			if (curr != prev) { gIndex++; prev = curr; }
			if (!converter.containsKey(gIndex)) { converter.put(gIndex, new ArrayList<Integer>()); }
			converter.get(gIndex).add(Integer.parseInt(line[1]));
			
		}
		
		lineScanner.close();

		for (int i = 0; i < gIndex + 1; i++) {
			Vertex v = new Vertex(i);
			v.adjList = converter.get(i);
			g.vertList.add(v);
		}
		
		return g;
	}
}
