import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class EulerDirectedGraph {
    private DirectedGraph graph;
    private int[] pre;
    private boolean[] visited;

    public EulerDirectedGraph(DirectedGraph graph) {
        this.graph = graph;
        this.pre = new int[graph.V()];
        this.visited = new boolean[graph.V()];
    }

    public boolean hasEulerCycle() {
        for (int v = 0; v < graph.V(); v++) {
            if (graph.inDegree(v) != graph.outDegree(v)) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<Integer> path() {
        ArrayList<Integer> result = new ArrayList<>();
        if (!hasEulerCycle()) {
            return result;
        }

        Stack<Integer> res = new Stack<>();
        Stack<Integer> back = new Stack<>();

        int left = graph.E();
        res.push(0);
        while (left != 0) {
            int v = res.peek();
            if (graph.outDegree(v) != 0) {
                int w = graph.adj(v).iterator().next();
                graph.removeEdge(v, w);
                res.push(w);
                left--;
            } else {
                back.push(res.pop());
            }
        }

        while (!back.isEmpty()) {
            res.push(back.pop());
        }
        while (!res.isEmpty()) {
            result.add(res.pop());
        }

        Collections.reverse(res);
        return result;
    }
}
