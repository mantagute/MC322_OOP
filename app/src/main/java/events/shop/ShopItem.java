package events.shop;

import deck.BuyPile;
import entities.Hero;

public interface ShopItem {
    
    public String getName();

    public String getEmoji();

    public int  getPrice();

    public String getDetails();

    public boolean isSold();

    public void purchase(Hero hero, BuyPile buyPile);

}


