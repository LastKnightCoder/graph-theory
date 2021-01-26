import java.util.ArrayList;
import java.util.Arrays;

@SuppressWarnings("all")
public class CCGraph {
    private int cccount;
    private Graph graph;
    private boolean[] visited;
    private ArrayList<ArrayList<Integer>> lists;

    public CCGraph(Graph graph) {
        this.graph = graph;
        this.cccount = 0;
        visited = new boolean[graph.V()];
        Arrays.fill(visited, false);
        lists = new ArrayList<>();
        dfs();
    }

    public void dfs() {
        for (int i = 0; i < visited.length; i++) {
            if (!visited[i]) {
                cccount++;
                ArrayList<Integer> list = new ArrayList<>();
                lists.add(list);
                dfs(i, list);
            }
        }
    }

    private void dfs(int v, ArrayList<Integer> list) {
        visited[v] = true;
        list.add(v);
        for (int w: graph.adj(v)) {
            if (!visited[w]) {
                dfs(w, list);
            }
        }
    }

    public int getCCcount() {
        return cccount;
    }

    public ArrayList<ArrayList<Integer>> getCCList() {
        return lists;
    }

    public boolean isConnected(int v, int w) {
        for (int i = 0; i < cccount; i++) {
            ArrayList list = lists.get(i);
            if (list.contains(v) && list.contains(w)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Graph graph = new AdjSet("g.txt");
        CCGraph ccGraph = new CCGraph(graph);
        ccGraph.dfs();
        int cccount = ccGraph.getCCcount();
        System.out.println(cccount);

        ArrayList<ArrayList<Integer>> lists = ccGraph.getCCList();
        for (int i = 0; i < cccount; i++) {
            ArrayList<Integer> list = lists.get(i);
            System.out.print(i + ": ");
            for (int v: list) {
                System.out.print(v + " ");
            }
            System.out.println();
        }

        System.out.println(ccGraph.isConnected(0, 5));
        System.out.println(ccGraph.isConnected(0, 6));
    }
}
