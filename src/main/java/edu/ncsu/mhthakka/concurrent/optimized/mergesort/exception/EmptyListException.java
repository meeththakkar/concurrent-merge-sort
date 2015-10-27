package edu.ncsu.mhthakka.concurrent.optimized.mergesort.exception;


/**
 * Example Runtime Exception ( this must not be caught explicitly in code) 
 * 
 * Smple Exception handling.
 * @author mhthakka@ncsu.edu
 */
public class EmptyListException extends RuntimeException {

	/**
	 * generated
	 */
	private static final long serialVersionUID = -6088649227140778001L;

	public EmptyListException() {
		//adding some details here. 
		//likewise, more processing can be done in Subclasses of exceptions.
		super("Please provide non empty and not null list to mergeSorter.");
	}

}
