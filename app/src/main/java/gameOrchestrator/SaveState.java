package gameOrchestrator;

import java.util.List;

public class SaveState {
    private double heroHealth;
    private List<String> deckCardNames;
    private List<String> pathTaken;

    public SaveState(double heroHealth, List<String> deckCardNames, List<String> pathTaken) {
        this.heroHealth = heroHealth;
        this.deckCardNames = deckCardNames;
        this.pathTaken = pathTaken;
    }

    public double getHeroHealth() {
        return heroHealth;
    }

    public List<String> getDeckCardNames() {
        return deckCardNames;
    }

    public List<String> getPathTaken() {
        return pathTaken;
    }
}
