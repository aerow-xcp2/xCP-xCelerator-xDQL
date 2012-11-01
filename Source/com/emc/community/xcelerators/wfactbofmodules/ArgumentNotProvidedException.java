package com.emc.community.xcelerators.wfactbofmodules;

import com.documentum.fc.common.DfException;

/**
 * Exception thrown when required argument is not provided
 * @author Michal.Malczewski
 *
 */
public class ArgumentNotProvidedException extends DfException {

	/**
	 * For serialization purposes
	 */
	private static final long	serialVersionUID	= 1L;
	
	
	
	private final String argumentName;

	private ArgumentNotProvidedException(String argumentName) {
		super("Required argument [" + argumentName + "] is not provided");
		this.argumentName = argumentName;
	}
	
	/**
	 * Gets name of the argument whic was missing
	 * @return
	 */
	public String getArgumentName() {
		return argumentName;
	}
	
	/**
	 * Factory method created in order to avoid confusion with {@link Exception#Exception(String)} constructor. 
	 * This class creates message itself, so purpose of {@link ArgumentNotProvidedException#ArgumentNotProvidedException(String)} is different.
	 * @param argumentName
	 * @return
	 */
	public static ArgumentNotProvidedException createForMissingArgument(String argumentName) {
		return new ArgumentNotProvidedException(argumentName);
	}
	
}
