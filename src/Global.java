import java.awt.*;
import java.util.ArrayList;

public class Global {
    public static final Color[] colors = {
            Color.BLUE,
            Color.RED,
            Color.GREEN,
            Color.MAGENTA,
            Color.YELLOW,
            new Color(255, 150, 0),
            Color.CYAN};
    public static boolean[] heldKeys = {false, false, false, false};
    public static boolean needNew = false;
    public static boolean gameover = false;
    public static int w = 10;
    public static int h = 20;
    public static int size = 30;
    public static int width = w * size;
    public static int height = h * size;
    public static int framerate = 1000 / 8;
    public static int id_count = 0;
    public static long prevFrame = System.currentTimeMillis();
    public static long graceStart = -1;
    public static int gracePeriodTime = 400;
    public static ArrayList<Tetromino> tetrominos = new ArrayList<>();
    public static Window screen = new Window();
}
