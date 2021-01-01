import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@SuppressWarnings("all")
public class SingleSourcePath {
    private Graph graph;
    private boolean[] visited;
    private int[] pre;
    // 从节点 s 开始遍历
    private int s;

    public SingleSourcePath(Graph graph, int s) {
        this.graph = graph;
        this.s = s;

        visited = new boolean[graph.V()];
        Arrays.fill(visited, false);

        pre = new int[graph.V()];
        Arrays.fill(pre, -1);

        // 默认认为源的前一个节点为自己，这个值访问不到，可以是任何值
        dfs(s, s);
    }

    private void dfs(int v, int parent) {
        visited[v] = true;
        pre[v] = parent;
        for (int w: graph.adj(v)) {
            if (!visited[w]) {
                dfs(w, v);
            }
        }
    }

    public boolean isConnected(int t) {
        // 如果 pre[t] != -1，说明遍历到了该节点，则该节点与源节点是联通的
        return pre[t] != -1;
    }

    public Iterable<Integer> path(int t) {
        ArrayList<Integer> path = new ArrayList<>();
        // 如果不是联通的，直接返回
        if (!isConnected(t)) return path;

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
        Graph graph = new AdjSet("g4.txt");
        SingleSourcePath singleSourcePath = new SingleSourcePath(graph, 0);
        System.out.println("0 -> 6: " + singleSourcePath.path(6));
    }
}
