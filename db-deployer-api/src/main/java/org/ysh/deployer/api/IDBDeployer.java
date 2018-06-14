package org.ysh.deployer.api;

import java.io.File;

public interface IDBDeployer {
	/**
	 * update the database
	 * 
	 */
	void update() throws DBDeployException;

	/**
	 * Reset the database
	 * 
	 */
	void reset() throws DBDeployException;

	/**
	 * Create a new DDL script
	 * 
	 * @return
	 */
	File createNewDDL() throws DBDeployException;

	/**
	 * Create a new DML script
	 * 
	 * @return
	 */
	File createNewDML() throws DBDeployException;
}
