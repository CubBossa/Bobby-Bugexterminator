package de.cubbossa.pathfinder.test.cases;

import de.cubbossa.pathfinder.module.visualizing.VisualizerHandler;
import de.cubbossa.pathfinder.module.visualizing.visualizer.ParticleVisualizer;
import de.cubbossa.testing.Test;
import de.cubbossa.pathfinder.test.TestPlugin;
import de.cubbossa.testing.TestSet;
import org.bukkit.NamespacedKey;

import static org.junit.jupiter.api.Assertions.*;

public class VisualizerAPITest implements TestSet {

	@Test
	public void createVisualizer() {
		assertNotNull(VisualizerHandler.getInstance());

		ParticleVisualizer visualizer = VisualizerHandler.PARTICLE_VISUALIZER_TYPE.create(new NamespacedKey(TestPlugin.getInstance(), "t_vat_1"), "t_vat_1");
		assertNotNull(visualizer);
		assertEquals(visualizer.getKey(), new NamespacedKey(TestPlugin.getInstance(), "t_vat_1"));
	}
}
