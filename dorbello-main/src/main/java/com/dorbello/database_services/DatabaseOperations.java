package com.dorbello.database_services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseOperations {

    /**
     * Creates a new ID/Parent and assigns "-" to location and assigned pick up time.
     * @param ID
     */
    public void initializeID(String ID){
        try (Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/times_db?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                        "userv", "dfCa#uFcF8W*o&jG")){
            
            //make sure there is no currently existing ID
            String query = "SELECT * FROM times_db.server_to_client WHERE ID = ?";
            PreparedStatement parameterizedQuery = conn.prepareStatement(query);
            parameterizedQuery.setString(1, ID);

            ResultSet rs = parameterizedQuery.executeQuery();
            
            //if the ID does not exist
            if(!rs.next()){
                query = "INSERT INTO times_db.server_to_client VALUES (?, \"-\", \"-\")";
                parameterizedQuery = conn.prepareStatement(query);
                parameterizedQuery.setString(1, ID);

                @SuppressWarnings("unused")
                int recordsAffected = parameterizedQuery.executeUpdate();
            } else {
                //TODO: issue new ID and reinitialize
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends the location and assigned pick up time to the database at the correct ID.
     * @param ID
     * @param location
     * @param assignedPickupTime
     * @return
     */
    public boolean sendServerToClient(String ID, String location, String assignedPickupTime) {
        try (Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/times_db?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                        "userv", "dfCa#uFcF8W*o&jG")){
            
            String query = "UPDATE times_db.server_to_client SET LOCAT = ?, APT = ? WHERE ID = ?";
            
            //always use parameterized queries to mitigate the risk of SQL Injection.
            PreparedStatement parameterizedQuery = conn.prepareStatement(query);
            parameterizedQuery.setString(1, location);
            parameterizedQuery.setString(2, assignedPickupTime);
            parameterizedQuery.setString(3, ID);

            //execute the update and record the number of rows affected.
            int recordsAffected = parameterizedQuery.executeUpdate();

            //if 1 or more records was affected, we know the update was successful.
            return recordsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Receives the ETA from a certain ID.
     * @param ID
     * @return boolean
     */
    public int receiveClientToServer(String ID) {
        int ETA = -1;

        try (Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/times_db?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                        "userv", "dfCa#uFcF8W*o&jG")){
            
            String query = "SELECT * FROM times_db.client_to_server WHERE ID = ?";
            
            //always use parameterized queries to mitigate the risk of SQL Injection.
            PreparedStatement parameterizedQuery = conn.prepareStatement(query);
            parameterizedQuery.setString(1, ID);

            //execute the update and record the number of rows affected.
            ResultSet rs = parameterizedQuery.executeQuery();
            
            if (rs.next()){
                ETA = rs.getInt("ETA");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ETA;
    }
}