public class TreePath {
    Node root;

    public void TreePath(List<List<enemy>> enemies) {
        this.root = listToTree(enemies, 0, enemies.size() - 1);
    }

    private listToTree(List<List<enemy>> enemies, int start, int end) {
        if (start > end) {
            return null;
        }

        int mid = (start + end) / 2;
        Node node = new Node(enemies.get(mid));

        node.left = listToTree(enemies, start, mid - 1);
        node.right = listToTree(enemies, mid + 1, end);

        return node;
    }
}
