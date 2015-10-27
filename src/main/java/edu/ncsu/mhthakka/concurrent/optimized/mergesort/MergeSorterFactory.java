package edu.ncsu.mhthakka.concurrent.optimized.mergesort;

import java.util.List;
import java.util.concurrent.ExecutorService;

import edu.ncsu.mhthakka.concurrent.optimized.mergesort.util.LastNameComparator;
/**
 * factory class to instantiate instances of {@link MergeSorter} with proper
 * executor and comparator.
 * 
 * @author mhthakka@ncsu.edu
 *
 */
public class MergeSorterFactory {

	private ExecutorService executor;
	private LastNameComparator comparator;

	public MergeSorterFactory(ExecutorService executor, LastNameComparator comparator) {
		this.executor = executor;
		this.comparator = comparator;
	}

	/**
	 * Simple factory method to inject executor and comparator and factory itself.
	 * 
	 * @param list
	 *            List to be sorted.
	 * @param the
	 *            {@link MergeSortCallBack} instance. which will be called when
	 *            sorting completes.
	 * @return
	 */
	public MergeSorter getInstance(List<String> list, MergeSortCallBack callback) {
		return new MergeSorter(list, callback, executor, this, comparator);
	}
}
