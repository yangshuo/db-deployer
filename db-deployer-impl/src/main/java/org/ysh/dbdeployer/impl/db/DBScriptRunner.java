package org.ysh.dbdeployer.impl.db;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ysh.dbdeployer.impl.db.changelog.ChangeLogDao;
import org.ysh.dbdeployer.impl.db.changelog.ChangeLogPo;
import org.ysh.dbdeployer.impl.file.SqlScriptFileReader;
import org.ysh.dbdeployer.api.DBDeployException;

import com.google.common.collect.Lists;

public class DBScriptRunner {
	private static final Logger logger = LoggerFactory.getLogger(DBScriptRunner.class);

	private File sqlScriptFile;
	private char sqlStatementDelimiterChar;
	private Connection connection;
	private ChangeLogDao changeLogDao;

	public DBScriptRunner(File sqlScriptFile, char sqlStatementDelimiterChar, Connection connection) {
		super();
		this.sqlScriptFile = sqlScriptFile;
		this.sqlStatementDelimiterChar = sqlStatementDelimiterChar;
		this.connection = connection;
		this.changeLogDao = new ChangeLogDao(connection);
	}

	public void run() throws DBDeployException {

		try {
			logger.info("Start to run script:{}", sqlScriptFile.getName());

			final List<String> sqlStatements = new SqlScriptFileReader(sqlScriptFile, sqlStatementDelimiterChar).read();
			logger.info("Total statements:{}....", sqlStatements.size());

			int count = 1;

			connection.setAutoCommit(false);
			for (String sqlStatement : sqlStatements) {
				logger.info("apply statement {}", count++);
				executeStatement(sqlStatement);
			}
			changeLogDao.save(Lists.newArrayList(new ChangeLogPo(sqlScriptFile.getName())));
			connection.commit();

			logger.info("Success to run script:{}", sqlScriptFile.getName());

		} catch (Exception e) {
			logger.error("Fail to run script {}", sqlScriptFile.getName());
			logger.error(e.getMessage(), e);
			throw new DBDeployException(e);
		}
	}

	private void executeStatement(String sqlStatement) throws SQLException {
		try {
			final Statement statement = connection.createStatement();
			statement.execute(sqlStatement);
			statement.close();
		} catch (SQLException e) {
			logger.error("Fail to execute statement:{}", sqlStatement);
			throw e;
		}
	}
}
