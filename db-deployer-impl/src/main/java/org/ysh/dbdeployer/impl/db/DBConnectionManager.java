package org.ysh.dbdeployer.impl.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.ysh.dbdeployer.api.DBDeployException;

public class DBConnectionManager {
	private String jdbcUrl;
	private String username;
	private String password;
	private String jdbcDriverName;

	public DBConnectionManager(String jdbcDriverName, String jdbcUrl, String username, String password)
			throws ClassNotFoundException {
		super();
		this.jdbcUrl = jdbcUrl;
		this.username = username;
		this.password = password;
		this.jdbcDriverName = jdbcDriverName;
		this.init();
	}

	private void init() throws ClassNotFoundException {
		Class.forName(this.jdbcDriverName);
	}

	public Connection getConnection() throws DBDeployException {
		try {
			return DriverManager.getConnection(this.jdbcUrl, this.username, this.password);
		} catch (SQLException e) {
			throw new DBDeployException(e);
		}
	}
}
