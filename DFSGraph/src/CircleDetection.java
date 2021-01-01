import java.util.Arrays;

public class CircleDetection {
    private Graph graph;
    private boolean[] visited;
    private boolean hasCircle;

    public CircleDetection(Graph graph) {
        this.graph = graph;
        visited = new boolean[graph.V()];
        Arrays.fill(visited, false);
        hasCircle = false;
    }

    public void dfs() {
        for (int i = 0; i < visited.length; i++) {
            if (!visited[i]) {
                if (dfs(i, i)) {
                    hasCircle = true;
                    // 不用查找其他联通分量了
                    break;
                }
            }
        }
    }

    private boolean dfs(int v, int parent) {
        visited[v] = true;
        for (int w: graph.adj(v)) {
            if (!visited[w]) {
                if (dfs(w, v)) {
                    // 如果已经在某相邻节点中找到环，不用再遍历其他的相邻节点
                    return true;
                }
            } else if (w != parent){
                // 如果已经找到环，不用继续遍历
                return true;
            }
        }
        return false;
    }

    public boolean hasCircle() {
        return this.hasCircle;
    }

    public static void main(String[] args) {
        Graph graph = new AdjSet("g.txt");
        CircleDetection circleDetection = new CircleDetection(graph);
        circleDetection.dfs();
        System.out.println(circleDetection.hasCircle());

        Graph graph2 = new AdjSet("g2.txt");
        CircleDetection circleDetection2 = new CircleDetection(graph2);
        circleDetection2.dfs();
        System.out.println(circleDetection2.hasCircle());
    }
}
