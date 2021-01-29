public class BaseBallSolution {
    public static void main(String[] args) {
        DirectedWeightedGraph baseball = new DirectedWeightedGraph("g17.txt");
        MaxFlow maxflow = new MaxFlow(baseball, 0, 10);
        System.out.println(maxflow.result());
        for(int v = 0; v < baseball.V(); v ++)
            for(int w: baseball.adj(v))
                System.out.println(String.format("%d-%d: %d / %d", v, w, maxflow.flow(v, w), baseball.getWeight(v, w)));
    }
}
