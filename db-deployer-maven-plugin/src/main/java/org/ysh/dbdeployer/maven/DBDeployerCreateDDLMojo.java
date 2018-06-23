package org.ysh.dbdeployer.maven;

import org.ysh.dbdeployer.api.DBDeployException;

import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = "DDL")
public class DBDeployerCreateDDLMojo extends AbstractDBDeployerMojo {
	public void doExecute() throws DBDeployException {
		createDBDeployer().createNewDDL();
	}
}