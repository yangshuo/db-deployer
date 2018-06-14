package org.ysh.dbdeployer.impl;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ysh.dbdeployer.impl.db.DBConnectionManager;
import org.ysh.dbdeployer.impl.db.DBScriptRunner;
import org.ysh.dbdeployer.impl.file.SqlScriptFileManager;
import org.ysh.dbdeployer.impl.file.SqlScriptFileUtils;
import org.ysh.deployer.api.DBDeployException;
import org.ysh.deployer.api.IDBDeployer;

public class DBDeployerImpl implements IDBDeployer {
	private static final Logger logger = LoggerFactory.getLogger(DBDeployerImpl.class);

	private Configuration configuration;
	private DBConnectionManager dbConnectionManager;

	public DBDeployerImpl(Configuration configuration) throws DBDeployException {
		this.configuration = configuration;
		this.init();
	}

	private void init() throws DBDeployException {
		final String jdbcUrl = configuration.getUrl();
		final String username = configuration.getUsername();
		final String password = configuration.getPassword();
		final String jdbcDriver = configuration.getJdbcDriver();

		try {
			this.dbConnectionManager = new DBConnectionManager(jdbcDriver, jdbcUrl, username, password);
		} catch (ClassNotFoundException e) {
			throw new DBDeployException(e);
		}
	}

	@Override
	public void reset() throws DBDeployException {
		// 1. create the changelog
		try (Connection connection = dbConnectionManager.getConnection()) {
			logger.info("Start to create changelog....");
			File initSqlFile = new File(this.getClass().getResource("/changelog.sql").getFile());
			new DBScriptRunner(initSqlFile, configuration.getSqlStatementDelimiter(), connection).run();
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new DBDeployException(e);
		}

		// 2. run all scripts
		update();
	}

	@Override
	public void update() throws DBDeployException {
		try (final Connection connection = dbConnectionManager.getConnection()) {
			connection.setAutoCommit(false);

			final SqlScriptFileManager sqlScriptFileManager = new SqlScriptFileManager(connection,
					configuration.getSqlScriptDir());
			final List<File> sqlScriptFiles = sqlScriptFileManager.findSqlScriptFiles();

			if (CollectionUtils.isEmpty(sqlScriptFiles)) {
				logger.warn("There is no scripts to apply");
				return;
			}

			// execute each sql script file
			for (File sqlScriptFile : sqlScriptFiles) {
				DBScriptRunner runner = new DBScriptRunner(sqlScriptFile, configuration.getSqlStatementDelimiter(),
						connection);
				runner.run();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DBDeployException(e);
		}

	}

	@Override
	public File createNewDDL() throws DBDeployException {
		try {
			final String sqlScriptFileName = SqlScriptFileUtils.createNewDDLFileName();
			File sqlScriptFile = new File(configuration.getSqlScriptDir(), sqlScriptFileName);
			sqlScriptFile.createNewFile();

			logger.info("create new DDL {}", sqlScriptFileName);
			return sqlScriptFile;

		} catch (IOException ioe) {
			throw new DBDeployException(ioe);
		}
	}

	@Override
	public File createNewDML() throws DBDeployException {
		try {
			final String sqlScriptFileName = SqlScriptFileUtils.createNewDMLFileName();
			File sqlScriptFile = new File(configuration.getSqlScriptDir(), sqlScriptFileName);
			sqlScriptFile.createNewFile();

			logger.info("create new DML {}", sqlScriptFileName);
			return sqlScriptFile;
		} catch (IOException ioe) {
			throw new DBDeployException(ioe);
		}
	}

	public static void main(String[] args) throws DBDeployException {
		Configuration configuration = new Configuration();
		configuration.setJdbcDriver("com.mysql.jdbc.Driver");
		configuration.setUrl("jdbc:mysql://localhost:3306/demo");
		configuration.setUsername("root");
		configuration.setPassword("root");
		configuration.setSqlScriptDir(new File("/Users/yangshuo/sql"));
		configuration.setSqlStatementDelimiter('/');

		IDBDeployer dbDeployer = new DBDeployerImpl(configuration);
		dbDeployer.reset();
		// dbDeployer.createNewDDL();
		// dbDeployer.createNewDML();
	}

}
