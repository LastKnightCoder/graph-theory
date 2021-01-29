import java.util.Arrays;

public class HungarianDFS {
    private Graph graph;
    private int maxMatching;
    private boolean[] visited;
    private int[] matching;

    public HungarianDFS(Graph graph) {
        this.graph = graph;
        BinaryPartitionDetection bmp = new BinaryPartitionDetection(graph);
        if (!bmp.isBipartite()) {
            throw new IllegalArgumentException("该图不是二分图");
        }
        int[] colors = bmp.colors();
        this.maxMatching = 0;
        this.visited = new boolean[graph.V()];
        this.matching = new int[graph.V()];
        Arrays.fill(matching, -1);

        for (int v = 0; v < graph.V(); v++) {
            if (colors[v] == 0 && matching[v] == -1) {
                Arrays.fill(visited, false);
                if (dfs(v)) {
                    maxMatching++;
                }
            }
        }
    }

    private boolean dfs(int v) {
        visited[v] = true;

        for (int w: graph.adj(v)) {
            if (!visited[w]) {
                visited[w] = true;
                if (matching[w] == -1 || dfs(matching[w])) {
                    matching[v] = w;
                    matching[w] = v;
                    return true;
                }
            }
        }

        return false;
    }

    public int result() {
        return maxMatching;
    }

    public static void main(String[] args) {
        Graph graph = new AdjSet("g18.txt");
        HungarianDFS hungarianDFS = new HungarianDFS(graph);
        System.out.println(hungarianDFS.result());
    }
}
