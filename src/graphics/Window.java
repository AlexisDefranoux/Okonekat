package graphics;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    private GameContainer panel;
    private static final int width = 540;
    private static final int height = 540;

    public Window(){

        panel = new GameContainer();
        panel.setBackground(Color.ORANGE);
        this.setSize(width, height);
        this.setTitle("Take no ko");
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setContentPane(panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void renew(){
        panel.renew();
        panel.repaint();
    }

}
