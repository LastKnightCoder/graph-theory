package leetcode980;

import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

public class Solution {
    private int visited;
    private int[] start;
    private int[] end;
    private int rows;
    private int columns;
    private int left;
    private int[][] grid;
    private HashMap<String, Integer> memo= new HashMap<>();

    public int uniquePathsIII(int[][] grid) {
        this.grid = grid;
        this.rows = grid.length;
        this.columns = grid[0].length;
        this.visited = 0;
        this.start = new int[2];
        this.end = new int[2];
        this.left = rows * columns;

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                if (grid[row][column] == 1) {
                    start[0] = row;
                    start[1] = column;
                    grid[row][column] = 0;
                } else if (grid[row][column] == 2) {
                    end[0] = row;
                    end[1] = column;
                    grid[row][column] = 0;
                } else if (grid[row][column] == -1) {
                    left--;
                }
            }
        }

        return dfs(start);
    }

    private int dfs(int[] v) {
        String strV = format(v);

        if (memo.containsKey(visited + strV)) {
            return memo.get(visited + strV);
        }

        visited = visited ^ (1 << (v[0] * columns + v[1]));
        left--;
        if (left == 0 && (v[0] == end[0] && v[1] == end[1])) {
            visited = visited ^ (1 << (v[0] * columns + v[1]));
            left++;
            memo.put(visited + strV, 1);
            return 1;
        }

        int res = 0;
        for (int[] w: adj(v[0], v[1])) {
            if ((visited & (1 << (w[0] * columns + w[1]))) == 0 && grid[w[0]][w[1]] != -1) {
                res += dfs(w);
            }
        }

        left++;
        visited = visited ^ (1 << (v[0] * columns + v[1]));
        memo.put(visited+strV, res);
        return res;
    }

    private Iterable<int[]> adj(int row, int column) {
        int[][] dirs = {{-1, 0}, {0, -1}, {0, 1}, {1, 0}};
        return Arrays.stream(dirs)
                .map(dir -> new int[]{dir[0] + row, dir[1] + column})
                .filter(item -> item[0] < rows && item[0] >= 0 && item[1] < columns && item[1] >= 0)
                .collect(Collectors.toList());
    }

    private String format(int[] v) {
        int location = v[0] * columns + v[1];
        int first = location / 10;
        int second = location % 10;
        return "" + first + second;

    }

    public static void main(String[] args) {
        int[][] grid = {{1,0,0,0},{0,0,0,0},{0,0,2,-1}};
        Solution solution = new Solution();
        int res = solution.uniquePathsIII(grid);
        System.out.println(res);
    }
}
