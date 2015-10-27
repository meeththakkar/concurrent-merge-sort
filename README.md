# concurrent-merge-sort

Simple concurrent implementation of merge sort.
(CI builds : https://circleci.com/gh/meeththakkar/concurrent-merge-sort )
Following program demostrates following Aspects of programming
<br/>
1. concurrent programming. 
<br/>
2. basic spring IoC framework.
<br/>
3. use of logging.



There are two differen implmentations of same algorithm to demonstrate concurrent implementation.

First one is edu.ncsu.mhthakka.concurrent.callable.mergesort.CallableMergeSorter
  This is a Class which implements Merge Sort. It  uses Callable API of java. This approach needs too many threads, because Future.get() method is a blocking call. For each sub list to be sorted in merge sort we "might" require new thread. If thread ppool has free theread it might be used instead of creating new thread. The actual number can not be determined but theoretically it may go as high as number of elements to be sorted. 
  
  Second one is edu.ncsu.mhthakka.concurrent.optimized.mergesort.MergeSorter
      This class implements merge sort in concurrent fashion. It sorts the List of String which is passed as an argument in constructor for doing so it splits the list in two almost euqal parts and passes these string in recursively to two of its instances of same type. On completion of task this class it calls the MergeSortCallBack.onSortComplete(List) method. instance of MergeSortCallBack is constructor argument.
