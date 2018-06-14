package org.ysh.dbdeployer.impl.file;

import org.ysh.dbdeployer.impl.consts.SqlScriptTypeConsts;

public class SqlScriptFileUtils {
	public static final String createScriptFilePattern() {
		return String.format("^(%s|%s)_([0-9]+)\\.sql$", SqlScriptTypeConsts.DDL, SqlScriptTypeConsts.DML);
	}

	public static final String createNewDMLFileName() {
		return createNewScriptFileName(SqlScriptTypeConsts.DML);
	}

	public static final String createNewDDLFileName() {
		return createNewScriptFileName(SqlScriptTypeConsts.DDL);
	}

	private static final String createNewScriptFileName(String type) {
		return String.format("%s_%d.sql", type, System.currentTimeMillis());
	}

	public static void main(String[] args) {
		System.out.println(createScriptFilePattern());
		System.out.println(createNewDDLFileName());
	}
}
