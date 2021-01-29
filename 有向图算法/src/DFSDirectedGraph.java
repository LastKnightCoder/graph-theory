import java.util.ArrayList;

public class DFSDirectedGraph {
    private DirectedGraph graph;
    private ArrayList<Integer> pre;
    private ArrayList<Integer> post;
    private boolean[] visited;

    public DFSDirectedGraph(DirectedGraph graph) {
        this.graph = graph;
        this.pre = new ArrayList<>();
        this.post = new ArrayList<>();
        this.visited = new boolean[graph.V()];

        for (int v = 0; v < graph.V(); v++) {
            if (!visited[v]) {
                dfs(v);
            }
        }
    }

    private void dfs(int v) {
        visited[v] = true;
        pre.add(v);
        for (int w: graph.adj(v)) {
            if (!visited[w]) {
                dfs(w);
            }
        }
        post.add(v);
    }

    public ArrayList<Integer> pre() {
        return pre;
    }

    public ArrayList<Integer> post() {
        return post;
    }
}
