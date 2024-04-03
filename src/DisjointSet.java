public class DisjointSet {
    private int[] set;

    public DisjointSet(int size){
        this.set = new int[size-1];
        for (int i=0; i < size; i++){
            set[i]=i;
        }
    }

    public void union(int node1, int node2){
        set[find(node2)] = set[find(node1)];
    }

    public int find(int node){
        if (node != set[node]){
            find(set[node]);
            set[node] = node;
        }
        return node;
    }
}
