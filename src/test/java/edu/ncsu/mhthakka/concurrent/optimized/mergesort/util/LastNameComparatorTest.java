package edu.ncsu.mhthakka.concurrent.optimized.mergesort.util;

import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;

import edu.ncsu.mhthakka.concurrent.optimized.mergesort.util.LastNameComparator;
import junit.framework.TestCase;

/**
 * 
 * @author mhthakka@ncsu.edu
 *
 */
public class LastNameComparatorTest extends TestCase {

	/**
	 * Brute force test.
	 */

	public void testComparator() {

		Random rand = new Random();

		LastNameComparator comparator = new LastNameComparator();

		for (int i = 0; i < 5000; i++) {
			String fName1 = RandomStringUtils.randomAlphabetic((int) (10 * rand.nextDouble()));
			String lName1 = RandomStringUtils.randomAlphabetic((int) (10 * rand.nextDouble()));

			String name1 = fName1 + " " + lName1;

			String fName2 = RandomStringUtils.randomAlphabetic((int) (10 * rand.nextDouble()));
			String lName2 = RandomStringUtils.randomAlphabetic((int) (10 * rand.nextDouble()));

			String name2 = fName2 + " " + lName2;

			int expected = lName1.compareTo(lName2);

			if (expected != 0) {
				expected = expected / Math.abs(expected);
			}

			assertEquals(expected, comparator.compare(name1, name2));

		}

	}

}
