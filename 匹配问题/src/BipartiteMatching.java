public class BipartiteMatching {
    private Graph graph;
    private int maxMatching;

    public BipartiteMatching(Graph graph) {
        this.graph = graph;
        BinaryPartitionDetection bpd = new BinaryPartitionDetection(graph);
        if (!bpd.isBipartite()) {
            throw new IllegalArgumentException("该图不为一个二分图");
        }
        int[] colors = bpd.colors();

        DirectedWeightedGraph directedWeightedGraph = new DirectedWeightedGraph(graph.V() + 2);
        for (int v = 0; v < graph.V(); v++) {
            if (colors[v] == 0) {
                directedWeightedGraph.addEdge(graph.V(), v, 1);
            } else {
                directedWeightedGraph.addEdge(v, graph.V() + 1, 1);
            }

            for (int w: graph.adj(v)) {
                if (v < w) {
                    if (colors[v] == 0) {
                        directedWeightedGraph.addEdge(v, w, 1);
                    } else {
                        directedWeightedGraph.addEdge(w, v, 1);
                    }
                }
            }
        }

        MaxFlow maxFlow = new MaxFlow(directedWeightedGraph, graph.V(), graph.V() +1);
        maxMatching = maxFlow.result();
    }

    public int result() {
        return maxMatching;
    }

    public boolean isPerfectMatching() {
        return maxMatching * 2 == graph.V();
    }

    public static void main(String[] args) {
        Graph graph = new AdjSet("g18.txt");
        BipartiteMatching bm = new BipartiteMatching(graph);
        System.out.println(bm.result());
    }
}
