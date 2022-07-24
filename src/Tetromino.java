import java.awt.*;

public class Tetromino {
    public int x, y, type, rotation, id;
    public boolean done = false;
    private int[][] offsets = new int[4][2];

    Tetromino(int x, int y) {
        this.x = x;
        this.y = y;
        randomize();
    }

    Tetromino() {
        this.x = MainLoop.w / 2;
        this.y = -2;
        randomize();
        this.offsets = MainLoop.tetromino_offsets[this.type];
    }

    public void randomize() {
        this.type = (int) (Math.random() * 7);
        this.rotation = 0;//(int) (Math.random() * 4);
        this.id = MainLoop.id_count++;
    }

    public void incrementRotation() {
        this.rotation = (this.rotation + 1) % 4;
        this.offsets = rotate(offsets);
    }

    public void show(Graphics2D g2d) {
//        int[][] squares = rotate(MainLoop.tetromino_offsets[this.type], this.rotation);

        Color c = Color.WHITE;
        switch (this.type) {
            case 0 -> c = Color.BLUE;
            case 1 -> c = Color.RED;
            case 2 -> c = Color.GREEN;
            case 3 -> c = Color.MAGENTA;
            case 4 -> c = Color.YELLOW;
            case 5 -> c = new Color(255, 150, 0);
            case 6 -> c = Color.CYAN;
        }
        g2d.setColor(c);

        for (int i = 0; i < 4; i++) {
            int[] cords = this.offsets[i];//squares[i];
            g2d.fillRect((this.x + cords[0]) * MainLoop.size + 1, (this.y + cords[1]) * MainLoop.size + 1,
                    MainLoop.size - 2, MainLoop.size - 2);
        }
    }

    public void update() {
        if (MainLoop.heldKeys[0]) this.x--;
        if (MainLoop.heldKeys[2]) this.x++;
        checkWallCollision();
        checkXCollision();
        this.y++;
        checkFloorCollision();
        if (!done) checkYCollision();
        if (MainLoop.heldKeys[1] && !done) {
            this.y++;
            checkFloorCollision();
            if (!done) checkYCollision();
        }
        if (MainLoop.heldKeys[1] && !done) {
            this.y++;
            checkFloorCollision();
            if (!done) checkYCollision();
        }
    }

    private int[][] rotate(int[][] cords, int r) {
        if (r == 0) return cords;
        int[][] result = new int[4][2];
        // rotation 0 = return
        // rotation 1 = (y => -x, x => y)
        // rotation 2 = (y => -y, x => -x)
        // rotation 3 = (y => x, x => -y)
        for (int i = 0; i < 4; i++) {
            switch (r) {
                case 1 -> {
                    result[i][0] = -cords[i][1];
                    result[i][1] = cords[i][0];
                }
                case 2 -> {
                    result[i][0] = -cords[i][0];
                    result[i][1] = -cords[i][1];
                }
                case 3 -> {
                    result[i][0] = cords[i][1];
                    result[i][1] = -cords[i][0];
                }
            }
        }
        return result;
    }

    private int[][] rotate(int[][] cords) {
        int[][] result = new int[4][2];
        for (int i = 0; i < 4; i++) {
            result[i][0] = -cords[i][1];
            result[i][1] = cords[i][0];
        }
        return result;
    }

    private void checkWallCollision() {
//        int[][] squares = rotate(MainLoop.tetromino_offsets[this.type], this.rotation);
        for (int i = 0; i < 4; i++) {
            int[] cords = this.offsets[i];//squares[i];
            while (this.x + cords[0] < 0) this.x++;
            while (this.x + cords[0] >= MainLoop.w) this.x--;
        }
    }

    private void checkFloorCollision() {
//        int[][] squares = rotate(MainLoop.tetromino_offsets[this.type], this.rotation);
        for (int i = 0; i < 4; i++) {
            int[] cords = this.offsets[i];//squares[i];
            if (this.y + cords[1] >= MainLoop.h) {
                this.y--;
                this.done = true;
            }
        }
    }

    private void checkXCollision() {
        for (Tetromino t : MainLoop.tetrominos) {
            if (this.id != t.id) {
//                int[][] squares = rotate(MainLoop.tetromino_offsets[this.type], this.rotation);
//                int[][] other_squares = rotate(MainLoop.tetromino_offsets[t.type], t.rotation);
                for (int i = 0; i < 4; i++) {
                    int[] cords = this.offsets[i];//squares[i];
                    for (int j = 0; j < 4; j++) {
                        int[] other_cords = t.offsets[i];//other_squares[j];
                        if (this.x + cords[0] == t.x + other_cords[0] && this.y + cords[1] == t.y + other_cords[1]) {
                            if (MainLoop.heldKeys[0]) this.x++;
                            if (MainLoop.heldKeys[2]) this.x--;
                        }
                    }
                }
            }
        }

    }

    private void checkYCollision() {
        for (Tetromino t : MainLoop.tetrominos) {
            if (this.id != t.id) {
//                int[][] squares = rotate(MainLoop.tetromino_offsets[this.type], this.rotation);
//                int[][] other_squares = rotate(MainLoop.tetromino_offsets[t.type], t.rotation);
                for (int i = 0; i < 4; i++) {
                    int[] cords = this.offsets[i];//squares[i];
                    for (int j = 0; j < 4; j++) {
                        int[] other_cords = t.offsets[i];//other_squares[j];

//                        MainLoop.ptime -= MainLoop.framerate;
//                        g2d.setColor(Color.PINK);
//                        g2d.drawRect(
//                                (this.x + cords[0]) * MainLoop.size,
//                                (this.y + cords[1]) * MainLoop.size,
//                                MainLoop.size, MainLoop.size);
//                        g2d.setColor(Color.CYAN);
//                        g2d.drawRect(
//                                (t.x + other_cords[0]) * MainLoop.size,
//                                (t.y + other_cords[1]) * MainLoop.size,
//                                MainLoop.size, MainLoop.size);

                        if (this.x + cords[0] == t.x + other_cords[0] && this.y + cords[1] == t.y + other_cords[1]) {
                            this.y--;
                            this.done = true;
                            if (this.y <= 0) MainLoop.exit();
                        }
                    }
                }
            }
        }

    }
}
