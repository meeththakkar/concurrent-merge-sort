package edu.ncsu.mhthakka.concurrent.optimized.mergesort;

import java.util.List;

/**
 * An interface to pass the callback object. once an instance of
 * {@link MergeSorter} completes its task it will call
 * {@link MergeSortCallBack#onSortComplete(List)} method.
 * 
 * @author mhthakka@ncsu.edu
 */
public interface MergeSortCallBack {
	/**
	 * deal with next steps once the {@link MergeSorter} completes its task.
	 * 
	 * @param sortedList
	 */
	public void onSortComplete(List<String> sortedList);
}