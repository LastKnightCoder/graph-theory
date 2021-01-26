import java.util.ArrayList;
import java.util.Collections;

public class HamiltonCycle {
    private Graph graph;
    private boolean[] visited;
    private int left;
    private int[] pre;
    private int end;
    private FindEdge findEdge;

    public HamiltonCycle(Graph graph) {
        this.graph = graph;
        this.findEdge = new FindEdge(graph);
        this.pre = new int[graph.V()];
        this.end = -1;
        if (findEdge.getResult().size() != 0) {
            return;
        }
        this.visited = new boolean[graph.V()];
        this.left = graph.V();


        pre[0] = 0;
        dfs(0);
    }

    private boolean dfs(int v) {
        visited[v] = true;

        left--;

        if (left == 0 && graph.hasEdge(v, 0)) {
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
        Graph graph = new AdjSet("g8.txt");
        HamiltonCycle hamiltonCycle = new HamiltonCycle(graph);
        for (int w: hamiltonCycle.result()) {
            System.out.print(w + " ");
        }
    }
}
