package de.domisum.lib.codex.mysql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import de.domisum.lib.auxilium.util.java.annotations.APIUsage;

import java.sql.Connection;
import java.sql.SQLException;

@APIUsage
public class MySQLConPoolWrapper
{

	private HikariDataSource connectionPool;


	// INIT
	@APIUsage public MySQLConPoolWrapper(String serverAddress, int serverPort, String username, String password)
	{
		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setDataSourceClassName("com.mysql.cj.jdbc.MysqlDataSource");

		hikariConfig.addDataSourceProperty("serverName", serverAddress);
		hikariConfig.addDataSourceProperty("port", serverPort);
		hikariConfig.addDataSourceProperty("user", username);
		hikariConfig.addDataSourceProperty("password", password);

		hikariConfig.setMaximumPoolSize(3);


		this.connectionPool = new HikariDataSource(hikariConfig);
	}

	@APIUsage public void close()
	{
		this.connectionPool.close();
	}


	// GETTERS
	@APIUsage public Connection getConnection()
	{
		try
		{
			return this.connectionPool.getConnection();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}

		throw new IllegalStateException("Hikari didn't return a connection");
	}

}