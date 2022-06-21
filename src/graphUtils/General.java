package graphUtils;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.HashMap;

import graphComponents.Graph;
import graphComponents.Vertex;


public class General {
	public static Graph fromSNAPFile(String filename) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filename));
		Scanner lineScanner = new Scanner(br);
				
		lineScanner.nextLine();	//Skip header
		lineScanner.nextLine();
		
		int graphSize = Integer.parseInt(lineScanner.nextLine().split("\t")[0].split(":")[1]);	//Get size of graph
		Graph newGraph = new Graph(graphSize);	//Create Graph
		
		lineScanner.nextLine();
		
		HashMap<Integer, Integer> converter = new HashMap<Integer, Integer>();
		
		int graphIndex = 0;
		int currentIndex;
		
		while (lineScanner.hasNextLine()) {
			currentIndex = Integer.parseInt(lineScanner.nextLine().split("\t")[0]);
			
			if (!converter.containsKey(currentIndex)) {
				converter.put(currentIndex, graphIndex);
				
				graphIndex++;
			}
			
		}
		
		lineScanner.close();
		
		br = new BufferedReader(new FileReader(filename));
		lineScanner = new Scanner(br);
		
		lineScanner.nextLine();
		lineScanner.nextLine();
		lineScanner.nextLine();
		lineScanner.nextLine();
		
		while (lineScanner.hasNextLine()) {
			String[] splitLine = lineScanner.nextLine().split("\t");
			
			int from = converter.get(Integer.parseInt(splitLine[0]));
			int to   = converter.get(Integer.parseInt(splitLine[1]));
			
			int weight = 1;
			
			if (from != to) {
                newGraph.getVertex(from).addToAdj(to, weight);
			}
			
		}
		
		lineScanner.close();
		
		return newGraph;
	}
}
