package backend;

/**
 * Created by nikitin_ns on 01.10.2015.
 */
public class GraphW {

        private int Matrix[][];
        private int countVerts;
        private final int INFINITY = 1000000;
        private int nVerts;
        private int nTree;
        private DistPar sPath[];
        private int currentVert;
        private int startToCurrent;

        private Vertex vertexList[];

        public GraphW() {
            vertexList = new Vertex[ConstParameters.MAX_VERTS];

            nVerts = 0;
            nTree = 0;

            Matrix = new int[ConstParameters.MAX_VERTS][ConstParameters.MAX_VERTS];

            for(int j = 0; j < ConstParameters.MAX_VERTS; j++)
                for(int k = 0; k < ConstParameters.MAX_VERTS; k++)
                    Matrix[j][k] = INFINITY;

            sPath = new DistPar[ConstParameters.MAX_VERTS];
        }

        public void addVertex(int key) {
            vertexList[countVerts++] = new Vertex(key);
            nVerts++;
        }

        public void addEdge(int start, int end, int weight) {
            Matrix[start][end] = weight;

        }

        public void path() {
            int startTree = 0;
            vertexList[startTree].wasVisited = true;
            nTree = 1;

            for(int j = 0; j < nVerts; j++) {
                int tempDist = Matrix[startTree][j];
                sPath[j] = new DistPar(startTree, tempDist);
            }

            while(nTree < nVerts) {
                int indexMin = getMin();
                int minDist = sPath[indexMin].distance;

                if(minDist == INFINITY) {
                    System.out.println("There are unreachable vertices");
                    break;
                }
                else {
                    currentVert = indexMin;
                    startToCurrent = sPath[indexMin].distance;
                }

                vertexList[currentVert].wasVisited = true;
                nTree++;
                adjust_sPath();
            }
            displyPath();

            nTree = 0;

            for(int j = 0; j < nVerts; j++)
                vertexList[j].wasVisited = false;
        }

        public int getMin() {
            int minDist = INFINITY;
            int indexMin = 0;

            for(int j = 1; j < nVerts; j++) {
                if(!vertexList[j].wasVisited && sPath[j].distance < minDist) {
                    minDist = sPath[j].distance;
                    indexMin = j;
                }
            }
            return indexMin;
        }

        public void adjust_sPath() {
            int colum = 1;

            while (colum < nVerts) {
                if(vertexList[colum].wasVisited) {
                    colum++;
                    continue;
                }

                int currentToFringe = Matrix[currentVert][colum];
                int startToFringe = startToCurrent + currentToFringe;
                int sPathDist = sPath[colum].distance;

                if(startToFringe < sPathDist) {
                    sPath[colum].parentVertex = currentVert;
                    sPath[colum].distance = startToFringe;
                }
                colum++;
            }
        }

        public void displyPath() {
            for(int j = 0; j < nVerts; j++) {
                System.out.print(vertexList[j].vertexName + "=");
                if(sPath[j].distance == INFINITY)
                    System.out.print("inf");
                else
                    System.out.print(sPath[j].distance);
                int parent = vertexList[sPath[j].parentVertex].vertexName;
                System.out.print("(" + parent + ")");
                System.out.println(" ");
            }
            System.out.println(" ");
        }


}



