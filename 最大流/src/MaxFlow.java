import java.util.*;

public class MaxFlow {
    private DirectedWeightedGraph graph;
    private int s;
    private int t;
    private DirectedWeightedGraph rcGraph;
    private int maxFlow = 0;

    public MaxFlow(DirectedWeightedGraph graph, int s, int t) {
        this.graph = graph;

        if (graph.V() < 2) {
            throw new IllegalArgumentException("网络流中节点个数必须大于1");
        }
        graph.validateVertex(s);
        graph.validateVertex(t);
        this.s = s;
        this.t = t;

        DirectedWeightedGraph rcGraph = new DirectedWeightedGraph(graph.V());
        for (int v = 0; v < graph.V(); v++) {
            for (int w: graph.adj(v)) {
                rcGraph.addEdge(v, w, graph.getWeight(v, w));
                rcGraph.addEdge(w, v, 0);
            }
        }
        this.rcGraph = rcGraph;

        while (true) {
            ArrayList<Integer> path =  getAugmentPath();
            if (path.size() == 0) {
                break;
            }

            int min = Integer.MAX_VALUE;
            for (int i = 1; i < path.size(); i++) {
                int v = path.get(i - 1);
                int w = path.get(i);

                min = Math.min(min, rcGraph.getWeight(v, w));
            }

            maxFlow += min;

            for (int i = 1; i < path.size(); i++) {
                int v = path.get(i - 1);
                int w = path.get(i);

                rcGraph.setWeight(v, w, rcGraph.getWeight(v, w) - min);
                rcGraph.setWeight(w, v, rcGraph.getWeight(w, v) + min);
            }
        }
    }

    private ArrayList<Integer> getAugmentPath() {
        Queue<Integer> queue = new LinkedList<>();
        int[] pre = new int[graph.V()];
        queue.add(s);
        Arrays.fill(pre, -1);
        pre[s] = s;

        while (!queue.isEmpty()) {
            int v = queue.remove();
            for (int w: rcGraph.adj(v)) {
                if (pre[w] == -1 && rcGraph.getWeight(v, w) > 0) {
                    pre[w] = v;
                    queue.add(w);
                }
            }
        }

        ArrayList<Integer> result = new ArrayList<>();
        if (pre[t] == -1) {
            return result;
        }

        int cur = t;
        while (cur != s) {
            result.add(cur);
            cur = pre[cur];
        }
        result.add(s);
        Collections.reverse(result);
        return result;
    }

    public int result() {
        return maxFlow;
    }

    public int flow(int v, int w) {
        if (!graph.hasEdge(v, w)) {
            throw new IllegalArgumentException("存在这条边");
        }
        graph.validateVertex(v);
        graph.validateVertex(w);

        return rcGraph.getWeight(w, v);
    }

    public static void main(String[] args) {
        DirectedWeightedGraph network = new DirectedWeightedGraph("g17.txt");
        MaxFlow maxflow = new MaxFlow(network, 0, 10);
        System.out.println(maxflow.result());
        for(int v = 0; v < network.V(); v ++)
            for(int w: network.adj(v))
                System.out.println(String.format("%d-%d: %d / %d", v, w, maxflow.flow(v, w), network.getWeight(v, w)));
    }
}
