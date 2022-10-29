package de.cubbossa.testing;

import java.util.List;

public record SimpleTestSuite(List<TestSet> tests) implements TestSuite {
}
