package frontend.Nodes;

import frontend.KindItems;

import java.awt.*;
import java.util.List;

/**
 * @author Ninise
 */

public class Node {

    private Point p;
    private int r;
    private Color color;
    private KindItems kind;
    private boolean selected = false;
    private Rectangle b = new Rectangle();

    public static int id = 0;
    private int id_local;

    /**
     * Construct a new node.
     */
    public Node(Point p, int r, Color color, KindItems kind) {
        this.p = p;
        this.r = r;
        this.color = color;
        this.kind = kind;
        setBoundary(b);
        this.id_local = ++id;
    }

    /**
     * Reset ID
     */
    public static void resetId() {
        id = 0;
    }

    public int getId() {
        return id_local;
    }

    /**
     * Calculate this node's rectangular boundary.
     */
    private void setBoundary(Rectangle b) {
        b.setBounds(p.x - r, p.y - r, 2 * r, 2 * r);
    }

    /**
     * Draw this node.
     */
    public void draw(Graphics g) {
        g.setColor(this.color);
        if (this.kind == KindItems.Circular) {
            g.fillOval(b.x, b.y, b.width, b.height);
        } else if (this.kind == KindItems.Rounded) {
            g.fillRoundRect(b.x, b.y, b.width, b.height, r, r);
        } else if (this.kind == KindItems.Square) {
            g.fillRect(b.x, b.y, b.width, b.height);
        }
        if (selected) {
            g.setColor(Color.darkGray);
            g.drawRect(b.x, b.y, b.width, b.height);
        }
        g.drawString(String.valueOf(id_local), b.x, b.y);
    }

    /**
     * Return this node's location.
     */
    public Point getLocation() {
        return p;
    }

    /**
     * Return true if this node contains p.
     */
    public boolean contains(Point p) {
        return b.contains(p);
    }

    /**
     * Return true if this node is selected.
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * Mark this node as selected.
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * Collected all the selected nodes in list.
     */
    public static void getSelected(List<Node> list, List<Node> selected) {
        selected.clear();
        for (Node n : list) {
            if (n.isSelected()) {
                selected.add(n);
            }
        }
    }

    /**
     * Select no nodes.
     */
    public static void selectNone(List<Node> list) {
        for (Node n : list) {
            n.setSelected(false);
        }
    }

    /**
     * Select a single node; return true if not already selected.
     */
    public static boolean selectOne(List<Node> list, Point p) {
        for (Node n : list) {
            if (n.contains(p)) {
                if (!n.isSelected()) {
                    Node.selectNone(list);
                    n.setSelected(true);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Select each node in r.
     */
    public static void selectRect(List<Node> list, Rectangle r) {
        for (Node n : list) {
            n.setSelected(r.contains(n.p));
        }
    }

    /**
     * Toggle selected state of each node containing p.
     */
    public static void selectToggle(List<Node> list, Point p) {
        for (Node n : list) {
            if (n.contains(p)) {
                n.setSelected(!n.isSelected());
            }
        }
    }

    /**
     * Update each node's position by d (delta).
     */
    public static void updatePosition(List<Node> list, Point d) {
        for (Node n : list) {
            if (n.isSelected()) {
                n.p.x += d.x;
                n.p.y += d.y;
                n.setBoundary(n.b);
            }
        }
    }

    /**
     * Update each node's radius r.
     */
    public static void updateRadius(List<Node> list, int r) {
        for (Node n : list) {
            if (n.isSelected()) {
                n.r = r;
                n.setBoundary(n.b);
            }
        }
    }

    /**
     * Update each node's color.
     */
    public static void updateColor(List<Node> list, Color color) {
        for (Node n : list) {
            if (n.isSelected()) {
                n.color = color;
            }
        }
    }

    /**
     * Update each node's kind.
     */
    public static void updateKind(List<Node> list, KindItems kind) {
        for (Node n : list) {
            if (n.isSelected()) {
                n.kind = kind;
            }
        }
    }
}
