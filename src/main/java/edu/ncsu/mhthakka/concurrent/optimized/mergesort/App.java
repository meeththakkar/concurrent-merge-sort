package edu.ncsu.mhthakka.concurrent.optimized.mergesort;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.ncsu.mhthakka.concurrent.optimized.mergesort.util.RandomNamePopulator;

/**
 *Test Class for {@link MergeSorter} with Main Method. to play around with
 * 
 * @author mhthakka@ncsu.edu
 *
 */
public class App {

	
	final static Logger logger = Logger.getLogger(App.class);
	
	// using simple static field for an object.
	private static ClassPathXmlApplicationContext context;

	private static long startTime;


	public static void main(String[] args) {

		logger.info("Starting execution");
		
		context = new ClassPathXmlApplicationContext("applicationContext.xml");

		
		List<String> list = new ArrayList<String>();
	
		int number = 50000;

		RandomNamePopulator.populateRandomNames(list, number);
		
		MergeSorterFactory factory = context.getBean(MergeSorterFactory.class);
		MergeSortCallBack callback = new MergeSortCallBack() {
			
			public void onSortComplete(List<String> sortedList) {
				
				logger.info("execution Time =  " +(System.currentTimeMillis()-startTime )+ "milliseconds");
				
				
				logger.debug("Printing sorted List");
				for (String string : sortedList) {
					logger.debug(string);
				}
				
				
				//need to close spring context.
				App.exit();	
			}
			
		};
		
		MergeSorter mergeSorter = factory.getInstance(list, callback );
		
		startTime = System.currentTimeMillis();
		mergeSorter.sort();

	}


	/**
	 * utility method to close the spring context
	 */
	public static void exit() {
		((ConfigurableApplicationContext) context).close();
		System.exit(0);
	}
}
