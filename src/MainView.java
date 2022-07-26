import javax.swing.*;
import java.awt.*;

public class MainView extends JPanel {
    Tetromino next = new Tetromino(Global.next);
    private final GameView gameView;
    private final int width = Global.width;
    private final int height = Global.height;
    private final int size = Global.size;

    MainView() {
        gameView = new GameView(width, height);
        this.setPreferredSize(new Dimension(width + size * 6, height ));
        this.setVisible(true);
        this.add(gameView);
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setFont(new Font("Consolas", Font.PLAIN, size));
        g2d.setColor(Global.colors[8]);
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
        g2d.setColor(Color.BLACK);
        g2d.fillRect(width + size, size * 8, size * 4, size * 4);
        next.showNext(g2d, Global.w + 2.5f, 10f, size * 0.8f);
        g2d.setColor(Color.WHITE);
        g2d.drawString("Highscore:", width + size / 4, size);
        g2d.drawString("" + Global.highscore, width + size / 4, size * 2);
        g2d.drawString("Score:", width + size / 4, size * 3);
        g2d.drawString("" + Global.score, width + size / 4, size * 4);
        gameView.draw(g2d);
    }
}