package game;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;


public class Player extends Rectangle {

    public int vida,spd = 4, curAnimation = 0, curFrame = 0, targetFrame = 15, dir = 1, contadorTiro, modCadencia, ladoX,ladoY, modTiros = 1;
    public boolean right, left, up, down, shoot = true, tiroUp, tiroDown, tiroLeft, tiroRight;
    public BufferedImage direcao = Spritesheet.playerFront[0];



    public static List<Bullet> bullets = new ArrayList<Bullet>();

    public Player(int x, int y) {
        super(x,y,32,32);
        this.vida = 3;
    }
    public void resetaPlayer(int StartX, int StartY) {
        this.x = StartX;
        this.y = StartY;
        this.vida = 3;
        this.spd = 4;
        this.modCadencia = 0;
        this.modTiros = 1;

        right = left = up = down = false;
        tiroUp = tiroLeft = tiroRight = tiroDown = false;
    }

    public void tick() {
        if (right && World.isFree(x + spd, y)) {
            x += spd;
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

        if (tiroUp){
            ladoY = -1;
        }else if (tiroDown){
            ladoY = 1;
        }
        if (tiroRight){
            ladoX = 1;
        }else if (tiroLeft){
            ladoX = -1;
        }

        contadorTiro = contadorTiro + 1 + modCadencia;


        shoot = ladoX != 0 || ladoY != 0;

            if (contadorTiro >= 30) {

                if (shoot) {

                    for (int i = 1; i <= modTiros; i++) {
                        bullets.add(new Bullet(x+(10*i*ladoY), y+(10*i*ladoX), dir, ladoX, ladoY));
                    }
                    }
                contadorTiro = 0;
            }

            ladoY = 0;
            ladoX = 0;


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
