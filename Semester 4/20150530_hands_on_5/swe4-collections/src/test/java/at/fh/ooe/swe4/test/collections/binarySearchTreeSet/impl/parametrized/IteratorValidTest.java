package at.fh.ooe.swe4.test.collections.binarySearchTreeSet.impl.parametrized;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import at.fh.ooe.swe4.collections.impl.BinarySearchTreeSet.BinarySearchTreeIterator;
import at.fh.ooe.swe4.junit.test.suite.watcher.AbstractConsoleLoggingTest;
import at.fh.ooe.swe4.test.collections.binarySearchTreeSet.api.BinarySearchTreeTestDataProducer;
import at.fh.ooe.swe4.test.collections.binarySearchTreeSet.api.BinarySearchTreeTestDataProducer.TestCase;
import at.fh.ooe.swe4.test.collections.binarySearchTreeSet.api.BinarySearchTreeTestDataProducer.TestData;

/**
 * This test class is a parameterized test for the
 * {@link BinarySearchTreeIterator} implementation.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date May 27, 2015
 */
@RunWith(Parameterized.class)
public class IteratorValidTest extends AbstractConsoleLoggingTest {

	@Parameter
	public TestData testData;

	@Parameters
	public static final List<Object[]> getTestParameters() {
		final Object[][] parameters = new Object[TestCase.values().length][1];
		int idx = 0;
		for (TestCase testCase : TestCase.values()) {
			parameters[idx][0] = BinarySearchTreeTestDataProducer
					.createTestData(testCase);
			idx++;
		}
		return Arrays.asList(parameters);
	}

	@Test
	public void valid() {
		// -- Given --
		final Iterator<Integer> it = new BinarySearchTreeIterator<Integer>(
				testData.root);

		// -- When --
		for (int i = 0; i < testData.orderedValues.size(); i++) {
			// -- Then --
			assertEquals(testData.orderedValues.get(i), it.next());
		}
	}
}
