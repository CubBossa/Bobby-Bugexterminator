package de.cubbossa.testing;

import de.cubbossa.pathfinder.test.TestPlugin;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.function.Consumer;

@Getter
@Setter
@RequiredArgsConstructor
public class SimpleTestContext implements TestContext {

	private final ArrayList<String> pendingChecks = new ArrayList<>();
	private final Runnable skipExecutor;
	private final Consumer<Throwable> exceptionHandler;

	@Override
	public void awaitExecution(String check) {
		pendingChecks.add(check);
	}

	@Override
	public void completeExecution(String check) {
		pendingChecks.remove(check);
		if (pendingChecks.isEmpty()) {
			next();
		}
	}

	@Override
	public void next() {
		skipExecutor.run();
	}

	@Override
	public void wait(long duration, Callback callback) {
		Bukkit.getScheduler().runTaskLater(TestPlugin.getInstance(), () -> {
			try {
				callback.run();
				next();
			} catch (Throwable t) {
				fail(t);
			}
		}, duration / 5);
	}

	@Override
	public void fail(Throwable t) {
		this.exceptionHandler.accept(t);
	}
}
