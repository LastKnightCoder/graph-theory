import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.TreeSet;

public class AdjSet implements Graph {
    private int E;
    private int V;
    private TreeSet<Integer>[] sets;

    public AdjSet(String filename) {
        File file = new File(filename);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
            this.V = scanner.nextInt();
            if (this.V < 0) {
                throw new IllegalArgumentException("V Must Be Positive");
            }

            // 初始化链表
            this.sets = new TreeSet[this.V];
            for (int i = 0; i < this.V; i++) {
                this.sets[i] = new TreeSet<>();
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
                if (sets[a].contains(b)) {
                    throw new IllegalArgumentException("Parallel edge exists");
                }

                // 将相邻顶点添加到自己的链表中
                this.sets[a].add(b);
                this.sets[b].add(a);
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
        return sets[v];
    }

    @Override
    public boolean hasEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        return this.sets[v].contains(w);
    }

    @Override
    public int degree(int v) {
        validateVertex(v);
        return this.sets[v].size();
    }

    @Override
    public void removeEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        sets[v].remove(w);
        sets[w].remove(v);
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
        AdjSet adjSet = new AdjSet("g.txt");
        System.out.println(adjSet);
    }
}

