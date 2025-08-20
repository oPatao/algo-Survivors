package game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Spritesheet {
    public static BufferedImage spritesheet;
    public static BufferedImage spritesheetInvocacao;

    public static BufferedImage[] invocacao;
    
    public static BufferedImage[] playerFront;
    public static BufferedImage[] playerBack;
    public static BufferedImage[] playerLeft;
    public static BufferedImage[] playerRight;

    public static BufferedImage[] zumbiFrente;
    public static BufferedImage[] zumbiBack;
    public static BufferedImage[] zumbiLeft;
    public static BufferedImage[] zumbiRight;

    public static BufferedImage[] skeliFrente;
    public static BufferedImage[] skeliBack;
    public static BufferedImage[] skeliLeft;
    public static BufferedImage[] skeliRight;

    public static BufferedImage[] bullet;
    public static BufferedImage[] pedra;
    public static BufferedImage[] grama;
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

        skeliFrente = new BufferedImage[4];
        skeliFrente[0] = Spritesheet.getSprite(2, 227, 28,43);
        skeliFrente[1] = Spritesheet.getSprite(34, 227, 28,43);
        skeliFrente[2] = Spritesheet.getSprite(66, 227, 28,43);
        skeliFrente[3] = Spritesheet.getSprite(98, 227, 28,43);

        skeliBack = new BufferedImage[4];
        skeliBack[0] = Spritesheet.getSprite(130, 275, 28,43);
        skeliBack[1] = Spritesheet.getSprite(162, 275, 28,43);
        skeliBack[2] = Spritesheet.getSprite(194, 275, 28,43);
        skeliBack[3] = Spritesheet.getSprite(226, 275, 28,43);

        skeliLeft = new BufferedImage[4];
        skeliLeft[0] = Spritesheet.getSprite(130, 227, 28,43);
        skeliLeft[1] = Spritesheet.getSprite(162, 227, 28,43);
        skeliLeft[2] = Spritesheet.getSprite(194, 227, 28,43);
        skeliLeft[3] = Spritesheet.getSprite(226, 227, 28,43);

        skeliRight = new BufferedImage[4];
        skeliRight[0] = Spritesheet.getSprite(2, 275, 28,43);
        skeliRight[1] = Spritesheet.getSprite(34, 275, 28,43);
        skeliRight[2] = Spritesheet.getSprite(66, 275, 28,43);
        skeliRight[3] = Spritesheet.getSprite(98, 275, 28,43);
        int n;



        try{
            spritesheetInvocacao = ImageIO.read(getClass().getResource("/invocacaoDoSkelli.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        invocacao = new BufferedImage[68];
        for (int i = 0; i < 68; i++) {
            n= 32 * i;
            invocacao[i] = Spritesheet.getSpriteInvocacao(n, 0, 32,79);
            
        }
        
        pedra = new BufferedImage[2];

        try{
            pedra[0] = ImageIO.read(getClass().getResource("/preda1.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try{
            pedra[1] = ImageIO.read(getClass().getResource("/tijolinho.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        grama = new BufferedImage[2];
        try{
            grama[0] = ImageIO.read(getClass().getResource("/grama.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try{
            grama[1] = ImageIO.read(getClass().getResource("/chaoSkeli.png"));
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
    public static BufferedImage getSpriteInvocacao(int x, int y, int width, int height) throws IOException {
        return spritesheetInvocacao.getSubimage(x, y, width, height);

    }
}
