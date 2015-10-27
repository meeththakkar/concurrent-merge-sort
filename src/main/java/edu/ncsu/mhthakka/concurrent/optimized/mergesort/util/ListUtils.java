package edu.ncsu.mhthakka.concurrent.optimized.mergesort.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Separate Class for utility methods.
 * 
 * @author mhthakka@ncsu.edu
 *
 */
// Reasons to have utility class:
// 1.we don't want to mess  code in other classes.
// 2. we want reuse code in decrease code duplication.
public class ListUtils {

	/**
	 * java {@link List#subList(int, int)} this method Returns a view of the
	 * portion of this list between the specified fromIndex, inclusive, and
	 * toIndex, exclusive. (If fromIndex and toIndex are equal, the returned
	 * list is empty.) The returned list is backed by this list, so
	 * non-structural changes in the returned list are reflected in this list,
	 * and vice-versa. The returned list supports all of the optional list
	 * operations supported by this list.
	 * 
	 * 
	 * We need to make an independent list. Notice that String object references
	 * are not changing ( not using duplicate memory to store same strings.
	 * 
	 * @param subList
	 * @return independent list created from given list.
	 */
	public static List<String> getCopyOfList(List<String> subList) {
		List<String> ret = new ArrayList<String>();
		for (String str : subList) {
			ret.add(str);
		}
		return ret;
	}

}
