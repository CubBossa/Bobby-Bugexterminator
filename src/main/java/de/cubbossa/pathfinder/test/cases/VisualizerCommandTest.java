package de.cubbossa.pathfinder.test.cases;

import de.cubbossa.pathfinder.PathPlugin;
import de.cubbossa.pathfinder.module.visualizing.VisualizerHandler;
import de.cubbossa.pathfinder.module.visualizing.events.VisualizerCreatedEvent;
import de.cubbossa.pathfinder.module.visualizing.visualizer.PathVisualizer;
import de.cubbossa.pathfinder.test.TestPlugin;
import de.cubbossa.testing.MinecraftAsserts;
import de.cubbossa.testing.Test;
import de.cubbossa.testing.TestContext;
import de.cubbossa.testing.TestSet;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VisualizerCommandTest implements TestSet {

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

	@Test(timeout = 3000)
	public void importVisualizerRainbow(TestContext context) {

		MinecraftAsserts.assertInEvent(VisualizerCreatedEvent.class, context, value -> {

			if (value.getVisualizer().getKey().equals(NamespacedKey.fromString("pathfinder:example_rainbow"))) {
				PathVisualizer<?, ?> visualizer = VisualizerHandler.getInstance().getPathVisualizer(new NamespacedKey(PathPlugin.getInstance(), "example_rainbow"));
				assertNotNull(visualizer);
			}
		});
		Bukkit.dispatchCommand(sender, "pathvisualizer import pathfinder$example_rainbow.yml");

	}

	@Test(timeout = 1000)
	public void createVisualizer(TestContext context) {

		MinecraftAsserts.assertInEvent(VisualizerCreatedEvent.class, context, value -> {
			if (value.getVisualizer().getKey().equals(NamespacedKey.fromString("pathfinder:t_vct_1"))) {
				PathVisualizer<?, ?> visualizer = VisualizerHandler.getInstance().getPathVisualizer(new NamespacedKey(PathPlugin.getInstance(), "t_vct_1"));
				assertNotNull(visualizer);
				assertTrue(visualizer.getNameFormat().matches("<#.+>T_vct_1</#.+>"));
			}
		});
		Bukkit.dispatchCommand(sender, "pathvisualizer create pathfinder:particle t_vct_1");
	}
}