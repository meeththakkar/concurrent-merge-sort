package edu.ncsu.mhthakka.concurrent.optimized.mergesort;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.ncsu.mhthakka.concurrent.optimized.mergesort.exception.EmptyListException;
import edu.ncsu.mhthakka.concurrent.optimized.mergesort.util.RandomNamePopulator;
import junit.framework.TestCase;


/**
 * 
 * Note: these are Sanity tests , these are not detailed Junit Tests.
 *  
 * @author mhthakka@ncsu.edu
 *
 */

public class MergeSorterTest extends TestCase {

	final static Logger logger = Logger.getLogger(MergeSorterTest.class);

	long startTime;

	
	
	public void testException() throws InterruptedException, ExecutionException {
		
		List<String> list = new ArrayList<String>();

		final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

		
		MergeSorterFactory factory = context.getBean(MergeSorterFactory.class);
		MergeSortCallBack callback = new MergeSortCallBack() {
			public void onSortComplete(List<String> sortedList) {
			//	context.close();
			//	System.exit(0);

			}

		};
		

		MergeSorter mergeSorter = factory.getInstance(list, callback);
	try{	
		mergeSorter.sort();
		
		fail("code should not reach here beucase we are expecting exception to be thrown.");
	}
		catch(EmptyListException e)
		{
			//NO-OP
		}
		
		
		


	}
	
	
	// TODO: load tests , integrataion tests must not be done in jnits.
	public void testCorrectnessOfSotring() {

		final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

		List<String> list = new ArrayList<String>();


		list.add("Mike a");
		list.add("Mike A");
		list.add("Mike AA");
		list.add("Mike ab");
		list.add("Neal Az");
		list.add("Neal aa");
		list.add("Neal Aa");
		list.add("Neal az");

		MergeSorterFactory factory = context.getBean(MergeSorterFactory.class);
		MergeSortCallBack callback = new MergeSortCallBack() {

			public void onSortComplete(List<String> sortedList) {

				logger.info("execution Time =  " + (System.currentTimeMillis() - startTime) + "milliseconds");

				logger.debug("Printing sorted List");
				for (String string : sortedList) {
					logger.debug(string);
				}

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

					assertEquals(expected.get(i), sortedList.get(i));

				}
				
				context.close();
		//System.exit(0);

			}

		};

		MergeSorter mergeSorter = factory.getInstance(list, callback);

		
		startTime = System.currentTimeMillis();
		mergeSorter.sort();

		
		
	}

	// TODO: load test/ integration tests must not be done in junit.
	public void testLoad() {

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

		List<String> list = new ArrayList<String>();

		int number = 20000;

		RandomNamePopulator.populateRandomNames(list, number);

		MergeSorterFactory factory = context.getBean(MergeSorterFactory.class);
		MergeSortCallBack callback = new MergeSortCallBack() {

			public void onSortComplete(List<String> sortedList) {

				logger.info("execution Time =  " + (System.currentTimeMillis() - startTime) + "milliseconds");

				logger.debug("Printing sorted List");
				for (String string : sortedList) {
					logger.debug(string);
				}

			}

		};

		MergeSorter mergeSorter = factory.getInstance(list, callback);

		startTime = System.currentTimeMillis();
		mergeSorter.sort();

	}

}
