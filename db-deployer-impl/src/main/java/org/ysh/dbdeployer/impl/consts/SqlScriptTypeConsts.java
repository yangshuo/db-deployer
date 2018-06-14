package org.ysh.dbdeployer.impl.consts;

public class SqlScriptTypeConsts {
	public static final String DDL = "DDL";
	public static final String DML = "DML";
	
	private SqlScriptTypeConsts() {}
	
	
	public static final boolean isDDL(String sqlScriptType) {
		return sqlScriptType != null && DDL.equals(sqlScriptType);
	}
	
	public static final boolean isDML(String sqlScriptType) {
		return sqlScriptType != null && DML.equals(sqlScriptType);
	}
}
