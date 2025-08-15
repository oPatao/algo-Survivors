package game;

public class Mod {

    public enum EFEITO{
        AUMENTAR_VELOCIDADE,
        AUMENTAR_CADENCIA,
        AUMENTAR_QUANTIDADE_TIRO
    }

    public EFEITO efeito;
    public String nome, descricao;

    public Mod(EFEITO efeito){
        this.efeito = efeito;

        switch (efeito){
            case AUMENTAR_VELOCIDADE:
                this.nome = "bota veloz";
                this.descricao = "aumenta a velocidade do personagem em +1";
                break;
            case AUMENTAR_CADENCIA:
                this.nome = "poção de agilidade";
                this.descricao = "aumenta a cadencia de arremesso em +1";
                break;
            case AUMENTAR_QUANTIDADE_TIRO:
                this.nome = "arremesso duplo";
                this.descricao = "aumenta a quantidade de arremesso em +1";
                break;
        }
    }
    public void aplicarEfeito(Player player){
        switch (efeito){
            case AUMENTAR_VELOCIDADE:
                player.spd +=1;
                break;
            case AUMENTAR_CADENCIA:
                player.modCadencia +=1;
                break;
            case AUMENTAR_QUANTIDADE_TIRO:
                player.modTiros +=1;
                break;
        }

    }
}
