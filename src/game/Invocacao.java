package game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Invocacao extends Rectangle {

    public int curFrame= 0, targetFrame = 6,curAnimation = 0;
    public boolean invocando = false;

    public static BufferedImage invocacao = Spritesheet.invocacao[0];


    public Invocacao() {
        super(0, 0, 32, 79);
    }

    public void invocar(int x, int y) {
        this.x = x;
        this.y = y;
        this.invocando = true;
        this.curAnimation = 0;
        this.curFrame = 0;
    }

    public void invocacaoTick(){
        invocacao = Spritesheet.invocacao[curFrame];
        curFrame++;
        if(curFrame == targetFrame){
            curFrame = 0;
            curAnimation++;
            if (curAnimation >= Spritesheet.invocacao.length - 1) {
                curAnimation = 0;
                invocando = false;
            }
        }
    }
    public void invocacaoRender(Graphics g){
        if(invocando){
            g.drawImage(Spritesheet.invocacao[curAnimation], this.x, this.y, 32, 79, null);
        }
    }
}
