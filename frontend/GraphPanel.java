package frontend;

import frontend.Nodes.Edge;
import frontend.Nodes.Node;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.*;
import java.util.List;

/**
 * @author Ninise
 */

public class GraphPanel extends JComponent{
    // toolbar panel
    private ControlPanel control = new ControlPanel();

    private List<Node> nodes = new ArrayList<>();
    private List<Node> selected = new ArrayList<>();
    private List<Edge> edges = new ArrayList<>();

    private KindItems kind = KindItems.Circular;

    private Point mousePt = new Point(ConstParametres.WEIGHT / 2, ConstParametres.HEIGHT / 2);
    private Rectangle mouseRect = new Rectangle();

    public GraphPanel() {
        createFrame();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(ConstParametres.WEIGHT, ConstParametres.HEIGHT);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(new Color(0x00f0f0f0));
        g.fillRect(0, 0, getWidth(), getHeight());
        for (Edge e : edges) {
            e.draw(g);
        }
        for (Node n : nodes) {
            n.draw(g);
        }
        if (ConstParametres.selecting) {
            g.setColor(Color.darkGray);
            g.drawRect(mouseRect.x, mouseRect.y,
                    mouseRect.width, mouseRect.height);
        }
    }

    void createFrame(){
        JFrame  mainFrame = new JFrame(ConstParametres.MAIN_FORM_NAME);

        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.setOpaque(true);
        this.addMouseListener(new MouseHandler());
        this.addMouseMotionListener(new MouseMotionHandler());

        mainFrame.add(this.control, BorderLayout.NORTH);
        mainFrame.add(new JScrollPane(this), BorderLayout.CENTER);
        mainFrame.getRootPane().setDefaultButton(this.control.defaultButton);

        mainFrame.pack();
        mainFrame.setLocationByPlatform(true);
        mainFrame.setVisible(true);
    }

    class ControlPanel extends JToolBar {
        private Action newNode = new NewNodeAction("New");
        private Action clearAll = new ClearAction("Clear");
        private Action kind = new KindComboAction("Kinds");
        private Action color = new ColorAction("Color");
        private Action connect = new ConnectAction("Connect");
        private Action delete = new DeleteAction("Delete");
        private Action random = new RandomAction("Random");
        private JButton defaultButton = new JButton(newNode);
        private JComboBox kindCombo = new JComboBox();
        private ColorIcon nodeColor = new ColorIcon(Color.pink);
        private JPopupMenu popup = new JPopupMenu();

