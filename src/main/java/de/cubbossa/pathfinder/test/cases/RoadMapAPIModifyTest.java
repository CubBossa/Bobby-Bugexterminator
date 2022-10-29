package de.cubbossa.pathfinder.test.cases;

import de.cubbossa.pathfinder.core.node.Node;
import de.cubbossa.pathfinder.core.node.implementation.Waypoint;
import de.cubbossa.pathfinder.core.roadmap.RoadMap;
import de.cubbossa.pathfinder.core.roadmap.RoadMapHandler;
import de.cubbossa.pathfinder.module.visualizing.VisualizerHandler;
import de.cubbossa.pathfinder.module.visualizing.visualizer.ParticleVisualizer;
import de.cubbossa.pathfinder.module.visualizing.visualizer.PathVisualizer;
import de.cubbossa.testing.Test;
import de.cubbossa.pathfinder.test.TestPlugin;
import de.cubbossa.testing.TestSet;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

public class RoadMapAPIModifyTest implements TestSet {

	public RoadMap roadMap;

	@Override
	public void beforeAll() {
		roadMap = RoadMapHandler.getInstance().createRoadMap(TestPlugin.getInstance(), "t_rmamt_x");
	}

	@Override
	public void afterAll() {
		RoadMapHandler.getInstance().deleteRoadMap(roadMap);
	}

	@Test
	public void setRoadMapName() {
		String current = roadMap.getNameFormat();
		RoadMapHandler.getInstance().setRoadMapName(roadMap, "t_rmamt_1");
		assertNotEquals(current, "t_rmamt_1");
		assertEquals("t_rmamt_1", roadMap.getNameFormat());
	}

	@Test
	public void setRoadMapCurveLength() {
		Double current = roadMap.getDefaultCurveLength();
		RoadMapHandler.getInstance().setRoadMapCurveLength(roadMap, 4.7);
		assertNotSame(current, 4.7);
		assertEquals(4.7, roadMap.getDefaultCurveLength());
	}

	@Test
	public void setRoadMapVisualizer() {
		ParticleVisualizer vis = VisualizerHandler.PARTICLE_VISUALIZER_TYPE.create(new NamespacedKey(TestPlugin.getInstance(), "t_rmamt_2"), "t_rmamt_2");

		PathVisualizer<?, ?> prev = roadMap.getVisualizer();
		RoadMapHandler.getInstance().setRoadMapVisualizer(roadMap, vis);
		assertNotNull(roadMap.getVisualizer());
		PathVisualizer<?, ?> current = roadMap.getVisualizer();
		assertNotSame(prev, current);
		assertNotSame(prev, vis);
		assertNotNull(current);
		assertNotNull(current.getKey());
		assertEquals(vis.getKey(), current.getKey());
		assertEquals(vis, current);
	}

	@Test
	public void createNode() {
		int nodeIdCounter = RoadMapHandler.getInstance().getNodeIdCounter();
		Waypoint node = roadMap.createNode(RoadMapHandler.WAYPOINT_TYPE, new Location(Bukkit.getWorlds().get(0), 1, 2, 3));
		assertNotNull(node);
		assertEquals(node.getLocation().toVector(), new Vector(1, 2, 3));
		assertEquals(nodeIdCounter + 1, node.getNodeId());
		roadMap.removeNodes(node);
	}

	@Test
	public void addNode () {
		Waypoint node = new Waypoint(23, roadMap);
		node.setLocation(new Location(Bukkit.getWorlds().get(0), 1, 2, 3));

		roadMap.addNode(node);
		Node stored = roadMap.getNode(23);
		assertNotNull(stored);
		assertNotNull(stored.getLocation());
		assertEquals(stored.getLocation().toVector(), new Vector(1, 2, 3));
	}
}
