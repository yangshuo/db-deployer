package org.ysh.dbdeployer.maven;

import org.ysh.dbdeployer.api.DBDeployException;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = "DML")
public class DBDeployerCreateDMLMojo extends AbstractDBDeployerMojo {
	public void doExecute() throws DBDeployException {
		createDBDeployer().createNewDML();
	}
}