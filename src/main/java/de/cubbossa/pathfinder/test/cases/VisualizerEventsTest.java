package de.cubbossa.pathfinder.test.cases;

import de.cubbossa.pathfinder.module.visualizing.VisualizerHandler;
import de.cubbossa.pathfinder.module.visualizing.events.VisualizerCreatedEvent;
import de.cubbossa.pathfinder.module.visualizing.events.VisualizerNameChangedEvent;
import de.cubbossa.pathfinder.module.visualizing.events.VisualizerPropertyChangedEvent;
import de.cubbossa.pathfinder.module.visualizing.visualizer.ParticleVisualizer;
import de.cubbossa.pathfinder.test.TestPlugin;
import de.cubbossa.testing.Test;
import de.cubbossa.testing.TestContext;
import de.cubbossa.testing.TestSet;
import org.bukkit.NamespacedKey;

import static de.cubbossa.testing.MinecraftAsserts.assertEventCalled;
import static de.cubbossa.testing.MinecraftAsserts.assertInEvent;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class VisualizerEventsTest implements TestSet {

	@Test(timeout = 1000)
	public void visualizerCreate(TestContext context) {
		assertEventCalled(VisualizerCreatedEvent.class, context);
		ParticleVisualizer visualizer = VisualizerHandler.getInstance().createPathVisualizer(
				VisualizerHandler.PARTICLE_VISUALIZER_TYPE,
				new NamespacedKey(TestPlugin.getInstance(), "t_vet_1")
		);
	}

	@Test(timeout = 1000)
	public void visualizerSetSpeed(TestContext context) {
		assertInEvent(VisualizerPropertyChangedEvent.class, context, value -> {
			assertEquals("speed", value.getField());
			assertEquals(0.1f, value.getNewValue());
		});
		ParticleVisualizer visualizer = VisualizerHandler.getInstance().createPathVisualizer(
				VisualizerHandler.PARTICLE_VISUALIZER_TYPE,
				new NamespacedKey(TestPlugin.getInstance(), "t_vet_2")
		);
		VisualizerHandler.getInstance().setProperty(
				TestPlugin.getInstance().getTestExecutor(),
				visualizer, 0.1f, "speed", true,
				visualizer::getSpeed, visualizer::setSpeed
		);
	}
}
