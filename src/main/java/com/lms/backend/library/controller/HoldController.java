package com.lms.backend.library.controller;

import com.lms.backend.library.dto.HoldDto;
import com.lms.backend.library.dto.HoldResponseDto;
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

    @PostMapping
    public HoldResponseDto createHold(@RequestBody HoldDto dto) {
        return holdService.createHold(dto);
    }
}
