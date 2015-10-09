package frontend.Nodes;

import java.awt.*;

/**
 * @author Ninise
 */

public class Edge {

    private static int we;
    private int local_we;

    public Node n1;
    public Node n2;

    public Edge(Node n1, Node n2) {
        this.n1 = n1;
        this.n2 = n2;
        this.local_we = calcDistance();
    }

    public void draw(Graphics g) {
        Point p1 = n1.getLocation();
        Point p2 = n2.getLocation();
        g.setColor(Color.darkGray);
        g.drawLine(p1.x, p1.y, p2.x, p2.y);

        g.drawString(calcDistance() + "", (p2.x + p1.x) / 2, (p2.y +  p1.y) / 2);
        setWe();
    }

    private int calcDistance() {
        Point p1 = n1.getLocation();
        Point p2 = n2.getLocation();

        we = (int) (Math.sqrt(Math.pow(p2.x  - p1.x, 2) + (Math.pow(p2.y - p1.y, 2))));

        return we;
    }

    private void setWe() {
        local_we = we;
    }

    public int getWe() {
        return local_we;
    }
}
