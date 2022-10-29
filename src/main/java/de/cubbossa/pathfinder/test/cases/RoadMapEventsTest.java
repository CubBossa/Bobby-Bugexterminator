package de.cubbossa.pathfinder.test.cases;

import de.cubbossa.pathfinder.core.events.roadmap.*;
import de.cubbossa.pathfinder.core.roadmap.RoadMap;
import de.cubbossa.pathfinder.core.roadmap.RoadMapHandler;
import de.cubbossa.pathfinder.module.visualizing.VisualizerHandler;
import de.cubbossa.pathfinder.module.visualizing.visualizer.PathVisualizer;
import de.cubbossa.testing.Test;
import de.cubbossa.testing.TestContext;
import de.cubbossa.pathfinder.test.TestPlugin;
import de.cubbossa.testing.TestSet;
import org.bukkit.NamespacedKey;

import static de.cubbossa.testing.MinecraftAsserts.assertInEvent;
import static org.junit.jupiter.api.Assertions.*;

public class RoadMapEventsTest implements TestSet {

	@Test(timeout = 1000)
	public void createEvent(TestContext context) {
		NamespacedKey key = new NamespacedKey(TestPlugin.getInstance(), "t_rmet_1");
		assertInEvent(RoadMapCreatedEvent.class, context, value -> {
			assertEquals(key, value.getRoadMap().getKey());
			RoadMapHandler.getInstance().deleteRoadMap(RoadMapHandler.getInstance().getRoadMap(key));
		});
		RoadMapHandler.getInstance().createRoadMap(TestPlugin.getInstance(), "t_rmet_1");
	}
	
	@Test
	public void deleteEvent(TestContext context) {
		NamespacedKey key = new NamespacedKey(TestPlugin.getInstance(), "t_rmet_2");
		RoadMap rm = RoadMapHandler.getInstance().createRoadMap(TestPlugin.getInstance(), key.getKey());
		assertNotNull(rm);
		assertEquals(key, rm.getKey());
		RoadMapHandler.getInstance().deleteRoadMap(rm);

		assertInEvent(RoadMapDeletedEvent.class, context, value -> {
			assertNull(RoadMapHandler.getInstance().getRoadMap(key));
		});
	}

	@Test
	public void nameChangedEvent(TestContext context) {
		RoadMap roadMap = RoadMapHandler.getInstance().createRoadMap(TestPlugin.getInstance(), "t_rmet_3");
		assertNotNull(roadMap);
		String current = roadMap.getNameFormat();

		RoadMapHandler.getInstance().setRoadMapName(roadMap, "t_rmet_4");

		assertInEvent(RoadMapSetNameEvent.class, context, value -> {
			assertNotEquals(current, value.getNameFormat());
			RoadMapHandler.getInstance().deleteRoadMap(roadMap);
		});
	}

	@Test
	public void visualizerChangedEvent(TestContext context) {
		PathVisualizer<?, ?> visualizer = VisualizerHandler.COMBINED_VISUALIZER_TYPE.create(new NamespacedKey(TestPlugin.getInstance(), "t_rmet_5"), "t_rmet_5");
		RoadMap roadMap = RoadMapHandler.getInstance().createRoadMap(TestPlugin.getInstance(), "t_rmet_5");
		assertNotNull(roadMap);
		assertNotNull(visualizer);
		PathVisualizer<?, ?> current = roadMap.getVisualizer();

		RoadMapHandler.getInstance().setRoadMapVisualizer(roadMap, visualizer);

		assertInEvent(RoadMapSetVisualizerEvent.class, context, value -> {
			assertNotEquals(current, value.getVisualizer());
			assertEquals(value.getVisualizer().getKey(), visualizer.getKey());
			if (current != null) {
				assertNotEquals(current.getKey(), value.getVisualizer().getKey());
			}
			RoadMapHandler.getInstance().deleteRoadMap(roadMap);
			VisualizerHandler.getInstance().deletePathVisualizer(visualizer);
		});
	}

	@Test
	public void curveLengthChangedEvent(TestContext context) {
		RoadMap roadMap = RoadMapHandler.getInstance().createRoadMap(TestPlugin.getInstance(), "t_rmet_6");
		assertNotNull(roadMap);
		double current = roadMap.getDefaultCurveLength();

		RoadMapHandler.getInstance().setRoadMapName(roadMap, "t_rmet_6");

		assertInEvent(RoadMapSetCurveLengthEvent.class, context, value -> {
			assertNotEquals(current, value.getValue());
			RoadMapHandler.getInstance().deleteRoadMap(roadMap);
		});
	}
}
