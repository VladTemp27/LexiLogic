package org.amalgam.lexilogicserver.model.DAL;

import org.amalgam.lexilogicserver.model.DatabaseUtil;
import org.amalgam.lexilogicserver.model.utilities.corbautils.LobbyImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LobbyDAL {
    public void insertNewLobby(int lobbyId, String createdBy, String winner) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO lobby (createdBy, winner) VALUES (?, ?)");
            stmt.setString(1, createdBy);
            stmt.setString(2, winner);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("INSERT NEW LOBBY SUCCESS");
            }
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public LobbyImpl getLobbyByID(int lobbyId) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM lobby WHERE lobbyID = ?");
            stmt.setInt(1, lobbyId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new LobbyImpl(rs.getInt("lobbyID"), rs.getString("createdBy"), rs.getString("winner"));
                } else {
                    return null;
                }
            }
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public List<LobbyImpl > getLobbyByUserID (int playerID){
        List<LobbyImpl> lobbies = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseUtil.getConnection();

            String query = "SELECT * FROM lobby WHERE playerID = ?";
            statement = connection.prepareStatement(query);

            statement.setInt(1,playerID);
            resultSet = statement.executeQuery();

            while (resultSet.next()){
                LobbyImpl lobby = new LobbyImpl();
                lobby.lobbyID(resultSet.getInt("lobbyID"));
                lobby.createdBy(resultSet.getString("createdBy"));
                lobby.winner(resultSet.getString("winner"));
                lobbies.add(lobby);

            }

        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return lobbies;
    }
}
