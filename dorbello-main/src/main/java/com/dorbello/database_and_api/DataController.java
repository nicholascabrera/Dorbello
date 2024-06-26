package com.dorbello.database_and_api;

import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.web.bind.annotation.*;

import com.dorbello.exceptions.*;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@CrossOrigin
@RestController
public class DataController {

    @PostMapping("/info")
    public EntityModel<DatabaseOperations> initialize(@RequestBody IDInfo idInfo){
        DatabaseOperations operations = new DatabaseOperations(idInfo.getId());
        boolean successful = operations.initializeID();
        
        if (successful){
            return EntityModel.of(
                operations, 
                linkTo(methodOn(DataController.class).initialize(idInfo)).withSelfRel(),
                linkTo(methodOn(DataController.class).initializeAndSend(new UpdateInfo(null, null), idInfo.getId())).withRel("initialize all fields"),
                linkTo(methodOn(DataController.class).send(new UpdateInfo(null, null), idInfo.getId())).withRel("update"),
                linkTo(methodOn(DataController.class).receive(idInfo.getId())).withRel("get")
            );
        }

        throw new InitializationUnsuccessfulException(idInfo.getId());
    }

    @PostMapping("/info/{id}")
    public EntityModel<DatabaseOperations> initializeAndSend(@RequestBody UpdateInfo info, @PathVariable String id){
        DatabaseOperations operations = new DatabaseOperations(id, info.getLocation(), info.getAssignedPickupTime());
        // true if it works, false if it didnt.
        boolean successful = operations.postServerToClient();

        if (successful){
            return EntityModel.of(
                operations, 
                linkTo(methodOn(DataController.class).initializeAndSend(info, id)).withSelfRel(),
                linkTo(methodOn(DataController.class).initialize(new IDInfo(id))).withRel("initialize"),
                linkTo(methodOn(DataController.class).send(info, id)).withRel("update"),
                linkTo(methodOn(DataController.class).receive(id)).withRel("get")
            );
        }

        throw new PostUnsuccessfulException(id);
    }

    @PutMapping("/info/{id}")
    public EntityModel<DatabaseOperations> send(@RequestBody UpdateInfo info, @PathVariable String id){
        DatabaseOperations operations = new DatabaseOperations(id, info.getLocation(), info.getAssignedPickupTime());
        // true if it works, false if it didnt.
        boolean successful = operations.putServerToClient();

        if (successful){
            return EntityModel.of(
                operations, 
                linkTo(methodOn(DataController.class).send(info, id)).withSelfRel(),
                linkTo(methodOn(DataController.class).initialize(new IDInfo(id))).withRel("initialize"),
                linkTo(methodOn(DataController.class).initializeAndSend(info, id)).withRel("initialize all fields"),
                linkTo(methodOn(DataController.class).receive(id)).withRel("get")
            );
        }

        throw new PutUnsuccessfulException(id);
    }

    @GetMapping("/info/{id}")
    public EntityModel<DatabaseOperations> receive(@PathVariable("id") String id){
        DatabaseOperations operations = new DatabaseOperations(id);

        boolean successful = operations.receiveClientToServer();

        // Ensure that the DatabaseOperations constructor and any invoked methods handle all exceptions appropriately.
        if (successful) {
            return EntityModel.of(
                operations, 
                linkTo(methodOn(DataController.class).receive(id)).withSelfRel(),
                linkTo(methodOn(DataController.class).initialize(new IDInfo(id))).withRel("initialize"),
                linkTo(methodOn(DataController.class).initializeAndSend(new UpdateInfo(null, null), id)).withRel("initialize all fields"),
                linkTo(methodOn(DataController.class).send(new UpdateInfo(null, null), id)).withRel("update")
            );
        }
        
        throw new ParentNotFoundException(id);
    }

    @GetMapping("/test_info")
    public String test_receive(){
        DatabaseOperations operations = new DatabaseOperations();
        boolean successful = operations.testDatabaseFunctionality();
        
        if (successful){
            return "API: Functional\nDatabase: Functional\n";
        }

        return "API: Functional\nDatabase: Nonfunctional\n";
    }
}

class IDInfo {
    private String id;

    @JsonCreator
    public IDInfo(@JsonProperty("id") String id){
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

class UpdateInfo {
    private String location;
    private String assignedPickupTime;

    @JsonCreator
    public UpdateInfo(@JsonProperty("location") String location, @JsonProperty("assignedPickupTime") String assignedPickupTime){
        this.location = location;
        this.assignedPickupTime = assignedPickupTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAssignedPickupTime() {
        return assignedPickupTime;
    }

    public void setAssignedPickupTime(String assignedPickupTime) {
        this.assignedPickupTime = assignedPickupTime;
    }
}