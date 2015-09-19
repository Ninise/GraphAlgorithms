package frontend;

import com.sun.deploy.panel.ControlPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ninise on 9/18/15.
 */
public class GraphPanel {

    private JFrame mainFrame;
    private ControlPanel control = new ControlPanel();

    public GraphPanel() {
        createFrame();
    }

    void createFrame(){
        mainFrame = new JFrame(ConstParametres.MAIN_FORM_NAME);

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setPreferredSize(ConstParametres.MAIN_FORM_SIZE);

        mainFrame.pack();
        mainFrame.setLocationByPlatform(true);
        mainFrame.setVisible(true);
    }

}
