package leetcode695;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Solution {
    private int rows;
    private int columns;
    private boolean[][] visited;
    private int[][] grid;

    public int maxAreaOfIsland(int[][] grid) {
        if (grid == null) {
            return 0;
        }
        this.rows = grid.length;
        if (rows == 0) {
            return 0;
        }

        this.columns = grid[0].length;
        if (columns == 0) {
            return 0;
        }

        this.grid = grid;
        this.visited = new boolean[rows][columns];
        int max = 0;

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                if (!visited[row][column] && grid[row][column] == 1) {
                    max = Math.max(max, dfs(row, column));
                }
            }
        }
        return max;
    }

    private int dfs(int row,int column) {
        int count = 1;
        visited[row][column] = true;

        for (int[] w: adj(row, column)) {
            int nextRow = w[0];
            int nextColumn = w[1];
            if (grid[nextRow][nextColumn] == 0) {
                continue;
            }
            if (!visited[nextRow][nextColumn]) {
                count += dfs(nextRow, nextColumn);
            }
        }
        return count;
    }

    private Iterable<int[]> adj(int row, int column) {
        int[][] dirs = {{-1, 0}, {0, -1}, {0, 1}, {1, 0}};
        return Arrays.stream(dirs)
                .map(dir -> new int[]{dir[0] + row, dir[1] + column})
                .filter(item -> item[0] < rows && item[0] >= 0 && item[1] < columns && item[1] >= 0)
                .collect(Collectors.toList());
    }
}
