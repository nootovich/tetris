import javax.swing.*;

public class Window extends JFrame {
    generalView generalView;

    Window() {
        generalView = new generalView();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(generalView);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
