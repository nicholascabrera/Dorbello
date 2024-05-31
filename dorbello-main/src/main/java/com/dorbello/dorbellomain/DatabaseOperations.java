package com.dorbello.dorbellomain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.dorbello.models.ETAPage;
import com.dorbello.models.Page;
import com.dorbello.models.RoutingPage;

public class DatabaseOperations {
    // private String ID;
    // private String location;
    // private String assignedPickupTime;
    // private int ETA;

    public DatabaseOperations(){}

    // public DatabaseOperations(String ID){
    //     this.ID = ID;
    //     this.location = "";
    //     this.assignedPickupTime = "";
    //     this.ETA = 0;
    // }

    // public DatabaseOperations(String ID, String location, String assignedPickupTime){
    //     this.ID = ID;
    //     this.location = location;
    //     this.assignedPickupTime = assignedPickupTime;
    //     this.ETA = 0;
    // }

    // public String getID() {
    //     return ID;
    // }

    // public String getLocation() {
    //     return location;s
    // }

    // public String getAssignedPickupTime() {
    //     return assignedPickupTime;
    // }

    // public int getETA() {
    //     return ETA;
    // }

    private String organization;
    private String group;
    private String child;
    private String parent;
    private String APT;         //assigned pick up time
    private String location;    //assigned pick up location
    private int ETA;            //parents Estimated Time to Arrival

    public DatabaseOperations(Page page){
        this.organization = page.getOrganization();
        this.group = page.getGroup();
        this.child = page.getChild();
        this.parent = page.getParent();
        this.APT = page.getAPT();
        this.location = page.getLocation();
        this.ETA = page.getETA();
    }

    public DatabaseOperations(ETAPage page){
        this.organization = page.getOrganization();
        this.group = page.getGroup();
        this.child = page.getChild();
        this.parent = page.getParent();
        this.ETA = page.getETA();
    }

    public DatabaseOperations(RoutingPage page){
        this.organization = page.getOrganization();
        this.group = page.getGroup();
        this.child = page.getChild();
        this.parent = page.getParent();
        this.APT = page.getAPT();
        this.location = page.getLocation();
    }

    public String getAPT() {
        return APT;
    }

    public String getChild() {
        return child;
    }

    public int getETA() {
        return ETA;
    }

    public String getGroup() {
        return group;
    }

    public String getLocation() {
        return location;
    }

    public String getOrganization() {
        return organization;
    }

    public String getParent() {
        return parent;
    }

    public void setAPT(String aPT) {
        APT = aPT;
    }

    public void setChild(String child) {
        this.child = child;
    }

