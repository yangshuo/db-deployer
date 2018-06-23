package org.ysh.dbdeployer.impl.file;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.lang3.math.NumberUtils;
import org.ysh.dbdeployer.impl.db.changelog.ChangeLogDao;
import org.ysh.dbdeployer.impl.db.changelog.ChangeLogPo;
import org.ysh.dbdeployer.api.DBDeployException;

public class SqlScriptFileManager {
	private ChangeLogDao changeLogDao;
	private File sqlScriptDir;

	public SqlScriptFileManager(Connection connection, File sqlScriptDir) {
		super();
		this.changeLogDao = new ChangeLogDao(connection);
		this.sqlScriptDir = sqlScriptDir;
	}

	public List<File> findSqlScriptFiles() throws DBDeployException {
		final Set<String> executedSqlFiles = changeLogDao.queryAll().stream().map(ChangeLogPo::getScriptFile)
				.collect(Collectors.toSet());

		return FileUtils
				.listFiles(sqlScriptDir, new RegexFileFilter(SqlScriptFileUtils.createScriptFilePattern()),
						TrueFileFilter.TRUE)
				.stream().filter(file -> executedSqlFiles.contains(file.getName()) == false)
				.sorted((left, right) -> getScriptFileTimeStamp(left).compareTo(getScriptFileTimeStamp(right)))
				.collect(Collectors.toList());
	}

	private Long getScriptFileTimeStamp(File scriptFile) {
		final Matcher matcher = Pattern.compile(SqlScriptFileUtils.createScriptFilePattern())
				.matcher(scriptFile.getName());
		matcher.matches();
		return NumberUtils.toLong(matcher.group(2));
	}

	public static void main(String[] args) throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", "root", "root");

		File sqlScriptDir = new File("/Users/yangshuo/sql");

		SqlScriptFileManager manager = new SqlScriptFileManager(connection, sqlScriptDir);
		manager.findSqlScriptFiles().forEach(System.out::println);
	}

}
