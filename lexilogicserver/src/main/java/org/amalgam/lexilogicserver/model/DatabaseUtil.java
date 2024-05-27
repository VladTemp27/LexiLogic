package org.amalgam.lexilogicserver.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
	private static final String JDBC_URL = "jdbc:mysql://localhost:3306/lexi?user=root&password=";
	private static Connection connection;
	public static Connection getConnection() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		if (connection == null || connection.isClosed()) {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

			// Create a new connection if it doesn't exist or is closed
			connection = DriverManager.getConnection(JDBC_URL);
			System.out.println("\nnew Connection to database:" + connection);
		}
		return connection;
	}

	public static void closeConnection() throws SQLException {
		if (connection != null && !connection.isClosed()) {
			connection.close();
		}
	}
}
