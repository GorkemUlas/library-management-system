package com.lms.backend.library.controller;

import com.lms.backend.library.dto.HoldDto;
import com.lms.backend.library.entity.Hold;
import com.lms.backend.library.service.HoldService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/holds")
public class HoldController {
    private final HoldService holdService;

    public HoldController(HoldService holdService) {
        this.holdService = holdService;
    }

    /*@PostMapping
    public Hold createHold(@RequestBody Hold hold) {
        return holdService.addHold(hold);
    }*/

    @PostMapping
    public ResponseEntity<Hold> createHold(@Valid @RequestBody HoldDto dto) {
        Hold created = holdService.createHold(dto);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    public Hold getHold(@PathVariable Long id) {
        return holdService.getHold(id);
    }

    @GetMapping
    public List<Hold> getAllHolds() {
        return holdService.getAllHolds();
    }

    @DeleteMapping("/{id}")
    public void deleteHold(@PathVariable Long id) {
        holdService.deleteHold(id);
    }
}