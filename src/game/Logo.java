package game;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Logo extends Rectangle {
    public int spd = 4;
    public BufferedImage logo = Spritesheet.algoLogo;


    public Logo(int x, int y) {
        super(x,y,288, 64);
    }

    public void tick(int altura){
        if (y <= altura/4){
            y += spd;
        }

    }
    public void render(Graphics g) {
        g.drawImage(logo,x,y, 576,128,null);

    }
}
