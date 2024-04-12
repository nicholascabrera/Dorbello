package com.dorbello.controllers;

import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dorbello.database_services.DatabaseOperations;

@RestController
public class DataController {

    @PostMapping("/info")
    public EntityModel<DatabaseOperations> initialize(@RequestBody String id){
        DatabaseOperations operations = new DatabaseOperations(id);
        operations.initializeID();
        return EntityModel.of(operations, linkTo(methodOn(DataController.class).receive(id)).withSelfRel());
    }

    @PutMapping("/info/{id}")
    public EntityModel<DatabaseOperations> send(@RequestBody String location, @RequestBody String assignedPickupTime, @PathVariable String id){
        DatabaseOperations operations = new DatabaseOperations(id, location, assignedPickupTime);
        // true if it works, false if it didnt.
        operations.sendServerToClient();
        return EntityModel.of(operations, linkTo(methodOn(DataController.class).receive(id)).withSelfRel());
    }

    @GetMapping("/info/{id}")
    public EntityModel<DatabaseOperations> receive(@PathVariable String id){
        DatabaseOperations operations = new DatabaseOperations(id);
        operations.receiveClientToServer();
        return EntityModel.of(operations, linkTo(methodOn(DataController.class).receive(id)).withSelfRel());
    }
}