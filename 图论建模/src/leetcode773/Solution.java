package leetcode773;

import java.util.*;
import java.util.stream.Collectors;

public class Solution {
    private HashMap<String, Integer> visited = new HashMap<>();
    private int rows;
    private int columns;

    public int slidingPuzzle(int[][] board) {
        this.rows = board.length;
        this.columns = board[0].length;
        String initState = boardToString(board);

        if (initState.equals("123450")) {
            return 0;
        }
        Queue<String> queue = new LinkedList();
        queue.add(initState);
        visited.put(initState, 0);

        while (!queue.isEmpty()) {
            String state = queue.remove();
            for (String nextState: adj(state)) {
                if (!visited.containsKey(nextState)) {
                    queue.add(nextState);
                    visited.put(nextState, visited.get(state) + 1);

                    if ("123450".equals(nextState)) {
                        return visited.get(nextState);
                    }
                }
            }
        }

        return -1;
    }

    private String boardToString(int[][] board) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                stringBuilder.append(board[i][j]);
            }
        }

        return stringBuilder.toString();
    }

    private Iterable<String> adj(String state) {
        ArrayList<String> result = new ArrayList<>();
        char[] chars = state.toCharArray();
        int index = state.indexOf("0");

        int row = index / columns;
        int column = index % columns;

        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        List<Integer> locations = Arrays.stream(dirs)
                .map(dir -> new int[]{dir[0] + row, dir[1] + column})
                .filter(item -> item[0] < rows && item[0] >= 0 && item[1] < columns && item[1] >= 0)
                .map(item -> item[0] * columns + item[1])
                .collect(Collectors.toList());

        for (int i: locations) {
            String res = swap(chars, index, i);
            result.add(res);
            swap(chars, i, index);
        }

        return result;
    }

    private String swap(char[] chars, int i, int j) {
        char temp = chars[i];
        chars[i] = chars[j];
        chars[j] = temp;
        return new String(chars);
    }
}
