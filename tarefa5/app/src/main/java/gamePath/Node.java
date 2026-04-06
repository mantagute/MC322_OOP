package gamePath;

import java.util.List;

import entities.enemy;;

public class Node {
    List<enemy> enemies;
    Node left, right;

    Node(List<enemy> enemies) {
        this.enemies = new ArrayList<enemy>(enemies);
        left = right = null;
    }
}
