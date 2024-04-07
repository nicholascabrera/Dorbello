package com.dorbello.main_services;

import com.dorbello.database_services.DatabaseOperations;

public class Main {
    public static void main(String[] args){
        System.out.println("ETA: " + new runner().executeReceive());
    }

    static class runner {
        private final DatabaseOperations operations;
        
        public runner(){
            this.operations = new DatabaseOperations();
        }

        public void executeSend(){
            operations.initializeID("test");
            operations.sendServerToClient("test", "test location", "22:00");
        }

        public int executeReceive(){
            return operations.receiveClientToServer("test");
        }
    }
}