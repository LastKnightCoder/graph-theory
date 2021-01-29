import java.util.*;

public class HungarianBFS {
    private Graph graph;
    private int[] matching;
    private int maxMatching;

    public HungarianBFS(Graph graph) {
        this.graph = graph;
        BinaryPartitionDetection bpm = new BinaryPartitionDetection(graph);
        if (!bpm.isBipartite()) {
            throw new IllegalArgumentException("该图不是二分图");
        }
        this.matching = new int[graph.V()];
        Arrays.fill(matching, -1);

        int[] colors = bpm.colors();

        for (int v = 0; v < graph.V(); v++) {
            // colors[v] == 0 说明是左边节点
            // matching[v] == -1 说明未匹配，从这种节点开始遍历
            if (colors[v] == 0 && matching[v] == -1) {
                // bfs 的返回值表示是否找到一个右边未匹配的节点
                if (bfs(v)) {
                    // 找到匹配数就加一
                    maxMatching++;
                }
            }
        }
    }

    // 进行 bfs 遍历
    private boolean bfs(int v) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(v);
        int[] pre = new int[graph.V()];
        Arrays.fill(pre, -1);
        pre[v] = v;
        while (!queue.isEmpty()) {
            int cur = queue.remove();
            for (int next: graph.adj(cur)) {
                if (pre[next] == -1) {
                    // 如果右边的节点已经匹配了
                    if (matching[next] != -1) {
                        // 从右边节点的匹配节点开始继续遍历
                        queue.add(matching[next]);
                        pre[next] = cur;
                        pre[matching[next]] = next;
                    } else {
                        // 右边的节点未匹配，找到了，更新匹配关系
                        pre[next] = cur;
                        ArrayList<Integer> path = getPath(pre, v, next);
                        for (int i = 0; i < path.size(); i += 2) {
                            matching[path.get(i)] = path.get(i + 1);
                            matching[path.get(i + 1)] = path.get(i);
                        }

                        return true;
                    }
                }
            }
        }

        return false;
    }

    private ArrayList<Integer> getPath(int[] pre, int start, int end) {
        int cur = end;

        ArrayList<Integer> result = new ArrayList<>();

        while (cur != start) {
            result.add(cur);
            cur = pre[cur];
        }
        result.add(start);
        Collections.reverse(result);
        return result;
    }

    public int result() {
        return maxMatching;
    }

    public static void main(String[] args) {
        Graph graph = new AdjSet("g18.txt");
        HungarianBFS hungarianBFS = new HungarianBFS(graph);
        System.out.println(hungarianBFS.result());
    }
}
