package edu.ncsu.mhthakka.concurrent.callable.mergesort;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;

import edu.ncsu.mhthakka.concurrent.optimized.mergesort.util.FixedNamePopulator;


/**
 * 
 * @author mhthakka@ncsu.edu
 *
 */
public class App {

	final static Logger logger = Logger.getLogger(App.class);


	// default constructor.
	private App() {
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {

		logger.info("Starting execution");

		
		List<String> list = new ArrayList<String>();

		// using simple POJO to populate a list. this can be easily replaced
		// with
		// more complicated method like reading values from file

		// each iteration populates 1000 names. here we are soring 200000 names.
		for (int i = 0; i < 200; i++) {
			FixedNamePopulator.populateList(list);
		}

		// caution :Greedy.
		ExecutorService executor = Executors.newCachedThreadPool();
		CallableMergeSorter mergeSorter = new CallableMergeSorter(list, executor);

		long startTime = System.currentTimeMillis();

		Future<List<String>> future = executor.submit(mergeSorter);

		List<String> sorted = future.get();

		logger.info("execution Time =  " + (System.currentTimeMillis() - startTime) + "milliseconds");


		
		logger.debug("Printing sorted List");
		for (String string : sorted) {
			logger.debug(string);
		}
		
		
		exit();
	}

	/**
	 * utility method to close the spring context
	 */
	public static void exit() {
		System.exit(0);
	}
}
