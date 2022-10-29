package de.cubbossa.pathfinder.test.cases;

import de.cubbossa.pathfinder.PathPlugin;
import de.cubbossa.pathfinder.core.ExamplesHandler;
import de.cubbossa.pathfinder.core.node.NodeGroupHandler;
import de.cubbossa.pathfinder.core.roadmap.RoadMapHandler;
import de.cubbossa.pathfinder.module.visualizing.VisualizerHandler;
import de.cubbossa.testing.Test;
import de.cubbossa.pathfinder.test.TestPlugin;
import de.cubbossa.testing.TestSet;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EnabledTest implements TestSet {

	@Test
	public void testEnabled() {
		assertNotNull(TestPlugin.getInstance().getServer().getPluginManager().getPlugin("Pathfinder"));
	}

	@Test
	public void testDirectoryCreated() {
		Plugin plugin = TestPlugin.getInstance().getServer().getPluginManager().getPlugin("Pathfinder");
		assertNotNull(plugin);
		File folder = plugin.getDataFolder();
		assertNotNull(folder);
		List<File> children = List.of(folder.listFiles());
		assertNotNull(children.contains(new File(folder, "config.yml")));
		assertNotNull(children.contains(new File(folder, "data")));
	}

	@Test
	public void allSingletonsPresent() {
		assertNotNull(PathPlugin.getInstance());
		assertNotNull(RoadMapHandler.getInstance());
		assertNotNull(NodeGroupHandler.getInstance());
		assertNotNull(VisualizerHandler.getInstance());
		assertNotNull(ExamplesHandler.getInstance());
	}
}
