import java.util.ArrayList;
import java.util.Collections;

public class Kruskal {
    private WeightedGraph graph;
    public ArrayList<WeightedEdge> result = new ArrayList<>();

    public Kruskal(WeightedGraph graph) {
        this.graph = graph;
        int V = graph.V();

        ArrayList<WeightedEdge> edges = new ArrayList<>();
        for (int v = 0; v < V; v++) {
            for (int w: graph.adj(v)) {
                if (v < w) {
                    edges.add(new WeightedEdge(v, w, graph.getWeight(v, w)));
                }
            }
        }

        Collections.sort(edges, (w1, w2) -> w1.getWeight() - w2.getWeight());
        UnionFind unionFind = new UnionFind(V);
        for (WeightedEdge edge: edges) {
            int v = edge.getV();
            int w = edge.getW();
            if (!unionFind.isConnected(v, w)) {
                result.add(edge);
                unionFind.unionElements(v, w);
            }
        }
    }

    public Iterable<WeightedEdge> result() {
        return result;
    }

    public static void main(String[] args) {
        WeightedGraph graph = new WeightedGraph("g12.txt");
        Kruskal kruskal = new Kruskal(graph);
        System.out.println(kruskal.result());
    }
}
