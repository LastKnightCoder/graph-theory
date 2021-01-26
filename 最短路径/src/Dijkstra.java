import javax.xml.xpath.XPath;
import java.util.*;

public class Dijkstra {
    private WeightedGraph graph;
    private int s;
    private int[] dis;
    private boolean[] visited;
    private int[] pre;

    public Dijkstra(WeightedGraph graph, int s) {
        this.graph = graph;
        graph.validateVertex(s);
        this.s = s;

        this.dis = new int[graph.V()];
        this.visited = new boolean[graph.V()];
        this.pre = new int[graph.V()];

        Arrays.fill(pre, -1);
        Arrays.fill(dis, Integer.MAX_VALUE);

        pre[s] = s;
        dis[s] = 0;

        PriorityQueue<Node> queue =
                new PriorityQueue<>((node1, node2) -> node1.getDis() - node2.getDis());
        queue.add(new Node(s, 0));
        while (!queue.isEmpty()) {

            int v = queue.remove().getV();

            if (visited[v]) {
                continue;
            }
            visited[v] = true;

            for (int w: graph.adj(v)) {
                if (!visited[w]) {
                    dis[w] = Math.min(dis[w], dis[v] + graph.getWeight(v, w));
                    queue.add(new Node(w, dis[w]));
                    pre[w] = v;
                }
            }
        }
    }

    public int[] result() {
        return dis;
    }

    public Iterable<Integer> path(int t) {
        ArrayList<Integer> path = new ArrayList<>();
        if (!visited[t]) {
            return path;
        }
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
        Dijkstra dijkstra = new Dijkstra(graph, 0);
        int[] result = dijkstra.result();
        for (int i = 0; i < result.length; i++) {
            System.out.println(String.format("%d-%d: %d", 0, i, result[i]));
        }
        System.out.println(dijkstra.path(3));
    }
}
