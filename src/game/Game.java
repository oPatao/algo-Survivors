package game;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.*;
import java.util.List;


public class Game extends Canvas implements Runnable, KeyListener {
    public static int WIDTH = 1024, HEIGHT = 1024;

    public static Player player;
    public World world;

    public static List<Inimigo> inimigos = new ArrayList<Inimigo>();
    public static int controleSpawn = 0, targetSpawn = 120, last = 6, score = 0, upgradeTarget = 100;

    private int framesAnimaçãoPontinosIni = 0, maxFramesPontinhos = 20, estadoPontinhos = 0;
    private String textoPontinho = ".";

    public boolean gameOver = false;
    public boolean startGame = false;

    public boolean isUpgrading = false;

    private Musica BackgroundMusica;

    private List<Mod> mods;
    private List<Mod> modEscolha;

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

        inicializarMods();

        BackgroundMusica = new Musica("/levitate.wav");
        BackgroundMusica.setVolume(0.8f);
        BackgroundMusica.loopIni(1, 7);
    }

    private void inicializarMods(){
        mods = new ArrayList<>();
        mods.add(new Mod(Mod.EFEITO.AUMENTAR_CADENCIA));
        mods.add(new Mod(Mod.EFEITO.AUMENTAR_VELOCIDADE));
        mods.add(new Mod(Mod.EFEITO.AUMENTAR_QUANTIDADE_TIRO));
    }
    private void apresentarMods(){
        isUpgrading = true;
        modEscolha = new ArrayList<>();
        Collections.shuffle(mods);
        for (int i = 0; i < 3 && i < mods.size(); i++) {
            modEscolha.add(mods.get(i));
        }
    }

    public void tick(){
        if(!startGame){
            framesAnimaçãoPontinosIni++;
            if (framesAnimaçãoPontinosIni == maxFramesPontinhos) {
                framesAnimaçãoPontinosIni = 0;
                estadoPontinhos += 1;
                if (estadoPontinhos > 2){
                    estadoPontinhos = 0;
                }
            }
            return;
        }

        if(isUpgrading){
            return;
        }

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

                    if(score >= upgradeTarget){
                        apresentarMods();
                        upgradeTarget *= 2;
                    }

                    i--;
                    break;
                }
            }
        }


        controleSpawn++;
        if(controleSpawn == targetSpawn){
            spawnRandomInimigo();
            controleSpawn = 0;
            if(targetSpawn >= 60){
                targetSpawn--;
            }
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

            g.setColor(Color.WHITE);
            g.setFont(new Font("Times New Roman", Font.BOLD, 32));

            if (estadoPontinhos == 0) {
                textoPontinho = ".";
            } else if (estadoPontinhos == 1) {
                textoPontinho = "..";
            } else {
                textoPontinho = "...";
            }

            String mensagem = "Aperte ENTER para iniciar" + textoPontinho;

            int textWidth = g.getFontMetrics().stringWidth(mensagem);
            int x = (WIDTH - textWidth) / 2;
            int y = HEIGHT / 2;

            g.drawString(mensagem, x, y);



        }else {
            if(isUpgrading){
                upgradingScreen(g);
            }else {

                player.render(g);
                for (Inimigo i : inimigos) {
                    i.render(g);
                }
            }


            g.setColor(Color.green);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Score: " + score, WIDTH - 128, 16);
        }
        bs.show();
    }

    private void upgradingScreen(Graphics g) {
        g.setColor(new Color(0,0,0,200));
        g.fillRect(0,0, WIDTH, HEIGHT);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("Escolha um Upgrade!", WIDTH/2 - 150, 100);

        for (int i = 0; i < mods.size(); i++) {
            Mod mods = modEscolha.get(i);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString((i+1) + ". " + mods.nome, 150, 200 + i * 100);
            g.setFont(new Font("Arial", Font.PLAIN, 15));
            g.drawString(mods.descricao, 150, 225 + i * 100);
        }
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
        int evento = rand.nextInt(4);
        if (!(last == evento && new Random().nextInt(100) > 25)) {

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
        if (isUpgrading) {
            if (e.getKeyCode() >= KeyEvent.VK_1 && e.getKeyCode() <= KeyEvent.VK_3) {
                int escolha = e.getKeyCode() - KeyEvent.VK_1;
                if (escolha < modEscolha.size()) {
                    modEscolha.get(escolha).aplicarEfeito(player);
                    isUpgrading = false;
                }
            }
            return;
        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            startGame = true;
            BackgroundMusica.setVolume(0.6f);
            BackgroundMusica.loop();
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
            player.tiroRight = false;
        }else if(e.getKeyCode() == KeyEvent.VK_LEFT){
            player.tiroLeft = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_UP) {
            player.tiroUp = false;
        }else if(e.getKeyCode() == KeyEvent.VK_DOWN){
            player.tiroDown = false;
        }


        if (e.getKeyCode() == KeyEvent.VK_W) {
            player.up = false;
        }else if (e.getKeyCode() == KeyEvent.VK_S) {
            player.down = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            player.left = false;
        }else if (e.getKeyCode() == KeyEvent.VK_D) {
            player.right = false;
        }
    }
}