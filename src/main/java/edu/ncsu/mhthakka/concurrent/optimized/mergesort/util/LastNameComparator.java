package edu.ncsu.mhthakka.concurrent.optimized.mergesort.util;

import java.util.Comparator;

/**
 * Compares Last name of given Name. Note: Assumption is that String has
 * following format "FirstName LastName" This Class dosen't use String compare
 * method. Reason for using custom method is as following. This method will be
 * called many times. to avoid performance slow-down we must create most
 * efficient code in this area.
 * 
 * This Class doesn't perform case insensitive comparison.
 * 
 * @author mhthakka@ncsu.edu
 *
 */
public class LastNameComparator implements Comparator<String> {

	public int compare(String o1, String o2) {

		// split string on space. and take second element. and compare them.

		/*
		 * String lastNameo1 = o1.split(" ")[1]; 
		 * String lastNameo2 = o2.split(" ")[1];
		 */

		// above method can be optimized.
		// we can reduce allocation of array of String. and String in those arrays.

		
		int index1 = o1.indexOf(' ');
		int index2 = o2.indexOf(' ');

		// now, rather than creating sub string which allocates one more object,
		// we can use same string and do comparison

		// actual last name will start from index+1 position.
		index1 = index1 + 1;
		index2 = index2 + 1;

		// len is maximux safe length to compare charachters in juxtapositioned
		// index.
		int len1 = o1.length() - (index1);
		int len2 =o2.length() - (index2); 

		int len;
		if(len1<= len2)
		{
			len = len1;
		}
		else
		{
			len = len2;
		}

		
		for (int i = 0; i < len; i++) {
			if (o1.charAt(i + index1) < o2.charAt(i + index2))
				return -1;
			else if (o1.charAt(i+index1) > o2.charAt(i+ index2))
				return 1;
			// continue in case of ==
		}

		// if execution reached here that means all the character upto lenth
		// of index 1 are same.

		if ((o1.length() - index1) == (o2.length() - index2)) {
			return 0;
		} else if ((o1.length() - index1) > (o2.length() - index2)) {
			return 1;
		} else {
			return -1;
		}

	}

}
