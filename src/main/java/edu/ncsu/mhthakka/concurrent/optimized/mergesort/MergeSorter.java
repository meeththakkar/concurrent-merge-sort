package edu.ncsu.mhthakka.concurrent.optimized.mergesort;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import org.apache.log4j.Logger;

import edu.ncsu.mhthakka.concurrent.optimized.mergesort.exception.EmptyListException;
import edu.ncsu.mhthakka.concurrent.optimized.mergesort.util.LastNameComparator;
import edu.ncsu.mhthakka.concurrent.optimized.mergesort.util.ListUtils;

/**
 * This class implements merge sort in Async way. It sorts the List of String
 * which is passed as an argument in constructor. on completion of task this
 * class will call the {@link MergeSortCallBack#onSortComplete(List)} method.
 * instance of {@link MergeSortCallBack} is constructor argument.
 * 
 * 
 * @author mhthakka@ncsu.edu
 *
 */
public class MergeSorter implements Runnable, MergeSortCallBack {

	final static Logger logger = Logger.getLogger(MergeSortCallBack.class);

	// task executor.
	private ExecutorService taskExecutor;

	
	//TODO: Optimization vs ease of coding issue: factory should not be a field. it is a singleton
	// factory to create new MergeSorter for sub parts.
	private MergeSorterFactory factory;

	
	// last name comparator. for "Firstname LastName" format
	private LastNameComparator comparator;

	// reference to world. using callback this class will notify the caller that
	// task is complete.
	// we can also use reference to calling object but that will increase the
	// dependency.
	private MergeSortCallBack callBack;

	// list used to store the sorted sub lists which were spawend by this
	// MergeSorter
	private List<List<String>> sortedSubListList = null;

	// List to be sorted .
	private List<String> nameList;

	/**
	 * simple filed based constructor. not to be used publicly. must be consumed
	 * by factory.
	 * 
	 * @param list
	 * @param callBack
	 * @param executor
	 * @param factory
	 * @param comparator
	 */
	protected MergeSorter(List<String> list, MergeSortCallBack callBack, ExecutorService executor,
			MergeSorterFactory factory, LastNameComparator comparator) {
		this.nameList = list;
		this.taskExecutor = executor;
		this.factory = factory;
		this.comparator = comparator;
		this.callBack = callBack;

		// we know that maximum size of this List will be 2.
		sortedSubListList = new ArrayList<List<String>>(2);
	}

	/**
	 * {@link Runnable}'s method.
	 */
	public void run() {
		logger.debug("executing with thread Id" + Thread.currentThread().getId());
		spawnAndSort();
	}

	/**
	 * if size of list is 1 then return the list becuase its sorted. if size is
	 * greaterthan one then devide it into two parts provide these two parts as
	 * an argument to two independent tasks which runs in async.
	 */
	private void spawnAndSort() {

		if (nameList.size() <= 1) {
			// do nothing sorting is done.
			// just report to parent that sorting is done.
			callBack.onSortComplete(nameList);

		} else {
			int toIndex = nameList.size() / 2;

			// we need to use ListUtils.getCopyOfList because,
			// java List.subList(int, int) this method Returns a view of the
			// portion of this list between the specified fromIndex, inclusive,
			// and toIndex, exclusive. (If fromIndex and toIndex are equal, the
			// returned list is empty.) The returned list is backed by this
			// list,
			List<String> subList1 = ListUtils.getCopyOfList(nameList.subList(0, toIndex));
			List<String> subList2 = ListUtils.getCopyOfList(nameList.subList(toIndex, nameList.size()));
			// System.out.println("0, " + toIndex + ", " + list.size());

			MergeSorter subSorter1 = factory.getInstance(subList1, this);
			MergeSorter subSorter2 = factory.getInstance(subList2, this);

			subSorter1.sort();
			subSorter2.sort();

		}

	}

	/**
	 * merges sortedRightList and sortedLeftList
	 */
	private void merge() {

		//TODO: this can be optimized we dont need to clear the list. 
		//clearing list for simplicity and understandablity of code.
		nameList.clear();

		
		//store the sublists in separate variables.
		List<String> sortedSubList1 = sortedSubListList.get(0);
		List<String> sortedSubList2 = sortedSubListList.get(1);

		// remove the smaller value one by one an insert it into list.
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

	/**
	 * Start Sorting.
	 */
	// this is high-level method which is exposed to world.
	public void sort() {
		if (nameList == null || nameList.size() == 0) {
			throw new EmptyListException();
		}

		this.taskExecutor.execute(this);
	}

	/**
	 * This method will be called twice for each instance of {@link MergeSorter}
	 * by it's children One of the call will hit the code which will merge the
	 * two sublists. Another once will cause the calling thread to return. ( and
	 * subsequently it will die. beucase it will not have anything further to
	 * act upon)
	 * 
	 * This method must be synchronized because two children may call it at same
	 * time. This method must be called upon parent. to notify parent that
	 * sorting is complete.
	 * 
	 * @param list2
	 *            SubList which is sorted
	 */
	// not using Callback here. It can be used but not needed.
	public synchronized void onSortComplete(List<String> sortedList) {

		sortedSubListList.add(sortedList);

		// following code will only be executed when we have all the data .
		if (sortedSubListList.size() == 2) {
			merge();

			// now our list/sublist is fully sorted. time to notify to level
			// above.
			// sorting complete notify the caller.
			callBack.onSortComplete(nameList);

		}

	}

}
