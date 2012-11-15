package com.emc.community.xcelerators.wfactbofmodules.xdql.params;

import com.documentum.fc.common.IDfId;

/**
 * Represents result of {@link IBofWfActXdql} methods returning an instance of a new object
 * @author Michal.Malczewski
 *
 */
public class CreatedObjectIdResult {

	private String createdObjectId;

	/**
	 * Default construcor, simply creates new instance
	 */
	public CreatedObjectIdResult() {}
	
	/**
	 * Inline .ctor
	 */
	public CreatedObjectIdResult(IDfId createdObjectId) {
		this.createdObjectId = createdObjectId.getId();
	}

	/**
	 * Gets ID of the object created
	 * @return
	 */
	public String getCreatedObjectId() {
		return createdObjectId;
	}

	public void setCreatedObjectId(String createdObjectId) {
		this.createdObjectId = createdObjectId;
	}
}
