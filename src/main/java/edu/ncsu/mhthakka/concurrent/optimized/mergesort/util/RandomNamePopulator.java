package edu.ncsu.mhthakka.concurrent.optimized.mergesort.util;

import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * populates random names in provided list 
 * Format "firstname lastname"
 * @author mhthakka@ncsu.edu
 *
 */
public class RandomNamePopulator {

	
	/**
	 * populate random strings with format of "firstname lastname"
	 * max length of firstname and last name is 10.
	 * firstname and lastname will be alphabetic strings. separated by space.
	 *  
	 * @param list to be populated.
	 * @param number of entries.
	 */
	public static void populateRandomNames(List<String> list, int number) {
		Random rand = new Random();
		for (int i = 0; i < number ; i++) {
			String fName1 = RandomStringUtils.randomAlphabetic((int) (10*rand.nextDouble()));
			String lName1 = RandomStringUtils.randomAlphabetic((int) (10*rand.nextDouble()));
			String name1 = fName1+" "+ lName1;
			list.add(name1);
			
		}
	}

	
}
