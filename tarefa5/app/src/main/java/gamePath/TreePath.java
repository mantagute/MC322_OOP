package gamePath;

import java.util.List;
import gameOrchestrator.Data.EnemyDefinition;

public class TreePath {
    Node root;

    public TreePath(List<List<EnemyDefinition>> enemies) {
        this.root = listToTree(enemies, 0, enemies.size() - 1);
    }

    private Node listToTree(List<List<EnemyDefinition>> enemies, int start, int end) {
        if (start > end) {
            return null;
        }

        int mid = (start + end) / 2;
        Node node = new Node(enemies.get(mid));

        node.setLeftNode(listToTree(enemies, start, mid - 1));
        node.setRightNode(listToTree(enemies, mid + 1, end));

        return node;
    }

    public Node getRoot() {
        return root;
    }
}
