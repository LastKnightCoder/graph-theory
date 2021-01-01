import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Path {
    private Graph graph;
    private boolean[] visited;
    private int[] pre;

    private int s;
    private int t;

    public Path(Graph graph, int s, int t) {
        this.graph = graph;
        this.s = s;
        this.t = t;

        visited = new boolean[graph.V()];
        Arrays.fill(visited, false);

        pre = new int[graph.V()];
        Arrays.fill(pre, -1);

        dfs(s, s);
    }

    private boolean dfs(int v, int parent) {
        visited[v] = true;
        pre[v] = parent;
        // 如果已经找到，直接返回，不再继续遍历
        if (v == t) {
            return true;
        }
        for (int w: graph.adj(v)) {
            if (!visited[w]) {
                // 如果在后代(相邻节点)的深度优先遍历中找到，不在继续遍历，提前返回
                if (dfs(w, v)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isConnected() {
        // 如果 pre[t] != -1，说明遍历到了该节点，则该节点与源节点是联通的
        return pre[t] != -1;
    }

    public Iterable<Integer> path() {
        ArrayList<Integer> path = new ArrayList<>();
        if (!isConnected()) {
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
        Graph graph = new AdjSet("g.txt");
        Path path = new Path(graph, 0, 6);
        System.out.println(Arrays.toString(path.visited));
        System.out.println(path.path());
    }
}
