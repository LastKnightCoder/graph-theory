import java.util.Arrays;

public class Floyed {
    private WeightedGraph graph;
    private int[][] dis;

    public Floyed(WeightedGraph graph) {
        this.graph = graph;
        this.dis = new int[graph.V()][graph.V()];

        for (int i = 0; i < dis.length; i++) {
            Arrays.fill(dis[i], Integer.MAX_VALUE);
        }

        for (int v = 0; v < graph.V(); v++) {
            dis[v][v] = 0;
            for (int w: graph.adj(v)) {
                dis[v][w] = graph.getWeight(v, w);
            }
        }

        for (int t = 0; t < graph.V(); t++) {
            for(int v = 0; v < graph.V(); v++) {
                for (int w = 0; w < graph.V(); w++) {
                    if (!(dis[v][t] == Integer.MAX_VALUE || dis[t][w] == Integer.MAX_VALUE)) {
                        dis[v][w] = Math.min(dis[v][w], dis[v][t] + dis[t][w]);
                    }

                }
            }
        }
    }

    public static void main(String[] args) {
        WeightedGraph graph = new WeightedGraph("g13.txt");
        Floyed floyed = new Floyed(graph);
        for (int i = 0; i < graph.V(); i++) {
            for (int j = 0; j < graph.V(); j++) {
                System.out.print(floyed.dis[i][j] + " ");
            }
            System.out.println();
        }
    }
}
