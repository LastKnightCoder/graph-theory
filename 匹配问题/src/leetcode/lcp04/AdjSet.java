package leetcode.lcp04;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.TreeSet;

public class AdjSet {
    private int E;
    private int V;
    private TreeSet<Integer>[] adj;



    public void validateVertex(int v) {
        if (v < 0 || v >= this.V) {
            throw new IllegalArgumentException("Vertex " + v + " is invalid");
        }
    }
    public int V() {
        return this.V;
    }


    public int E() {
        return this.E;
    }


    public Iterable<Integer> adj(int v) {
        validateVertex(v);
        return adj[v];
    }

    public boolean hasEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        return this.adj[v].contains(w);
    }

    public int degree(int v) {
        validateVertex(v);
        return this.adj[v].size();
    }
}

