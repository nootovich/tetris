import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.*;

// TODO: Fix a bug when you get invalid positions(oob, tp across a map) when rotating

public class MainLoop extends Global {
    public static void main(String[] args) {

        // Init
        tetrominos.add(new Tetromino());
        initKeyboard();
        while (!gameover) {

            // Wait for next frame
            long time = System.currentTimeMillis();
            if (time - prevFrame > framerate) {
                prevFrame = time;

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

                // Update game
                if (needNew) {
                    checkLines();
                    tetrominos.add(new Tetromino());
                }
            }
        }
        while (gameover) exit();
    }

    private static void checkLines() {

        // Get positions of all tetrominos and fill the board with their positions
        boolean[][] board = new boolean[h][w];
        for (Tetromino t : tetrominos) {
            for (int i = 0; i < 4; i++) {
                int[] cords = t.offsets[i];
                if (cords[1] == Global.w * 2) continue;
                if (t.y + cords[0] >= 20) continue;
                board[t.y + cords[0]][t.x + cords[1]] = true;
            }
        }

        // Go through every line
        for (int i = 0; i < board.length; i++) {

            // Check if it is full
            boolean remove = true;
            for (boolean bb : board[i]) {
                if (!bb) {
                    remove = false;
                    break;
                }
            }

            // If line needs to be removed go through every tetromino and remove squares at a respective y coordinate while also moving everything from above down
            if (remove) {
                for (Tetromino t : tetrominos) {
                    for (int j = 0; j < 4; j++) {
                        int[] cords = t.offsets[j];

                        // If square is already oob then skip
                        if (cords[1] == Global.w * 2) continue;

                        // Moving square oob instead of removing for simplicity
                        if (t.y + cords[0] == i) {
                            t.offsets[j][1] = w * 2;

                            // Move square down if it is not removed
                        } else if (t.y + cords[0] < i) {
                            t.offsets[j][0]++;
                        }
                    }
                }
            }
        }
    }

    private static void initKeyboard() { // TODO: make prettier
        screen.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == 'w' && !heldKeys[3]) {
                    tetrominos.get(tetrominos.size() - 1).incrementRotation();
                    heldKeys[3] = true;
                }
                if (e.getKeyChar() == 'a') heldKeys[0] = true;
                if (e.getKeyChar() == 's') heldKeys[1] = true;
                if (e.getKeyChar() == 'd') heldKeys[2] = true;

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

    public static int[][] getOffsets(int type, int rotation) {
        int[][] result = new int[4][2];
        try (Connection conn = DriverManager.getConnection(Secret.url, Secret.username, Secret.password); Statement stmt = conn.createStatement()) {
            ResultSet temp = stmt.executeQuery("select cords from offsets where type = " + type + " and rotation = " + rotation);
            temp.next();
            String[] cords = temp.getString("cords").split(",");
            for (int i = 0; i < 4; i++) {
                result[i][1] = Integer.parseInt(cords[2 * i]);
                result[i][0] = Integer.parseInt(cords[2 * i + 1]);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void exit() {
        gameover = true;
        if (System.currentTimeMillis() - prevFrame > 500) System.exit(69);
    }

}
