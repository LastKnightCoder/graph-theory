import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class TopoSort {
    private DirectedGraph graph;
    private int[] inDegree;
    private ArrayList<Integer> result = new ArrayList<>();
    private boolean hasCycle = false;

    public TopoSort(DirectedGraph graph) {
        this.graph = graph;
        this.inDegree = new int[graph.V()];

        Queue<Integer> queue = new LinkedList<>();
        for (int v = 0; v < graph.V(); v++) {
            inDegree[v] = graph.inDegree(v);
            if (inDegree[v] == 0) {
                queue.add(v);
            }
        }

        while (!queue.isEmpty()) {
            int v = queue.remove();
            result.add(v);
            for (int w: graph.adj(v)) {
                inDegree[w]--;
                if (inDegree[w] == 0) {
                    queue.add(w);
                }
            }
        }

        if (result.size() != graph.V()) {
            hasCycle = true;
            result.clear();
        }
    }

    public boolean isHasCycle() {
        return hasCycle;
    }

    public ArrayList<Integer> result() {
        return result;
    }

    public static void main(String[] args) {
        DirectedGraph graph = new DirectedGraph("g15.txt");
        TopoSort topoSort = new TopoSort(graph);
        System.out.println(topoSort.result());
    }
}
