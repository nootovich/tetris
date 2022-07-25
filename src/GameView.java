import javax.swing.*;
import java.awt.*;

public class GameView extends JPanel {
    public static Tetromino ghost = new Tetromino();
    GameView(int width, int height) {
        this.setPreferredSize(new Dimension(width, height));
        this.setVisible(true);
    }

    public void draw(Graphics2D g2d) {
        setBounds(0, 0, Global.width, Global.height);
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());

        Tetromino nt = Global.tetrominos.get(Math.max(0, Global.tetrominos.size() - 1));
        ghost = new Tetromino(nt.x, nt.y, nt.id, nt.type, nt.rotation);
        ghost.updateGhost();
        ghost.showGhost(g2d);

        for (Tetromino t : Global.tetrominos) {
            t.show(g2d);
        }
    }
}

