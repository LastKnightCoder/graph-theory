import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * @author XT
 */
public class AdjList implements Graph {
    private int E;
    private int V;
    // 对每一个顶点，使用一个链表来存储与它相邻的顶点
    private LinkedList<Integer>[] lists;

    public AdjList(String filename) {
        File file = new File(filename);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
            this.V = scanner.nextInt();
            if (this.V < 0) {
                throw new IllegalArgumentException("V Must Be Positive");
            }

            // 初始化链表
            this.lists = new LinkedList[this.V];
            for (int i = 0; i < this.V; i++) {
                this.lists[i] = new LinkedList<>();
            }

            this.E = scanner.nextInt();
            if (this.E < 0) {
                throw new IllegalArgumentException("E Must Be Positive");
            }

            for (int i = 0; i < this.E; i++) {
                int a = scanner.nextInt();
                validateVertex(a);
                int b = scanner.nextInt();
                validateVertex(b);

                if (a == b) {
                    throw new IllegalArgumentException("Self loop exists");
                }
                // 平行边的判断
                if (lists[a].contains(b)) {
                    throw new IllegalArgumentException("Parallel edge exists");
                }

                // 将相邻顶点添加到自己的链表中
                this.lists[a].add(b);
                this.lists[b].add(a);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            assert scanner != null;
            scanner.close();
        }
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= this.V) {
            throw new IllegalArgumentException("Vertex " + v + " is invalid");
        }
    }

    @Override
    public int V() {
        return this.V;
    }

    @Override
    public int E() {
        return this.E;
    }

    @Override
    public Iterable<Integer> adj(int v) {
        validateVertex(v);

        // 直接返回自己的链表即可
        return lists[v];
    }

    @Override
    public boolean hasEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        return this.lists[v].contains(w);
    }

    @Override
    public int degree(int v) {
        return this.lists[v].size();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("V: %d, E: %d\n", this.V, this.E));
        for (int v = 0; v < this.V; v++) {
            stringBuilder.append(String.format("%d : ", v));
            for (int w: adj(v)) {
                stringBuilder.append(String.format("%d ", w));
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        AdjList adjList = new AdjList("g.txt");
        System.out.println(adjList.degree(0));
        System.out.println(adjList);
    }
}

