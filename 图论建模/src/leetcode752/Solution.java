package leetcode752;

import java.util.*;
import java.util.stream.Collectors;

class Solution {
    public int openLock(String[] deadends, String target) {
        if ("0000".equals(target)) {
            return 0;
        }

        HashSet<String> deadSet = new HashSet<>();
        for (int i = 0; i < deadends.length; i++) {
            deadSet.add(deadends[i]);
        }

        if (deadSet.contains("0000")){
            return -1;
        }

        HashSet<String> visited = new HashSet<>();
        HashMap<String, Integer> dis = new HashMap<>();
        Queue<String> queue = new LinkedList();
        queue.add("0000");
        visited.add("0000");
        dis.put("0000", 0);

        while (!queue.isEmpty()) {
            String state = queue.remove();
            for (String nextState: adj(state, deadSet)) {
                if (!visited.contains(nextState)) {
                    queue.add(nextState);
                    visited.add(nextState);
                    dis.put(nextState, dis.get(state) + 1);

                    if (target.equals(nextState)) {
                        return dis.get(nextState);
                    }
                }
            }
        }

        return -1;
    }

    private Iterable<String> adj(String state, HashSet<String> deadSet) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            char[] chars = state.toCharArray();
            char s = chars[i];
            chars[i] = Character.forDigit((chars[i] - '0' + 1) % 10, 10);
            list.add(new String(chars));
            chars[i] = s;
            chars[i] = Character.forDigit((chars[i] - '0' - 1 + 10) % 10, 10);
            list.add(new String(chars));
        }
        return list.stream().filter(s -> !deadSet.contains(s)).collect(Collectors.toList());
    }


}
