package leetcode1091;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

class Solution {
//    private boolean[][] visited;
//    private int[][] dis;
    private int rows;
    private int columns;

    public int shortestPathBinaryMatrix(int[][] grid) {
        this.rows = grid.length;
        this.columns = grid[0].length;

        if (grid[0][0] == 1 || grid[rows - 1][columns - 1] == 1) {
            return -1;
        }

        if (rows == 1 && grid[rows - 1][columns - 1] == 0) {
            return 1;
        }

//        dis = new int[rows][columns];
//        visited = new boolean[rows][columns];

        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{0, 0});
        grid[0][0] = 1;
//        visited[0][0] = true;
//        dis[0][0] = 1;

        while (!queue.isEmpty()) {
            int[] location = queue.remove();
            int row = location[0];
            int column = location[1];

            for (int[] nextLocation: adj(row, column)) {
                int nextRow = nextLocation[0];
                int nextColumn = nextLocation[1];
                if (grid[nextRow][nextColumn] == 0) {
                    queue.add(new int[]{nextRow, nextColumn});
//                    visited[nextRow][nextColumn] = true;
                    grid[nextRow][nextColumn] = grid[row][column] + 1;

                    if (nextRow == rows - 1 && nextColumn == columns - 1) {
                        return grid[nextRow][nextColumn];
                    }
                }
            }
        }

        return -1;
    }

    private Iterable<int[]> adj(int row, int column) {
        int[][] dirs = {{-1, 0}, {0, -1}, {0, 1}, {1, 0},
                {1, 1}, {-1, 1}, {1, -1}, {-1, -1}};
        return Arrays.stream(dirs)
                .map(dir -> new int[]{dir[0] + row, dir[1] + column})
                .filter(item -> item[0] < rows && item[0] >= 0 && item[1] < columns && item[1] >= 0)
                .collect(Collectors.toList());
    }
}