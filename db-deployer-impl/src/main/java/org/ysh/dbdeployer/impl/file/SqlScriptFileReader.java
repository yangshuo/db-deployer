package org.ysh.dbdeployer.impl.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ysh.dbdeployer.api.DBDeployException;

public class SqlScriptFileReader {
	private static final Logger logger = LoggerFactory.getLogger(SqlScriptFileReader.class);

	private File sqlScriptFile;
	private char sqlStatementDelimiterChar;

	public SqlScriptFileReader(File sqlScriptFile, char sqlStatementDelimiterChar) {
		super();
		this.sqlScriptFile = sqlScriptFile;
		this.sqlStatementDelimiterChar = sqlStatementDelimiterChar;
	}

	public List<String> read() throws DBDeployException {
		final List<String> sqlStatements = new ArrayList<>();

		try (BufferedReader reader = new BufferedReader(new FileReader(sqlScriptFile))) {
			final char[] cbuffer = new char[2048];
			StringBuilder sqlStatement = new StringBuilder(1024);

			while (true) {
				int nCharts = reader.read(cbuffer);
				if (nCharts == -1) {
					if (sqlStatement.length() > 0) {
						sqlStatements.add(sqlStatement.toString());
					}
					break;
				}

				for (int i = 0; i < nCharts; i++) {
					if (cbuffer[i] == this.sqlStatementDelimiterChar) {
						if (sqlStatement.length() > 0) {
							sqlStatements.add(sqlStatement.toString());
						}
						sqlStatement = new StringBuilder(1024);
					} else {
						sqlStatement.append(cbuffer[i]);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DBDeployException(e);
		}

		return sqlStatements.stream().filter(statement -> StringUtils.isNotBlank(statement))
				.collect(Collectors.toList());
	}
}
