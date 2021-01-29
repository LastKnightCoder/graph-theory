import java.util.ArrayList;
import java.util.Collections;

public class TopoSort2 {
    private DirectedGraph graph;
    private boolean hasCycle;
    private ArrayList<Integer> result = new ArrayList<>();

    public TopoSort2(DirectedGraph graph) {
        this.graph = graph;
        DirectedCycleDetect directedCycleDetect = new DirectedCycleDetect(graph);
        this.hasCycle = directedCycleDetect.isHasCycle();

        if (hasCycle) {
            return;
        }

        DFSDirectedGraph dfsDirectedGraph = new DFSDirectedGraph(graph);
        for (int v: dfsDirectedGraph.post()) {
            result.add(v);
        }
        Collections.reverse(result);
    }

    public boolean isHasCycle() {
        return hasCycle;
    }

    public ArrayList<Integer> result() {
        return result;
    }
}
