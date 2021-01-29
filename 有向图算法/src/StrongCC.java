import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.TreeSet;

public class StrongCC {
    private DirectedGraph graph;
    private int[] visited;
    private int scccount;

    public StrongCC(DirectedGraph graph) {
        this.graph = graph;
        this.visited = new int[graph.V()];
        Arrays.fill(visited, -1);
        this.scccount = 0;

        TreeSet<Integer>[] reverse = new TreeSet[graph.V()];
        for (int i = 0; i < graph.V(); i++) {
            reverse[i] = new TreeSet<>();
        }
        for (int v = 0; v < graph.V(); v++) {
            for (int w: graph.adj(v)) {
                reverse[w].add(v);
            }
        }
        DFSDirectedGraph dfsDirectedGraph = new DFSDirectedGraph(new DirectedGraph(reverse));

        ArrayList<Integer> res = dfsDirectedGraph.post();
        Collections.reverse(res);

        for(int v: res) {
            if (visited[v] == -1) {
                dfs(v, scccount);
                scccount++;
            }
        }
    }

    private void dfs(int v, int id) {
        visited[v] = id;
        for (int w: graph.adj(v)) {
            if (visited[w] == -1) {
                dfs(w, id);
            }
        }
    }

    public ArrayList<Integer>[] components() {
        ArrayList<Integer>[] results = new ArrayList[scccount];

        for (int i = 0; i< scccount; i++) {
            results[i] = new ArrayList<>();
        }

        for (int v = 0; v < graph.V(); v++) {
            results[visited[v]].add(v);
        }
        return results;
    }

    public static void main(String[] args) {
        DirectedGraph graph = new DirectedGraph("g15.txt");
        StrongCC strongCC = new StrongCC(graph);
        for (int i = 0; i < strongCC.components().length; i++) {
            System.out.println(strongCC.components()[i]);
        }
    }
}
