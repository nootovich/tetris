import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.*;
import java.util.ArrayList;

public class MainLoop {
    public static int w = 20;
    public static int h = 30;
    public static int size = 30;
    public static int framerate = 1000 / 5;
    public static int width = w * size;
    public static int height = h * size;
    public static int id_count = 0;
    public static long ptime = System.currentTimeMillis();
    public static ArrayList<Tetromino> tetrominos = new ArrayList<>();
    public static Window screen = new Window();
    public static boolean needNew = false;
    public static boolean[] heldKeys = {false, false, false, false};

    public static void main(String[] args) {
        tetrominos.add(new Tetromino());
        initKeyboard();
        while (true) {
            long time = System.currentTimeMillis();
            if (time - ptime > framerate) {
                ptime = time;
                needNew = true;
                for (Tetromino t : tetrominos) {
                    if (!t.done) {
                        t.update();
                        needNew = false;
                    }
                }
                if (needNew) tetrominos.add(new Tetromino());
                screen.repaint();
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
                if (e.getKeyChar() == 'a') MainLoop.heldKeys[0] = true;
                if (e.getKeyChar() == 's') MainLoop.heldKeys[1] = true;
                if (e.getKeyChar() == 'd') MainLoop.heldKeys[2] = true;
                if (e.getKeyChar() == 'w' && !MainLoop.heldKeys[3]) {
                    tetrominos.get(tetrominos.size() - 1).incrementRotation();
                    MainLoop.heldKeys[3] = true;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyChar() == 'a') MainLoop.heldKeys[0] = false;
                if (e.getKeyChar() == 's') MainLoop.heldKeys[1] = false;
                if (e.getKeyChar() == 'd') MainLoop.heldKeys[2] = false;
                if (e.getKeyChar() == 'w') MainLoop.heldKeys[3] = false;
            }
        });
    }

    public static int[][] getOffsets(int type, int rotation) {
        int[][] result = new int[4][2];
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tetrominos", "root", "1234"); Statement stmt = conn.createStatement();) {
            ResultSet temp = stmt.executeQuery("select cords from offsets where type = " + type + " and rotation = " + rotation);
            temp.next();
            String[] cords = temp.getString("cords").split(",");
            for (int i = 0; i < 4; i++) {
                result[i][0] = Integer.parseInt(cords[2 * i]);
                result[i][1] = Integer.parseInt(cords[2 * i + 1]);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public static void exit() {
        if (System.currentTimeMillis() - ptime > 500) System.exit(69);
    }

}
