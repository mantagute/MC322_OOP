package gamePath;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import gameOrchestrator.Data;

public class TreePathTest {

    @Test
    public void RootFromTreePathIsTheMiddleElement() { 
        TreePath treePath = new TreePath(Data.enemies);
        assertEquals(Data.enemies.get(3).get(0).name(), treePath.getRoot().getEnemiesDefinitions().get(0).name());
    }

    @Test
    public void RootHasSiblings() {
        TreePath treePath = new TreePath(Data.enemies);
        assertNotNull(treePath.getRoot().getLeftNode());
        assertNotNull(treePath.getRoot().getRightNode());
    }

    @Test
    public void LeafNodesHaveNullChildren() {
        TreePath treePath = new TreePath(Data.enemies);
        assertNull(treePath.getRoot().getLeftNode().getLeftNode().getLeftNode());
        assertNull(treePath.getRoot().getLeftNode().getLeftNode().getRightNode());
        assertNull(treePath.getRoot().getLeftNode().getRightNode().getLeftNode());
        assertNull(treePath.getRoot().getRightNode().getRightNode().getLeftNode());
        assertNull(treePath.getRoot().getRightNode().getRightNode().getRightNode());
        assertNull(treePath.getRoot().getRightNode().getLeftNode().getRightNode());
    }
}
