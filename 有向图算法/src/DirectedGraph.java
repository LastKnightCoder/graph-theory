import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.TreeSet;

public class DirectedGraph {
    private int E;
    private int V;
    private TreeSet<Integer>[] adj;

    private int[] inDegree;
    private int[] outDegree;

    public DirectedGraph(TreeSet<Integer>[] adj) {
        this.adj = adj;
        this.V = adj.length;

        this.E = 0;
        inDegree = new int[V];
        outDegree = new int[V];

        for (int v = 0; v < V; v++) {
            for (int w: adj(v)) {
                outDegree[v]++;
                inDegree[w]++;
                E++;
            }
        }
    }

    public DirectedGraph(String filename) {
        File file = new File(filename);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
            this.V = scanner.nextInt();
            if (this.V < 0) {
                throw new IllegalArgumentException("V Must Be Positive");
            }

            this.adj = new TreeSet[this.V];
            for (int i = 0; i < this.V; i++) {
                this.adj[i] = new TreeSet<>();
            }

            this.E = scanner.nextInt();
            if (this.E < 0) {
                throw new IllegalArgumentException("E Must Be Positive");
            }

            this.inDegree = new int[V];
            this.outDegree = new int[V];

            for (int i = 0; i < this.E; i++) {
                int a = scanner.nextInt();
                validateVertex(a);
                int b = scanner.nextInt();
                validateVertex(b);

                if (a == b) {
                    throw new IllegalArgumentException("Self loop exists");
                }
                if (adj[a].contains(b)) {
                    throw new IllegalArgumentException("Parallel edge exists");
                }

                adj[a].add(b);
                inDegree[b]++;
                outDegree[a]++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            assert scanner != null;
            scanner.close();
        }
    }

    private void validateVertex(int v) {
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


    public int inDegree(int v) {
        validateVertex(v);
        return inDegree[v];
    }

    public int outDegree(int v) {
        validateVertex(v);
        return outDegree[v];
    }

    public void removeEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        adj[v].remove(w);
        outDegree[v]--;
        inDegree[w]--;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("V: %d, E: %d\n", this.V, this.E));
        for (int v = 0; v < this.V; v++) {
            stringBuilder.append(String.format("%d : ", v));
            for (int w: adj(v)) {
                stringBuilder.append(String.format("%d ", w));
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        DirectedGraph graph = new DirectedGraph("g15.txt");
        System.out.println(graph);
    }
}

