import javax.swing.*;

public class HCP_GUI extends JDialog {
    private JPanel contentPane;


    public HCP_GUI() {
        setContentPane(contentPane);
        setModal(true);
    }

    public static void main(String[] args) {
        HCP_GUI dialog = new HCP_GUI();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
