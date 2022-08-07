package graphUtils;


import graphComponents.*;
import java.util.*;
import java.io.*;


public class General {
	public static Graph fromSNAPFile(String filename) throws IOException {
		Scanner lineScanner = new Scanner(new BufferedReader(new FileReader(filename)));
		lineScanner.nextLine(); lineScanner.nextLine(); lineScanner.nextLine(); lineScanner.nextLine();

		HashMap<Integer, Integer> converter = new HashMap<Integer, Integer>();		

		int gIndex = -1;
		int prev = -1;

		while (lineScanner.hasNextLine()) {
			int curr = Integer.parseInt(lineScanner.nextLine().split("\t")[0]);

			if (curr != prev) { prev = curr; gIndex++; converter.put(prev, gIndex); }
		}

		lineScanner.close();

		lineScanner = new Scanner(new BufferedReader(new FileReader(filename)));
		lineScanner.nextLine(); lineScanner.nextLine(); lineScanner.nextLine(); lineScanner.nextLine();

		Graph g = new Graph();	//Create Graph
		HashMap<Integer, ArrayList<Integer>> graphMap = new HashMap<Integer, ArrayList<Integer>>();
		
		while (lineScanner.hasNextLine()) {
			String[] line = lineScanner.nextLine().split("\t");

			int from = converter.get(Integer.parseInt(line[0]));
			int to   = converter.get(Integer.parseInt(line[1]));
			
			if (from == to) { continue; }

			if (!graphMap.containsKey(from)) { graphMap.put(from, new ArrayList<Integer>()); }
			graphMap.get(from).add(to);
			
		}
		
		lineScanner.close();

		for (int i = 0; i < graphMap.size(); i++) {
			Vertex v = new Vertex(i);
			v.adjList = graphMap.get(i);
			g.vertList.add(v);
		}
		
		return g;
	}
}
