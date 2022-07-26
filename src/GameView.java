import javax.swing.*;
import java.awt.*;

public class GameView extends JPanel {
    public static Tetromino ghost = new Tetromino();

    GameView(int width, int height) {
        this.setPreferredSize(new Dimension(width, height));
        this.setVisible(true);
    }

    public void draw(Graphics2D g2d) {

        // Background
        setBounds(0, 0, Global.width, Global.height);
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, Global.width, Global.height);

        // Draw lines
        for (int i = 0; i < Global.h; i++) {
            g2d.setColor(Global.colors[7]);
            g2d.drawLine(0, i * Global.size, Global.width, i * Global.size);
        }
        for (int i = 0; i < Global.w; i++) {
            g2d.setColor(Global.colors[7]);
            g2d.drawLine(i * Global.size, 0, i * Global.size, Global.height);
        }

        // Draw ghost
        Tetromino nt = Global.tetrominos.get(Math.max(0, Global.tetrominos.size() - 1));
        ghost = new Tetromino(nt.x, nt.y, nt.id, nt.type, nt.rotation);
        ghost.updateGhost();
        ghost.showGhost(g2d);

        // Draw tetrominos
        for (Tetromino t : Global.tetrominos) {
            t.show(g2d);
        }
    }
}

