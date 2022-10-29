package de.cubbossa.testing;

import de.cubbossa.pathfinder.test.TestPlugin;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;
import java.util.concurrent.TimeoutException;

@Getter
public class TestExecutor {

	private final TestSet set;
	private final Method testMethod;
	private final long timeout;

	private long duration = 0;
	private boolean running = false;
	private boolean failed = false;
	private @Nullable Throwable throwable = null;

	public TestExecutor(TestSet set, Method testMethod, long timeout) {
		this.set = set;
		this.testMethod = testMethod;
		this.timeout = timeout;
	}

	public TestContext run(Runnable onComplete) {
		long start = System.currentTimeMillis();

		running = true;

		TestContext context = new SimpleTestContext(() -> {
			running = false;
			duration = System.currentTimeMillis() - start;
			onComplete.run();
		}, t -> {
			running = false;
			duration = System.currentTimeMillis() - start;
			failed = true;
			throwable = t;
			onComplete.run();
		});
		try {
			if (timeout > 0) {
				if (testMethod.getParameters().length == 1 && testMethod.getParameters()[0].getType().equals(TestContext.class)) {
					testMethod.invoke(set, context);
				} else {
					testMethod.invoke(set);
				}
				Bukkit.getScheduler().runTaskLaterAsynchronously(TestPlugin.getInstance(), () -> {
					if (running) {
						failed = true;
						throwable = new TimeoutException("Test exceeded millisecond limit. Cancelling...");
						context.next();
					}
				}, timeout / 50 /* timeout in ticks */);
			} else {
				if (testMethod.getParameters().length == 1 && testMethod.getParameters()[0].getType().equals(TestContext.class)) {
					testMethod.invoke(set, context);
				} else {
					testMethod.invoke(set);
				}
				if (running) {
					context.next();
				}
			}
		} catch (Throwable t) {
			failed = true;
			throwable = t;
			context.next();
		}
		return context;
	}
}
