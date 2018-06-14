package org.ysh.dbdeployer.impl.db.changelog;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ysh.deployer.api.DBDeployException;

import com.google.common.collect.Lists;

public class ChangeLogDao {
	private static final Logger logger = LoggerFactory.getLogger(ChangeLogDao.class);

	private Connection connection;

	public ChangeLogDao(Connection connection) {
		super();
		this.connection = connection;
	}

	public void save(List<ChangeLogPo> changeLogs) throws DBDeployException {
		if (CollectionUtils.isEmpty(changeLogs)) {
			return;
		}

		final String sql = "INSERT INTO changelog(script_file) VALUES(?)";

		try (final PreparedStatement statement = connection.prepareStatement(sql)) {
			for (ChangeLogPo changeLog : changeLogs) {
				statement.setString(1, changeLog.getScriptFile());
				statement.executeUpdate();
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new DBDeployException(e);
		}
	}

	public List<ChangeLogPo> queryAll() throws DBDeployException {
		final List<ChangeLogPo> changeLogs = Lists.newArrayList();

		final String sql = "SELECT * FROM changelog";
		try (final Statement statement = connection.createStatement();
				final ResultSet rs = statement.executeQuery(sql)) {
			while (rs.next()) {
				changeLogs.add(buildChangeLog(rs));
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new DBDeployException(e);
		}

		return changeLogs;
	}

	private ChangeLogPo buildChangeLog(ResultSet rs) throws SQLException {
		final String sqlFile = rs.getString(1);
		return new ChangeLogPo(sqlFile);
	}

}
