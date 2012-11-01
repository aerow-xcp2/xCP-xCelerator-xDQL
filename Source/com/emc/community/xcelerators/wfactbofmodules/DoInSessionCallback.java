package com.emc.community.xcelerators.wfactbofmodules;

import com.documentum.fc.client.IDfSession;
import com.documentum.fc.common.DfException;

/**
 * Callback interface for operations requiring Documentum session
 * @author Michal.Malczewski
 *
 * @param <R> Type of result
 */
public interface DoInSessionCallback<R> {

	/**
	 * Executes operation with session that will be automatically managed
	 * @param session
	 */
	R doInSession(IDfSession session) throws DfException;
}
