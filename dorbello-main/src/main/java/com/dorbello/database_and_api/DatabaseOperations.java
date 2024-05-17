package com.dorbello.database_and_api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseOperations {
    private String ID;
    private String location;
    private String assignedPickupTime;
    private int ETA;

    public DatabaseOperations(){}

    public DatabaseOperations(String ID){
        this.ID = ID;
        this.location = "";
        this.assignedPickupTime = "";
        this.ETA = 0;
    }

    public DatabaseOperations(String ID, String location, String assignedPickupTime){
        this.ID = ID;
        this.location = location;
        this.assignedPickupTime = assignedPickupTime;
        this.ETA = 0;
    }

    public String getID() {
        return ID;
    }

    public String getLocation() {
        return location;
    }

    public String getAssignedPickupTime() {
        return assignedPickupTime;
    }

    public int getETA() {
        return ETA;
    }

    /**
     * Creates a new ID/Parent and assigns "-" to location and assigned pick up time.
     * @param ID
     */
    public synchronized boolean initializeID(){
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

                int recordsAffected = parameterizedQuery.executeUpdate();
                return recordsAffected > 0;
            } else {
                //TODO: issue new ID and reinitialize
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Sends the location and assigned pick up time to the database at the correct ID.
     * @param ID
     * @param location
     * @param assignedPickupTime
     * @return
     */
    public synchronized boolean putServerToClient() {
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
     * Sends the location and assigned pick up time to the database at the correct ID.
     * @param ID
     * @param location
     * @param assignedPickupTime
     * @return
     */
    public synchronized boolean postServerToClient() {
        try (Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/times_db?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                        "userv", "dfCa#uFcF8W*o&jG")){
            
            String query = "INSERT INTO times_db.server_to_client VALUES (?, ?, ?)";
            
            //always use parameterized queries to mitigate the risk of SQL Injection.
            PreparedStatement parameterizedQuery = conn.prepareStatement(query);
            parameterizedQuery.setString(1, ID);
            parameterizedQuery.setString(2, location);
            parameterizedQuery.setString(3, assignedPickupTime);

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
     * @return ETA
     */
    public synchronized boolean receiveClientToServer() {
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
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Receives the ETA from a certain ID.
     * @param ID
     * @return ETA
     */
    public synchronized boolean testDatabaseFunctionality() {
        try (Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/times_db?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                        "userv", "dfCa#uFcF8W*o&jG")){
            
            String query = "SELECT * FROM times_db.client_to_server WHERE ID = \"test\"";
            
            //always use parameterized queries to mitigate the risk of SQL Injection.
            PreparedStatement parameterizedQuery = conn.prepareStatement(query);

            //execute the update and record the number of rows affected.
            ResultSet rs = parameterizedQuery.executeQuery();
            
            if (rs.next()){
                ETA = rs.getInt("ETA");
                return ETA == 10;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}