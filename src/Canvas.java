import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class Canvas extends JPanel {
    Canvas() {
        this.setPreferredSize(new Dimension(MainLoop.width, MainLoop.height));
        this.setVisible(true);
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
        for (Tetromino t : MainLoop.tetrominos) {
            t.show(g2d);
        }
        g2d.setColor(Color.WHITE);
        g2d.drawString(Arrays.toString(MainLoop.heldKeys), 10, 10);
//        g2d.drawString(MainLoop.tetrominos.get(MainLoop.tetrominos.size() - 1).rotation + "", 10, 30);
    }
}
