import frontend.GraphPanel;

import java.awt.*;

/**
 * Created by ninise on 9/18/15.
 */
public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GraphPanel();
            }
        });
    }
}
