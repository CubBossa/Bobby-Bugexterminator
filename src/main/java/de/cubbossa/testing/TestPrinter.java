package de.cubbossa.testing;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.bukkit.command.CommandSender;

@Getter
@Setter
@RequiredArgsConstructor
public class TestPrinter {

	private final CommandSender target;
	private int exceptionLineCount = 5;

	public void logTest(TestExecutor test) {
		if (test.isFailed()) {

			target.sendMessage(Component.text("Test failed: " + test.getSet().getClass().getSimpleName() + "::" +
					test.getTestMethod().getName(), NamedTextColor.RED));
			if (test.getThrowable() != null) {
				target.sendMessage(ExceptionUtils.getStackTrace(test.getThrowable().getCause() == null ? test.getThrowable() : test.getThrowable().getCause()));
			}
			return;
		}
		target.sendMessage(Component.text("Test " + test.getSet().getClass().getSimpleName() + "::" +
						test.getTestMethod().getName() + " completed in " + test.getDuration() + "ms.",
				NamedTextColor.GREEN));
	}

	public void logSummary(TestSummary summary) {
		target.sendMessage(Component.empty()
				.append(Component.newline())
				.append(Component.text("=".repeat(30)))
				.append(Component.newline())
				.append(Component.text("Total: " + summary.getTotalCount()))
				.append(Component.newline())
				.append(Component.text("Success: " + summary.getSuccessCount()))
				.append(Component.newline())
				.append(Component.text("Fail: " + summary.getFailureCount()))
				.append(Component.newline())
				.append(Component.text("=".repeat(30)))
				.append(Component.newline())
				.append(Component.text("Time elapsed: " + (summary.getEndTime() - summary.getStartTime()) + "ms"))
				.append(Component.newline())
				.append(Component.text("=".repeat(30))));
	}
}