        public ControlPanel() {
            this.setLayout(new FlowLayout(FlowLayout.LEFT));
            this.setBackground(Color.lightGray);

            this.add(defaultButton);
            this.add(new JButton(clearAll)); // clear action
            this.add(kindCombo);
            this.add(new JLabel(nodeColor));

            JSpinner jsNodeSize = new JSpinner();
            jsNodeSize.setModel(new SpinnerNumberModel(ConstParametres.RADIUS, 5, 100, 5));

            jsNodeSize.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    JSpinner s = (JSpinner) e.getSource();
                    ConstParametres.radiusCanChanged = (Integer) s.getValue();
                    Node.updateRadius(nodes, ConstParametres.radiusCanChanged);
                    GraphPanel.this.repaint();
                }
            });

            this.add(new JLabel(ConstParametres.SIZE_LABEL_NAME));
            this.add(jsNodeSize);
            this.add(new JButton(random)); // random action

            // POPUP MENU
            popup.add(new JMenuItem(newNode)); // newNode action
            popup.add(new JMenuItem(delete)); // delete node action
            popup.add(new JMenuItem(connect)); // connect action
            popup.add(new JMenuItem(color)); // color action
            JMenu subMenu = new JMenu("KindItems");
            for(KindItems k : KindItems.values()) {
                kindCombo.addItem(k);
                subMenu.add(new JMenuItem(new KindItemAction(k)));
            }
            popup.add(subMenu);
            kindCombo.addActionListener(kind); //kind action

        }

        class KindItemAction extends AbstractAction {

            private KindItems k;

            public KindItemAction(KindItems k) {
                super(k.toString());
                this.k = k;
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                kindCombo.setSelectedItem(k);
            }
        }
    }

    private class MouseHandler extends MouseAdapter {

        @Override
        public void mouseReleased(MouseEvent e) {
            ConstParametres.selecting = false;
            mouseRect.setBounds(0, 0, 0, 0);
            if (e.isPopupTrigger()) {
                showPopup(e);
            }
            e.getComponent().repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) {
            mousePt = e.getPoint();
            if (e.isShiftDown()) {
                Node.selectToggle(nodes, mousePt);
            } else if (e.isPopupTrigger()) {
                Node.selectOne(nodes, mousePt);
                showPopup(e);
            } else if (Node.selectOne(nodes, mousePt)) {
                ConstParametres.selecting = false;
            } else {
                Node.selectNone(nodes);
                ConstParametres.selecting = true;
            }
            e.getComponent().repaint();
        }

        private void showPopup(MouseEvent e) {
            control.popup.show(e.getComponent(), e.getX(), e.getY());
        }
    }

    private class MouseMotionHandler extends MouseMotionAdapter {

        Point delta = new Point();

        @Override
        public void mouseDragged(MouseEvent e) {
            if (ConstParametres.selecting) {
                mouseRect.setBounds(
                        Math.min(mousePt.x, e.getX()),
                        Math.min(mousePt.y, e.getY()),
                        Math.abs(mousePt.x - e.getX()),
                        Math.abs(mousePt.y - e.getY()));
                Node.selectRect(nodes, mouseRect);
            } else {
                delta.setLocation(
                        e.getX() - mousePt.x,
                        e.getY() - mousePt.y);
                Node.updatePosition(nodes, delta);
                mousePt = e.getPoint();
            }
            e.getComponent().repaint();
        }
    }

     /*****************************
     *           ACTIONS          *
     *****************************/

    private class NewNodeAction extends AbstractAction {
        public NewNodeAction(String name) {
            super(name);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Node.selectNone(nodes);
            Point p = mousePt.getLocation();
            Color color = control.nodeColor.getColor();
            Node n = new Node(p, ConstParametres.radiusCanChanged, color, kind);
            n.setSelected(true);
            nodes.add(n);
            repaint();
        }
    }

    private class ClearAction extends AbstractAction {
        public ClearAction(String name) {
            super(name);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            nodes.clear();
            edges.clear();
            repaint();
        }
    }

    private class ColorAction extends AbstractAction{
        public ColorAction(String name) {
            super(name);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Color color = control.nodeColor.getColor();
            color = JColorChooser.showDialog(
                    GraphPanel.this, "Choose a color", color);
            if (color != null) {
                Node.updateColor(nodes, color);
                control.nodeColor.setColor(color);
                control.repaint();
                repaint();
            }
        }
    }

    private class ConnectAction extends AbstractAction {
        public ConnectAction(String name) {
            super(name);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Node.getSelected(nodes, selected);
            if (selected.size() > 1) {
                for (int i = 0; i < selected.size() - 1; ++i) {
                    Node n1 = selected.get(i);
                    Node n2 = selected.get(i + 1);
                    edges.add(new Edge(n1, n2));
                }
            }
            repaint();
        }
    }

    private class DeleteAction extends AbstractAction {
        public DeleteAction(String name) {
            super(name);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            ListIterator<Node> iter = nodes.listIterator();
            while (iter.hasNext()) {
                Node n = iter.next();
                if (n.isSelected()) {
                    deleteEdges(n);
                    iter.remove();
                }
            }
            repaint();
        }

        private void deleteEdges(Node n) {
            ListIterator<Edge> iter = edges.listIterator();
            while (iter.hasNext()) {
                Edge e = iter.next();
                if (e.n1 == n || e.n2 == n) {
                    iter.remove();
                }
            }
        }
    }

    private class RandomAction extends AbstractAction {
        public RandomAction(String name) {
            super(name);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < 16; i++) {
                Point p = new Point(ConstParametres.rnd.nextInt(getWidth()), ConstParametres.rnd.nextInt(getHeight()));
                nodes.add(new Node(p, ConstParametres.radiusCanChanged, new Color(ConstParametres.rnd.nextInt()), kind));
            }
            repaint();
        }
    }

    private class KindComboAction extends AbstractAction {

        public KindComboAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent e) {
            JComboBox combo = (JComboBox) e.getSource();
            kind = (KindItems) combo.getSelectedItem();
            Node.updateKind(nodes, kind);
            repaint();
        }
    }
}

