package de.cubbossa.pathfinder.test.cases;

import de.cubbossa.pathfinder.core.node.NodeGroupHandler;
import de.cubbossa.pathfinder.core.roadmap.RoadMapHandler;
import de.cubbossa.pathfinder.module.visualizing.VisualizerHandler;
import de.cubbossa.pathfinder.test.TestPlugin;
import de.cubbossa.testing.TestSet;
import de.cubbossa.testing.TestSuite;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Suite implements TestSuite {
	@Override
	public Collection<TestSet> tests() {
		return List.of(
				// basic
				new EnabledTest(),
				new VisualizerAPITest(),
				new NodeGroupAPITest(),
				new RoadMapAPITest(),
				new RoadMapAPIModifyTest(),
				// commands
				new VisualizerCommandTest(),
				new RoadMapCommandTest(),
				// events
				new RoadMapEventsTest(),
				new VisualizerEventsTest()
		);
	}

	@Override
	public void afterSuite() {
		Bukkit.getScheduler().runTask(TestPlugin.getInstance(), () -> {
			new ArrayList<>(RoadMapHandler.getInstance().getRoadMaps().values()).forEach(roadMap -> {
				RoadMapHandler.getInstance().deleteRoadMap(roadMap);
			});
			new ArrayList<>(VisualizerHandler.getInstance().getPathVisualizerMap().values()).forEach(visualizer -> {
				VisualizerHandler.getInstance().deletePathVisualizer(visualizer);
			});
			new ArrayList<>(NodeGroupHandler.getInstance().getNodeGroups()).forEach(groupables -> {
				NodeGroupHandler.getInstance().deleteNodeGroup(groupables);
			});
		});
	}
}
