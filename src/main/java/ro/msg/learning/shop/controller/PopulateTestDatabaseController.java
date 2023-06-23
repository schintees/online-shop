package ro.msg.learning.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.msg.learning.shop.service.PopulateTestDatabaseService;

@Profile("test")
@RequestMapping(value = "/test-database",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class PopulateTestDatabaseController {

    @Autowired
    private PopulateTestDatabaseService populateTestDatabaseService;

    @PostMapping("/populate")
    public ResponseEntity<String> populate() {
        populateTestDatabaseService.populate();
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/clear")
    public ResponseEntity<String> clear() {
        populateTestDatabaseService.clear();
        return ResponseEntity.ok().build();
    }

}
