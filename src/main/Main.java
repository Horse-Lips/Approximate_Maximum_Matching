package main;

import graphComponents.*;
import graphUtils.*;
import java.io.IOException;


public class Main {
    
    public static void main(String[] args) throws IOException {
        System.out.println("Hello");

        Graph g = General.fromSNAPFile("Graphs/3KAS.txt");
        
        g.degreeReduction();
    }

}
