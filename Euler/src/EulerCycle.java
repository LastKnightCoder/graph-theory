import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class EulerCycle {
    private Graph graph;

    public EulerCycle(Graph graph) {
        this.graph = graph;
    }

    public boolean hasEulerCycle() {

        CCGraph ccGraph = new CCGraph(graph);
        if (ccGraph.getCCcount() != 1) {
            return false;
        }

        for (int i = 0; i < graph.V(); i++) {
            if (graph.degree(i) % 2 != 0) {
                return false;
            }
        }
        return true;
    }

    public Iterable<Integer> result() {
        ArrayList res = new ArrayList();
        if (!hasEulerCycle()) return res;

        // 剩余未被遍历的边数
        int left = graph.E();
        Stack<Integer> result = new Stack<>();
        Stack<Integer> back = new Stack<>();

        int curV = 0;
        result.push(curV);
        while (left != 0) {
            // 每次更新 curV 为栈顶节点
            curV = result.peek();
            // 如果栈顶元素有相邻节点，寻找新的环
            if (graph.degree(curV) != 0) {
                // 获得一个相邻节点
                int w = graph.adj(curV).iterator().next();
                result.push(w);
                graph.removeEdge(curV, w);
                left--;
            } else {
                back.push(result.pop());
            }
        }

        while (!back.isEmpty()) {
            result.push(back.pop());
        }

        while (!result.isEmpty()) {
            res.add(result.pop());
        }

        Collections.reverse(res);
        return res;
    }

    public static void main(String[] args) {
        Graph graph = new AdjSet("g11.txt");
        EulerCycle eulerCycle = new EulerCycle(graph);
        for (int w: eulerCycle.result()) {
            System.out.print(w + " ");
        }
    }
}
