package edu.ncsu.mhthakka.concurrent.callable.mergesort;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;

import edu.ncsu.mhthakka.concurrent.optimized.mergesort.util.RandomNamePopulator;
import junit.framework.TestCase;

/**
 * 
 * @author mhthakka@ncsu.edu
 *
 */
public class CallableMergeSortTest extends TestCase {

	final static Logger logger = Logger.getLogger(CallableMergeSortTest.class);

	

		
	public void testSortCorrectness() throws InterruptedException, ExecutionException {

		List<String> list = new ArrayList<String>();
		// each iteration populates 1000 names. i.e. 200,000 names.

		list.add("Mike a");
		list.add("Mike A");
		list.add("Mike AA");
		list.add("Mike ab");
		list.add("Neal Az");
		list.add("Neal aa");
		list.add("Neal Aa");
		list.add("Neal az");
		
		
		ExecutorService executor = Executors.newCachedThreadPool();
		CallableMergeSorter mergeSorter = new CallableMergeSorter(list, executor);

		
		Future<List<String>> future = executor.submit(mergeSorter);

		List<String> sorted = future.get();
		

		
		List<String> expected = new ArrayList<String>();
		
		
		expected.add("Mike A");
		expected.add("Mike AA");
		expected.add("Neal Aa");
		expected.add("Neal Az");
		expected.add("Mike a");
		expected.add("Neal aa");
		expected.add("Mike ab");
		expected.add("Neal az");
		

		
		for (int i = 0; i < expected.size(); i++) {
			
			assertEquals(expected.get(i), sorted.get(i));
			
		}
		

	}
	
	// TODO: load test/ integration tests must not be done in junit.
	public void testLoad() throws InterruptedException, ExecutionException{
		
		
		logger.info("testWithRandomNames");
		List<String> list = new ArrayList<String>();
		
	
		int number = 20000;
		
		
		RandomNamePopulator.populateRandomNames(list, number);
		
		
		
		// caution : Cached threadpool executor has no upper bound on number of
		// threads it spawns
		ExecutorService executor = Executors.newCachedThreadPool();
		CallableMergeSorter mergeSorter = new CallableMergeSorter(list, executor);

		long startTime = System.currentTimeMillis();

		Future<List<String>> future = executor.submit(mergeSorter);

		List<String> sorted = future.get();

		logger.info("execution Time for "+number+" items  " + (System.currentTimeMillis() - startTime) + "milliseconds");
		
		
		
	}

}
