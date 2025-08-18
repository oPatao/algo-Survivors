package game;

import java.awt.*;

public class Grass extends Rectangle {
    public Grass(int x, int y) {
        super(x, y, 32, 32);
    }

    public void render(Graphics g) {
        g.drawImage(Spritesheet.grama, x, y, 32, 32, null);
    }
}