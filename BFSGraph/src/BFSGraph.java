import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BFSGraph {

    private Graph graph;
    private boolean[] visited;
    private ArrayList<Integer> order;

    public BFSGraph(Graph graph) {
        this.graph = graph;
        visited = new boolean[graph.V()];
        order = new ArrayList<>(graph.V());

        for (int i = 0; i < graph.V(); i++) {
            if (!visited[i]) {
                bfs(i);
            }
        }
    }

    private void bfs(int s) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(s);
        visited[s] = true;
        while (!queue.isEmpty()) {
            int v = queue.remove();
            order.add(v);
            for (int w: graph.adj(v)) {
                if (!visited[w]) {
                    queue.add(w);
                    visited[w] = true;
                }
            }
        }
    }

    public static void main(String[] args) {
        Graph graph = new AdjSet("g4.txt");
        BFSGraph bfsGraph = new BFSGraph(graph);
        System.out.println(bfsGraph.order);
    }
}
