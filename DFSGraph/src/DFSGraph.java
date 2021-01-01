import java.util.ArrayList;
import java.util.Arrays;

@SuppressWarnings("all")
public class DFSGraph {
    private Graph graph;
    private ArrayList<Integer> preorder;
    private ArrayList<Integer> postorder;
    private boolean[] visited;

    public DFSGraph(Graph graph) {
        this.graph = graph;
        preorder = new ArrayList<>(graph.V());
        postorder = new ArrayList<>(graph.V());
        visited = new boolean[graph.V()];
        Arrays.fill(visited, false);
    }

    public void dfs() {
        for (int i = 0; i < visited.length; i++) {
            if (!visited[i]) {
                dfs(i);
            }
        }
    }

    private void dfs(int v) {
        visited[v] = true;
        preorder.add(v);
        for (int w: graph.adj(v)) {
            if (!visited[w]) {
                dfs(w);
            }
        }
        postorder.add(v);
    }

    public Iterable<Integer> preorder() {
        return preorder;
    }

    public Iterable<Integer> postorder() {
        return postorder;
    }

    public static void main(String[] args) {
        Graph graph = new AdjSet("g.txt");
        DFSGraph dfsGraph = new DFSGraph(graph);
        dfsGraph.dfs();
        System.out.println(dfsGraph.postorder());
    }
}
