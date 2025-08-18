package game;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Logo extends Rectangle {
    public int spd = 4;
    public BufferedImage logo = Spritesheet.algoLogo;


    public Logo(int x, int y) {
        super(x,y,128, 576);
    }

    public void tick(int altura){
        if (y <= altura/4){
            y += spd;
            System.out.println(y);
        }

    }
    public void render(Graphics g) {
        g.drawImage(logo,x,y,null);

    }
}
