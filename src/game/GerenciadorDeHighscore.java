package game;

import java.util.ArrayList;
import java.io.*;
import java.util.Collections;
import java.util.List;

public class GerenciadorDeHighscore {
    private static final String HIGHSCORE_FILE = "highscore.dat";
    private static List<EntradaPontos> scores;

    public GerenciadorDeHighscore() {
        scores = new ArrayList<>();
        carregaPontos();
    }
    @SuppressWarnings("unchecked")
    private void carregaPontos() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(HIGHSCORE_FILE))) {
            scores = (List<EntradaPontos>) ois.readObject();
        } catch (FileNotFoundException e) {

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    private void saveScores() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(HIGHSCORE_FILE))) {
            oos.writeObject(scores);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addScore(String name, int score) {
        scores.add(new EntradaPontos(score, name));
        Collections.sort(scores);
        if (scores.size() > 10) {
            scores = new ArrayList<>(scores.subList(0, 10));
        }
        saveScores();
    }

    public List<EntradaPontos> getHighScores() {
        return scores;
    }

    public void resetPontos() {
        File file = new File(HIGHSCORE_FILE);
        if (file.exists()) {
            file.delete();
        }
        scores.clear();
    }
}
