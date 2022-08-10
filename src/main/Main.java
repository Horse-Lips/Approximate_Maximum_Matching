package main;


import graphComponents.*;
import graphUtils.*;
import matchingAlgs.*;
import reductions.*;
import java.io.IOException;
import java.util.*;
import partition.*;


public class Main {
    
    public static void main(String[] args) throws IOException {
        Graph g1 = General.fromSNAPFile("Graphs/60KBrightKite.txt");
		
		Partition.partition(g1, 500);
		/*Graph g2 = General.fromSNAPFile("Graphs/3KAS.txt");
		Matching.maxMatch(g1, new HashMap<Integer, ArrayList<Integer>>());

		StarReduction.reduce(g2);
		HashMap<Integer, ArrayList<Integer>> groupings = DegreeReduction.reduce(g2);

		Matching.maxMatch(g2, groupings);

		//Matching.printMatching(g);
		System.out.println("Unreduced matching size: " + Matching.matchingSize(g1));
		System.out.println("Reduced matching size: " + Matching.matchingSize(g2));*/
	}

}
