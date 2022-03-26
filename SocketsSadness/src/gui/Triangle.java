import javax.swing.*;
import java.awt.*;

public class Triangle extends JPanel {
    Color color;

    int width = 30;
    int height = 30;

    public Triangle(Color color) {
        this.color = color;
        setPreferredSize(new Dimension (width,height));
    }

    public void paintComponent(Graphics g)    {
        super.paintComponent(g);
        g.drawPolygon(new int[] {0, 15,30}, new int[] {30, 0, 30}, 3);
        g.setColor(color);
        g.fillPolygon(new int[] {0, 15,30}, new int[] {30, 0, 30}, 3);


    }
    private void showGUI() {
        JPanel panel = new JPanel();
        panel.add(this, FlowLayout.CENTER);
        panel.setVisible(true);
    }
}
