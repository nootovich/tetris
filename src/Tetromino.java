import java.awt.*;

public class Tetromino {
    public int x, y, type, rotation, id;
    public boolean done = false;
    public int[][] offsets;

    Tetromino() {
        this.y = -2;
        this.rotation = 0;
        this.x = Global.w / 2;
        this.id = Global.id_count++;
        this.type = (int) (Math.random() * 7);
        this.offsets = MainLoop.getOffsets(this.type, this.rotation);
    }

    public void incrementRotation() {
        this.rotation = (this.rotation + 1) % 4;
        this.offsets = MainLoop.getOffsets(this.type, this.rotation);
    }

    public void show(Graphics2D g2d) {
        g2d.setColor(Global.colors[this.type]);
        for (int i = 0; i < 4; i++) {
            int[] cords = this.offsets[i];
            g2d.fillRect((this.x + cords[1]) * Global.size + 1, (this.y + cords[0]) * Global.size + 1, Global.size - 2, Global.size - 2);
            if (!done) g2d.drawString(cords[0] + ":" + cords[1] + "   " + this.x + ":" + this.y, 20, 40 + 20 * i);
        }
    }

    public void update() {
        if (Global.heldKeys[0]) this.x--;
        if (Global.heldKeys[2]) this.x++;
        checkWallCollision();
        checkXCollision();
        this.y++;
        checkFloorCollision();
        if (!done) checkYCollision();
        if (Global.heldKeys[1] && !done) {
            this.y++;
            checkFloorCollision();
            if (!done) checkYCollision();
        }
        if (Global.heldKeys[1] && !done) {
            this.y++;
            checkFloorCollision();
            if (!done) checkYCollision();
        }
    }

    private void checkWallCollision() {
        for (int i = 0; i < 4; i++) {
            int[] cords = this.offsets[i];
            if (cords[1] == Global.w * 2) break;
            while (this.x + cords[1] < 0) this.x++;
            while (this.x + cords[1] >= Global.w) this.x--;
        }
    }

    private void checkFloorCollision() {
        for (int i = 0; i < 4; i++) {
            int[] cords = this.offsets[i];
            if (cords[1] == Global.w * 2) continue;
            if (this.y + cords[0] >= Global.h) {
                this.y--;
                this.done = true;
            }
        }
    }

    private void checkXCollision() {
        for (Tetromino t : Global.tetrominos) {
            if (this.id != t.id) {
                for (int i = 0; i < 4; i++) {
                    int[] cords = this.offsets[i];
                    if (cords[1] == Global.w * 2) continue;
                    for (int j = 0; j < 4; j++) {
                        int[] other_cords = t.offsets[j];
                        if (this.x + cords[1] == t.x + other_cords[1] && this.y + cords[0] == t.y + other_cords[0]) {
                            if (Global.heldKeys[0]) this.x++;
                            if (Global.heldKeys[2]) this.x--;
                        }
                    }
                }
            }
        }

    }

    private void checkYCollision() {
        boolean collision = false;

        // Going through all other tetrominos, getting their positions and checking for collisions
        for (Tetromino t : Global.tetrominos) {
            if (this.id != t.id) {
                for (int i = 0; i < 4; i++) {
                    int[] cords = this.offsets[i];
                    if (cords[1] == Global.w * 2) continue;
                    for (int j = 0; j < 4; j++) {
                        int[] other_cords = t.offsets[j];

                        // Collision
                        if (this.x + cords[1] == t.x + other_cords[1] &&
                                this.y + cords[0] == t.y + other_cords[0]) {

                            collision = true;
                            this.y--;

                            // Grace period start
                            if (Global.graceStart == -1 && !Global.heldKeys[1]) {
                                Global.graceStart = System.currentTimeMillis();

                                // If grace period time has run out place tetromino
                            } else if (System.currentTimeMillis() - Global.graceStart > Global.gracePeriodTime) {
                                this.done = true;
                                Global.graceStart = -1;
                            }
                        }
                    }

                    // If any square is outside of view end game
                    for (int j = 0; j < 4; j++) {
                        int[] other_cords = t.offsets[j];
                        if (collision && this.y + other_cords[0] <= 0) MainLoop.exit();
                    }
                }
            }
        }
        if (!collision) {
            Global.graceStart = -1;
        }
    }
}
