package com.dorbello.dorbellomain;

import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.web.bind.annotation.*;

@RestController
public class DataController {

    @PostMapping("/info")
    public EntityModel<DatabaseOperations> initialize(@RequestBody String id){
        DatabaseOperations operations = new DatabaseOperations(id);
        operations.initializeID();
        return EntityModel.of(operations, linkTo(methodOn(DataController.class).initialize(id)).withSelfRel());
    }

    @PutMapping("/info/{id}")
    public EntityModel<DatabaseOperations> send(@RequestBody UpdateInfo info, @PathVariable String id){
        DatabaseOperations operations = new DatabaseOperations(id, info.getLocation(), info.getAssignedPickupTime());
        // true if it works, false if it didnt.
        operations.sendServerToClient();
        return EntityModel.of(operations, linkTo(methodOn(DataController.class).send(info, id)).withSelfRel());
    }

    @GetMapping("/info/{id}")
    public EntityModel<DatabaseOperations> receive(@PathVariable("id") String id){
        DatabaseOperations operations = new DatabaseOperations(id);
        int ETA = -1;
        ETA = operations.receiveClientToServer();

        while(ETA == -1){   //wait for ETA to be updated, this is necessary as database operations are not thread-safe.
            //do nothing
        }

        // Ensure that the DatabaseOperations constructor and any invoked methods handle all exceptions appropriately.
        return EntityModel.of(operations, linkTo(methodOn(DataController.class).receive(id)).withSelfRel());
    }

    @GetMapping("/test_info/{id}")
    public String test_receive(@PathVariable("id") String id){
        return "Testing: " + id;
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