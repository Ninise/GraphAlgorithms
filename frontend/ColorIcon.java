package frontend;

import javax.swing.*;
import java.awt.*;

/**
 * @author Ninise
 */

public class ColorIcon implements Icon {

    private static final int WEIGHT = 20;
    private static final int HEIGHT = 20;
    private Color color;

    public ColorIcon(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
         this.color = color;
    }

    public void paintIcon(Component c, Graphics g, int x, int y) {
        g.setColor(color);
        g.fillRect(x, y, WEIGHT, HEIGHT);
    }

    public int getIconWidth() {
        return WEIGHT;
    }

    public int getIconHeight() {
        return HEIGHT;
    }
}
