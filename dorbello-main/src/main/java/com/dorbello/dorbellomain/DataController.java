package com.dorbello.dorbellomain;

import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.web.bind.annotation.*;

import com.dorbello.exceptions.*;
import com.dorbello.models.ETAPage;
import com.dorbello.models.Page;
import com.dorbello.models.RoutingPage;

@RestController
public class DataController {

    /** 
     * Below is the new architecture.
     * POST
     * PUT
     * GET
     */

    @PostMapping("/post")
    public EntityModel<Page> POST(@RequestBody Page page){
        DatabaseOperations operations = new DatabaseOperations(page);
        boolean successful = operations.post();

        if (successful){
            return EntityModel.of(
                page,
                linkTo(methodOn(DataController.class).POST(page)).withSelfRel(),
                linkTo(methodOn(DataController.class).PUT(new ETAPage())).withRel("PUT")
            );
        }

        throw new PostUnsuccessfulException(page.getParent());
    }

    @PutMapping("/put/eta")
    public EntityModel<Page> PUT(@RequestBody ETAPage etaPage){
        DatabaseOperations operations = new DatabaseOperations(etaPage);
        boolean successful = operations.putETA();

        if (successful){
            Page page = operations.getPage();
            return EntityModel.of(
                page,
                linkTo(methodOn(DataController.class).PUT(etaPage)).withSelfRel(),
                linkTo(methodOn(DataController.class).PUT(new RoutingPage())).withRel("PUT ROUTING INFO"),
                linkTo(methodOn(DataController.class).POST(new Page())).withRel("POST")
            );
        }

        throw new PutUnsuccessfulException(etaPage.getParent());
    }

    @PutMapping("/put/routing")
    public EntityModel<Page> PUT(@RequestBody RoutingPage routingPage){
        DatabaseOperations operations = new DatabaseOperations(routingPage);
        boolean successful = operations.putRouting();

        if (successful){
            Page page = operations.getPage();
            return EntityModel.of(
                page,
                linkTo(methodOn(DataController.class).PUT(routingPage)).withSelfRel(),
                linkTo(methodOn(DataController.class).PUT(new ETAPage())).withRel("PUT ETA"),
                linkTo(methodOn(DataController.class).POST(new Page())).withRel("POST")
            );
        }

        throw new PutUnsuccessfulException(routingPage.getParent());
    }

    /**
     * Below is the old architecture, with two tables. It is inefficient and, in some places, bugged out.
     */
    // @PostMapping("/info")
    // public EntityModel<DatabaseOperations> initialize(@RequestBody IDInfo idInfo){
    //     DatabaseOperations operations = new DatabaseOperations(idInfo.getId());
    //     boolean successful = operations.initializeID();
        
    //     if (successful){
    //         return EntityModel.of(
    //             operations, 
    //             linkTo(methodOn(DataController.class).initialize(idInfo)).withSelfRel(),
    //             linkTo(methodOn(DataController.class).initializeAndSend(new UpdateInfo(null, null), idInfo.getId())).withRel("initialize all fields"),
    //             linkTo(methodOn(DataController.class).send(new UpdateInfo(null, null), idInfo.getId())).withRel("update"),
    //             linkTo(methodOn(DataController.class).receive(idInfo.getId())).withRel("get")
    //         );
    //     }

    //     throw new InitializationUnsuccessfulException(idInfo.getId());
    // }

    // @PostMapping("/info/{id}")
    // public EntityModel<DatabaseOperations> initializeAndSend(@RequestBody UpdateInfo info, @PathVariable String id){
    //     DatabaseOperations operations = new DatabaseOperations(id, info.getLocation(), info.getAssignedPickupTime());
    //     // true if it works, false if it didnt.
    //     boolean successful = operations.postServerToClient();

    //     if (successful){
    //         return EntityModel.of(
    //             operations, 
    //             linkTo(methodOn(DataController.class).initializeAndSend(info, id)).withSelfRel(),
    //             linkTo(methodOn(DataController.class).initialize(new IDInfo(id))).withRel("initialize"),
    //             linkTo(methodOn(DataController.class).send(info, id)).withRel("update"),
    //             linkTo(methodOn(DataController.class).receive(id)).withRel("get")
    //         );
    //     }

    //     throw new PostUnsuccessfulException(id);
    // }

    // @PutMapping("/info/{id}")
    // public EntityModel<DatabaseOperations> send(@RequestBody UpdateInfo info, @PathVariable String id){
    //     DatabaseOperations operations = new DatabaseOperations(id, info.getLocation(), info.getAssignedPickupTime());
    //     // true if it works, false if it didnt.
    //     boolean successful = operations.putServerToClient();

    //     if (successful){
    //         return EntityModel.of(
    //             operations, 
    //             linkTo(methodOn(DataController.class).send(info, id)).withSelfRel(),
    //             linkTo(methodOn(DataController.class).initialize(new IDInfo(id))).withRel("initialize"),
    //             linkTo(methodOn(DataController.class).initializeAndSend(info, id)).withRel("initialize all fields"),
    //             linkTo(methodOn(DataController.class).receive(id)).withRel("get")
    //         );
    //     }

    //     throw new PutUnsuccessfulException(id);
    // }

    // @GetMapping("/info/{id}")
    // public EntityModel<DatabaseOperations> receive(@PathVariable("id") String id){
    //     DatabaseOperations operations = new DatabaseOperations(id);

    //     boolean successful = operations.receiveClientToServer();

    //     // Ensure that the DatabaseOperations constructor and any invoked methods handle all exceptions appropriately.
    //     if (successful) {
    //         return EntityModel.of(
    //             operations, 
    //             linkTo(methodOn(DataController.class).receive(id)).withSelfRel(),
    //             linkTo(methodOn(DataController.class).initialize(new IDInfo(id))).withRel("initialize"),
    //             linkTo(methodOn(DataController.class).initializeAndSend(new UpdateInfo(null, null), id)).withRel("initialize all fields"),
    //             linkTo(methodOn(DataController.class).send(new UpdateInfo(null, null), id)).withRel("update")
    //         );
    //     }
        
    //     throw new ParentNotFoundException(id);
    // }

    // @GetMapping("/test_info")
    // public String test_receive(){
    //     DatabaseOperations operations = new DatabaseOperations();
    //     boolean successful = operations.testDatabaseFunctionality();
        
    //     if (successful){
    //         return "API: Functional\nDatabase: Functional\n";
    //     }

    //     return "API: Functional\nDatabase: Nonfunctional\n";
    // }
}

// class IDInfo {
//     private String id;

//     public IDInfo(String id){
//         this.id = id;
//     }

//     public String getId() {
//         return id;
//     }

//     public void setId(String id) {
//         this.id = id;
//     }
// }

// class UpdateInfo {
//     private String location;
//     private String assignedPickupTime;

//     public UpdateInfo(String location, String assignedPickupTime){
//         this.location = location;
//         this.assignedPickupTime = assignedPickupTime;
//     }

//     public String getLocation() {
//         return location;
//     }

//     public void setLocation(String location) {
//         this.location = location;
//     }

//     public String getAssignedPickupTime() {
//         return assignedPickupTime;
//     }

//     public void setAssignedPickupTime(String assignedPickupTime) {
//         this.assignedPickupTime = assignedPickupTime;
//     }
// }