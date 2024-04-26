package org.amalgam.lexilogicserver.Server;
import org.amalgam.lexilogicserver.DAL.DALPlayer.PlayerDALPOA;
import org.amalgam.lexilogicserver.DAL.DALPlayer.PlayerDALPackage.Player;
import org.amalgam.lexilogicserver.DAL.SQLExceptions.SQLCreateError;
import org.amalgam.lexilogicserver.DAL.SQLExceptions.SQLDeleteError;
import org.amalgam.lexilogicserver.DAL.SQLExceptions.SQLRetrieveError;
import org.amalgam.lexilogicserver.DAL.SQLExceptions.SQLUpdateError;

import java.sql.*;

public class Servant extends PlayerDALPOA {
	private static final String JDBC_URL = "jdbc:mysql://localhost:3306/lexi?user=root&password";
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
	@Override
	public void insertNewPlayer(String username, String password, String lastLogin) throws SQLCreateError {
		try(Connection conn = getConnection()) {
			PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO player (name, password, lastLogin) VALUE (?,?,?)");
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			preparedStatement.setString(3, lastLogin);
			preparedStatement.execute();
			System.out.println("true");
		} catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Player getPlayerByID(int playerID) throws SQLRetrieveError {
		return null;
	}

	@Override
	public void updatePassword(int playerID, String newPassword) throws SQLUpdateError {

	}

	@Override
	public void updateUsername(int playerID, String newUsername) throws SQLUpdateError {

	}

	@Override
	public void deletePlayer(int playerID) throws SQLDeleteError {

	}
}
