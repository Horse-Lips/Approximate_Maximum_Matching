package partition;


import graphComponents.*;
import java.util.*;
import java.io.*;


public class Partition {
	public static ArrayList<Graph> partition(Graph g, int partitionSize) throws IOException {
		ArrayList<Graph> graphList = new ArrayList<Graph>();
		int largest = g.size();
		graphList.add(g);

		while (largest > partitionSize) {
			System.out.println(largest);
			ArrayList<Graph> newList = new ArrayList<Graph>();

			for (Graph currGraph: graphList) {
				if (currGraph.size() > partitionSize) {
					newList.addAll(split(currGraph));

				} else {
					newList.add(currGraph);

				}
			}

			largest = 0;
			for (Graph currGraph: newList) { if (currGraph.size() > largest) { largest = currGraph.size(); } }
			graphList = newList;
		}
		
		return graphList;
	}


	public static ArrayList<Graph> split(Graph g) throws IOException {
		for (Vertex v: g.vertList) { v.visited = false; }

		String graphString = "";

		for (Vertex v: g.vertList) {
			if (v.visited) { continue; }
			v.visited = true;

			for (int wInd: v.adjList) {
				Vertex w = g.get(wInd);

				if (w.visited) { continue; }

				graphString = graphString + v.id + "," + w.id + "\n";
			}
		}

		File tempFile = File.createTempFile("tempGraph-", ".graph");
		tempFile.deleteOnExit();

		FileWriter tempWriter = new FileWriter(tempFile);
		tempWriter.write(graphString.substring(0, graphString.length() - 1));
		tempWriter.close();

		Process ltProc = Runtime.getRuntime().exec("./LT/lt " + tempFile.getCanonicalPath());
		
		BufferedReader procLines = new BufferedReader(new InputStreamReader(ltProc.getInputStream()));

		String procLine = "";
		ArrayList<String> allLines = new ArrayList<String>();

		while ((procLine = procLines.readLine()) != null) { allLines.add(procLine); }

		System.out.println(allLines);

		ArrayList<Integer> A = new ArrayList<Integer>();
		ArrayList<Integer> B = new ArrayList<Integer>();

		for (String c: allLines.get(allLines.size() - 5).substring(5).split(" ")) { if (c != "") { A.add(Integer.parseInt(c)); } }
		for (String c: allLines.get(allLines.size() - 4).substring(5).split(" ")) { if (c != "") { B.add(Integer.parseInt(c)); } }
		for (String c: allLines.get(allLines.size() - 3).substring(5).split(" ")) { if (c != "") { A.add(Integer.parseInt(c)); B.add(Integer.parseInt(c)); } }

		System.out.println(A);
		System.out.println(B);

		ArrayList<Graph> newGraphs = new ArrayList<Graph>();

		newGraphs.add(newGraph(g, A));
		newGraphs.add(newGraph(g, B));
		
		return newGraphs;
	}

	public static Graph newGraph(Graph g, ArrayList<Integer> vertSet) {
		Graph g1 = new Graph();
		HashMap<Integer, Integer> converter = new HashMap<Integer, Integer>();

		for (int i = 0; i < vertSet.size(); i++) { converter.put(vertSet.get(i), i); }

		for (int i = 0; i < vertSet.size(); i++) {
			Vertex vNew = new Vertex(i);
			
			for (int adj: g.get(vertSet.get(i)).adjList) {
				if (vertSet.contains(adj)) { vNew.adjList.add(converter.get(adj)); }
			}

			g1.add(vNew);		
		}

		return g1;
	}
}

