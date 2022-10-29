package de.cubbossa.pathfinder.test.cases;

import de.cubbossa.pathfinder.PathPlugin;
import de.cubbossa.pathfinder.core.roadmap.RoadMap;
import de.cubbossa.pathfinder.core.roadmap.RoadMapHandler;
import de.cubbossa.testing.Test;
import de.cubbossa.testing.TestContext;
import de.cubbossa.pathfinder.test.TestPlugin;
import de.cubbossa.testing.TestSet;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static org.junit.jupiter.api.Assertions.*;

public class RoadMapCommandTest implements TestSet {

	private CommandSender sender;

	@Override
	public void beforeAll() {
		sender = TestPlugin.getInstance().getTestExecutor();
		if (sender == null) {
			sender = Bukkit.getConsoleSender();
		}
	}

	public boolean isPlayer() {
		return sender instanceof Player;
	}

	@Test(timeout = 1000)
	public void createRoadMap(TestContext context) {

		int roadMapCount = RoadMapHandler.getInstance().getRoadMaps().size();
		Bukkit.dispatchCommand(sender, "roadmap create t_rmct_1");

		context.wait(500, () -> {
			assertEquals(roadMapCount + 1, RoadMapHandler.getInstance().getRoadMaps().size());
			RoadMap created = RoadMapHandler.getInstance().getRoadMap(new NamespacedKey(PathPlugin.getInstance(), "t_rmct_1"));
			assertNotNull(created);
			assertTrue(created.getNameFormat().matches("<.+>T_rmct_1</#.+>"));

			RoadMapHandler.getInstance().deleteRoadMap(created);
		});
	}

	@Test(timeout = 1000)
	public void renameRoadMap(TestContext context) {

		RoadMap roadMap = RoadMapHandler.getInstance().createRoadMap(TestPlugin.getInstance(), "t_rmct_2");
		Bukkit.dispatchCommand(sender, "roadmap edit pathfinder-test:t_rmct_2 name t_rmct_3");

		context.wait(500, () -> {
			assertEquals(roadMap.getNameFormat(), "t_rmct_3");

			RoadMapHandler.getInstance().deleteRoadMap(roadMap);
		});
	}

}
