package game;

import java.awt.*;

public class Grass extends Rectangle {
    static int troca = 0;
    public Grass(int x, int y) {

        super(x, y, 32, 32);
    }

    public void render(Graphics g) {
        g.drawImage(Spritesheet.grama[troca], x, y, 32, 32, null);
    }
}