package gamePath;

import java.util.ArrayList;
import java.util.List;

import gameOrchestrator.Data.EnemyDefinition;

public class Node {
    private List<EnemyDefinition> enemies;
    protected Node left, right;

    public Node(List<EnemyDefinition> enemies) {
        this.enemies = new ArrayList<EnemyDefinition>(enemies);
        left = right = null;
    }

    public List<EnemyDefinition> getEnemiesDefinitions() {
        return enemies;
    }

    public Node getLeftNode() {
        return left;
    }

    public Node getRightNode() {
        return right;
    }
}
