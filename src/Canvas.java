import javax.swing.*;
import java.awt.*;

public class Canvas extends JPanel {
    Canvas() {
        this.setPreferredSize(new Dimension(Global.width, Global.height));
        this.setVisible(true);
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
        for (Tetromino t : Global.tetrominos) {
            t.show(g2d);
        }
    }
}
