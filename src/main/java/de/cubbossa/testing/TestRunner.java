package de.cubbossa.testing;

import de.cubbossa.pathfinder.test.TestPlugin;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Bukkit;

import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Level;

@Getter
@Setter
@RequiredArgsConstructor
public class TestRunner {

	private TestSummary summary;
	private TestPrinter printer;

	private Queue<Runnable> pendingTasks = new LinkedList<>();
	private Runnable currentTask = null;
	private TestContext currentContext = null;

	public void run(TestSet tests) {
		run(new SimpleTestSuite(List.of(tests)));
	}

	public void run(TestSuite suite) {

		getSummary().setStartTime(System.currentTimeMillis());

		TestPlugin.getInstance().getLogger().log(Level.INFO, "=".repeat(30));
		TestPlugin.getInstance().getLogger().log(Level.INFO, "Building Queue");
		TestPlugin.getInstance().getLogger().log(Level.INFO, "=".repeat(30));

		Thread.UncaughtExceptionHandler exceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
			currentContext.fail(e);
		});

		queue(suite::beforeSuite);
		for (TestSet set : suite.tests()) {
			queue(suite::beforeTestSet);
			queue(set::beforeAll);

			for (TestExecutor test : getTests(set)) {
				queue(suite::beforeTest);
				queue(set::beforeEach);
				TestPlugin.getInstance().getLogger().log(Level.INFO, set.getClass().getSimpleName() + "::" + test.getTestMethod().getName());
				pendingTasks.add(() -> Bukkit.getScheduler().runTask(TestPlugin.getInstance(), () -> currentContext = test.run(() -> {
					summary.store(test);
					printer.logTest(test);
					next();
				})));
				queue(set::afterEach);
				queue(suite::afterTest);
			}

			queue(suite::afterTestSet);
			queue(set::afterAll);
		}
		queue(suite::afterSuite);
		queue(() -> {
			getSummary().setEndTime(System.currentTimeMillis());
			getPrinter().logSummary(getSummary());
			Thread.setDefaultUncaughtExceptionHandler(exceptionHandler);
		});

		TestPlugin.getInstance().getLogger().log(Level.INFO, "=".repeat(30));
		TestPlugin.getInstance().getLogger().log(Level.INFO, "Starting execution");
		TestPlugin.getInstance().getLogger().log(Level.INFO, "=".repeat(30));

		pendingTasks.poll().run();
	}

	private void queue(Runnable runnable) {
		pendingTasks.add(() -> {
			runnable.run();
			next();
		});
	}

	private void next() {
		currentTask = pendingTasks.poll();
		if (currentTask != null) {
			currentTask.run();
		}
	}

	private List<TestExecutor> getTests(TestSet set) {
		List<TestExecutor> executors = new ArrayList<>();
		for (Method method : getAnnotatedMethods(set)) {
			int timeout = method.getAnnotation(Test.class).timeout();
			executors.add(new TestExecutor(set, method, timeout));
		}
		return executors;
	}

	private List<Method> getAnnotatedMethods(TestSet set) {
		return Arrays.stream(set.getClass().getDeclaredMethods())
				.filter(method -> method.isAnnotationPresent(Test.class))
				.toList();

	}
}
