package de.cubbossa.pathfinder.test.cases;

import de.cubbossa.pathfinder.PathPlugin;
import de.cubbossa.pathfinder.core.roadmap.RoadMap;
import de.cubbossa.pathfinder.core.roadmap.RoadMapHandler;
import de.cubbossa.pathfinder.data.DataStorage;
import de.cubbossa.pathfinder.data.SqliteDatabase;
import de.cubbossa.pathfinder.test.TestPlugin;
import de.cubbossa.testing.Test;
import de.cubbossa.testing.TestContext;
import de.cubbossa.testing.TestSet;
import lombok.SneakyThrows;
import org.bukkit.NamespacedKey;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RoadMapDatabaseTest implements TestSet {

	private final DataStorage storage = new SqliteDatabase(new File(PathPlugin.getInstance().getDataFolder() + "/data/", "database.db"));

	@Override
	@SneakyThrows
	public void beforeAll() {
		storage.connect();
	}

	@Override
	public void afterAll() {
		storage.disconnect();
	}

	@Test
	public void roadmapCreate(TestContext context) {

		RoadMap roadMap = RoadMapHandler.getInstance().createRoadMap(TestPlugin.getInstance(), "t_rdt_1", true, true);
		context.wait(100, () -> {
			assertTrue(storage.loadRoadMaps().containsKey(new NamespacedKey(TestPlugin.getInstance(), "t_rdt_1")));
			RoadMapHandler.getInstance().deleteRoadMap(roadMap);
		});
	}
}
