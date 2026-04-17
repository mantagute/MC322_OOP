package entities;

/**
 * Representa o herói controlado pelo jogador.
 * Herda todos os comportamentos de combate de {@link Entity},
 * sendo controlado diretamente pelas escolhas do usuário durante o turno.
 */
public class Hero extends Entity {

    /**
     * Constrói um herói com os atributos base especificados.
     *
     * @param name   nome do herói
     * @param health pontos de vida iniciais
     * @param energy energia máxima por turno
     */
    public Hero(String name, int health, int energy) {
        super(name, health, energy);
    }
}
