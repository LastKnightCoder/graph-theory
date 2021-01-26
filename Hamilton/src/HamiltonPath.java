import java.util.ArrayList;
import java.util.Collections;

public class HamiltonPath {
    private Graph graph;
    private boolean[] visited;
    private int left;
    private int[] pre;
    private int end;
    private int s;

    public HamiltonPath(Graph graph, int s) {
        this.graph = graph;
        this.s = s;
        this.visited = new boolean[graph.V()];
        this.left = graph.V();
        this.pre = new int[graph.V()];
        this.end = -1;

        pre[0] = 0;
        dfs(s);
    }

    private boolean dfs(int v) {
        visited[v] = true;

        left--;

        if (left == 0) {
            end = v;
            return true;
        }

        for (int w: graph.adj(v)) {
            if (!visited[w]) {
                pre[w] = v;
                if (dfs(w)) return true;
            }
        }
        visited[v] = false;
        left++;
        return false;
    }

    public Iterable<Integer> result() {
        ArrayList result = new ArrayList();

        if (end == -1) {
            return result;
        }

        int cur = end;
        while (cur != 0) {
            result.add(cur);
            cur = pre[cur];
        }
        result.add(0);

        Collections.reverse(result);
        return result;
    }

    public static void main(String[] args) {
        Graph graph = new AdjSet("g9.txt");

        HamiltonPath hamiltonPath = new HamiltonPath(graph, 0);
        System.out.println(hamiltonPath.result());
    }
}
