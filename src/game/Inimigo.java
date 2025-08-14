package game;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Inimigo extends Rectangle {

    public int spd = 2, curAnimation = 0, curFrame = 0, targetFrame = 15;
    public BufferedImage direcao = Spritesheet.zumbiFrente[0];

    public Inimigo(int x, int y) {
        super(x,y,32,32);
    }

    public boolean colindindoEntreSi(int x, int y){
        Rectangle futuroRetangulo = new Rectangle(x,y,32,32);
        for(Inimigo inimigo : Game.inimigos){
            if(inimigo == this){
                continue;
            }
            if (futuroRetangulo.intersects(inimigo)) {
                return true;
            }
        }
        return false;
    }
    public void perseguirPlayer(){
        Player p = Game.player;

        if (x < p.x && World.isFree(x + spd, y) && !colindindoEntreSi(x + spd, y)) {
            if(new Random().nextInt(100) > 50){
                x+=spd;
            }

            direcao = Spritesheet.zumbiRight[curAnimation];
            curFrame++;
            if (curFrame == targetFrame) {
                curFrame = 0;
                curAnimation++;
                if (curAnimation >= 2) {
                    curAnimation = 0;
                }
            }
        } else if (x > p.x && World.isFree(x - spd, y) && !colindindoEntreSi(x - spd, y) ) {
            if(new Random().nextInt(100) > 50){
                x-=spd;
            }

            direcao = Spritesheet.zumbiLeft[curAnimation];
            curFrame++;
            if (curFrame == targetFrame) {
                curFrame = 0;
                curAnimation++;
                if (curAnimation >= 2) {
                    curAnimation = 0;
                }
            }
        }
        if (y < p.y && World.isFree(x , y + spd) && !colindindoEntreSi(x , y + spd)) {
            if(new Random().nextInt(100) > 50){
                y+=spd;
            }

            direcao = Spritesheet.zumbiFrente[curAnimation];
            curFrame++;
            if (curFrame == targetFrame) {
                curFrame = 0;
                curAnimation++;
                if (curAnimation >= 2) {
                    curAnimation = 0;
                }
            }
        } else if (y > p.y && World.isFree(x, y - spd) && !colindindoEntreSi(x , y - spd)) {
            if(new Random().nextInt(100) > 50){
                y-=spd;
            }

            direcao = Spritesheet.zumbiFrente[curAnimation];
            curFrame++;
            if (curFrame == targetFrame) {
                curFrame = 0;
                curAnimation++;
                if (curAnimation >= 2) {
                    curAnimation = 0;
                }
            }
        }
    }

    public void tick() {
        perseguirPlayer();
/*        if (right && World.isFree(x + spd, y)) {
            x += spd;
            direcao = Spritesheet.zumbiRight[curAnimation];
            curFrame++;
            if (curFrame == targetFrame) {
                curFrame = 0;
                curAnimation++;
                if (curAnimation >= 2) {
                    curAnimation = 0;
                }
            }
        } else if (left && World.isFree(x - spd, y)) {
            x -= spd;
            direcao = Spritesheet.zumbiLeft[curAnimation];
            curFrame++;
            if (curFrame == targetFrame) {
                curFrame = 0;
                curAnimation++;
                if (curAnimation >= 2) {
                    curAnimation = 0;
                }
            }

        }

        if (up && World.isFree(x, y - spd)) {
            y -= spd;
            direcao = Spritesheet.zumbiBack[curAnimation];
            curFrame++;
            if (curFrame == targetFrame) {
                curFrame = 0;
                curAnimation++;
                if (curAnimation >= 2) {
                    curAnimation = 0;
                }
            }
        } else if (down && World.isFree(x, y + spd)) {
            y += spd;
            direcao = Spritesheet.zumbiFrente[curAnimation];
            curFrame++;
            if (curFrame == targetFrame) {
                curFrame = 0;
                curAnimation++;
                if (curAnimation >= 2) {
                    curAnimation = 0;
                }
            }
        }
        direcao = Spritesheet.zumbiRight[curAnimation];
        curFrame++;
        if (curFrame == targetFrame) {
            curFrame = 0;
            curAnimation++;
            if (curAnimation >= 2) {
                curAnimation = 0;
            }
        }*/
    }
    public void render(Graphics g){
        //g.setColor(Color.CYAN);
        //g.fillRect(x,y,width,height);
        g.drawImage(direcao,x,y,32,32,null);
    }
}
