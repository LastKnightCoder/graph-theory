import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class BellmanFord {
    private int[] dis;
    private WeightedGraph graph;
    private int s;
    private boolean hasNegativeCycle = false;
    private int[] pre;

    public BellmanFord(WeightedGraph graph, int s) {
        this.graph = graph;
        graph.validateVertex(s);
        this.s = s;
        this.dis = new int[graph.V()];
        this.pre = new int[graph.V()];

        Arrays.fill(dis, Integer.MAX_VALUE);
        Arrays.fill(pre, -1);
        dis[s] = 0;
        pre[s] = s;

        for (int k = 1; k < graph.V(); k++) {
            for (int v = 0; v < graph.V(); v++) {
                for (int w: graph.adj(v)) {
                    if (dis[v] != Integer.MAX_VALUE
                            && dis[v] + graph.getWeight(v, w) < dis[w]) {
                        dis[w] = dis[v] + graph.getWeight(v, w);
                        pre[w] = v;
                    }
                }
            }
        }

        for (int v = 0; v < graph.V(); v++) {
            for (int w: graph.adj(v)) {
                if (dis[v] != Integer.MAX_VALUE
                        && (dis[v] + graph.getWeight(v, w) < dis[w])) {
                    hasNegativeCycle = true;
                }
            }
        }
    }

    public int[] result() {
        return dis;
    }

    public Iterable<Integer> path(int t) {
        ArrayList<Integer> path = new ArrayList<>();
        int cur = t;
        while (cur != s) {
            path.add(cur);
            cur = pre[cur];
        }
        path.add(s);

        Collections.reverse(path);
        return path;
    }

    public static void main(String[] args) {
        WeightedGraph graph = new WeightedGraph("g13.txt");
        BellmanFord bellmanFord = new BellmanFord(graph, 0);
        if (!bellmanFord.hasNegativeCycle) {
            int[] result = bellmanFord.result();
            for (int i = 0; i < result.length; i++) {
                System.out.println(String.format("%d-%d: %d", 0, i, result[i]));
            }
            System.out.println(bellmanFord.path(3));
        } else {
            System.out.println("Graph has negative cycle");
        }

    }
}
