package com.emc.community.xcelerators.wfactbofmodules.xdql;

import com.documentum.fc.common.DfException;
import com.emc.community.xcelerators.wfactbofmodules.xdql.params.CreatedObjectIdResult;
import com.emc.community.xcelerators.wfactbofmodules.xdql.params.NewObjectDefinitionParams;
import com.emc.community.xcelerators.wfactbofmodules.xdql.params.XdqlParams;

/**
 * Extends standard BPM with executing DQL queries as XDQL. 
 * @author Michal.Malczewski
 *
 */
public interface IBofWfActXdql {

	/**
	 * Executes given DQL query and returns XML result
	 * @param xdqlDefinition Definition of the XDQL query
	 */
	String executeQueryAsXdqlAndGetStringResults(final XdqlParams xdqlDefinition) throws DfException;

	/**
	 * Executes given DQL query and stores result as a new repository object
	 * @param xdqlDefinition Definition of the XDQL query
	 * @param newObjectDefinition Definition of the resulting object
	 */
	CreatedObjectIdResult executeQueryAsXdqlAndStoreResultAsNewObject(final XdqlParams xdqlDefinition, final NewObjectDefinitionParams newObjectDefinition) throws DfException;

}