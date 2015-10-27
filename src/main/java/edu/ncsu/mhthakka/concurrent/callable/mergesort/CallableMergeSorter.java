package edu.ncsu.mhthakka.concurrent.callable.mergesort;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import edu.ncsu.mhthakka.concurrent.optimized.mergesort.util.LastNameComparator;
import edu.ncsu.mhthakka.concurrent.optimized.mergesort.util.ListUtils;

/**
 * This is a Class which implements MergeSort but uses {@link Callable} API of java.
 * 
 * This approach needs too many threads, because {@link Future#get()} method is a blocking call. 
 * The actual number can not be determined but theoretically it may go as high as number of elements to be sorted. 
 * 
 * @author mhthakka@ncsu.edu
 *
 */
public class CallableMergeSorter implements Callable<List<String>> {

	
	private List<String> nameList;
	
	// task executor.
	private ExecutorService  executor;

	// last name comparator. for "Firstname LastName" format
	//static initialization. 
	private static LastNameComparator comparator = new LastNameComparator();

	
	public CallableMergeSorter(List<String> list,ExecutorService  executor) {
		super();
		this.nameList = list;
		this.executor =  executor;
	}

	

	/**
	 * performs sorting of a list given as constructor argument. 
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	public List<String> call() throws InterruptedException, ExecutionException{
		
		
		if (nameList.size() <= 1) {
			// do nothing sorting is done.
			// just report to parent that sorting is done.
			return nameList;

		} else {
			//devide whole list into two sub lists and process them in parallel.
			int toIndex = nameList.size() / 2;

			// we need to use ListUtils.getCopyOfList because,
			// java List.subList(int, int) this method Returns a view of the
			// portion of this list between the specified fromIndex, inclusive,
			// and toIndex, exclusive. (If fromIndex and toIndex are equal, the
			// returned list is empty.) The returned list is backed by this
			// list,
			List<String> subList1 = ListUtils.getCopyOfList(nameList.subList(0, toIndex));
			List<String> subList2 = ListUtils.getCopyOfList(nameList.subList(toIndex, nameList.size()));

			
			CallableMergeSorter left = new CallableMergeSorter(subList1, executor);
			CallableMergeSorter right = new CallableMergeSorter(subList2, executor);
			
			
			//both tasks will run in async.
			Future<List<String>> sortedLeft = executor.submit(left);
			Future<List<String>> sortedRight =executor.submit(right);

			
			//safe to wait for first list, because we want both of them to complete.
			List<String> sortedSubList1 = sortedLeft.get();
			List<String> sortedSubList2 = sortedRight.get();
			
			mergeintoNameList(sortedSubList1, sortedSubList2);
			
			return nameList;
		

		}

		
	}


	/**
	 * merges both arguments and stores them in {@link CallableMergeSorter#nameList}
	 * @param sortedSubList1
	 * @param sortedSubList2
	 */
	private void mergeintoNameList(List<String> sortedSubList1, List<String> sortedSubList2) {
		nameList.clear();

		// use stack type pop operation.
		while (sortedSubList1.size() > 0 && sortedSubList2.size() > 0) {

			if (comparator.compare(sortedSubList1.get(0), sortedSubList2.get(0)) <= 0) {
				nameList.add(sortedSubList1.remove(0));
			} else {
				nameList.add(sortedSubList2.remove(0));

			}

		}

		// see if any pending elements are remaining in any list and append them
		// to end of list.
		if (sortedSubList1.size() > 0) {
			nameList.addAll(sortedSubList1);
		}

		if (sortedSubList2.size() > 0) {
			nameList.addAll(sortedSubList2);
		}
	}
	
	
	

	  
	}