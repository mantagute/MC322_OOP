package gamePath;

import java.util.ArrayList;
import java.util.List;

import gameOrchestrator.Data.EnemyDefinition;

public class Node {
    private List<EnemyDefinition> enemies;
    private Node left, right;

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

    protected Node setLeftNode(Node left) {
        this.left = left;
        return left;
    }

    protected Node setRightNode(Node right) {
        this.right = right;
        return right;
    }
}
