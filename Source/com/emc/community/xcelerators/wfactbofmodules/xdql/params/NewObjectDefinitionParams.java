package com.emc.community.xcelerators.wfactbofmodules.xdql.params;


/**
 * Defines what the new object created for result of {@link IBofWfActXdql} operations will be like
 * @author Michal.Malczewski
 *
 */
public class NewObjectDefinitionParams {

	private String objectTypeDefaultDmDocument;
	private String objectName;
	
	
	/**
	 * Gets object type of the resulting object. If not set, dm_document will be used.
	 */
	public String getObjectTypeDefaultDmDocument() {
		return objectTypeDefaultDmDocument;
	}
	
	/**
	 * Sets object type of the resulting object. If not set, dm_document will be used.
	 */
	public void setObjectTypeDefaultDmDocument(String objectTypeDefaultDmDocument) {
		this.objectTypeDefaultDmDocument = objectTypeDefaultDmDocument;
	}
	
	/**
	 * Gets object name of the resulting object.
	 */
	public String getObjectName() {
		return objectName;
	}
	
	/**
	 * Sets object name of the resulting object.
	 */	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	
	
}
