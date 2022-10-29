package de.cubbossa.pathfinder.test.cases;

import de.cubbossa.pathfinder.core.node.NodeGroup;
import de.cubbossa.pathfinder.core.node.NodeGroupHandler;
import de.cubbossa.testing.Test;
import de.cubbossa.pathfinder.test.TestPlugin;
import de.cubbossa.testing.TestSet;
import org.bukkit.NamespacedKey;

import static org.junit.jupiter.api.Assertions.*;

public class NodeGroupAPITest implements TestSet {

	@Test
	public void createNodeGroup() {
		int nodegroupCount = NodeGroupHandler.getInstance().getNodeGroups().size();
		NodeGroup n = NodeGroupHandler.getInstance().createNodeGroup(new NamespacedKey(TestPlugin.getInstance(), "t_ngat_1"), "t_ngat_1");
		assertEquals(nodegroupCount + 1, NodeGroupHandler.getInstance().getNodeGroups().size());
		NodeGroup found = NodeGroupHandler.getInstance().getNodeGroup(new NamespacedKey(TestPlugin.getInstance(), "t_ngat_1"));
		assertNotNull(found);
		assertNotNull(found.getKey());
		assertEquals(n.getKey(), found.getKey());
	}

	@Test
	public void deleteNodegroup() {
		int nodegroupCount = NodeGroupHandler.getInstance().getNodeGroups().size();
		NodeGroup n = NodeGroupHandler.getInstance().createNodeGroup(new NamespacedKey(TestPlugin.getInstance(), "t_ngat_2"), "t_ngat_2");
		assertEquals(nodegroupCount + 1, NodeGroupHandler.getInstance().getNodeGroups().size());
		NodeGroupHandler.getInstance().deleteNodeGroup(n);
		assertEquals(nodegroupCount, NodeGroupHandler.getInstance().getNodeGroups().size());
		assertNull(NodeGroupHandler.getInstance().getNodeGroup(new NamespacedKey(TestPlugin.getInstance(), "t_ngat_2")));
	}

	@Test
	public void duplicateKey () {
		NodeGroup first = NodeGroupHandler.getInstance().createNodeGroup(new NamespacedKey(TestPlugin.getInstance(), "t_ngat_3"), "t_ngat_3");
		assertNotNull(first);
		assertThrows(IllegalArgumentException.class, () -> NodeGroupHandler.getInstance().createNodeGroup(new NamespacedKey(TestPlugin.getInstance(), "t_ngat_3"), "t_ngat_3"));
		assertEquals(first, NodeGroupHandler.getInstance().getNodeGroup(new NamespacedKey(TestPlugin.getInstance(), "t_ngat_3")));
	}
}
