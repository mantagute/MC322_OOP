package cards;
import entities.Entity;

public abstract class Card {

    private String name;
    private int energyCost;
    private String description;
    private boolean multiTarget;

    public Card(String name, int energyCost, String description, boolean multiTarget) {
        this.name = name;
        this.energyCost = energyCost;
        this.description = description;
        this.multiTarget = multiTarget;
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

    public boolean isMultiTarget() {
        return multiTarget;
    }

    public boolean isSelfTarget() {
        return false;
    }

    public abstract String getDetails();
    
    public abstract void useCard(Entity user, Entity target);

    public abstract double getEffectValue();
}
