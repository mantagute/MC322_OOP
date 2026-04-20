package gameOrchestrator;

import java.util.List;

/**
 * Representa o estado mínimo necessário para retomar uma partida salva.
 *
 * <p>Um {@code SaveState} é criado por {@link App#buildSaveState()} após cada vitória
 * e serializado em disco por {@link SaveManager}. Ao reabrir o jogo, ele é
 * desserializado e aplicado ao estado atual via {@link App#loadGame()}.
 *
 * <p>O estado capturado corresponde ao momento <b>entre batalhas</b>:
 * após a vitória da fase anterior e antes do início da próxima, garantindo
 * que o jogador recomece exatamente na batalha seguinte ao save, com
 * a vida e o baralho do estado pós-vitória.
 *
 * @see SaveManager
 * @see App#buildSaveState()
 * @see App#loadGame()
 */
public class SaveState {

    /** Pontos de vida do herói no momento em que o save foi criado. */
    private double heroHealth;

    /**
     * Nomes de todas as cartas do baralho do herói (buy pile + discard pile)
     * no momento do save. Usados para reconstruir o baralho via {@link Data#getCardbyName}.
     */
    private List<String> deckCardNames;

    /**
     * Sequência de direções percorridas na árvore de progressão até o momento do save
     * (ex.: {@code ["left", "right", "left"]}). Usada para reposicionar o jogador
     * no nó correto ao carregar o jogo.
     */
    private List<String> pathTaken;

    /**
     * Constrói um novo {@code SaveState} com os dados do estado atual do jogo.
     *
     * @param heroHealth    pontos de vida atuais do herói
     * @param deckCardNames nomes de todas as cartas do baralho (buy pile + discard pile)
     * @param pathTaken     lista ordenada de direções percorridas na árvore de progressão
     */
    public SaveState(double heroHealth, List<String> deckCardNames, List<String> pathTaken) {
        this.heroHealth = heroHealth;
        this.deckCardNames = deckCardNames;
        this.pathTaken = pathTaken;
    }

    /**
     * Retorna os pontos de vida do herói salvos.
     *
     * @return vida do herói no momento do save
     */
    public double getHeroHealth() {
        return heroHealth;
    }

    /**
     * Retorna os nomes de todas as cartas do baralho salvo.
     *
     * @return lista de nomes de cartas (buy pile + discard pile)
     */
    public List<String> getDeckCardNames() {
        return deckCardNames;
    }

    /**
     * Retorna a sequência de direções percorridas na árvore de progressão.
     *
     * @return lista ordenada de direções ({@code "left"} ou {@code "right"})
     */
    public List<String> getPathTaken() {
        return pathTaken;
    }
}
