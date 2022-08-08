package partition;


import graphComponents.*;
import java.util.*;
import java.io.*;


public class Partition {
	
	public static ArrayList<Graph> partition(Graph g, int partitionSize) throws IOException {
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

		while ((procLine = procLines.readLine()) != null) { System.out.println(procLine); }

		return null;
	}
}
