package game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Bullet extends Rectangle {

    public int direction = 1, speed = 8, ladoX = 0, ladoY = 0, targetFrame = 15, curAnimation = 0, curFrame = 0, dobra = 1;
//    public BufferedImage bala = ;

    public Bullet(int x, int y, int dir, int ladoX, int ladoY) {
        super(x+8*ladoX,y+8*ladoY,20,20);
        this.direction = dir;
        this.ladoX = ladoX;
        this.ladoY = ladoY;
    }
    public void tick(){

        x+=speed*direction*ladoX;
        y+=speed*direction*ladoY;
        curFrame++;
        if(curFrame == targetFrame){
            curFrame = 0;
            curAnimation++;
            if(curAnimation == 4 ){
                curAnimation = 0;
            }
        }

        if (0>x || x >Game.WIDTH || 0 > y || y >Game.HEIGHT) {
            Player.bullets.remove(this);
        }

    }
    public void render(Graphics g){
        g.drawImage(Spritesheet.bullet[curAnimation],x,y,20,20,null);
    }
}
