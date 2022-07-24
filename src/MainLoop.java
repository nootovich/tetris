import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MainLoop extends Global {

    public static void main(String[] args) {
        tetrominos.add(new Tetromino());
        initKeyboard();
        while (true) {
            // Wait for next frame
            long time = System.currentTimeMillis();
            if (time - ptime > framerate) {
                ptime = time;

                // Update tetrominos
                needNew = true;
                for (Tetromino t : tetrominos) {
                    if (!t.done) {
                        t.update();
                        needNew = false;
                    }
                }

                // Render
                screen.repaint();

                // Update board
                if (needNew) {
                    checkLines();
                    tetrominos.add(new Tetromino());
                }
            }
        }
    }

    private static void checkLines() {
        // Fill the board
        boolean[][] board = new boolean[h][w];
        for (Tetromino t : tetrominos) {
            for (int i = 0; i < 4; i++) {
                int[] cords = t.offsets[i];
                if (cords[1] == Global.w * 2) continue;
                if (t.y + cords[0] >= 20) continue;
                board[t.y + cords[0]][t.x + cords[1]] = true;
            }
        }

        // Remove finished lines
        for (int i = 0; i < board.length; i++) {
            // Check for finished lines
            boolean remove = true;
            for (boolean bb : board[i]) {
                if (!bb) remove = false;
            }

            // Remove if finished
            if (remove) {
                for (Tetromino t : tetrominos) {
                    for (int j = 0; j < 4; j++) {
                        int[] cords = t.offsets[j];
                        if (cords[1] == Global.w * 2) continue;
                        if (t.y + cords[0] == i) {
                            System.out.println("somehow?");
                            t.offsets[j][1] = w * 2; // TODO: Fix this somehow
                        } else if (t.y + cords[0] < i) {
                            t.offsets[j][0]++;
                        }
                    }
                }
            }
        }
    }

    private static void initKeyboard() {
        screen.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == 'a') heldKeys[0] = true;
                if (e.getKeyChar() == 's') heldKeys[1] = true;
                if (e.getKeyChar() == 'd') heldKeys[2] = true;
                if (e.getKeyChar() == 'w' && !heldKeys[3]) {
                    tetrominos.get(tetrominos.size() - 1).incrementRotation();
                    heldKeys[3] = true;
                }

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyChar() == 'a') heldKeys[0] = false;
                if (e.getKeyChar() == 's') heldKeys[1] = false;
                if (e.getKeyChar() == 'd') heldKeys[2] = false;
                if (e.getKeyChar() == 'w') heldKeys[3] = false;
            }
        });
    }

    public static void exit() {
        if (System.currentTimeMillis() - ptime > 500) System.exit(69);
    }

}
