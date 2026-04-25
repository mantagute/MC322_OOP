package events.shop;

import cards.DamageCard;
import deck.BuyPile;
import entities.Hero;

public class DamageCardItem implements ShopItem{
    
    private DamageCard damageCard;
    private boolean sold = false;

    public DamageCardItem(DamageCard damageCard) {
        this.damageCard = damageCard;
    }

    public String getName(){
        return damageCard.getName();
    }

    public String getEmoji(){
        return "⚔️";
    }

    public int getPrice() {
        return 30;
    }

    public String getDetails(){
        return damageCard.getDetails();
    }

    public void purchase(Hero hero, BuyPile buyPile) {
        hero.spendGold(getPrice());
        buyPile.push(damageCard);
        sold = true;
    }

    public boolean isSold() {
        return sold;
    }
}
