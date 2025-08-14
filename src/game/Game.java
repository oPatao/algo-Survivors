package game;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


public class Game extends Canvas implements Runnable, KeyListener {
    public static int WIDTH = 1024, HEIGHT = 1024;

    public static Player player;
    public World world;

    public static List<Inimigo> inimigos = new ArrayList<Inimigo>();
    public static int controleSpawn = 0, targetSpawn = 120, last = 6, score = 0;

    public boolean gameOver = false;
    public boolean startGame = false;

    public Game(){
        this.addKeyListener(this);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        try {
            new Spritesheet();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        player = new Player(512,512);
        world = new World(WIDTH, HEIGHT);

        inimigos.add(new Inimigo(63,64));
    }

    public void tick(){
        player.tick();
        for (Inimigo inimigo : inimigos) {
            inimigo.tick();
        }

        for (int i = 0 ; i < Player.bullets.size(); i++){
            Bullet b = Player.bullets.get(i);
            for(int j = 0; j < inimigos.size(); j++){
                Inimigo inimigo = inimigos.get(j);
                if(b.intersects(inimigo)){
                    inimigos.remove(inimigo);
                    Player.bullets.remove(b);
                    score+=10;
                    i--;
                    break;
                }
            }
        }


        controleSpawn++;
        if(controleSpawn == targetSpawn){
            spawnRandomInimigo();
            controleSpawn = 0;
        }

    }
    public void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        world.render(g);
        if (!startGame){
            int contador;

            for (contador = 0; contador <= 3; contador++){
                g.setColor(Color.WHITE);
                g.setFont(new Font("Times New Roman", Font.BOLD, 32));
                g.drawString("Score: aperte Enter para iniciar...", WIDTH / 2, HEIGHT / 2);
        }
        }else {

            player.render(g);
            for (Inimigo i : inimigos) {
                i.render(g);
            }


            g.setColor(Color.green);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Score: " + score, WIDTH - 128, 16);
        }
        bs.show();
    }

    public static void main(String[] args) {
        Game game = new Game();
        JFrame frame = new JFrame("Game");

        frame.add(game);
        frame.setTitle("Game");
        frame.pack();

        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);

        new Thread(game).start();
    }

    public static void spawnRandomInimigo(){
        Random rand = new Random();
        int pos = 1024/2, ini = 33, ini2 = HEIGHT - ini;
        int evento = rand.nextInt(5);
        System.out.println(ini2);
        if (!(last == evento && new Random().nextInt(100) > 50)) {

            switch (evento){
                case 0:
                    inimigos.add(new Inimigo(ini,pos-32));
                    inimigos.add(new Inimigo(ini,pos));
                    inimigos.add(new Inimigo(ini,pos+32));
                    break;
                case 1:
                    inimigos.add(new Inimigo(ini2 - ini,pos-32));
                    inimigos.add(new Inimigo(ini2 - ini, pos));
                    inimigos.add(new Inimigo(ini2 - ini,pos+32));
                    break;
                case 2:
                    inimigos.add(new Inimigo(pos-32, ini));
                    inimigos.add(new Inimigo(pos, ini));
                    inimigos.add(new Inimigo(pos+32,ini));
                    break;
                case 3:
                    inimigos.add(new Inimigo(pos-32,ini2 - ini));
                    inimigos.add(new Inimigo(pos, ini2 - ini));
                    inimigos.add(new Inimigo(pos+32, ini2 - ini));
                    break;
                default:
                    break;
            }
        }

        last = evento;
    }

    @Override
    public void run() {

        while(true){
            tick();
            render();
            try{
                Thread.sleep(1000/60);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            startGame = true;
        }

        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            player.tiroRight = true;
        }else if(e.getKeyCode() == KeyEvent.VK_LEFT){
            player.tiroLeft = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_UP) {
            player.tiroUp = true;
        }else if(e.getKeyCode() == KeyEvent.VK_DOWN){
            player.tiroDown = true;
        }


        if (e.getKeyCode() == KeyEvent.VK_W) {
            player.up = true;
        }else if (e.getKeyCode() == KeyEvent.VK_S) {
            player.down = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            player.left = true;
        }else if (e.getKeyCode() == KeyEvent.VK_D) {
            player.right = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            player.right = false;
        }else if(e.getKeyCode() == KeyEvent.VK_LEFT){
            player.left = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_UP) {
            player.up = false;
        }else if(e.getKeyCode() == KeyEvent.VK_DOWN){
            player.down = false;
        }
    }
}