import java.util.*;

public class SingleSourcePath {
    private Graph graph;
    private int s;

    private boolean[] visited;
    private int[] pre;
    private int[] dis;

    public SingleSourcePath(Graph graph, int s) {
        this.graph = graph;
        this.s = s;

        visited = new boolean[graph.V()];
        pre = new int[graph.V()];
        dis = new int[graph.V()];

        Arrays.fill(visited, false);
        Arrays.fill(pre, -1);
        Arrays.fill(dis, -1);

        bfs(s);
    }

    private void bfs(int s) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(s);
        visited[s] = true;
        pre[s] = s;
        dis[s] = 0;

        while (!queue.isEmpty()) {
            int v = queue.remove();
            for (int w: graph.adj(v)) {
                if (!visited[w]) {
                    queue.add(w);
                    visited[w] = true;
                    pre[w] = v;
                    dis[w] = dis[v] + 1;
                }
            }
        }
    }

    private void validate(int v) {
        if (v < 0 || v >= graph.V()) {
            throw new IllegalArgumentException("v is out of index");
        }
    }

    public int dis(int t) {
        validate(t);
        return dis[t];
    }

    public boolean isConnected(int t) {
        validate(t);
        return visited[t];
    }

    public ArrayList<Integer> path(int t) {
        ArrayList<Integer> res = new ArrayList<>();
        if (!isConnected(t)) {
            return res;
        }

        int cur = t;
        while (cur != s) {
            res.add(cur);
            cur = pre[cur];
        }
        res.add(s);

        Collections.reverse(res);
        return res;
    }

    public static void main(String[] args) {
        Graph graph = new AdjSet("g4.txt");
        SingleSourcePath singleSourcePath = new SingleSourcePath(graph, 0);
        // 0 -> 6: [0, 2, 6]
        System.out.println("0 -> 6: " + singleSourcePath.path(6));
        System.out.println("0 -> 6 distance: " + singleSourcePath.dis(6));
    }
}
