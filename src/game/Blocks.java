package game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Blocks extends Rectangle {
    static int troca = 0;
    public Blocks(int x, int y) {
        super(x,y,32,32);
    }
    public void render(Graphics g) {
        g.drawImage(Spritesheet.pedra[troca],x,y,32,32,null);

    }

}
