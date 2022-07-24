import javax.swing.*;

public class Window extends JFrame {
    Canvas canvas;

    Window() {
        canvas = new Canvas();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(canvas);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
