import java.util.Arrays;

public class BinaryPartitionDetection {
    private Graph graph;
    private boolean[] visited;
    // 以 0 1 表示不同颜色
    private int[] colors;

    private boolean isBipartite;

    public BinaryPartitionDetection(Graph graph) {
        this.graph = graph;
        visited = new boolean[graph.V()];
        Arrays.fill(visited, false);
        // 初始化 colors 为 -1，表示未染色
        colors = new int[graph.V()];
        Arrays.fill(colors, -1);

        isBipartite = true;
        for (int i = 0; i < visited.length; i++) {
            if (!visited[i]) {
                if (!dfs(i, 0)) {
                    isBipartite = false;
                    break;
                }
            }
        }
    }

    private boolean dfs(int v, int color) {
        visited[v] = true;
        colors[v] = color;
        for (int w: graph.adj(v)) {
            if (!visited[w]) {
                if (!dfs(w, 1 - color)) {
                    return false;
                }
            } else if (colors[v] == colors[w]) {
                System.out.println(String.format("v: %s, w: %s", v, w));
                return false;
            }
        }
        return true;
    }

    public boolean isBipartite() {
        return isBipartite;
    }

    public int[] colors() {
        return colors;
    }
}
