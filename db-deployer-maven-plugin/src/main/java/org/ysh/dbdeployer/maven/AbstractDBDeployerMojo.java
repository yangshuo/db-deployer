package org.ysh.dbdeployer.maven;

import java.io.File;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;
import org.ysh.dbdeployer.api.DBDeployException;
import org.ysh.dbdeployer.api.IDBDeployer;

import org.ysh.dbdeployer.impl.Configuration;
import org.ysh.dbdeployer.impl.DBDeployerImpl;

public abstract class AbstractDBDeployerMojo extends AbstractMojo {

	@Parameter(property = "jdbc.driverClass")
	private String driverClass;
	@Parameter(property = "jdbc.url")
	private String url;
	@Parameter(property = "jdbc.username")
	private String username;
	@Parameter(property = "jdbc.password")
	private String password;
	@Parameter(property = "sql.scriptDir")
	private File sqlScriptDir;
	@Parameter(property = "sql.statementDelimiter")
	private char sqlStatementDelimiter;

	public void setDriverClass(final String driverClass) {
		this.driverClass = driverClass;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public void setSqlScriptDir(final File sqlScriptDir) {
		this.sqlScriptDir = sqlScriptDir;
	}

	public void setSqlStatementDelimiter(final char sqlStatementDelimiter) {
		this.sqlStatementDelimiter = sqlStatementDelimiter;
	}

	protected IDBDeployer createDBDeployer() throws DBDeployException {
		final Configuration configuration = new Configuration();
		configuration.setJdbcDriver(driverClass);
		configuration.setUrl(url);
		configuration.setUsername(username);
		configuration.setPassword(password);
		configuration.setSqlScriptDir(sqlScriptDir);
		configuration.setSqlStatementDelimiter(sqlStatementDelimiter);
		return new DBDeployerImpl(configuration);
	}

	protected void logConfiguration() {
		getLog().debug(String.format("jdbcURL:%s", url));
		getLog().debug(String.format("jdbcDriver:%s", driverClass));
		getLog().debug(String.format("jdbcUserName:%s", username));
		getLog().debug(String.format("jdbcPassword:%s", password));
		getLog().debug(String.format("sqlScriptDir:%s", sqlScriptDir));
		getLog().debug(String.format("sqlStatementDelimiter:%c", sqlStatementDelimiter));
	}

	public void execute() throws MojoExecutionException, MojoFailureException {
		logConfiguration();
		try {
			doExecute();
		} catch (DBDeployException e) {
			throw new MojoFailureException(e.getMessage(), e);
		}
	}

	protected abstract void doExecute() throws DBDeployException;
}