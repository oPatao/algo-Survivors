package game;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;


public class SkeliAss extends Rectangle {

    public int spd = 4, curAnimation = 0, curFrame = 0, targetFrame = 15, vida = 300;
    public BufferedImage direcao = Spritesheet.skeliFrente[0];

    public SkeliAss(int x, int y) {
        super(x,y,28,43);
    }

    public boolean colindindoEntreSi(int x, int y){
        Rectangle futuroRetangulo = new Rectangle(x,y,28,43);
        for(SkeliAss skeliAss : Game.skeliAsses){
            if(skeliAss == this){
                continue;
            }
            if (futuroRetangulo.intersects(skeliAss)) {
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

            direcao =Spritesheet.skeliRight[curAnimation];
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

            direcao = Spritesheet.skeliLeft[curAnimation];
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

            direcao = Spritesheet.skeliBack[curAnimation];
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

            direcao = Spritesheet.skeliFrente[curAnimation];
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
        g.drawImage(direcao,x,y,28,43,null);
    }
}
