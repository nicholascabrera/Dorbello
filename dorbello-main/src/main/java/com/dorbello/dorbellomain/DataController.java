package com.dorbello.dorbellomain;

import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.web.bind.annotation.*;

import com.dorbello.exceptions.*;

@RestController
public class DataController {

    @PostMapping("/info")
    public EntityModel<DatabaseOperations> initialize(@RequestBody IDInfo idInfo){
        DatabaseOperations operations = new DatabaseOperations(idInfo.getId());
        boolean successful = operations.initializeID();
        
        if (successful){
            return EntityModel.of(operations, linkTo(methodOn(DataController.class).initialize(idInfo)).withSelfRel());   
        }

        throw new InitializationUnsuccessfulException(idInfo.getId());
    }

    @PostMapping("/info/{id}")
    public EntityModel<DatabaseOperations> initializeAndSend(@RequestBody UpdateInfo info, @PathVariable String id){
        DatabaseOperations operations = new DatabaseOperations(id, info.getLocation(), info.getAssignedPickupTime());
        // true if it works, false if it didnt.
        boolean successful = operations.postServerToClient();

        if (successful){
            return EntityModel.of(operations, linkTo(methodOn(DataController.class).send(info, id)).withSelfRel());
        }

        throw new PostUnsuccessfulException(id);
    }

    @PutMapping("/info/{id}")
    public EntityModel<DatabaseOperations> send(@RequestBody UpdateInfo info, @PathVariable String id){
        DatabaseOperations operations = new DatabaseOperations(id, info.getLocation(), info.getAssignedPickupTime());
        // true if it works, false if it didnt.
        boolean successful = operations.putServerToClient();

        if (successful){
            return EntityModel.of(operations, linkTo(methodOn(DataController.class).send(info, id)).withSelfRel());
        }

        throw new ParentNotFoundException(id);
    }

    @GetMapping("/info/{id}")
    public EntityModel<DatabaseOperations> receive(@PathVariable("id") String id){
        DatabaseOperations operations = new DatabaseOperations(id);

        boolean successful = operations.receiveClientToServer();

        // Ensure that the DatabaseOperations constructor and any invoked methods handle all exceptions appropriately.
        if (successful) {
            return EntityModel.of(operations, linkTo(methodOn(DataController.class).receive(id)).withSelfRel());
        }
        
        throw new ParentNotFoundException(id);
    }

    @GetMapping("/test_info/{id}")
    public String test_receive(@PathVariable("id") String id){
        return "Testing: " + id;
    }
}

class IDInfo {
    private String id;

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