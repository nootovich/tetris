import java.awt.*;
import java.util.ArrayList;

public class Global {
    public static final int[][][] tetromino_offsets = {{ // TODO: Fix this somehow
            {0, -2}, {0, -1}, {0, 0}, {0, 1}}, {
            {0, -1}, {0, 0}, {-1, 0}, {-1, 1}}, {
            {-1, -1}, {-1, 0}, {0, 0}, {0, 1}}, {
            {-1, 0}, {0, 0}, {1, 0}, {0, 1}}, {
            {-1, 0}, {0, 0}, {-1, 1}, {0, 1}}, {
            {-1, 1}, {-1, 0}, {0, 0}, {1, 0}}, {
            {-1, -1}, {-1, 0}, {0, 0}, {1, 0}}};
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
    public static int w = 10;
    public static int h = 20;
    public static int size = 30;
    public static int width = w * size;
    public static int height = h * size;
    public static int framerate = 1000 / 12;
    public static int id_count = 0;
    public static long ptime = System.currentTimeMillis();
    public static ArrayList<Tetromino> tetrominos = new ArrayList<>();
    public static Window screen = new Window();
    public static int[][] getOffsets(int type) {
        return  tetromino_offsets[type].clone();
    }
}
