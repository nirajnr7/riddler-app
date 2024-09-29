package com.riddler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.riddler.entity.Riddle;
import com.riddler.service.RiddleService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/riddles")
public class RiddleController {

    @Autowired
    private RiddleService riddleService;

    @GetMapping
    public List<Riddle> getAllRiddles() {
        return riddleService.getAllRiddles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Riddle> getRiddleById(@PathVariable Integer id) {
        Optional<Riddle> riddle = riddleService.getRiddleById(id);
        return riddle.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Riddle createRiddle(@RequestBody Riddle riddle) {
        return riddleService.createRiddle(riddle);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Riddle> updateRiddle(@PathVariable Integer id, @RequestBody Riddle riddleDetails) {
        try {
            Riddle updatedRiddle = riddleService.updateRiddle(id, riddleDetails);
            return ResponseEntity.ok(updatedRiddle);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRiddle(@PathVariable Integer id) {
        riddleService.deleteRiddle(id);
        return ResponseEntity.noContent().build();
    }
}
