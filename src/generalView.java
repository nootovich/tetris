import javax.swing.*;
import java.awt.*;

public class generalView extends JPanel {
    private GameView gameView;
    private int width = Global.width;
    private int height = Global.height;
    private int size = Global.size;
    Tetromino next = new Tetromino(Global.next);

    generalView() {
        gameView = new GameView(width, height);
        this.setPreferredSize(new Dimension(width + size * 6, height + size * 4));
        this.setVisible(true);
        this.add(gameView);
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setFont(new Font("Consolas", Font.PLAIN, size * 2));
        g2d.setColor(Color.GRAY);
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
        g2d.setColor(Color.BLACK);
        g2d.fillRect(width + size, size, size * 4, size * 4);
        next.showNext(g2d, Global.w + 3, 3, size);
        g2d.setColor(Color.WHITE);
        g2d.drawString("" + Global.score, width / 2, height + size * 3);
        gameView.draw(g2d);
    }
}