    public void setETA(int eTA) {
        ETA = eTA;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public Page getPage() {
        return new Page(this.organization, this.group, this.child, this.parent, this.APT, this.location, this.ETA);
    }

    public boolean post(){
        try (Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/times_db?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                        "userv", "dfCa#uFcF8W*o&jG")){
            
            //check to see if the organization, group, child, then parent already exist in the database
            //if they do, grab their UIDs. if not, generate them.
            String OUID = "";
            String GRUID = "";
            String CUID = "";
            String PUID = "";

            //check the organization first
            String query = "SELECT * FROM times_db.t_org WHERE ORG_NAME = ?";
            PreparedStatement parameterizedQuery = conn.prepareStatement(query);
            parameterizedQuery.setString(1, this.organization);

            ResultSet rs = parameterizedQuery.executeQuery();
            
            //if no organization by that name exists, generate a OUID and add it to the tables.
            if(!rs.next()){
                //TODO: find the built in java hash function and use that to quickly generate the OUID.
                //TODO: once hashed, add the OUID and the organization name to the table t_org.
            }
            //if the organization does exist, then grab the OUID.
            else {
                OUID = rs.getString("OUID");
            }

            //create a group query.
            query = "SELECT * FROM times_db.t_group WHERE GROUP_NAME = ?";
            parameterizedQuery = conn.prepareStatement(query);
            parameterizedQuery.setString(1, this.group);

            rs = parameterizedQuery.executeQuery();

            //if no group by that name exists, generate a GRUID and add it to the tables.
            if(!rs.next()){
                //TODO: find the built in java hash function and use that to quickly generate the GRUID.
                //TODO: once hashed, add the GRUID and group name to the table t_group.
            }
            //if the organization does exist, then grab the GRUID.
            else {
                GRUID = rs.getString("GRUID");
            }

            //create a child query.
            query = "SELECT * FROM times_db.t_child WHERE CHILD_NAME = ?";
            parameterizedQuery = conn.prepareStatement(query);
            parameterizedQuery.setString(1, this.child);

            rs = parameterizedQuery.executeQuery();

            //if no child by that name exists, generate a CUID and add it to the tables.
            if(!rs.next()){
                //TODO: find the built in java hash function and use that to quickly generate the CUID.
                //TODO: once hashed, add the CUID and child name to the table t_child.
            }
            //if the child does exist, then grab the CUID.
            else {
                CUID = rs.getString("CUID");
            }

            //create a parent query.
            query = "SELECT * FROM times_db.t_parent WHERE PARENT_NAME = ?";
                parameterizedQuery = conn.prepareStatement(query);
            parameterizedQuery.setString(1, this.parent);

            rs = parameterizedQuery.executeQuery();

            //if no parent by that name exists, generate a PUID and add it to the tables.
            if(!rs.next()){
                //TODO: find the built in java hash function and use that to quickly generate the PUID.
                //TODO: once hashed, add the PUID and parent name to the table t_parent.
            }
            //if the parent does exist, then grab the PUID.
            else {
                PUID = rs.getString("PUID");
            }

            //insert the eta into t_eta
            query = "INSERT INTO times_db.t_eta VALUES (?, ?, ?)";
            parameterizedQuery = conn.prepareStatement(query);
            parameterizedQuery.setString(1, CUID);
            parameterizedQuery.setString(2, PUID);
            parameterizedQuery.setInt(3, ETA);

            int recordsAffected = parameterizedQuery.executeUpdate();   //checkpoint 1

            if (!(recordsAffected > 0)) return false; //if no records were affected, something happened. TODO: add a custom exception here instead of a return statement.

            //insert the apt and location into t_child_dynamic
            query = "INSERT INTO times_db.t_child_dynamic VALUES (?, ?, ?)";
            parameterizedQuery = conn.prepareStatement(query);
            parameterizedQuery.setString(1, CUID);
            parameterizedQuery.setString(2, this.location);
            parameterizedQuery.setString(3, this.APT);

            recordsAffected = parameterizedQuery.executeUpdate();       //checkpoint 2

            if (!(recordsAffected > 0)) return false; //if no records were affected, something happened. TODO: add a custom exception here instead of a return statement.

            //insert the UIDs into t_master
            query = "INSERT INTO times_db.t_master VALUES (?, ?, ?, ?)";
            parameterizedQuery = conn.prepareStatement(query);
            parameterizedQuery.setString(1, OUID);
            parameterizedQuery.setString(2, GRUID);
            parameterizedQuery.setString(3, CUID);
            parameterizedQuery.setString(4, PUID);

            recordsAffected = parameterizedQuery.executeUpdate();       //checkpoint 3

            if (!(recordsAffected > 0)) return false; //if no records were affected, something happened. TODO: add a custom exception here instead of a return statement.


            //if all checkpoints have been passed, then the POST has been successful.
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Given an existing organization, group, child and parent, update the corresponding eta.
     * @return
     */
    public boolean putETA() {
        try (Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/times_db?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                        "userv", "dfCa#uFcF8W*o&jG")){
            
            /*
             * because we assume that the PUT command is using existing parameters (org, group, child, parent)
             * we can generate the UIDs by hashing the names of each parameter. 
             * TODO: in the future, this is not collision resistant. Names can repeat, so we should use a username or something like that.
             * After hashing the names of each thing, we search the master table for that page to verify it exists.
             * After it is verified, we update the t_eta table.
             */
            String OUID = "";   //TODO: hash the org name
            String GRUID = "";  //TODO: hash the group name
            String CUID = "";   //TODO: hash the child name
            String PUID = "";   //TODO: hash the parent name
            
            //verify the page exists
            String query = "SELECT * FROM times_db.t_master WHERE OUID = ?, GRUID = ?, CUID = ?, PUID = ?";
            PreparedStatement parameterizedQuery = conn.prepareStatement(query);
            parameterizedQuery.setString(1, OUID);
            parameterizedQuery.setString(2, GRUID);
            parameterizedQuery.setString(3, CUID);
            parameterizedQuery.setString(4, PUID);

            ResultSet rs = parameterizedQuery.executeQuery();
            
            //if the page exists...
            if(rs.next()){
                //update the eta.
                query = "UPDATE times_db.t_eta SET ETA = ? WHERE CUID = ?, PUID = ?";
                parameterizedQuery = conn.prepareStatement(query);
                parameterizedQuery.setInt(1, this.ETA);
                parameterizedQuery.setString(2, CUID);
                parameterizedQuery.setString(3, PUID);

                int rowsAffected = parameterizedQuery.executeUpdate();

                //if more than one row is affected, the eta has been successfully updated.
                if(rowsAffected > 0){
                    /*
                     * Now that the eta is updated, we must give a meaningful output. 
                     * This requires returning the full page of information.
                     * Currently, we are only missing the APT and location.
                     * This can be found by querying the t_child_dynamic table.
                     */

                    query = "SELECT * FROM times_db.t_child_dynamic WHERE CUID = ?";
                    parameterizedQuery = conn.prepareStatement(query);
                    parameterizedQuery.setString(1, CUID);

                    rs = parameterizedQuery.executeQuery();

                    if(rs.next()){
                        this.APT = rs.getString("APT");
                        this.location = rs.getString("LOCAT");
                    } else {
                        return false; //if there is no result set, then no APT or LOCAT exist for this page. TODO: add a custom exception here instead of a return statement.
                    }
                } else {
                    return false; //if no rows are affected, the eta was not updated. TODO: add a custom exception here instead of a return statement.
                }
                
                return rowsAffected > 0;

            } else {
                return false; //if there is no result set, then the page does not exist. TODO: add a custom exception here instead of a return statement.
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Given an existing organization, group, child and parent, update the corresponding assigned pick up time and pick up location.
     * @return
     */
    public boolean putRouting() {
        try (Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/times_db?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                        "userv", "dfCa#uFcF8W*o&jG")){
            
            /*
             * because we assume that the PUT command is using existing parameters (org, group, child, parent)
             * we can generate the UIDs by hashing the names of each parameter. 
             * TODO: in the future, this is not collision resistant. Names can repeat, so we should use a username or something like that.
             * After hashing the names of each thing, we search the master table for that page to verify it exists.
             * After it is verified, we update the t_child_dynamic table.
             */
            String OUID = "";   //TODO: hash the org name
            String GRUID = "";  //TODO: hash the group name
            String CUID = "";   //TODO: hash the child name
            String PUID = "";   //TODO: hash the parent name
            
            //verify the page exists
            String query = "SELECT * FROM times_db.t_master WHERE OUID = ?, GRUID = ?, CUID = ?, PUID = ?";
            PreparedStatement parameterizedQuery = conn.prepareStatement(query);
            parameterizedQuery.setString(1, OUID);
            parameterizedQuery.setString(2, GRUID);
            parameterizedQuery.setString(3, CUID);
            parameterizedQuery.setString(4, PUID);

            ResultSet rs = parameterizedQuery.executeQuery();
            
            //if the page exists...
            if(rs.next()){
                //update the APT and location.
                query = "UPDATE times_db.t_child_dynamic SET APT = ?, LOCAT = ? WHERE CUID = ?";
                parameterizedQuery = conn.prepareStatement(query);
                parameterizedQuery.setString(1, this.APT);
                parameterizedQuery.setString(2, this.location);
                parameterizedQuery.setString(3, CUID);

                int rowsAffected = parameterizedQuery.executeUpdate();

                //if more than one row is affected, the APT and locations have been successfully updated.
                if(rowsAffected > 0){
                    /*
                     * Now that the APT and location are updated, we must give a meaningful output. 
                     * This requires returning the full page of information.
                     * Currently, we are only missing the ETA.
                     * This can be found by querying the t_eta table.
                     */

                    query = "SELECT * FROM times_db.eta WHERE CUID = ?, PUID = ?";
                    parameterizedQuery = conn.prepareStatement(query);
                    parameterizedQuery.setString(1, CUID);
                    parameterizedQuery.setString(2, PUID);

                    rs = parameterizedQuery.executeQuery();

                    if(rs.next()){
                        this.ETA = rs.getInt("ETA");
                    } else {
                        return false; //if there is no result set, then no ETA exists for this page. TODO: add a custom exception here instead of a return statement.
                    }
                } else {
                    return false; //if no rows are affected, the APT and location were not updated. TODO: add a custom exception here instead of a return statement.
                }
                
                return rowsAffected > 0;

            } else {
                return false; //if there is no result set, then the page does not exist. TODO: add a custom exception here instead of a return statement.
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }



    // /**
    //  * Creates a new ID/Parent and assigns "-" to location and assigned pick up time.
    //  * @param ID
    //  */
    // public synchronized boolean initializeID(){
    //     try (Connection conn = DriverManager.getConnection(
    //                     "jdbc:mysql://localhost:3306/times_db?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
    //                     "userv", "dfCa#uFcF8W*o&jG")){
            
    //         //make sure there is no currently existing ID
    //         String query = "SELECT * FROM times_db.server_to_client WHERE ID = ?";
    //         PreparedStatement parameterizedQuery = conn.prepareStatement(query);
    //         parameterizedQuery.setString(1, ID);

    //         ResultSet rs = parameterizedQuery.executeQuery();
            
    //         //if the ID does not exist
    //         if(!rs.next()){
    //             query = "INSERT INTO times_db.server_to_client VALUES (?, \"-\", \"-\")";
    //             parameterizedQuery = conn.prepareStatement(query);
    //             parameterizedQuery.setString(1, ID);

    //             int recordsAffected = parameterizedQuery.executeUpdate();
    //             return recordsAffected > 0;
    //         } else {
    //             //: issue new ID and reinitialize
    //         }

    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }
    //     return false;
    // }

    // /**
    //  * Sends the location and assigned pick up time to the database at the correct ID.
    //  * @param ID
    //  * @param location
    //  * @param assignedPickupTime
    //  * @return
    //  */
    // public synchronized boolean putServerToClient() {
    //     try (Connection conn = DriverManager.getConnection(
    //                     "jdbc:mysql://localhost:3306/times_db?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
    //                     "userv", "dfCa#uFcF8W*o&jG")){
            
    //         String query = "UPDATE times_db.server_to_client SET LOCAT = ?, APT = ? WHERE ID = ?";
            
    //         //always use parameterized queries to mitigate the risk of SQL Injection.
    //         PreparedStatement parameterizedQuery = conn.prepareStatement(query);
    //         parameterizedQuery.setString(1, location);
    //         parameterizedQuery.setString(2, assignedPickupTime);
    //         parameterizedQuery.setString(3, ID);

    //         //execute the update and record the number of rows affected.
    //         int recordsAffected = parameterizedQuery.executeUpdate();

    //         //if 1 or more records was affected, we know the update was successful.
    //         return recordsAffected > 0;
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }

    //     return false;
    // }

    // /**
    //  * Sends the location and assigned pick up time to the database at the correct ID.
    //  * @param ID
    //  * @param location
    //  * @param assignedPickupTime
    //  * @return
    //  */
    // public synchronized boolean postServerToClient() {
    //     try (Connection conn = DriverManager.getConnection(
    //                     "jdbc:mysql://localhost:3306/times_db?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
    //                     "userv", "dfCa#uFcF8W*o&jG")){
            
    //         String query = "INSERT INTO times_db.server_to_client VALUES (?, ?, ?)";
            
    //         //always use parameterized queries to mitigate the risk of SQL Injection.
    //         PreparedStatement parameterizedQuery = conn.prepareStatement(query);
    //         parameterizedQuery.setString(1, ID);
    //         parameterizedQuery.setString(2, location);
    //         parameterizedQuery.setString(3, assignedPickupTime);

    //         //execute the update and record the number of rows affected.
    //         int recordsAffected = parameterizedQuery.executeUpdate();

    //         //if 1 or more records was affected, we know the update was successful.
    //         return recordsAffected > 0;
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }

    //     return false;
    // }

    // /**
    //  * Receives the ETA from a certain ID.
    //  * @param ID
    //  * @return ETA
    //  */
    // public synchronized boolean receiveClientToServer() {
    //     try (Connection conn = DriverManager.getConnection(
    //                     "jdbc:mysql://localhost:3306/times_db?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
    //                     "userv", "dfCa#uFcF8W*o&jG")){
            
    //         String query = "SELECT * FROM times_db.client_to_server WHERE ID = ?";
            
    //         //always use parameterized queries to mitigate the risk of SQL Injection.
    //         PreparedStatement parameterizedQuery = conn.prepareStatement(query);
    //         parameterizedQuery.setString(1, ID);

    //         //execute the update and record the number of rows affected.
    //         ResultSet rs = parameterizedQuery.executeQuery();
            
    //         if (rs.next()){
    //             ETA = rs.getInt("ETA");
    //             return true;
    //         }
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }

    //     return false;
    // }

    // /**
    //  * Receives the ETA from a certain ID.
    //  * @param ID
    //  * @return ETA
    //  */
    // public synchronized boolean testDatabaseFunctionality() {
    //     try (Connection conn = DriverManager.getConnection(
    //                     "jdbc:mysql://localhost:3306/times_db?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
    //                     "userv", "dfCa#uFcF8W*o&jG")){
            
    //         String query = "SELECT * FROM times_db.client_to_server WHERE ID = \"test\"";
            
    //         //always use parameterized queries to mitigate the risk of SQL Injection.
    //         PreparedStatement parameterizedQuery = conn.prepareStatement(query);

    //         //execute the update and record the number of rows affected.
    //         ResultSet rs = parameterizedQuery.executeQuery();
            
    //         if (rs.next()){
    //             ETA = rs.getInt("ETA");
    //             return ETA == 10;
    //         }
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }

    //     return false;
    // }
}