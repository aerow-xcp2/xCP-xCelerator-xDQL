package com.emc.community.xcelerators.wfactbofmodules.xdql.params;


/**
 * Defines DQL query being executed
 * @author Michal.Malczewski
 *
 */
public class XdqlParams {

	private String dql;
	private boolean includeContentsAsDom;

	
	/**
	 * Gets the DQL that will be executed
	 */
	public String getDql() {
		return dql;
	}
	
	/**
	 * Sets the DQL to be executed. <b>Required field</b>, exception will be thrown if not provided.
	 * @param dql
	 */
	public void setDql(String dql) {
		this.dql = dql;
	}

	/**
	 * Specifies whether content of the objects being returned should be included in the output XML
	 */
	public boolean isIncludeContentsAsDom() {
		return includeContentsAsDom;
	}

	/**
	 * Specifies whether content of the objects being returned should be included in the output XML
	 */
	public void setIncludeContentsAsDom(boolean includeContentsAsDom) {
		this.includeContentsAsDom = includeContentsAsDom;
	}
}
