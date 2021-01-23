import java.util.*;

public class WaterPuzzle {
    public Iterable<Integer> waterPuzzle() {
        HashSet<Integer> visited = new HashSet<>();
        HashMap<Integer, Integer>  pre = new HashMap<>();
        Queue<Integer> queue = new LinkedList<>();

        queue.add(0);
        visited.add(0);
        pre.put(0, 0);
        int endState = 0;

        boolean flag = false;
        while (!queue.isEmpty()) {
            int state = queue.remove();
            for (int nextState: adj(state)) {
                if (!visited.contains(nextState)) {
                    visited.add(nextState);
                    queue.add(nextState);
                    pre.put(nextState, state);
//                    System.out.println(nextState);
                    if (nextState / 10 == 4 || nextState % 10 == 4) {
                        endState = nextState;
                        flag = true;
                        break;
                    }
                }
            }
            if (flag) {
                break;
            }
        }


        ArrayList<Integer> result = new ArrayList();

        int cur = endState;
        result.add(cur);
        while (pre.get(cur) != 0) {
            cur = pre.get(cur);
            result.add(cur);
        }

        Collections.reverse(result);
        return result;
    }

    private Iterable<Integer> adj(int state) {
        int first = state / 10;
        int second = state % 10;

        Set<Integer> set = new HashSet<>();
        set.add(second);
        set.add(first*10);
        set.add(5*10 + second);
        set.add(first * 10 + 3);

        if (first + second >= 5) {
            set.add(5 * 10 + (first + second) - 5);
        } else {
            set.add((first + second) * 10);
        }

        if (first + second >= 3) {
            set.add((first + second - 3) * 10 + 3);
        } else {
            set.add(first + second);
        }

        return new ArrayList<>(set);
    }

    public static void main(String[] args) {
        WaterPuzzle waterPuzzle = new WaterPuzzle();
        for (int r: waterPuzzle.waterPuzzle()) {
            // 在个位数前加上0
            System.out.print((r >= 10 ? r : "0" + r) + " ");
        }
    }
}
