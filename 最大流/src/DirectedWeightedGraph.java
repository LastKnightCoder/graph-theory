import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DirectedWeightedGraph{
    private int V;
    private int E;
    private HashMap<Integer, Integer>[] adj;

    public DirectedWeightedGraph(String path) {
        File file = new File(path);
        Scanner scanner = null;

        try {
            scanner = new Scanner(file);
            this.V = scanner.nextInt();
            if (V < 0) {
                throw new IllegalArgumentException("V can't be negative");
            }
            adj = new HashMap[V];
            for (int i = 0; i < V; i++) {
                adj[i] = new HashMap<>();
            }

            this.E = scanner.nextInt();
            if (E < 0) {
                throw new IllegalArgumentException("E can't be negative");
            }

            for (int i = 0; i < E; i++) {
                int v = scanner.nextInt();
                int w = scanner.nextInt();
                int weight = scanner.nextInt();

                if (v == w) {
                    throw new IllegalArgumentException("存在自环边");
                }
                if (adj[v].containsKey(w)) {
                    throw new IllegalArgumentException("存在平行边");
                }

                adj[v].put(w, weight);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            scanner.close();
        }
    }

    public DirectedWeightedGraph(int V) {
        this.V = V;
        this.E = 0;
        this.adj = new HashMap[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new HashMap<>();
        }
    }

    public int V() {
        return this.V;
    }

    public int E() {
        return this.E;
    }

    public int getWeight(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        if (!hasEdge(v, w)) {
            throw new IllegalArgumentException(String.format("%d-%d 不存在", v, w));
        }
        return adj[v].get(w);
    }

    public void setWeight(int v, int w, int weight) {
        validateVertex(v);
        validateVertex(w);
        if (!hasEdge(v, w)) {
            throw new IllegalArgumentException(String.format("%d-%d 不存在", v, w));
        }
        adj[v].put(w, weight);
    }

    public void addEdge(int v, int w, int weight) {
        validateVertex(v);
        validateVertex(w);
        if (v == w) {
            throw new IllegalArgumentException("存在自环边");
        }
        if (adj[v].containsKey(w)) {
            throw new IllegalArgumentException("存在平行边");
        }

        adj[v].put(w, weight);
        E++;
    }

    public boolean hasEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        return adj[v].containsKey(w);
    }

    public Iterable<Integer> adj(int v) {
        validateVertex(v);
        return adj[v].keySet();
    }

    public void validateVertex(int v) {
        if (v < 0 && v >= V) {
            throw new IllegalArgumentException("节点超过 [0, V) 的范围");
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("V: %d, E: %d\n", this.V, this.E));
        for (int v = 0; v < this.V; v++) {
            stringBuilder.append(String.format("%d : ", v));
            for (Map.Entry w: adj[v].entrySet()) {
                stringBuilder.append(String.format("(%d: %d) ", w.getKey(), w.getValue()));
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
