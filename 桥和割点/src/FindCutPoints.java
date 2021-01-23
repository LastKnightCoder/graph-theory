import java.util.ArrayList;
import java.util.HashSet;

public class FindCutPoints {
    private Graph graph;
    private boolean[] visited;
    private int[] order;
    private int[] low;
    private int count = 0;

    private HashSet<Integer> result = new HashSet<>();

    public FindCutPoints(Graph graph) {
        this.graph = graph;

        visited = new boolean[graph.V()];
        order = new int[graph.V()];
        low = new int[graph.V()];

        for (int i = 0; i < graph.V(); i++) {
            if (!visited[i]) {
                dfs(i, i);
            }
        }
    }

    private void dfs(int v, int parent) {
        visited[v] = true;
        order[v] = count;
        low[v] = count;
        count++;

        int child = 0;
        for (int w: graph.adj(v)) {
            child++;
            if (!visited[w]) {
                dfs(w, v);
                low[v] = Math.min(low[v], low[w]);
                if (v != parent && low[w] >= order[v]) {
                    result.add(v);
                }
                if (v == parent && child > 1) {
                    result.add(v);
                }
            } else if (w != parent) {
                low[v] = Math.min(low[v], low[w]);
            }
        }
    }

    public HashSet<Integer> getResult() {
        return result;
    }

    public static void main(String[] args) {
        Graph graph = new AdjSet("tree.txt");
        FindCutPoints findCutPoints = new FindCutPoints(graph);
        HashSet<Integer> result = findCutPoints.getResult();

        for (int res: result) {
            System.out.println(res);
        }
    }
}
