public class Card {

    private String name;
    private int energyCost;

    public Card(String name, int energyCost) {
        this.name = name;
        this.energyCost = energyCost;
    }

    public int getEnergyCost() {
        return energyCost;
    }

    public String getName() {
        return name;
    }
}
