abstract class Card {

    private String name;
    private int energyCost;
    private String description;

    public Card(String name, int energyCost, String description) {
        this.name = name;
        this.energyCost = energyCost;
        this.description = description;
    }

    public int getEnergyCost() {
        return energyCost;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    abstract void useCard(Entity user, Entity target);
}
