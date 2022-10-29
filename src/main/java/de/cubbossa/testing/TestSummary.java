package de.cubbossa.testing;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
public class TestSummary {

	private ArrayList<TestExecutor> tests = new ArrayList<>();
	private int totalCount = 0;
	private int successCount = 0;
	private int failureCount = 0;
	@Setter
	private long startTime = 0;
	@Setter
	private long endTime = 0;

	public void store(TestExecutor executor) {
		tests.add(executor);
		if (executor.isFailed()) {
			failureCount++;
		} else {
			successCount++;
		}
		totalCount++;
	}
}
