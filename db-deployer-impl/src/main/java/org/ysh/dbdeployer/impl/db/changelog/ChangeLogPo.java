package org.ysh.dbdeployer.impl.db.changelog;

public class ChangeLogPo {
	private String scriptFile;

	public ChangeLogPo() {
		super();
	}

	public ChangeLogPo(String scriptFile) {
		super();
		this.scriptFile = scriptFile;
	}

	public String getScriptFile() {
		return scriptFile;
	}

	public void setScriptFile(String scriptFile) {
		this.scriptFile = scriptFile;
	}

	@Override
	public String toString() {
		return "ChangeLogPo [scriptFile=" + scriptFile + "]";
	}

}
