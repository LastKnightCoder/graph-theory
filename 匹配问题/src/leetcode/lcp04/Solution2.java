package leetcode.lcp04;

import java.util.*;

public class Solution2 {

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

    public class HungarianBFS {
        private AdjSet graph;
        private int[] matching;
        private int maxMatching;

        public HungarianBFS(AdjSet graph) {
            this.graph = graph;
            BinaryPartitionDetection bpm = new BinaryPartitionDetection(graph);
            if (!bpm.isBipartite()) {
                throw new IllegalArgumentException("该图不是二分图");
            }
            this.matching = new int[graph.V()];
            Arrays.fill(matching, -1);

            int[] colors = bpm.colors();

            for (int v = 0; v < graph.V(); v++) {
                if (colors[v] == 0 && matching[v] == -1) {
                    if (bfs(v)) {
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
                        if (matching[next] != -1) {
                            queue.add(matching[next]);
                            pre[next] = cur;
                            pre[matching[next]] = next;
                        } else {
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

        HungarianBFS hungarianBFS = new HungarianBFS(graph);
        return hungarianBFS.result();
    }

}
