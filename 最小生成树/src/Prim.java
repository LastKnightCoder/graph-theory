import java.util.ArrayList;
import java.util.PriorityQueue;

public class Prim {
    private WeightedGraph graph;
    private boolean[] visited;
    private ArrayList<WeightedEdge> result = new ArrayList<>();

    public Prim(WeightedGraph graph) {
        this.graph = graph;
        PriorityQueue<WeightedEdge> priorityQueue = new PriorityQueue<>((w1, w2) -> w1.getWeight() - w2.getWeight());
        this.visited = new boolean[graph.V()];
        visited[0] = true;
        for (int w: graph.adj(0)) {
            priorityQueue.add(new WeightedEdge(0, w, graph.getWeight(0, w)));
        }

        while (!priorityQueue.isEmpty()) {
            WeightedEdge edge = priorityQueue.remove();
            int v = edge.getV();
            int w = edge.getW();
            if (visited[v] && visited[w]) {
                continue;
            }
            result.add(edge);
            visited[w] = true;
            for (int next: graph.adj(w)) {
                priorityQueue.add(new WeightedEdge(w, next, graph.getWeight(w, next)));
            }
        }

    }

    public Iterable<WeightedEdge> result() {
        return result;
    }

    public static void main(String[] args) {
        WeightedGraph graph = new WeightedGraph("g12.txt");
        Prim prim = new Prim(graph);
        System.out.println(prim.result);
    }
}
