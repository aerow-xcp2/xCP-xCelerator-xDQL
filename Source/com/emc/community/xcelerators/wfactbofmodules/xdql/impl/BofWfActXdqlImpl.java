package com.emc.community.xcelerators.wfactbofmodules.xdql.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.documentum.fc.client.IDfSession;
import com.documentum.fc.client.IDfSysObject;
import com.documentum.fc.common.DfException;
import com.documentum.fc.common.DfLogger;
import com.documentum.xml.xdql.DfXmlQuery;
import com.documentum.xml.xdql.IDfXmlQuery;
import com.emc.community.xcelerators.wfactbofmodules.BofActivityBase;
import com.emc.community.xcelerators.wfactbofmodules.DoInSessionCallback;
import com.emc.community.xcelerators.wfactbofmodules.xdql.IBofWfActXdql;
import com.emc.community.xcelerators.wfactbofmodules.xdql.params.CreatedObjectIdResult;
import com.emc.community.xcelerators.wfactbofmodules.xdql.params.NewObjectDefinitionParams;
import com.emc.community.xcelerators.wfactbofmodules.xdql.params.XdqlParams;


public class BofWfActXdqlImpl extends BofActivityBase implements IBofWfActXdql{

	private static final String	CONTENT_ENCODING__DOM	= "dom";
	private static final String	DEFAULT_OBJECT_TYPE		= "dm_document";
	private static final String	DEFAULT_NAME_PREFIX		= "xdqlresult_";
	private static final String	XML_CONTENT_TYPE		= "xml";
	
	/* (non-Javadoc)
	 * @see com.emc.community.xcelerators.wfactbofmodules.xdql.impl.I#executeQueryAsXdqlAndGetStringResults(com.emc.community.xcelerators.wfactbofmodules.xdql.params.XdqlParams)
	 */
	public String executeQueryAsXdqlAndGetStringResults(final XdqlParams params) throws DfException {
		DoInSessionCallback<String> xdqlCallback = new DoInSessionCallback<String>() {
			
			public String doInSession(IDfSession session) throws DfException {
				return executeXdqlAndGetStringResult(params, session);				
			}
		};
		return doInSession(xdqlCallback);
	}
	
	/* (non-Javadoc)
	 * @see com.emc.community.xcelerators.wfactbofmodules.xdql.impl.I#executeQueryAndStoreResultAsNewObject(com.emc.community.xcelerators.wfactbofmodules.xdql.params.XdqlParams, com.emc.community.xcelerators.wfactbofmodules.xdql.params.NewObjectDefinitionParams)
	 */
	public CreatedObjectIdResult executeQueryAsXdqlAndStoreResultAsNewObject(final XdqlParams params, final NewObjectDefinitionParams newObjectDefinition) throws DfException {
			DoInSessionCallback<CreatedObjectIdResult> xdqlCallback = new DoInSessionCallback<CreatedObjectIdResult>() {
			
			public CreatedObjectIdResult doInSession(IDfSession session) throws DfException {
				final String xmlResult = executeXdqlAndGetStringResult(params, session);
				IDfSysObject result = storeXmlAsNewObject(xmlResult, newObjectDefinition, session);
				return new CreatedObjectIdResult(result.getObjectId());
			}
		};
		return doInSession(xdqlCallback);
	}
	
	/**
	 * Executes DQL query and gets result as XQDL
	 */
	private String executeXdqlAndGetStringResult(XdqlParams params, IDfSession session) throws DfException {
		final String dql 					= getRequiredArgument(params.getDql(), "params.dql");		
		final boolean includeContentsAsDom 	= params.isIncludeContentsAsDom();
		logDebug("Prepating to execute query: ", dql, "include contents as DOM: ", includeContentsAsDom, "; ");
			
		IDfXmlQuery query = new DfXmlQuery();
		query.setDql(dql);
		
		if (includeContentsAsDom) {
			query.includeContent(true);
			query.setContentEncoding(CONTENT_ENCODING__DOM);	
		}
		else {
			query.includeContent(false);
		}
				
		// FIXME: incorrect DQL does not cause exception
		query.execute(0, session);
		String xdqlResult = query.getXMLString();
		if (DfLogger.isDebugEnabled(this)) {
			logDebug("Got XDQL result\n", xdqlResult);
		}
		return xdqlResult;
	}
	
	/**
	 * Stores XML result as an instance of new object 
	 * @throws DfException 
	 * @throws IOException 
	 */
	private IDfSysObject storeXmlAsNewObject(String xml, NewObjectDefinitionParams newObjectDefinition, IDfSession session) throws DfException {
		String objectType = null;
		String objectName = null;
		if (newObjectDefinition != null) {
			objectType = newObjectDefinition.getObjectTypeDefaultDmDocument();
			objectName = newObjectDefinition.getObjectName();
		}
		if (objectType == null) {
			objectType = DEFAULT_OBJECT_TYPE;
		}
		if (objectName == null) {
			objectName = DEFAULT_NAME_PREFIX + System.currentTimeMillis();
		}
		
		logDebug("Creating instance of ", objectType, " to store XDQL result");
		IDfSysObject resultObj = (IDfSysObject) session.newObject(objectType);
		resultObj.setObjectName(objectName);
		resultObj.setContentType(XML_CONTENT_TYPE);
		ByteArrayOutputStream xmlAsStream = new ByteArrayOutputStream(xml.length());
		try {
			xmlAsStream.write(xml.getBytes());
		} catch (IOException e) {
			throw new DfException("Failed to store XML content", e);
		}
		resultObj.setContent(xmlAsStream);
		resultObj.save();
		return resultObj;
	}
}
