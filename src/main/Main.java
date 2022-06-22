package main;

import graphComponents.*;
import graphUtils.*;
import java.io.IOException;


public class Main {
    
    public static void main(String[] args) throws IOException {
        Graph g = General.fromSNAPFile("Graphs/200KGowalla.txt");
        
		g.starReduction();
        g.degreeReduction();
		
    }

}
