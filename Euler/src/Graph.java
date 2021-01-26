public interface Graph {
    int V();
    int E();
    int degree(int v);
    boolean hasEdge(int v, int w);
    Iterable<Integer> adj(int v);
    public void removeEdge(int v, int w);
}
