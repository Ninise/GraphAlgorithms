package backend;

/**
 * Created by ninise on 10/2/15.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class LiAlgorithm {

    class Point{
        private int x;
        private int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public boolean equals(Object o) {
            if(!(o instanceof Point)) return false;
            return (( (Point)o).getX() == x) && ( ((Point)o).getY() == y);
        }

        @Override
        public int hashCode() {
            return Integer.valueOf(x) ^ Integer.valueOf(y);
        }

        @Override
        public String toString() {
            return "x: " + Integer.valueOf(x).toString() + " y:" + Integer.valueOf(y).toString();
        }

    };

    int[][] fillmap = new int[10][10]; // Size of labyrinth
    int[][] labyrinth;
    List buf = new ArrayList();

    LiAlgorithm(int[][] labyrinth) {
        this.labyrinth = labyrinth;
    }

	/** This function checks whether the proposed path to point out a short more than
	I found your former and, if so, remembers a point in buffer. */

    void push(Point p, int n){
        if(fillmap[p.getY()][p.getX()]<=n) return; // If the new path is not In short, it is not needed
        fillmap[p.getY()][p.getX()]=n; // If a new path In short, what we need it
        buf.add(p); // Save point
    }

    /* Take first point from buffer, if his exist*/
    Point pop(){
        if(buf.isEmpty()) return null;
        return (Point)buf.remove(0);
    }

    Point[] find(Point start, Point end){
        int tx=0, ty=0, n = 0, t=0;
        Point p;
        for(int i=0; i<fillmap.length; i++)
            Arrays.fill(fillmap[i], Integer.MAX_VALUE);
        push(start, 0); // Start from 0
        while((p = pop()) != null){ // While have points we work
            if(p.equals(end)){
                System.out.print("Let us find a path of length: ");
                System.out.println(n);
            }

            n=fillmap[p.getY()][p.getX()]+labyrinth[p.getY()][p.getX()];


            if( (p.getY() +1 < labyrinth.length) && labyrinth[p.getY() + 1][p.getX()] != 0)
                push(new Point(p.getX(), p.getY() + 1), n);
            if( (p.getY() -1 >= 0) && (labyrinth[p.getY() - 1][p.getX()] != 0))
                push(new Point(p.getX(), p.getY() - 1), n);
            if( (p.getX() + 1 < labyrinth[p.getY()].length) && (labyrinth[p.getY()][p.getX() +1 ] != 0))
                push(new Point(p.getX() + 1, p.getY()), n);
            if( (p.getX() -1 >= 0) && (labyrinth[p.getY()][p.getX() -1 ] != 0) )
                push(new Point(p.getX() - 1, p.getY()), n);
        }
        if(fillmap[end.getY()][end.getX()]==Integer.MAX_VALUE){
            System.err.println("Path does not exist.");
            return null;
        } else
            System.out.println("Search is completed, go the path");
        List path = new ArrayList();
        path.add(end);
        int x = end.getX();
        int y = end.getY();
        n = Integer.MAX_VALUE;
        while((x!=start.getX())||(y!=start.getY())){

            if(fillmap[y + 1][x] < n) {
                tx = x;
                ty = y + 1;
                t = fillmap[y + 1][x];
            }

            if(fillmap[y - 1][x] < n) {
                tx = x;
                ty =y - 1;
                t = fillmap[y - 1][x];
            }

            if(fillmap[y][x + 1] < n) {
                tx= x + 1;
                ty = y;
                t = fillmap[y][x + 1];
            }

            if(fillmap[y][x - 1] < n) {
                tx= x - 1;
                ty = y;
                t = fillmap[y][x - 1];
            }
            x = tx;
            y = ty;
            n = t;
            path.add(new Point(x,y));
        }
        // Revers path
        Point[] result = new Point[path.size()];
        t = path.size();
        for(Object point: path)
            result[ --t ] = (Point)point;
        return result;
    }

    public static void main(String[] args){
        int[][] labyrinth = {
                {0,0,0,0,0,0,0,0,0,0},
                {0,1,6,6,6,6,6,1,1,0},
                {0,1,0,0,0,0,6,0,0,0},
                {0,1,0,1,1,1,1,1,1,0},
                {0,1,0,1,1,0,0,0,1,0}, // It is labyrinth
                {0,1,0,1,0,0,1,0,1,0}, // 0 - block
                {0,1,0,1,0,1,1,0,1,0}, // other numbers
                {0,1,0,0,0,0,0,0,1,0}, // is the degree of cross
                {0,1,8,1,1,1,1,1,1,0}, // 1 - the best
                {0,0,0,0,0,0,0,0,0,0}
        };

        LiAlgorithm pathFinder = new LiAlgorithm(labyrinth);
        Point start = pathFinder.new Point(1, 1);
        Point end = pathFinder.new Point(3, 3);
        Point[] path = pathFinder.find(start, end);

        for(Point p: path)
            System.out.println(p);
    }
}
