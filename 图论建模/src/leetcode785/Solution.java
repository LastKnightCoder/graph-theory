package leetcode785;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.Queue;

class Solution {
    private boolean[] visited;
    private boolean[] colors;
    public boolean isBipartite(@NotNull int[][] graph) {
        visited = new boolean[graph.length];
        colors = new boolean[graph.length];

        for (int i = 0; i < graph.length; i++) {
            if (!visited[i]) {
                if (!dfs(i, graph)) {
                    return false;
                }
            }
        }
        return true;
    }
    private boolean bfs(int v, int[][]graph) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(v);

        while (!queue.isEmpty()) {
            int s = queue.remove();
            visited[s] = true;
            int[] adj = graph[s];
            for (int w: adj) {
                if (!visited[w]) {
                    queue.add(w);
                    colors[w] = !colors[s];
                } else {
                    if (colors[s] == colors[w]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean dfs(int v, int[][] graph) {
        visited[v] = true;

        for (int w: graph[v]) {
            if (!visited[w]) {
                colors[w] = !colors[v];
                if (!dfs(w, graph)) return false;
            } else if (visited[w] && colors[w] == colors[v]) {
                return false;
            }
        }
        return true;
    }
}
