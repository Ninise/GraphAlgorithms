package backend;

public class Vertex {
    public int vertexName;
    public boolean wasVisited;

    public Vertex (int key) {
        this.vertexName = key;
        this.wasVisited = false;
    }
}
