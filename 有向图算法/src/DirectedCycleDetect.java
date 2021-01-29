public class DirectedCycleDetect {
    private DirectedGraph graph;
    private boolean[] visited;
    private boolean[] onPath;
    private boolean hasCycle;

    public DirectedCycleDetect(DirectedGraph graph) {
        this.graph = graph;
        this.visited = new boolean[graph.V()];
        this.onPath = new boolean[graph.V()];
        this.hasCycle = false;

        for (int v = 0; v < graph.V(); v++) {
            if (!visited[v]) {
                if (dfs(v)) {
                    hasCycle = true;
                    break;
                }
            }
        }
    }

    private boolean dfs(int v) {
        visited[v] = true;
        onPath[v] = true;
        for (int w: graph.adj(v)) {
            if (!visited[w]) {
                if (dfs(w)) {
                    return true;
                }
            } else if (onPath[w]) {
                return true;
            }
        }
        onPath[v] = false;
        return false;
    }

    public boolean isHasCycle() {
        return hasCycle;
    }

    public static void main(String[] args) {
        DirectedGraph graph = new DirectedGraph("g15.txt");
        DirectedCycleDetect directedCycleDetect = new DirectedCycleDetect(graph);
        System.out.println(directedCycleDetect.isHasCycle());
    }
}
