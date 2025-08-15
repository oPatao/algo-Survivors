package game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Spritesheet {
    public static BufferedImage spritesheet;

    public static BufferedImage[] playerFront;
    public static BufferedImage[] playerBack;
    public static BufferedImage[] playerLeft;
    public static BufferedImage[] playerRight;

    public static BufferedImage[] zumbiFrente;
    public static BufferedImage[] zumbiBack;
    public static BufferedImage[] zumbiLeft;
    public static BufferedImage[] zumbiRight;

    public static BufferedImage[] bullet;
    public static BufferedImage pedra;
    public static BufferedImage grama;
    public static BufferedImage algoLogo;

    public Spritesheet() throws IOException {
        try {
            spritesheet = ImageIO.read(getClass().getResource("/spritesheet.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        };
        playerFront = new BufferedImage[2];

        playerFront[0] = Spritesheet.getSprite(1, 11, 16,16);
        playerFront[1] = Spritesheet.getSprite(17, 11, 16,16);

        playerRight = new BufferedImage[2];
        playerRight[0] = Spritesheet.getSprite(35, 11, 16,16);
        playerRight[1] = Spritesheet.getSprite(51, 11, 16,16);

        playerBack = new BufferedImage[2];
        playerBack[0] = Spritesheet.getSprite(69, 11, 16,16);
        playerBack[1] = Spritesheet.getSprite(86, 11, 16,16);

        playerLeft = new BufferedImage[2];
        playerLeft[0] = Spritesheet.getSprite(112, 16, 16,16);
        playerLeft[1] = Spritesheet.getSprite(128, 16, 16,16);

        bullet = new BufferedImage[4];
        bullet[0] = Spritesheet.getSprite(63, 188, 10,10);
        bullet[1] = Spritesheet.getSprite(72, 188, 10,10);
        bullet[2] = Spritesheet.getSprite(81, 188, 10,10);
        bullet[3] = Spritesheet.getSprite(72, 188, 10,10);

        zumbiFrente = new BufferedImage[2];
        zumbiFrente[0] = Spritesheet.getSprite(32, 208, 16,16);
        zumbiFrente[1] = Spritesheet.getSprite(48, 208, 16,16);

        zumbiBack = new BufferedImage[2];
        zumbiBack[0] = Spritesheet.getSprite(112, 208, 16,16);
        zumbiBack[1] = Spritesheet.getSprite(128, 208, 16,16);

        zumbiLeft = new BufferedImage[3];
        zumbiLeft[0] = Spritesheet.getSprite(144, 208, 16,16);
        zumbiLeft[1] = Spritesheet.getSprite(160, 208, 16,16);
        zumbiLeft[2] = Spritesheet.getSprite(176, 208, 16,16);

        zumbiRight = new BufferedImage[3];
        zumbiRight[0] = Spritesheet.getSprite(64, 208, 16,16);
        zumbiRight[1] = Spritesheet.getSprite(80, 208, 16,16);
        zumbiRight[2] = Spritesheet.getSprite(96, 208, 16,16);


        try{
            pedra = ImageIO.read(getClass().getResource("/preda1.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try{
            grama = ImageIO.read(getClass().getResource("/grama.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try{
            algoLogo = ImageIO.read(getClass().getResource("/algoLogo.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static BufferedImage getSprite(int x, int y, int width, int height) throws IOException {
        return spritesheet.getSubimage(x, y, width, height);

    }
}
