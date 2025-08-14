package game;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;


public class Player extends Rectangle {

    public int spd = 4, curAnimation = 0, curFrame = 0, targetFrame = 15, dir = 1, contadorTiro, modificadorTiro, ladoX = 1,ladoY, controleTiro;
    public boolean right, left, up, down, shoot = true, tiroUp, tiroDown, tiroLeft, tiroRight;
    public BufferedImage direcao = Spritesheet.playerFront[0];



    public static List<Bullet> bullets = new ArrayList<Bullet>();

    public Player(int x, int y) {
        super(x,y,32,32);
    }
    public void tick() {
        if (right && World.isFree(x + spd, y)) {
            x += spd;
            ladoX = 1;
            direcao = Spritesheet.playerRight[curAnimation];
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
            ladoX = -1;
            direcao = Spritesheet.playerLeft[curAnimation];
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
            ladoY = -1;
            direcao = Spritesheet.playerBack[curAnimation];
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
            ladoY = 1;
            direcao = Spritesheet.playerFront[curAnimation];
            curFrame++;
            if (curFrame == targetFrame) {
                curFrame = 0;
                curAnimation++;
                if (curAnimation >= 2) {
                    curAnimation = 0;
                }
            }
        }
        if(ladoX != 0) controleTiro = ladoX;

        if (ladoX == 0 && ladoY == 0) {
            ladoX = controleTiro;
        }

        contadorTiro = contadorTiro + 1 + modificadorTiro;


        if (contadorTiro == 30) {

            if (shoot) {
                bullets.add(new Bullet(x, y, dir, ladoX, ladoY));
            }
            contadorTiro = 0;
        }

        ladoX = 0;
        ladoY = 0;
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).tick();
        }
    }
    public void render(Graphics g){
        //g.setColor(Color.CYAN);
        //g.fillRect(x,y,width,height);
        g.drawImage(direcao,x,y,32,32,null);
        for(int i = 0; i < bullets.size(); i++){
            bullets.get(i).render(g);
        }
    }
}
