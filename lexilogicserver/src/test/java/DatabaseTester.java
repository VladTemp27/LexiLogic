import org.amalgam.lexilogicserver.model.DatabaseUtil;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseTester {
	@Test
	public void testGetPlayerID(){
		int playerID = 1;
		try (Connection conn = DatabaseUtil.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM player WHERE playerID = ?");
			stmt.setInt(1, playerID);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()){
					int fetchedPlayerID = rs.getInt("playerID");
					String fetchedUsername = rs.getString("name");
					String fetchedPassword = rs.getString("password");
					String fetchedLastLogin = rs.getString("lastLogin");
					System.out.println("SUCCESS");
				} else {
					System.out.println("ERROR");
				}
			}

		} catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
}
