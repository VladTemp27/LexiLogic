package org.amalgam.lexilogicserver.model.DAL;

import com.mysql.cj.PreparedQuery;
import org.amalgam.lexilogicserver.model.DatabaseUtil;
import org.amalgam.lexilogicserver.model.utilities.referenceobjects.Player;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.LinkedList;

public class PlayerDAL {

    public static Player getPlayerByUsername(String username){
        try(Connection conn = DatabaseUtil.getConnection()){
            PreparedStatement stmnt = conn.prepareStatement("SELECT * FROM player WHERE name=?");
            stmnt.setString(1, username);
            ResultSet rs = stmnt.executeQuery();
            if(rs.next()){
                int fetchedPlayerID = rs.getInt("playerID");
                String fetchedUsername = rs.getString("name");
                String fetchedPassword = rs.getString("password");
                String fetchedLastLogin = rs.getString("lastLogin");
                return new Player(fetchedPlayerID, fetchedUsername, fetchedPassword, fetchedLastLogin);
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public static LinkedList<Player> getAllPlayers() {
        LinkedList<Player> listOfPlayers = new LinkedList<>();
        try (Connection conn = DatabaseUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM player");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int playerID = rs.getInt("playerID");
                    String username = rs.getString("name");
                    String password = rs.getString("password");
                    String lastLogin = rs.getString("lastLogin");
                    Player player = new Player(playerID, username, password, lastLogin);
                    listOfPlayers.add(player);
                }
            }
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return listOfPlayers;
    }


    public static void insertNewPlayer(String username, String password) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO player (name, password) VALUES (?, ?)");
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();
            System.out.println("CREATE NEW PLAYER SUCCESS");
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static Player getPlayerByID(int playerID) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM player WHERE playerID = ?");
            stmt.setInt(1, playerID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int fetchedPlayerID = rs.getInt("playerID");
                    String fetchedUsername = rs.getString("name");
                    String fetchedPassword = rs.getString("password");
                    String fetchedLastLogin = rs.getString("lastLogin");
                    return new Player(fetchedPlayerID, fetchedUsername, fetchedPassword, fetchedLastLogin);
                } else {
                    return null;
                }
            }
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updatePassword(int playerID, String newPassword) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("UPDATE player SET password = ? WHERE playerID = ?");
            stmt.setString(1, newPassword);
            stmt.setInt(2, playerID);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("UPDATE PASSWORD SUCCESS");
            }
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateUsername(int playerID, String newUsername) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("UPDATE player SET name = ? WHERE playerID = ?");
            stmt.setString(1, newUsername);
            stmt.setInt(2, playerID);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("UPDATE USERNAME SUCCESS");
            }
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Deprecated
    public void deletePlayer(int playerID) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM player WHERE playerID = ?");
            stmt.setInt(1, playerID);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("DELETE PLAYER SUCCESS");
            }
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deletePlayer(String username){
        try(Connection conn = DatabaseUtil.getConnection()){
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM player WHERE name = ?");
            stmt.setString(1, username);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("DELETE PLAYER SUCCESS");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

        public static void markAccountForDeletion(String playerName) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("UPDATE player SET forDeletion = 'true' WHERE name = ?");
            stmt.setString(1, playerName);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Account marked for deletion successfully.");
            } else {
                throw new RuntimeException("Failed to mark account for deletion.");
            }
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static LinkedList<Player> retrieveForDeletion(){
        LinkedList<Player> listOfPlayers = new LinkedList<>();
        try(Connection conn = DatabaseUtil.getConnection()){
            PreparedStatement stmt = conn.prepareStatement("SELECT playerID, name FROM player WHERE forDeletion = 1");
            try(ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    int playerID = rs.getInt("playerID");
                    String name = rs.getString("name");
                    Player cPlayer = new Player(playerID, name, null,null);
                    listOfPlayers.add(cPlayer);
                }
                return listOfPlayers;
            }
        }catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


}
