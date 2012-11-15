package com.emc.community.xcelerators.wfactbofmodules;

/**
 * Provides information about implementation version stored in library's manifest
 * @author Michal.Malczewski
 *
 */
class VersionInfo {

	private final Object module;
	private String version;
	
	/**
	 * Creates new instance
	 * @param module
	 */
	public VersionInfo(Object module) {
		this.module = module;
	}
	
	/**
	 * Gets version from the manifest
	 * @return
	 */
	public String getVersion() {
		// lazy init
		if (version == null) {
			version = module.getClass().getPackage().getImplementationVersion();
		}
		return version;
	}
	
	
	
}
