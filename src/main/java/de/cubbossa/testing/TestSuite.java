package de.cubbossa.testing;

import java.util.Collection;

public interface TestSuite {

	Collection<TestSet> tests();

	default void beforeSuite() {
	}

	default void beforeTestSet() {
	}

	default void beforeTest() {
	}

	default void afterTest() {
	}

	default void afterTestSet() {
	}

	default void afterSuite() {

	}
}
