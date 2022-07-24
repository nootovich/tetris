import java.awt.*;

public class Tetromino {
    public int x, y, type, rotation, id;
    public boolean done = false;
    private int[][] offsets;

    Tetromino() {
        this.x = MainLoop.w / 2;
        this.y = -2;
        this.rotation = 0;
        this.id = MainLoop.id_count++;
        this.type = (int) (Math.random() * 7);
        this.offsets = MainLoop.getOffsets(this.type, this.rotation);
    }

    public void incrementRotation() {
        this.rotation = (this.rotation + 1) % 4;
        this.offsets = MainLoop.getOffsets(this.type, this.rotation);
    }

    public void show(Graphics2D g2d) {
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
            int[] cords = this.offsets[i];
            g2d.fillRect((this.x + cords[1]) * MainLoop.size + 1, (this.y + cords[0]) * MainLoop.size + 1, MainLoop.size - 2, MainLoop.size - 2);
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

    private void checkWallCollision() {
        for (int i = 0; i < 4; i++) {
            int[] cords = this.offsets[i];
            while (this.x + cords[1] < 0) this.x++;
            while (this.x + cords[1] >= MainLoop.w) this.x--;
        }
    }

    private void checkFloorCollision() {
        for (int i = 0; i < 4; i++) {
            int[] cords = this.offsets[i];
            if (this.y + cords[0] >= MainLoop.h) {
                this.y--;
                this.done = true;
            }
        }
    }

    private void checkXCollision() {
        for (Tetromino t : MainLoop.tetrominos) {
            if (this.id != t.id) {
                for (int i = 0; i < 4; i++) {
                    int[] cords = this.offsets[i];
                    for (int j = 0; j < 4; j++) {
                        int[] other_cords = t.offsets[i];
                        if (this.x + cords[1] == t.x + other_cords[1] && this.y + cords[0] == t.y + other_cords[0]) {
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
                for (int i = 0; i < 4; i++) {
                    int[] cords = this.offsets[i];
                    for (int j = 0; j < 4; j++) {
                        int[] other_cords = t.offsets[j];
                        if (this.x + cords[1] == t.x + other_cords[1] && this.y + cords[0] == t.y + other_cords[0]) {
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
