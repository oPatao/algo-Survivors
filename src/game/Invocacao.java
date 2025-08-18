package game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Invocacao extends Rectangle {

    public int curFrame= 0, targetFrame =60,curAnimation = 0;

    public static BufferedImage invocacao = Spritesheet.invocacao[0];


    public Invocacao(int x, int y) {
        super(x, y, 32, 79);
    }

    public void invocacaoTick(){
        invocacao = Spritesheet.invocacao[curFrame];
        curFrame++;
        if(curFrame == targetFrame){
            curFrame = 0;
            curAnimation++;
            if (curAnimation == 68) {
                curAnimation = 0;
            }
        }
    }
    public void invocacaoRender(Graphics g){
        g.drawImage(invocacao,x,y,null);
    }
}
