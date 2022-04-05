package gui;

import javax.swing.*;
import java.awt.*;

public class Circle extends JPanel {
    Color color;

    int width = 30;
    int height = 30;

    public Circle(Color color) {
        this.color = color;
        setPreferredSize(new Dimension (width,height));
    }

    public void paintComponent(Graphics g)    {
        super.paintComponent(g);
        g.drawOval(0, 0, width, height);
        g.setColor(color);
        g.fillOval(0, 0, width, height);


    }
    private void showGUI() {
        JPanel panel = new JPanel();
        panel.add(this, FlowLayout.CENTER);
        panel.setVisible(true);
    }
}
