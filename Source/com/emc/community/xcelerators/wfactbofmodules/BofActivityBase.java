package com.emc.community.xcelerators.wfactbofmodules;

import com.documentum.fc.client.DfSingleDocbaseModule;
import com.documentum.fc.client.IDfSession;
import com.documentum.fc.common.DfException;
import com.documentum.fc.common.DfLogger;
import com.documentum.fc.impl.util.StringUtil;

/**
 * Base class for BOF activities, provided convenience helper methods, such as automatic session handling or simplified logging.
 * @author Michal.Malczewski
 *
 */
public abstract class BofActivityBase extends DfSingleDocbaseModule {

	private final VersionInfo versionInfo = new VersionInfo(this);
	
	/**
	 * Outputs debug message. Full message will contain module class name and concatenated elements of the message argument.
	 * @param message Parts of the message. All of them will be concatenated into a single string.
	 */
	protected void logDebug(Object... message) {
		if (DfLogger.isDebugEnabled(this) == false) {
			// logging is not on on this level, dont waste time to calculate message
			return;
		}
		String finalMessage = buildLogMessage(message);
		DfLogger.debug(this, finalMessage, null, null);
	}
	
	/**
	 * Concatenates actual message with module information
	 */
	private String buildLogMessage(Object...message) {
		StringBuilder msgBuilder = new StringBuilder();
		String moduleName 	= getClass().getSimpleName();
		String version 		= versionInfo.getVersion();
		msgBuilder.append("[").append(moduleName).append(" v." + version + "] ");
		if (message == null) {
			return msgBuilder.toString();
		}
		for (Object msgPart : message) {
			msgBuilder.append(msgPart);
		}
		return msgBuilder.toString();
	}
	
	
	/**
	 * Executes operation implemented in {@link DoInSessionCallback#doInSession(com.documentum.fc.client.IDfSession)} providing 
	 * Documentum session. Session is automatically managed.
	 * @param <R> Type of result
	 * @param cb
	 */

	protected <R> R doInSession(DoInSessionCallback<R> cb) throws DfException {
		IDfSession session = null;
		try {
			session = getSession();
			return cb.doInSession(session);
		}
		finally {
			if (session != null) {
				releaseSession(session);
			}
		}
	}
	
	/**
	 * Validates argument is provided and returns value if it is. Exception will be thrown otherwise,
	 * telling that parameteter spicified with "label" has not been provided. 
	 * Empty string is treated as a non-provided value.
	 */
	protected String getRequiredArgument(String value, String label) throws ArgumentNotProvidedException {
		assertProvided(value, label);
		return value;
	}
	
	/**
	 * Validates argument is provided, exception will be thrown otherwise,
	 * telling that parameteter spicified with "label" has not been provided.
	 * Empty string is treated as a non-provided value. 
	 */
	protected void assertProvided(String value, String label) throws ArgumentNotProvidedException {
		if (StringUtil.isEmptyOrNull(value)) {
			throw ArgumentNotProvidedException.createForMissingArgument(label);
		}
	}

	/**
	 * Validates argument is provided, exception will be thrown otherwise,
	 * telling that parameteter spicified with "label" has not been provided.
	 */
	protected void assertProvided(Object value, String label) throws ArgumentNotProvidedException {
		if (value == null) {
			throw ArgumentNotProvidedException.createForMissingArgument(label);
		}
	}
}
