import java.util.ArrayList;
import java.util.HashSet;

public class FindEdge {
    private int[] order;
    private int[] low;
    private Graph graph;
    private boolean[] visited;
    private int count = 0;

    private ArrayList<int[]> result;

    public FindEdge(Graph graph) {
        this.graph = graph;
        order = new int[graph.V()];
        low = new int[graph.V()];
        result = new ArrayList<>();
        visited = new boolean[graph.V()];

        for (int i = 0; i < graph.V(); i++) {
            if (!visited[i]) {
                dfs(i, i);
            }
        }
    }

    private void dfs(int v, int parent) {
        visited[v] = true;
        low[v] = count;
        order[v] = count;
        count++;

        for (int w: graph.adj(v)) {
            if (!visited[w]) {
                dfs(w, v);
                low[v] = Math.min(low[v], low[w]);
                if (low[w] > order[v]) {
                    result.add(new int[]{v, w});
                }
            } else if (w != parent){
                low[v] = Math.min(low[w], low[v]);
            }
        }
    }

    public ArrayList<int[]> getResult() {
        return result;
    }

    public static void main(String[] args) {
        Graph graph = new AdjSet("tree.txt");
        FindEdge findEdge = new FindEdge(graph);
        ArrayList<int[]> result = findEdge.getResult();

        for (int[] res: result) {
            System.out.println(res[0] + "-" + res[1]);
        }
    }
}
