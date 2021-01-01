import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class AdjMatrix implements Graph {
    private int E;
    private int V;
    private int[][] matrix;

    public AdjMatrix(String filename) {
        File file = new File(filename);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
            this.V = scanner.nextInt();
            if (this.V < 0) {
                throw new IllegalArgumentException("V Must Be Positive");
            }
            matrix = new int[this.V][this.V];
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
                if (this.matrix[a][b] == 1) {
                    throw new IllegalArgumentException("Parallel edge exists");
                }
                this.matrix[a][b] = 1;
                this.matrix[b][a] = 1;
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
        ArrayList<Integer> res = new ArrayList<>();
        for (int i = 0; i < this.V; i++) {
            if (this.matrix[v][i] == 1) {
                res.add(i);
            }
        }

        return res;
    }

    @Override
    public boolean hasEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        return this.matrix[v][w] == 1;
    }

    @Override
    public int degree(int v) {
        ArrayList list = (ArrayList) adj(v);
        return list.size();
    }


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("V: %d, E: %d\n", this.V, this.E));
        for (int i = 0; i < this.V; i++) {
            for (int j = 0; j < this.V; j++) {
                stringBuilder.append(String.format("%d ", this.matrix[i][j]));
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        AdjMatrix adjMatrix = new AdjMatrix("g.txt");
        System.out.println(adjMatrix.degree(0));
        System.out.println(adjMatrix);
    }
}
