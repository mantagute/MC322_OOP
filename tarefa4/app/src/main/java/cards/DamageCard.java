package cards;

import entities.Entity;

/**
 * Carta que causa dano ao alvo quando utilizada.
 * O dano final é calculado aplicando o multiplicador de efeitos
 * (ex.: Strength) do usuário sobre o dano base da carta.
 */
public class DamageCard extends Card {

    private double damage;

    /**
     * Constrói uma carta de dano com os atributos especificados.
     *
     * @param name        nome da carta
     * @param cost        custo em energia
     * @param damage      valor base de dano causado ao alvo
     * @param description descrição da carta
     * @param multiTarget {@code true} se a carta atinge todos os inimigos vivos
     */
    public DamageCard(String name, int cost, double damage, String description, boolean multiTarget) {
        super(name, cost, description, multiTarget);
        this.damage = damage;
    }

    /**
     * Causa dano ao alvo, aplicando o multiplicador de efeitos do usuário.
     *
     * @param user   entidade que joga a carta (fonte do multiplicador de efeitos)
     * @param target entidade que recebe o dano
     */
    @Override
    public void useCard(Entity user, Entity target) {
        target.receiveDamage(user.applyEffectMultiplier(this.damage));
    }

    /**
     * Retorna o valor base de dano desta carta.
     *
     * @return dano base da carta
     */
    @Override
    public double getEffectValue() {
        return damage;
    }

    /**
     * Retorna uma string formatada exibindo o dano base da carta.
     *
     * @return string no formato {@code " (Dano: X)"}
     */
    @Override
    public String getDetails() {
        return " (Dano: " + this.getEffectValue() + ")";
    }
}
