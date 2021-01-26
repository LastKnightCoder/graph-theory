public class UnionFind {
    private int[] parent;
    private int[] rank;

    public UnionFind(int size) {
        parent = new int[size];
        rank = new int[size];

        for (int i = 0; i < parent.length; i++) {
            parent[i] = i;
            rank[i] = 1;
        }
    }

    public int getSize() {
        return parent.length;
    }

    private int find(int index) {
        if (index < 0 || index >= parent.length) {
            throw new IllegalArgumentException("参数错误");
        }

        while (index != parent[index]) {
            index = parent[index];
        }
        return index;
    }

    public boolean isConnected(int p, int q) {
        return find(p) == find(q);
    }

    public void unionElements(int p, int q) {
        int pRoot = find(p);
        int qRoot = find(q);
        if (pRoot == qRoot) {
            return;
        }

        if (rank[pRoot] <= rank[qRoot]){
            parent[pRoot] = parent[qRoot];
            if (rank[pRoot] == rank[qRoot]) {
                rank[qRoot]++;
            }
        } else {
            parent[qRoot] = parent[pRoot];
        }
    }
}