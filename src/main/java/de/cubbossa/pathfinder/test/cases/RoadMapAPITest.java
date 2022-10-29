package de.cubbossa.pathfinder.test.cases;

import de.cubbossa.pathfinder.core.roadmap.RoadMap;
import de.cubbossa.pathfinder.core.roadmap.RoadMapHandler;
import de.cubbossa.testing.Test;
import de.cubbossa.pathfinder.test.TestPlugin;
import de.cubbossa.testing.TestSet;
import org.bukkit.NamespacedKey;

import static org.junit.jupiter.api.Assertions.*;

public class RoadMapAPITest implements TestSet {


	@Test
	public void createRoadMap() {

		assertNotNull(RoadMapHandler.getInstance());
		RoadMap roadMap = RoadMapHandler.getInstance().createRoadMap(TestPlugin.getInstance(), "t_rmat_1");
		assertNotNull(roadMap);
		assertEquals(1, RoadMapHandler.getInstance().getRoadMaps().size());

		RoadMapHandler.getInstance().deleteRoadMap(roadMap);
	}

	@Test
	public void getRoadMap() {
		int startCount = RoadMapHandler.getInstance().getRoadMaps().size();
		RoadMap rm1 = RoadMapHandler.getInstance().createRoadMap(TestPlugin.getInstance(), "t_rmat_2");
		RoadMap rm2 = RoadMapHandler.getInstance().createRoadMap(TestPlugin.getInstance(), "t_rmat_3");
		RoadMap rm3 = RoadMapHandler.getInstance().createRoadMap(TestPlugin.getInstance(), "t_rmat_4");

		assertEquals(rm1, RoadMapHandler.getInstance().getRoadMap(rm1.getKey()));
		assertEquals(rm2, RoadMapHandler.getInstance().getRoadMap(rm2.getKey()));
		assertEquals(rm3, RoadMapHandler.getInstance().getRoadMap(rm3.getKey()));
		assertEquals(startCount + 3, RoadMapHandler.getInstance().getRoadMaps().size());

		RoadMapHandler.getInstance().deleteRoadMap(rm1);
		RoadMapHandler.getInstance().deleteRoadMap(rm2);
		RoadMapHandler.getInstance().deleteRoadMap(rm3);
		assertEquals(startCount, RoadMapHandler.getInstance().getRoadMaps().size());
	}

	@Test
	public void deleteRoadMap() {
		RoadMap roadMap = RoadMapHandler.getInstance().createRoadMap(TestPlugin.getInstance(), "t_rmat_5");
		NamespacedKey key = roadMap.getKey();
		RoadMapHandler.getInstance().deleteRoadMap(roadMap);
		assertEquals(0, RoadMapHandler.getInstance().getRoadMaps().size());
		assertNull(RoadMapHandler.getInstance().getRoadMap(key));
	}

	@Test
	public void duplicateKey() {
		RoadMap rm = RoadMapHandler.getInstance().createRoadMap(TestPlugin.getInstance(), "t_rmat_6");
		int roadMapCount = RoadMapHandler.getInstance().getRoadMaps().size();
		assertThrows(IllegalArgumentException.class, () -> RoadMapHandler.getInstance().createRoadMap(TestPlugin.getInstance(), "t_rmat_6"));
		assertEquals(roadMapCount, RoadMapHandler.getInstance().getRoadMaps().size());

		RoadMapHandler.getInstance().deleteRoadMap(rm);
	}
}
