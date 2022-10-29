package de.cubbossa.testing;

import java.util.concurrent.Callable;

public interface TestContext {

	void awaitExecution(String check);

	void completeExecution(String check);

	void next();

	void wait(long duration, Callback callback);

	void fail(Throwable t);

	interface Callback {
		void run() throws Throwable;
	}
}
