package org.ysh.dbdeployer.maven;

import org.ysh.dbdeployer.api.DBDeployException;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = "update")
public class DBDeployerUpdateMojo extends AbstractDBDeployerMojo {

	@Override
	public void doExecute() throws DBDeployException {
		createDBDeployer().update();
	}
}