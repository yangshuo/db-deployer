package org.ysh.deployer.api;

public class DBDeployException extends Exception{

	private static final long serialVersionUID = -2684705339047625427L;

	public DBDeployException() {
		super();
	}

	public DBDeployException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DBDeployException(String message, Throwable cause) {
		super(message, cause);
	}

	public DBDeployException(String message) {
		super(message);
	}

	public DBDeployException(Throwable cause) {
		super(cause);
	}

	
	
}
