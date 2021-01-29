package leetcode.lcp04;

import java.util.*;

public class Solution {
    public class AdjSet {
        private int E;
        private int V;
        private TreeSet<Integer>[] adj;

        public AdjSet(int V) {
            this.V = V;
            this.E = 0;
            this.adj = new TreeSet[V];
            for (int v = 0; v < V; v++) {
                adj[v] = new TreeSet<>();
            }
        }

        public void addEdge(int v, int w) {
            validateVertex(v);
            validateVertex(w);

            adj[v].add(w);
            adj[w].add(v);
            E++;
        }

        public void validateVertex(int v) {
            if (v < 0 || v >= this.V) {
                throw new IllegalArgumentException("Vertex " + v + " is invalid");
            }
        }
        public int V() {
            return this.V;
        }


        public int E() {
            return this.E;
        }


        public Iterable<Integer> adj(int v) {
            validateVertex(v);
            return adj[v];
        }

        public boolean hasEdge(int v, int w) {
            validateVertex(v);
            validateVertex(w);
            return this.adj[v].contains(w);
        }

        public int degree(int v) {
            validateVertex(v);
            return this.adj[v].size();
        }
    }

    public class BinaryPartitionDetection {
        private AdjSet graph;
        private boolean[] visited;
        // 以 0 1 表示不同颜色
        private int[] colors;

        private boolean isBipartite;

        public BinaryPartitionDetection(AdjSet graph) {
            this.graph = graph;
            visited = new boolean[graph.V()];
            Arrays.fill(visited, false);
            // 初始化 colors 为 -1，表示未染色
            colors = new int[graph.V()];
            Arrays.fill(colors, -1);

            isBipartite = true;
            for (int i = 0; i < visited.length; i++) {
                if (!visited[i]) {
                    if (!dfs(i, 0)) {
                        isBipartite = false;
                        break;
                    }
                }
            }
        }

        private boolean dfs(int v, int color) {
            visited[v] = true;
            colors[v] = color;
            for (int w: graph.adj(v)) {
                if (!visited[w]) {
                    if (!dfs(w, 1 - color)) {
                        return false;
                    }
                } else if (colors[v] == colors[w]) {
                    System.out.println(String.format("v: %s, w: %s", v, w));
                    return false;
                }
            }
            return true;
        }

        public boolean isBipartite() {
            return isBipartite;
        }

        public int[] colors() {
            return colors;
        }
    }

    public class DirectedWeightedGraph{
        private int V;
        private int E;
        private HashMap<Integer, Integer>[] adj;

        public DirectedWeightedGraph(int V) {
            this.V = V;
            this.E = 0;
            this.adj = new HashMap[V];
            for (int v = 0; v < V; v++) {
                adj[v] = new HashMap<>();
            }
        }

        public int V() {
            return this.V;
        }

        public int E() {
            return this.E;
        }

        public int getWeight(int v, int w) {
            validateVertex(v);
            validateVertex(w);
            if (!hasEdge(v, w)) {
                throw new IllegalArgumentException(String.format("%d-%d 不存在", v, w));
            }
            return adj[v].get(w);
        }

        public void setWeight(int v, int w, int weight) {
            validateVertex(v);
            validateVertex(w);
            if (!hasEdge(v, w)) {
                throw new IllegalArgumentException(String.format("%d-%d 不存在", v, w));
            }
            adj[v].put(w, weight);
        }

        public void addEdge(int v, int w, int weight) {
            validateVertex(v);
            validateVertex(w);
            if (v == w) {
                throw new IllegalArgumentException("存在自环边");
            }
            if (adj[v].containsKey(w)) {
                throw new IllegalArgumentException("存在平行边");
            }

            adj[v].put(w, weight);
            E++;
        }

        public boolean hasEdge(int v, int w) {
            validateVertex(v);
            validateVertex(w);
            return adj[v].containsKey(w);
        }

        public Iterable<Integer> adj(int v) {
            validateVertex(v);
            return adj[v].keySet();
        }

        public void validateVertex(int v) {
            if (v < 0 && v >= V) {
                throw new IllegalArgumentException("节点超过 [0, V) 的范围");
            }
        }
    }

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
    }

    public class BipartiteMatching {
        private AdjSet graph;
        private int maxMatching;

        public BipartiteMatching(AdjSet graph) {
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
    }

    public int domino(int n, int m, int[][] broken) {
        int[][] board = new int[n][m];

        for (int[] p: broken) {
            board[p[0]][p[1]] = 1;
        }

        AdjSet graph = new AdjSet(n * m);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (j + 1 < m && board[i][j] == 0 && board[i][j + 1] == 0) {
                    graph.addEdge(i * m + j, i * m + j + 1);
                }

                if (i + 1 < n && board[i][j] == 0 && board[i + 1][j] == 0) {
                    graph.addEdge(i * m + j, (i + 1) * m + j);
                }
            }
        }

        BipartiteMatching bm = new BipartiteMatching(graph);
        return bm.result();
    }
}
