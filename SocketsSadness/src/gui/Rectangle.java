import javax.swing.*;
import java.awt.*;

public class Rectangle extends JPanel {
    Color color;

    int width = 60;
    int height = 30;

    public Rectangle(Color color) {
        this.color = color;
        setPreferredSize(new Dimension (width,height));
    }

    public void paintComponent(Graphics g)    {
        super.paintComponent(g);
        g.drawRect(0, 0, width, height);
        g.setColor(color);
        g.fillRect(0, 0, width, height);


    }
    private void showGUI() {
        JPanel panel = new JPanel();
        panel.add(this, FlowLayout.CENTER);
        panel.setVisible(true);
    }
}
