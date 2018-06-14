package org.ysh.dbdeployer.impl;

import java.io.File;

/**
 * 
 * @author yangshuo
 *
 */
public class Configuration {
	/** The JDBC URL */
	private String url;

	/** The user name used to connect to the db */
	private String username;

	/** The password used to connect to the db */
	private String password;

	/** Database scripts directory */
	private File sqlScriptDir;

	/** SQL statement delimiter */
	private char sqlStatementDelimiter;

	private String jdbcDriver;

	public Configuration() {
		super();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String user) {
		this.username = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public File getSqlScriptDir() {
		return sqlScriptDir;
	}

	public void setSqlScriptDir(File sqlScriptDir) {
		this.sqlScriptDir = sqlScriptDir;
	}

	public char getSqlStatementDelimiter() {
		return sqlStatementDelimiter;
	}

	public void setSqlStatementDelimiter(char sqlStatementDelimiter) {
		this.sqlStatementDelimiter = sqlStatementDelimiter;
	}

	public String getJdbcDriver() {
		return jdbcDriver;
	}

	public void setJdbcDriver(String jdbcDriver) {
		this.jdbcDriver = jdbcDriver;
	}

}
