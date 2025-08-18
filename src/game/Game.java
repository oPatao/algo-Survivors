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
    public static int WIDTH = 1024, HEIGHT = 768;
    public static Player player;
    public World world;
    public static Logo logo;
    public static List<Inimigo> inimigos = new ArrayList<Inimigo>();
    public static List<SkeliAss> skeliAsses = new ArrayList<SkeliAss>();
    public static int score = 0;

    private enum GameState {
        MENU,
        JOGANDO,
        UPGRADE,
        GAME_OVER_INPUT,
        HIGHSCORE
    }
    private GameState estadoAtual;

    private GerenciadorDeHighscore gerenciadorDeHighscore;
    private String nomeJogador = "";
    private int charIndex = 0;


    public static int controleSpawn = 0, targetSpawn = 60, lastSpawn = 6,
            upgradeTarget = 50, nivel = 0,
            spawnBoss = 0, skeliChance= 120, bossTemp = 2000, temp = 0;
    public static boolean bossFight = false;

    private int framesAnimaçãoPontos = 0, maxFramesPontos = 20, estadoPontos = 0;
    private String textoPontos = ".";

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
        player = new Player(WIDTH/2,HEIGHT/2);
        world = new World(WIDTH, HEIGHT);
        gerenciadorDeHighscore = new GerenciadorDeHighscore();

        logo = new Logo((WIDTH/2) -288, 0);


        //gerenciadorDeHighscore.resetPontos();
        inicializarMods();

        BackgroundMusica = new Musica("/levitate.wav");
        BackgroundMusica.setVolume(0.8f);

        resetGame();
    }

    public void resetGame(){
        estadoAtual = GameState.MENU;
        player.resetaPlayer(WIDTH/2, HEIGHT/2);
        inimigos.clear();
        Player.bullets.clear();
        skeliAsses.clear();
        score = 0;
        upgradeTarget = 50;
        targetSpawn = 60;
        nomeJogador = "AAA";
        charIndex = 0;
        spawnBoss = 0;
        nivel = 0;
        skeliChance = 80;
        bossFight = false;

        BackgroundMusica.play();
        BackgroundMusica.loopIni(1, 7);
    }



   /* public void tick(){
        if(!startGame){

            logo.tick(HEIGHT);
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
                        nivel++;
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
            logo.render(g);
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



    private void renderUpgradeScreen(Graphics g) {
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

    */



    public void tick() {
        switch (estadoAtual) {
            case MENU:
                tickMenu();
                break;
            case JOGANDO:
                tickJogando();
                break;
            case GAME_OVER_INPUT:

                break;
            case HIGHSCORE:

                break;
            case UPGRADE:

                break;
        }
    }
    private void tickMenu() {
        logo.tick(HEIGHT);
        framesAnimaçãoPontos++;
        if (framesAnimaçãoPontos > maxFramesPontos) {
            framesAnimaçãoPontos = 0;
            estadoPontos = (estadoPontos + 1) % 3;
        }
    }
    private void tickJogando() {

        if(temp >= bossTemp ){
            bossFight = false;
        }else if(temp <= bossTemp && bossFight == true ){
            temp++;
        }
        if(!bossFight) controleSpawn++;

        player.tick();

        for (int i = 0; i < inimigos.size(); i++) {
            inimigos.get(i).tick();
        }
        for (int i = 0; i < skeliAsses.size(); i++) {
            skeliAsses.get(i).tick();
        }

        for (int i = 0; i < Player.bullets.size(); i++) {
            Bullet b = Player.bullets.get(i);
            for (int j = 0; j < inimigos.size(); j++) {
                Inimigo inimigo = inimigos.get(j);
                if (b.intersects(inimigo)) {
                    inimigos.remove(j);
                    Player.bullets.remove(i);
                    score += 10;
                    if (score >= upgradeTarget) {
                        apresentarMods();
                        upgradeTarget *= 2;
                    }

                    i--;
                    break;
                }
            }
        }
        for (int i = 0; i < Player.bullets.size(); i++) {
            Bullet b = Player.bullets.get(i);
            for (int j = 0; j < skeliAsses.size(); j++) {
                SkeliAss skeliAss = skeliAsses.get(j);
                if (b.intersects(skeliAss)) {
                    Player.bullets.remove(i);

                    if (skeliAss.vida >= 0) {
                        skeliAss.vida--;
                    }else{
                        skeliAsses.remove(j);
                        score += 1000;
                    }

                    if (score >= upgradeTarget) {
                        apresentarMods();
                        upgradeTarget *= 2;
                    }

                    i--;
                    break;
                }
            }
        }

        for (int i = 0; i < inimigos.size(); i++) {
            Inimigo inimigo = inimigos.get(i);
            if (player.intersects(inimigo)) {
                inimigos.remove(i);
                player.vida--;
                i--;
                if (player.vida <= 0) {
                    estadoAtual = GameState.GAME_OVER_INPUT;
                    BackgroundMusica.stop();
                    return;
                }
            }
        }
        for (int i = 0; i < skeliAsses.size(); i++) {
            SkeliAss skeliAss = skeliAsses.get(i);
            if (player.intersects(skeliAss)) {
                skeliAsses.remove(i);
                player.vida = player.vida - 5;
                i--;
                if (player.vida <= 0) {
                    estadoAtual = GameState.GAME_OVER_INPUT;
                    BackgroundMusica.stop();
                    return;
                }
            }
        }
        if(nivel == 5 && spawnBoss == 0){
            spawnBossSkeli();
        }


        if (controleSpawn >= targetSpawn && !bossFight) {
            spawnRandomInimigo();
            controleSpawn = 0;
            if (targetSpawn > 60) {
                targetSpawn--;
            }
        }
    }

    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        world.render(g);

        switch (estadoAtual) {
            case MENU:
                renderMenu(g);
                break;
            case JOGANDO:
            case UPGRADE:
                renderJogando(g);
                if (estadoAtual == GameState.UPGRADE) {
                    renderUpgradeScreen(g);
                }
                break;
            case GAME_OVER_INPUT:
                renderGameOverInput(g);
                break;
            case HIGHSCORE:
                renderHighScore(g);
                break;
        }

        bs.show();
        g.dispose();
    }

    private void renderMenu(Graphics g) {
        logo.render(g);
        if (estadoPontos == 0) textoPontos = ".";
        else if (estadoPontos == 1) textoPontos = "..";
        else textoPontos = "...";

        String mensagem = "Aperte ENTER para iniciar" + textoPontos;
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 32));
        int textWidth = g.getFontMetrics().stringWidth(mensagem);
        g.drawString(mensagem, (WIDTH - textWidth) / 2, HEIGHT / 2);
    }
    private void renderJogando(Graphics g) {
        player.render(g);
        for (Inimigo i : inimigos) {
            i.render(g);
        }
        for(SkeliAss i : skeliAsses){
            i.render(g);
        }

        g.setColor(Color.GREEN);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + score, WIDTH - 150, 30);
        g.drawString("Vida: " + player.vida, 30, 30);
        g.drawString("nivel: "+nivel, WIDTH/2, 30);
    }
    private void renderGameOverInput(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        String msgGameOver = "GAME OVER";
        int textWidth = g.getFontMetrics().stringWidth(msgGameOver);
        g.drawString(msgGameOver, (WIDTH - textWidth) / 2, HEIGHT / 2 - 100);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Monospaced", Font.BOLD, 40));
        String msgNome = "INSIRA SEU NOME:";
        textWidth = g.getFontMetrics().stringWidth(msgNome);
        g.drawString(msgNome, (WIDTH - textWidth) / 2, HEIGHT / 2);


        for (int i = 0; i < 3; i++) {

            if (i == charIndex) {
                if ((framesAnimaçãoPontos / 10) % 2 == 0) {
                    g.setColor(Color.YELLOW);
                    g.fillRect((WIDTH / 2 - 60) + i * 50, HEIGHT / 2 + 75, 40, 5);
                }
            }
            g.setColor(Color.WHITE);
            g.drawString(String.valueOf(nomeJogador.charAt(i)), (WIDTH / 2 - 55) + i * 50, HEIGHT / 2 + 70);
        }
    }

    private void renderHighScore(Graphics g) {
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        String msg = "HIGH SCORES";
        int textWidth = g.getFontMetrics().stringWidth(msg);
        g.drawString(msg, (WIDTH - textWidth) / 2, 100);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Monospaced", Font.BOLD, 24));

        List<EntradaPontos> scores = gerenciadorDeHighscore.getHighScores();
        for (int i = 0; i < scores.size(); i++) {
            EntradaPontos entrada = scores.get(i);
            String rank = String.format("%2d.", i + 1);
            String name = entrada.getNome();
            String scoreStr = String.format("%06d", entrada.getScore());
            g.drawString(rank + " " + name + "   " + scoreStr, WIDTH / 2 - 150, 180 + i * 40);
        }

        g.setFont(new Font("Arial", Font.BOLD, 20));
        String msgRestart = "Aperte ENTER para voltar ao Menu";
        textWidth = g.getFontMetrics().stringWidth(msgRestart);
        g.drawString(msgRestart, (WIDTH - textWidth) / 2, HEIGHT - 100);
    }
    private void renderUpgradeScreen(Graphics g) {
        g.setColor(new Color(0,0,0,200));
        g.fillRect(0,0, WIDTH, HEIGHT);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("Escolha um Upgrade!", WIDTH/2 - 150, 100);

        for (int i = 0; i < modEscolha.size(); i++) {
            Mod mod = modEscolha.get(i);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString((i+1) + ". " + mod.nome, 150, 200 + i * 100);
            g.setFont(new Font("Arial", Font.PLAIN, 15));
            g.drawString(mod.descricao, 150, 225 + i * 100);
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
        switch (estadoAtual) {
            case MENU:
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    estadoAtual = GameState.JOGANDO;
                    BackgroundMusica.loop();
                    BackgroundMusica.setVolume(0.6f);
                }
                break;
            case JOGANDO:
                handlePlayingInput(e, true);
                break;
            case UPGRADE:
                if (e.getKeyCode() >= KeyEvent.VK_1 && e.getKeyCode() <= KeyEvent.VK_3) {
                    int escolha = e.getKeyCode() - KeyEvent.VK_1;
                    if (escolha < modEscolha.size()) {
                        modEscolha.get(escolha).aplicarEfeito(player);
                        estadoAtual = GameState.JOGANDO;
                        nivel++;
                    }
                }
                break;
            case GAME_OVER_INPUT:
                handleGameOverInput(e);
                break;
            case HIGHSCORE:
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    resetGame();
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
            handlePlayingInput(e, false);


    }

    private void handlePlayingInput(KeyEvent e, boolean isPressed) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) player.up = isPressed;
        if (code == KeyEvent.VK_S) player.down = isPressed;
        if (code == KeyEvent.VK_A) player.left = isPressed;
        if (code == KeyEvent.VK_D) player.right = isPressed;

        if (code == KeyEvent.VK_UP) player.tiroUp = isPressed;
        if (code == KeyEvent.VK_DOWN) player.tiroDown = isPressed;
        if (code == KeyEvent.VK_LEFT) player.tiroLeft = isPressed;
        if (code == KeyEvent.VK_RIGHT) player.tiroRight = isPressed;
    }

    private void handleGameOverInput(KeyEvent e) {
        int code = e.getKeyCode();
        char[] nomeChars = nomeJogador.toCharArray();

        if (code >= KeyEvent.VK_A && code <= KeyEvent.VK_Z) {
            nomeChars[charIndex] = (char) code;
            charIndex = (charIndex + 1) % 3;
        } else if (code == KeyEvent.VK_LEFT) {
            charIndex = (charIndex == 0) ? 2 : charIndex - 1;
        } else if (code == KeyEvent.VK_RIGHT) {
            charIndex = (charIndex + 1) % 3;
        } else if (code == KeyEvent.VK_UP) {
            nomeChars[charIndex]++;
            if (nomeChars[charIndex] > 'Z') nomeChars[charIndex] = 'A';
        } else if (code == KeyEvent.VK_DOWN) {
            nomeChars[charIndex]--;
            if (nomeChars[charIndex] < 'A') nomeChars[charIndex] = 'Z';
        } else if (code == KeyEvent.VK_ENTER) {
            gerenciadorDeHighscore.addScore(nomeJogador, score);
            estadoAtual = GameState.HIGHSCORE;
        }

        nomeJogador = new String(nomeChars);
    }

    private void inicializarMods(){
        mods = new ArrayList<>();
        mods.add(new Mod(Mod.EFEITO.AUMENTAR_CADENCIA));
        mods.add(new Mod(Mod.EFEITO.AUMENTAR_VELOCIDADE));
        mods.add(new Mod(Mod.EFEITO.AUMENTAR_QUANTIDADE_TIRO));
        mods.add(new Mod(Mod.EFEITO.AUMENTAR_VIDA));
    }
    private void apresentarMods(){
        estadoAtual = GameState.UPGRADE;
        modEscolha = new ArrayList<>();
        Collections.shuffle(mods);
        for (int i = 0; i < 3 && i < mods.size(); i++) {
            modEscolha.add(mods.get(i));
        }
    }

    public static void spawnRandomInimigo(){
        Random rand = new Random();
        int pos = HEIGHT / 2;
        int bordaEsquerda = 33;
        int bordaDireita = WIDTH - 64;
        int bordaCima = 33;
        int bordaBaixo = HEIGHT - 64;

        int evento = rand.nextInt(4 + spawnBoss);


        if (!(lastSpawn == evento && rand.nextInt(100) > 25)) {

            int nZumbi = rand.nextInt(nivel+1) + 1;

            int offset = (nZumbi / 2) * 32;

            switch (evento) {
                case 0:
                    for (int i = 0; i < nZumbi; i++) {
                        int spawnY = pos - offset + (i * 32);
                        inimigos.add(new Inimigo(bordaEsquerda, spawnY));
                    }
                    break;
                case 1:
                    for (int i = 0; i < nZumbi; i++) {
                        int spawnY = pos - offset + (i * 32);
                        inimigos.add(new Inimigo(bordaDireita, spawnY));
                    }
                    break;
                case 2:
                    for (int i = 0; i < nZumbi; i++) {
                        int spawnX = pos - offset + (i * 32);
                        inimigos.add(new Inimigo(spawnX, bordaCima));
                    }
                    break;
                case 3:
                    for (int i = 0; i < nZumbi; i++) {
                        int spawnX = pos - offset + (i * 32);
                        inimigos.add(new Inimigo(spawnX, bordaBaixo));
                    }
                    break;
                case 4:
                    if (rand.nextInt(100)  >= skeliChance){
                        skeliAsses.add(new SkeliAss(120,120));
                        skeliChance--;
                    } else {
                        skeliChance--;
                        spawnRandomInimigo();
                    }
                    break;
            }
        }

        lastSpawn = evento;
    }
    public static void spawnBossSkeli(){
        bossFight = true;
        inimigos.clear();
        skeliAsses.add(new SkeliAss(120,120));
        spawnBoss = 1;


    }
}