public class Hand extends Pile {

    public Hand() {
        super(new Card[] {});
    }

    public int getMinimumEnergyCost() {
        Card[] cardsInHand = getCards();
        int minEnergyCost = 999999999;
        for (Card card : cardsInHand) {
            if (card.getEnergyCost() < minEnergyCost) {
                minEnergyCost = card.getEnergyCost();
            }
        }
        return minEnergyCost;
    }

    public Card playCard(int index, Entity doer, Entity target) {
        Card card = extractCard(index);

        if (card instanceof DamageCard) {
            DamageCard attackCard = (DamageCard) card;
            doer.attack(target, attackCard);
        } else if (card instanceof ShieldCard) {
            ShieldCard shieldCard = (ShieldCard) card;
            doer.increaseShield(shieldCard);
        }

        return card;
    }
}