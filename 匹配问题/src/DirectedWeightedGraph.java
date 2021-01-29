import java.util.HashMap;

public class DirectedWeightedGraph{
    private int V;
    private int E;
    private HashMap<Integer, Integer>[] adj;

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
}
