package cards;

import entities.Entity;

/**
 * Carta que concede escudo ao usuário quando utilizada.
 * O escudo final é calculado aplicando o multiplicador de efeitos
 * (ex.: Strength) do usuário sobre o valor base da carta.
 * Cartas de escudo sempre têm como alvo o próprio usuário.
 */
public class ShieldCard extends Card {

    private double shield;

    /**
     * Constrói uma carta de escudo com os atributos especificados.
     *
     * @param name        nome da carta
     * @param energyCost  custo em energia
     * @param shield      valor base de escudo concedido ao usuário
     * @param description descrição da carta
     * @param multiTarget {@code true} se a carta atinge múltiplos alvos
     */
    public ShieldCard(String name, int energyCost, double shield, String description, boolean multiTarget) {
        super(name, energyCost, description, multiTarget);
        this.shield = shield;
    }

    /**
     * Concede escudo ao próprio usuário, aplicando o multiplicador de efeitos.
     *
     * @param user   entidade que joga a carta e recebe o escudo
     * @param target não utilizado nesta implementação (carta é sempre self-target)
     */
    public void useCard(Entity user, Entity target) {
        user.receiveShield(user.applyEffectMultiplier(this.shield));
    }

    /**
     * Indica que esta carta sempre tem como alvo o próprio usuário.
     *
     * @return {@code true}
     */
    public boolean isSelfTarget() {
        return true;
    }

    /**
     * Retorna o valor base de escudo desta carta.
     *
     * @return valor base de escudo
     */
    public double getEffectValue() {
        return shield;
    }

    /**
     * Retorna uma string formatada exibindo o escudo base da carta.
     *
     * @return string no formato {@code " (Escudo: X)"}
     */
    public String getDetails() {
        return " (Escudo: " + this.getEffectValue() + ")";
    }

    public void upgrade() {
        shield = shield + shield * 0.35;
    }
}