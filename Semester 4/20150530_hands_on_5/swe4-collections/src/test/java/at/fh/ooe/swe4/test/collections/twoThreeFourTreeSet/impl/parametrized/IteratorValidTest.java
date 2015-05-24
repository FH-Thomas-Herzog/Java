package at.fh.ooe.swe4.test.collections.twoThreeFourTreeSet.impl.parametrized;

import iterator.NMKTreeIterator;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import at.fh.ooe.swe4.junit.test.suite.watcher.AbstractConsoleLoggingTest;
import at.fh.ooe.swe4.test.collections.twoThreeFourTreeSet.api.TwoThreeFourTreeSetDataProducer;
import at.fh.ooe.swe4.test.collections.twoThreeFourTreeSet.api.TwoThreeFourTreeSetDataProducer.TestCase;
import at.fh.ooe.swe4.test.collections.twoThreeFourTreeSet.api.TwoThreeFourTreeSetDataProducer.TestData;

@RunWith(Parameterized.class)
public class IteratorValidTest extends AbstractConsoleLoggingTest {

	@Parameter
	public TestData testData;

	@Parameters
	public static final List<Object[]> getTestParameters() {
		final Object[][] parameters = new Object[TestCase.values().length][1];
		int idx = 0;
		for (TestCase testCase : TestCase.values()) {
			parameters[idx][0] = TwoThreeFourTreeSetDataProducer.createTestData(testCase);
			idx++;
		}
		return Arrays.asList(parameters);
	}

	@Test
	public void valid() {
		// -- Given --
		final Iterator<Integer> it = new NMKTreeIterator<Integer>(testData.root);

		// -- When --
		for (int i = 0; i < testData.orderedValues.size(); i++) {
			// -- Then --
			assertEquals(testData.orderedValues.get(i), it.next());
		}
	}
}
