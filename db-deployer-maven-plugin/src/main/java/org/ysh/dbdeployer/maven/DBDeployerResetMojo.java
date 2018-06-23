package org.ysh.dbdeployer.maven;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.ysh.dbdeployer.api.DBDeployException;

@Mojo(name = "reset")
public class DBDeployerResetMojo extends AbstractDBDeployerMojo {

	@Override
	public void doExecute() throws DBDeployException {
		createDBDeployer().reset();
	}

}