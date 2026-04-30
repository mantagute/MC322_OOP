package events;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import gameOrchestrator.GameFactory;
import cards.DamageCard;
import cards.ShieldCard;
import deck.BuyPile;
import deck.DiscardPile;
import entities.Hero;
import events.shop.DamageCardItem;
import events.shop.PotionItem;
import events.shop.ShieldCardItem;
import events.shop.ShopItem;
import gameOrchestrator.UserInterface;

/**
 * Evento de loja — permite ao herói gastar ouro em cartas e poções entre batalhas.
 *
 * <p>A cada visita, sorteia aleatoriamente uma {@link cards.DamageCard} e uma
 * {@link cards.ShieldCard} do pool disponível, sempre oferecendo também uma
 * {@link events.shop.PotionItem}. O jogador pode comprar itens enquanto tiver
 * ouro suficiente ou sair a qualquer momento.
 */

public class Shop extends Event {

    List<ShopItem> shopItems;
    
    public String getPreview() {
        return "🛒 Loja";
    }

    public EventResult initializeEvent(Hero hero, BuyPile buyPile, DiscardPile discardPile, Scanner scanner) {

        List<DamageCard> damagePool = new ArrayList<>(GameFactory.createShopDamageCards());
        List<ShieldCard> shieldPool = new ArrayList<>(GameFactory.createShopShieldCards());
        Collections.shuffle(damagePool);
        Collections.shuffle(shieldPool);

        shopItems = List.<ShopItem>of(new PotionItem(), new DamageCardItem(damagePool.get(0)), new ShieldCardItem(shieldPool.get(0)));

        

        while(shopItems.stream().anyMatch(item -> !item.isSold())) {
            UserInterface.printShop(hero, shopItems);
            UserInterface.printChoicePrompt();
            int choice = scanner.nextInt();
        
            if (choice == shopItems.size() + 1) break;
        
            if (choice < 1 || choice > shopItems.size()) {
                UserInterface.printWarning("Opção inválida.");
                continue;
            }
        
            ShopItem chosen = shopItems.get(choice - 1);
        
            if (chosen.isSold()) {
                UserInterface.printWarning("Item já vendido.");
            } else if (hero.getGold() < chosen.getPrice()) {
                UserInterface.printWarning("Ouro insuficiente!");
            } else {
                chosen.purchase(hero, buyPile);
            }
        }

        return EventResult.CONTINUE;
    }
}
