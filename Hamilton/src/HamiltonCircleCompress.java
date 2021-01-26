import java.util.ArrayList;
import java.util.Collections;

public class HamiltonCircleCompress {
    private Graph graph;
    private int visited;
    private int left;
    private int[] pre;
    private int end;

    public HamiltonCircleCompress(Graph graph) {
        this.graph = graph;
        this.visited = 0;
        this.left = graph.V();
        this.pre = new int[graph.V()];
        this.end = -1;

        pre[0] = 0;
        dfs(0);
    }

    private boolean dfs(int v) {
        visited = visited ^ (1 << v);
        left--;

        if (left == 0 && graph.hasEdge(v, 0)) {
            end = v;
            return true;
        }

        for (int w: graph.adj(v)) {
            if ((visited & (1 << w)) == 0) {
                pre[w] = v;
                if (dfs(w)) return true;
            }
        }
        visited = visited ^ (1 << v);
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
        HamiltonCircleCompress hamiltonCircleCompress = new HamiltonCircleCompress(graph);
        for (int w: hamiltonCircleCompress.result()) {
            System.out.print(w + " ");
        }
    }
}
