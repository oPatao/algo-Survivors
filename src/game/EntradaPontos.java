package game;

import java.io.Serializable;

public class EntradaPontos implements Serializable, Comparable<EntradaPontos> {
    private static final long serialVersionUID = 1L;
    private final int score;
    private final String nome;


    public EntradaPontos(int score, String nome) {
        this.score = score;
        this.nome = nome;
    }
    public int getScore() {
        return score;
    }
    public String getNome() {
        return nome;
    }
    @Override
    public int compareTo(EntradaPontos o) {
        return Integer.compare(o.getScore(), this.score);
    }
}
