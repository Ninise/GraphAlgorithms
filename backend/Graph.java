package backend;

public class Graph {

    private Vertex vertexList[];
    private int Matrix[][];

    private int countVerts;
    private Stack theStack;
    private Queue theQueue;

    public Graph() {
        vertexList = new Vertex[ConstParameters.MAX_VERTS];

        Matrix = new int[ConstParameters.MAX_VERTS][ConstParameters.MAX_VERTS];
        for(int j = 0; j < ConstParameters.MAX_VERTS; j++)
            for(int k = 0; k < ConstParameters.MAX_VERTS; k++)
                Matrix[j][k] = 0;

        theStack = new Stack();
        theQueue = new Queue();
        countVerts = 0;
    }

    public void addVertex(int key) {
        vertexList[countVerts++] = new Vertex(key);
    }

    public void addEdge(int start, int end) {
        Matrix[start][end] = 1;
        Matrix[end][start] = 1;
    }

    public void displayVertex(int v) {
        System.out.print("->" + vertexList[v].vertexName);
    }

    public void dfs() {
        vertexList[0].wasVisited = true;
        displayVertex(0);
        theStack.push(0);

        while(!theStack.isEmpty()) {
            int v = getAdjUnvisitedVertex(theStack.whatOnTop());
            if(v == -1)
                theStack.pop();
            else {
                vertexList[v].wasVisited = true;
                displayVertex(v);
                theStack.push(v);
            }
        }
        for(int j=0; j<countVerts; j++)
            vertexList[j].wasVisited = false;
    }

    public void bfs() {
        vertexList[0].wasVisited = true;
        displayVertex(0);
        theQueue.insert(0);

        int v2;

        while (!theQueue.isEmpty()) {
            int top = theQueue.remove();

            while ( (v2 = getAdjUnvisitedVertex(top)) != -1) {
                vertexList[v2].wasVisited = true;
                displayVertex(v2);
                theQueue.insert(v2);
            }
        }

        for (int j = 0; j < countVerts; j++)
            vertexList[j].wasVisited = false;

    }

    public int getAdjUnvisitedVertex(int v) {
        for(int j=0; j<countVerts; j++)
            if(Matrix[v][j]==1 && !vertexList[j].wasVisited)
                return j;
        return -1;
    }
}
