package de.cubbossa.pathfinder.test.cases;

import de.cubbossa.pathfinder.module.visualizing.VisualizerHandler;
import de.cubbossa.pathfinder.module.visualizing.visualizer.ParticleVisualizer;
import de.cubbossa.pathfinder.test.TestPlugin;
import de.cubbossa.testing.Test;
import de.cubbossa.testing.TestSet;
import org.bukkit.NamespacedKey;

import static org.junit.jupiter.api.Assertions.*;

public class VisualizerAPITest implements TestSet {

	@Test
	public void createVisualizer() {
		assertNotNull(VisualizerHandler.getInstance());

		ParticleVisualizer visualizer = VisualizerHandler.getInstance().createPathVisualizer(
				VisualizerHandler.PARTICLE_VISUALIZER_TYPE,
				new NamespacedKey(TestPlugin.getInstance(), "t_vat_1"),
				"t_vat_1");
		assertNotNull(visualizer);
		assertEquals(visualizer.getKey(), new NamespacedKey(TestPlugin.getInstance(), "t_vat_1"));
	}

	@Test
	public void duplicateKey() {
		NamespacedKey key = new NamespacedKey(TestPlugin.getInstance(), "t_vat_2");
		ParticleVisualizer visualizer = VisualizerHandler.getInstance().createPathVisualizer(
				VisualizerHandler.PARTICLE_VISUALIZER_TYPE, key, "t_vat_2"
		);
		assertNotNull(visualizer);
		assertNotNull(VisualizerHandler.getInstance().getPathVisualizer(key));
		assertThrows(IllegalArgumentException.class, () -> VisualizerHandler.getInstance().createPathVisualizer(
				VisualizerHandler.PARTICLE_VISUALIZER_TYPE, key
		));
		assertEquals(visualizer, VisualizerHandler.getInstance().getPathVisualizer(key));
	}
}
