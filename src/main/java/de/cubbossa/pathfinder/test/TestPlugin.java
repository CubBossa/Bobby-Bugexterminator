package de.cubbossa.pathfinder.test;

import de.cubbossa.pathfinder.test.cases.*;
import de.cubbossa.testing.TestPrinter;
import de.cubbossa.testing.TestRunner;
import de.cubbossa.testing.TestSummary;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class TestPlugin extends JavaPlugin {

	@Getter
	private static TestPlugin instance;
	@Getter
	private CommandSender testExecutor;

	@Override
	public void onLoad() {
		super.onLoad();
		instance = this;
	}

	@Override
	public void onEnable() {
		super.onEnable();

		getCommand("pathfinder-test-routine").setExecutor((commandSender, command, s, strings) -> {
			testExecutor = commandSender;
			TestRunner runner = new TestRunner();
			runner.setSummary(new TestSummary());
			runner.setPrinter(new TestPrinter(commandSender));
			runner.run(new Suite());
			return true;
		});
	}

	@Override
	public void onDisable() {
		super.onDisable();
	}
}
