package game;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class World {

    public static List<Blocks> blocos = new ArrayList<Blocks>();
    public static List<Grass> grama = new ArrayList<Grass>();


    public World(int x, int y) {
        int nBlocoX = x/32, nBlocoY = y/32;

        for (int xx =  0; xx < nBlocoX; xx++) {
            for (int yy =  0; yy < nBlocoY; yy++) {
                grama.add(new Grass(xx*32,yy*32));
            }
        }


        for (int xx = 0; xx< nBlocoX; xx++) {
            blocos.add(new Blocks(xx*32, 0));
        }
        for (int xx = 0; xx< nBlocoX; xx++) {
            blocos.add(new Blocks(xx*32, y-32));
        }
        for (int xx = 0; xx<nBlocoY; xx++) {
            blocos.add(new Blocks(0, xx*32));
        }
        for (int xx = 0; xx<nBlocoY; xx++) {
            blocos.add(new Blocks(x-32, xx*32));
        }
    }

    public static boolean isFree(int x, int y) {
        for (int i = 0; i < blocos.size(); i++ ) {
            Blocks b = blocos.get(i);
            if (b.intersects(new Rectangle(x,y,b.width,b.height))) {
                return false;
            }
        }
        return true;
    }

    public void render(Graphics g) {
        for (int i = 0; i < grama.size(); i++ ) {
            grama.get(i).render(g);
        }
        for(int i = 0; i<blocos.size(); i++){
            blocos.get(i).render(g);
        }
    }
}
